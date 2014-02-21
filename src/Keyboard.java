import java.awt.AWTException;

/**
 * Class: Keyboard
 * @author Nathan Jackels
 * Description: The class that runs the virtual keyboard.
 */
public class Keyboard {
	java.awt.Robot keyboard = null;
	
	/**
	 * Method: Keyboard()
	 * @author Nathan Jackels
	 * Description: The constructor of the class.
	 */
	public Keyboard(){
		try {
			this.keyboard = new java.awt.Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
