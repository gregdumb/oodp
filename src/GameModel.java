import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Controls game logic
 */
public class GameModel
{
	//private ArrayList holes;
	
	private ArrayList<ChangeListener> listeners;
	
	private ArrayList<Integer> holes;
	
	public GameModel() {
		listeners = new ArrayList<>();
		holes = new ArrayList<>(Collections.nCopies(12, 4));
		holes.set(3, 1);
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
	 * FOR DEBUGGING, just adds to a hole
	 * @param index
	 */
	public void incrementHole(int index) {
		int newVal = holes.get(index) + 1;
		holes.set(index, newVal);
		update();
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


