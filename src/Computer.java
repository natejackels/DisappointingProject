import java.io.IOException;


public class Computer extends Application {
	Keyboard keyboard;
	
	public Computer(Keyboard keyboard) {
		super("Computer");
		this.keyboard = keyboard;
	}

	@Override
	public RobotPacket interpret(RobotPacket e) {
		return computer(e.getEvent(), e.getInfo());
	}

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
		default:
			String[] info = {"InvalidCommand", "Computer"};
			return new RobotPacket("Robot", "BadPacket", info);
		}
	}
	
	private RobotPacket sucessful(String cmd, String[] params){
		String[] infoResult = new String[2 + params.length];
		infoResult[0] = "Computer";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "GoodCommand", infoResult);
	}
	
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
