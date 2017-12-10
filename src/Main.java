import logic.GameModel;
import ui.GameFrame;

/**
 * @author Greg
 *
 * Main class/method for mancala game
 */
public class Main
{
	public static void main(String[] args)
	{
		System.out.println("Starting game");
		
		GameModel model = new GameModel();
		GameFrame view = new GameFrame(model);
	}
}
