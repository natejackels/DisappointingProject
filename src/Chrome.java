import java.io.IOException;

/**
 * Class: Chrome
 * @author Nathan Jackels
 * Description: The class that implements controls for Chrome
 *
 */
public class Chrome extends Application {

	/**
	 * Method: Chrome(Keyboard k)
	 * @author Nathan Jackels
	 * @param k The reference to the keyboard class
	 * Description: The constructor for Chrome
	 */
	public Chrome(Keyboard k) {
		super("Chrome");
		this.keyboard = k;
	}

	/**
	 * Method: interpret(RobotPacket e)
	 * @author Nathan Jackels
	 * @param e The packet that will be interpreted by Chrome
	 * @return The result of interpreting e
	 * @see TSP/Control/Robot_Packet_Design
	 */
	@Override
	public RobotPacket interpret(RobotPacket e) {
		switch(e.getEvent()){
		case("History"):
			return history(e.getEvent(), e.getInfo());
		default:
			String[] info = {"Invalid Command", "Chrome"};
			return new RobotPacket("Robot", "BadPacket", info);
		}
	}

	/**
	 * Method: history(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args The string array of length 1 that will contain the string to be searched for within history
	 * @return Either a success or a failure packet
	 * Description: A method that will search Chrome's history for a string
	 */
	private RobotPacket history(String cmd, String[] args) {
		if((args == null) || (args.length == 0)){
			return this.failed(cmd, args);
		}
		String history = "chrome://history/";
		ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", history);
		try{
			pb.start();
		} catch (IOException e){
			return this.failed(cmd, args);
		}
		//Type string.
		keyboard.typeString(args[0]);
		keyboard.pressEnter();
		return this.sucessful(cmd, args);
	}

	/**
	 * Method: sucessful(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param params The parameters to be returned within the response packet.
	 * @return The response packet that corresponds to cmd and params.
	 * Description: A helper method to get a response packet that corresponds to cmd and params in the case of a sucessful execution.
	 */
	private RobotPacket sucessful(String cmd, String[] params){
		if(params == null){
			params = new String[0];
		}
		String[] infoResult = new String[2 + params.length];
		infoResult[0] = "Computer";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "GoodCommand", infoResult);
	}

	/**
	 * Method: failed(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param params The parameters to be returned within the response packet.
	 * @return The response packet that corresponds to cmd and params.
	 * Description: A helper method to get a response packet that corresponds to cmd and params in the case of a failed execution.
	 */
	private RobotPacket failed(String cmd, String[] params){
		if(params == null){
			params = new String[0];
		}
		String[] infoResult = new String[2 + params.length];
		infoResult[0] = "Computer";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "CommandFailed", infoResult);
	}
}
