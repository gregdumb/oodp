package logic;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Controls game logic
 */
public class GameModel {
	/**
	 * Listeners that will update when the modal changes
	 */
	private ArrayList<ChangeListener> listeners;

	/**
	 * Stores how many stones are in each hole
	 */
	private ArrayList<Integer> holes;
	
	private ArrayList<Integer> undoState;
	private int undoTurn = 0;

	/**
	 * Whos turn it is; player 0 or 1.
	 */
	private int turn = -1;

	/**
	 * The number of pieces we are currently holding in our hand
	 */
	private int hand = 0;
	private int position;

	private final int ANIMATION_DELAY = 250; //ms

	private final int STATE_NEW_TURN = 0;
	private final int STATE_CONTINUE_TURN = 1;
	private final int STATE_GAME_OVER = 2;

	private int gameState = 0;

	public GameModel() {
		listeners = new ArrayList<>();
		holes = new ArrayList<>(Collections.nCopies(14, 4));
		undoState = new ArrayList<>(Collections.nCopies(14, 0));
		holes.set(6, 0);
		holes.set(13, 0);
	}

	public void initialize(int defaultEachHole) {

		if(defaultEachHole < 1) return;

		Collections.fill(holes, defaultEachHole);
		holes.set(6, 0);
		holes.set(13, 0);
		
		turn = 0;
		undoTurn = -1;

		update();
	}

	/**
	 * Called by hole when it's clicked on
	 *
	 * @param holeId the hole that was clicked
	 */
	public void holeClicked(int holeId) {

		// Make sure player clicked on their own hole
		if (getHoleOwner(holeId) == getCurrentTurn() && holes.get(holeId) > 0) {

			position = holeId;

			takeTurn();
		}
		update();
	}

	/**
	 * This starts the chain of picking up the pieces
	 * in a hole and placing them in the subsequent holes.
	 */
	private void takeTurn() {

		saveUndoState();
		
		// Pick up pieces from hole, put in hand
		hand = grabHole(position);

		// Take pieces from hand and place on holes
		placeFromHand();
	}

	/**
	 * This recursively calls itself to take pieces out
	 * of the hand until the hand is empty
	 * @return
	 */
	private int placeFromHand() {
		// Go to next hole
		position = (position + 1) % 14;

		// Make sure we skip the enemy store
		if (!isOpponentsStore(position)) {
			// Put a piece down if we are NOT on the enemy store
			incrementHole(position);
			hand--;
			System.out.println("Landed on hole: " + Integer.toString(position));
			System.out.println("  Placing stone, holding " + Integer.toString(hand) + " more");
		} else {
			System.out.println("  Skipping enemy store");
		}

		// Update the GUI
		update();

		// If there are more pieces, call ourselves; otherwise end the chain.
		if(hand > 0) {
			// Create task for next placement
			TimerTask nextMoveTask = new TimerTask() {
				@Override
				public void run() {
					placeFromHand();
				}
			};

			// Start the timer to show the next move
			Timer newTimer = new Timer();
			newTimer.schedule(nextMoveTask, ANIMATION_DELAY);
		}
		else {
			endTurn();
		}

		return 1;
	}

	/**
	 * When the hand runs out of pieces, this is called to
	 * determine what to do.
	 */
	private void endTurn() {

		boolean keepGoing = false;

		// Check what to do after hand runs out
		// If a side is empty
		if (checkGameOver()) {
			keepGoing = false;
		}
		// If we landed on friendly store:
		else if (isFriendlyStore(position)) {
			System.out.println("Landed on friendly store, take another turn");
			setGameState(STATE_CONTINUE_TURN);
			keepGoing = false;
		}
		// If we landed on hole with stones in it:
		else if (holes.get(position) > 1) {
			System.out.println("Landed on non-empty hole, continuing");
			keepGoing = true;
		}
		// If we landed on empty hole on own side:
		else if (getHoleOwner(position) == getCurrentTurn()) {
			System.out.println("Landed on own empty hole, capturing opposite");
			captureHole(position);
			captureHole(getOppositeHole(position));
			keepGoing = false;
			nextTurn();
		}
		// If we landed on empty hole on enemy side:
		else {
			keepGoing = false;
			nextTurn();
		}

		if(keepGoing) {
			takeTurn();
		}

		update();
	}

	/**
	 * Get how many stones are in a hole
	 *
	 * @param index Index of hole
	 * @return number of stones
	 */
	public int getCountOfHole(int index) {
		return holes.get(index);
	}

