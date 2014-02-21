
/**
 * Class: Application
 * @author Nathan Jackels
 * Description: An abstract class to make application implementations easily searchable
 */
public abstract class Application {
	private String name = "";
	private int maxInstance = 0;	//[<-0:oo], [>0:#]
	
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
	 * Method: Application(String name, int maxInstance)
	 * @author Nathan Jackels
	 * @param name The name of the application
	 * @param maxInstance The maximum number of instances of this program
	 * Description: The super constructor for all implemented applications
	 */
	public Application(String name, int maxInstance){
		this.name = name;
		this.maxInstance = maxInstance;
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
}
