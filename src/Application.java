
/**
 * Class: Application
 * @author Nathan Jackels
 * Description: An abstract class to make application implementations easily searchable
 */
public abstract class Application {
	private String name = "";
	protected Keyboard keyboard;
	
	/**
	 * Method: Application(String name)
	 * @author Nathan Jackels
	 * @param name The name of the application
	 * Description: The super constructor for all implemented applications
	 */
	public Application(String name){
		this.name = name;
	}
	
	/**
	 * Method: getName()
	 * @author Nathan Jackels
	 * @return The name of the application
	 * Description: The getter method for the name of the implemented application, used for searching through implemented applications
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Method: interpret(RobotPacket e)
	 * @author Nathan Jackels
	 * @param e The RobotPacket that will be interpretted by the program
	 * @return The resulting RobotPacket of the interpretation [usually the Display command]
	 * Description: An abstract method to ensure all applications can interpret RobotPackets without casting.
	 */
	public abstract RobotPacket interpret(RobotPacket e);
	
	/**
	 * Method: setKeyboard(Keyboard k)
	 * @author Nathan Jackels
	 * @param k The keyboard for the application
	 * Description: A method to set the keyboard for the application
	 */
	public void setKeyboard(Keyboard k){
		this.keyboard = k;
	}
}
