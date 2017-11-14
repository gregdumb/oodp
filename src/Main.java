import logic.GameModel;
import ui.GameFrame;

/**
 * Created by Greg on 10/19/2017.
 */
public class Main
{
	/**
	 * Main method that runs the Mancala game
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("Starting game");
		
		GameModel model = new GameModel();
		GameFrame view = new GameFrame(model);

	}
}
