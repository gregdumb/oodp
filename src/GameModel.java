import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Greg on 10/31/2017.
 */
public class GameModel
{
	//private ArrayList holes;
	
	public ArrayList<Integer> holes;
	
	public GameModel() {
		holes = new ArrayList<Integer>(Collections.nCopies(12, 4));
		holes.set(3, 1);
	}
	
	class hole {
		public int numStones = 0;
	}
}


