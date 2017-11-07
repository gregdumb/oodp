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

		if(getHoleOwner(holeId) == getCurrentTurn()) {

			boolean keepGoing = true;
			int position = holeId;

			while(keepGoing) {
				int hand = holes.get(position);
				holes.set(position, 0);

				// Place pieces in hand
				while (hand > 0) {
					position = (position + 1) % 14; // Go to next hole

					if (!isOpponentsStore(position)) {
						incrementHole(position);
						hand--;
						System.out.println("Placing stone, holding " + Integer.toString(hand) + " more");
					}
					else {
						System.out.println("Skipping enemy store");
					}

					update();
				}

				// Check what to do after hand runs out
				if (isFriendlyStore(position)) {
					System.out.println("Landed on friendly store, take another turn");
					keepGoing = false;
				}
				else if (holes.get(position) > 1) {
					System.out.println("Landed on non-empty hole, continuing");
				}
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
	 * Moves to the next turn
	 */
	private void nextTurn() {
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


