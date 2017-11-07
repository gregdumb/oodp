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
		holes.set(3, 1);
	}

	/**
	 * Called by hole when it's clicked on
	 * @param holeId the hole that was clicked
	 */
	public void holeClicked(int holeId) {

		if(getHoleOwner(holeId) == getCurrentTurn()) {
			incrementHole(holeId);
			nextTurn();
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


