import java.io.IOException;

/**
 * Class: Computer
 * @author Nathan Jackels
 * The class that runs the vbs scripts in /RobotScripts/
 */
public class Computer extends Application {
	
	/**
	 * Method: Computer(Keyboard keyboard)
	 * @author Nathan Jackels
	 * @param keyboard The reference to the keyboard used by Robot.
	 * Description: The constructor for the class.
	 */
	public Computer(Keyboard keyboard) {
		super("Computer");
		this.keyboard = keyboard;
	}

	/**
	 * Method: interpret(RobotPacket e)
	 * @author Nathan Jackels
	 * @param e The robotpacket to be interpreted by computer.
	 * @return The response to the packet
	 * @see TSP/Control/Robot_Packet_Design
	 * Description: The method used by Robot to send commands to the computer class
	 */
	@Override
	public RobotPacket interpret(RobotPacket e) {
		return computer(e.getEvent(), e.getInfo());
	}

	/**
	 * Method: computer(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command for Computer
	 * @param params The params associated with the command
	 * @return The response to the command
	 * Description: The method that executes the commands.
	 */
	private RobotPacket computer(String cmd, String[] params) {
		switch(cmd){
		case("VolumeUp"):
			try {
				Runtime.getRuntime().exec("cmd.exe /C start ./RobotScripts/volUp.vbs");
				return sucessful(cmd, params);
			} catch (IOException e) {
				return failed(cmd, params);
			}
		case("VolumeDown"):
			try {
				Runtime.getRuntime().exec("cmd.exe /C start ./RobotScripts/volDown.vbs");
				return sucessful(cmd, params);
			} catch (IOException e) {
				return failed(cmd, params);
			}
		case("Mute"):
			try {
				Runtime.getRuntime().exec("cmd.exe /C start ./RobotScripts/mute.vbs");
				return sucessful(cmd, params);
			} catch (IOException e) {
				return failed(cmd, params);
			}
		case("SelectAll"):
			keyboard.releaseAll();
			keyboard.ctrlCMD(java.awt.event.KeyEvent.VK_A);
			return sucessful(cmd, params);
		default:
			String[] info = {"InvalidCommand", "Computer"};
			return new RobotPacket("Robot", "BadPacket", info);
		}
	}
	
	/**
	 * Method: successful(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command parameter
	 * @param params The array of parameters associated with cmd
	 * @return A generally succesful packet
	 * Description: A helper method to create success packets.
	 */
	private RobotPacket sucessful(String cmd, String[] params){
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
	 * @param cmd The command parameter
	 * @param params The array of parameters associated with cmd
	 * @return A failure packet
	 * Description: A helper method to create failure packets.
	 */
	private RobotPacket failed(String cmd, String[] params){
		String[] infoResult = new String[2 + params.length];
		infoResult[0] = "Computer";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "CommandFailed", infoResult);
	}
}
