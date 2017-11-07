package logic;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Controls game logic
 */
public class GameModel
{
	/** Listeners that will update when the modal changes */
	private ArrayList<ChangeListener> listeners;

	/** Stores how many stones are in each hole */
	private ArrayList<Integer> holes;

	/** Whos turn it is; player 0 or 1. */
	private int turn = 0;

	private final int STATE_NEW_TURN = 0;
	private final int STATE_CONTINUE_TURN = 1;
	private final int STATE_GAME_OVER = 2;

	private int gameState = 0;
	
	public GameModel() {
		listeners = new ArrayList<>();
		holes = new ArrayList<>(Collections.nCopies(14, 4));
		holes.set(6, 0);
		holes.set(13, 0);
	}

	/**
	 * Called by hole when it's clicked on
	 * @param holeId the hole that was clicked
	 */
	public void holeClicked(int holeId) {

		// Make sure player clicked on their own hole
		if(getHoleOwner(holeId) == getCurrentTurn()) {

			boolean keepGoing = true;
			int position = holeId;

			while(keepGoing) {
				// Pick up pieces from hole, put in hand
				int hand = grabHole(position);

				// Take pieces from hand and place on holes
				while (hand > 0) {
					position = (position + 1) % 14; // Go to next hole

					// Only put stone down if we are not on the enemy store
					if (!isOpponentsStore(position)) {
						incrementHole(position);
						hand--;
						System.out.println("Placing stone, holding " + Integer.toString(hand) + " more");
					}
					else {
						System.out.println("Skipping enemy store");
					}

					//update();

					try {Thread.sleep(100);} catch (Exception e) {}
				}

				// Check what to do after hand runs out
				// Landed on friendly store
				if (isFriendlyStore(position)) {
					System.out.println("Landed on friendly store, take another turn");
					setGameState(STATE_CONTINUE_TURN);
					keepGoing = false;
				}
				// Landed on hole with stones in it
				else if (holes.get(position) > 1) {
					System.out.println("Landed on non-empty hole, continuing");
					keepGoing = true;
				}
				// Landed on empty hole on own side
				else if (getHoleOwner(position) == getCurrentTurn()) {
					System.out.println("Landed on own empty hole, capturing opposite");
					captureHole(position);
					captureHole(getOppositeHole(position));
					keepGoing = false;
					nextTurn();
				}
				// Landed on empty hole on enemy side
				else {
					keepGoing = false;
					nextTurn();
				}
			}
		}
		update();
	}
	
	/**
	 * Get how many stones are in a hole
	 * @param index Index of hole
	 * @return number of stones
	 */
	public int getCountOfHole(int index) {
		return holes.get(index);
	}

	/**
	 * Get who's side of the board a hole is on
	 * @param index the hole
	 * @return player 0 or 1
	 */
	private int getHoleOwner(int index) {
		int fixedHole = index % 14;
		return (fixedHole >= 7) ? 1 : 0;
	}

	/**
	 * Check if a hole/store is the opponents store
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
	 * @param index starting hole
	 * @return hole on other side
	 */
	private int getOppositeHole(int index) {
		index = index % 14;

		// Stores don't have an opposite
		if(index == 6 || index == 13) {
			return -1;
		}

		return 12 - index;
	}

	/**
	 * Get the pieces in a hole
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
	 * Set state (used for displaying messages only)
	 * @param newState new state
	 */
	private void setGameState(int newState) {
		gameState = newState;
	}

	public String getStateMessage() {
		switch(gameState) {
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
	}
	
	/**
	 * Attach a new listener
	 * @param l
	 */
	public void attachListener(ChangeListener l) {
		listeners.add(l);
	}
	
	/**
	 * This needs to be called to update the attached listener
	 */
	private void update() {
		for(ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
}