	/**
	 * Get who's side of the board a hole is on
	 *
	 * @param index the hole
	 * @return player 0 or 1
	 */
	private int getHoleOwner(int index) {
		int fixedHole = index % 14;
		return (fixedHole >= 7) ? 1 : 0;
	}

	/**
	 * Check if a hole/store is the opponents store
	 *
	 * @param index index to check
	 * @return true if is enemy store, false if is not
	 */
	private boolean isOpponentsStore(int index) {
		int opponentStore = (turn == 0) ? 13 : 6;
		return (index == opponentStore);
	}

	private boolean isFriendlyStore(int index) {
		int friendlyStore = (turn == 0) ? 6 : 13;
		return (index == friendlyStore);
	}

	/**
	 * Find the hole directly on the other side of the board
	 *
	 * @param index starting hole
	 * @return hole on other side
	 */
	private int getOppositeHole(int index) {
		index = index % 14;

		// Stores don't have an opposite
		if (index == 6 || index == 13) {
			return -1;
		}

		return 12 - index;
	}

	/**
	 * Get the pieces in a hole
	 *
	 * @param index hole to grab pieces from
	 * @return the pieces
	 */
	private int grabHole(int index) {
		int grab = holes.get(index);
		holes.set(index, 0);
		return grab;
	}

	/**
	 * Takes pieces out of a hole and places them in own store
	 *
	 * @param index hole to get
	 */
	private void captureHole(int index) {
		int grab = grabHole(index);
		int storeIndex = (turn == 0) ? 6 : 13;
		int currentStore = holes.get(storeIndex);
		holes.set(storeIndex, currentStore + grab);
	}

	/**
	 * FOR DEBUGGING, just adds to a hole
	 *
	 * @param index
	 */
	private void incrementHole(int index) {
		int newVal = holes.get(index) + 1;
		holes.set(index, newVal);
	}

	public int getCurrentTurn() {
		return turn;
	}

	/**
	 * Checks if a side is empty
	 * @return true if a side is empty
	 */
	private boolean checkGameOver() {
		boolean empty0 = true;
		boolean empty1 = true;

		for(int i = 0; i < 6; i++) {
			empty0 = (empty0) && (holes.get(i) == 0);
		}

		for(int i = 7; i < 13; i++) {
			empty1 = (empty1) && (holes.get(i) == 0);
		}

		// If game is over, Grab remaining pieces and put them in store

		if(empty0 || empty1) {
			int sum0 = 0;
			int sum1 = 0;

			for (int i = 0; i <= 5; i++) {
				sum0 += grabHole(i);
			}

			for (int i = 7; i <= 12; i++) {
				sum1 += grabHole(i);
			}

			// Put pieces in store
			holes.set(6, holes.get(6) + sum0);
			holes.set(13, holes.get(13) + sum1);

			if (holes.get(13) == holes.get(6)) {
				System.out.println("It's a tie!");
			} else if (holes.get(13) > holes.get(6)) {
				System.out.println("Player 2 won!!");
			} else {
				System.out.println("Player 1 won!");
			}
		}

		return empty0 || empty1;
	}

	/**
	 * Set state (used for displaying messages only)
	 *
	 * @param newState new state
	 */
	private void setGameState(int newState) {
		gameState = newState;
	}

	public String getStateMessage() {
		switch (gameState) {
			case STATE_NEW_TURN:
				return "Select a hole to pick up the pieces";

			case STATE_CONTINUE_TURN:
				return "You landed on your own store; continue your turn";

			case STATE_GAME_OVER:
				return "Game over!";

			default:
				return "Idk what's going on";
		}
	}

	/**
	 * Moves to the next turn
	 */
	private void nextTurn() {
		setGameState(STATE_NEW_TURN);
		turn = (turn == 0) ? 1 : 0;

		checkGameOver();
	}
	
	/**
	 * Saves state of the holes & turn
	 */
	private void saveUndoState() {
		
		undoTurn = turn;
		
		for(int i = 0; i < holes.size(); i++) {
			undoState.set(i, holes.get(i));
		}
	}
	
	/**
	 * Undoes last move
	 */
	public void undo() {
		
		if(undoTurn == -1) {
			System.out.println("Cannot undo");
			return;
		}
		
		System.out.println("Undoing");
		
		for(int i = 0; i < holes.size(); i++) {
			holes.set(i, undoState.get(i));
		}
		
		turn = undoTurn;
		undoTurn = -1;
		
		update();
	}

	/**
	 * Attach a new listener
	 *
	 * @param l
	 */
	public void attachListener(ChangeListener l) {
		listeners.add(l);
	}

	/**
	 * This needs to be called to update the attached listener
	 */
	private void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

}


