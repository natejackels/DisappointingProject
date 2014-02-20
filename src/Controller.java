/**
 *	Class: Control
 *	@author Robin McNally
 *	Description: This class handles the communication between the four other parts of the program
 */
/*
	TODO -> decide on args for main
	TODO -> Implement main
	TODO -> Implement sendPacket(guiPacket)
	TODO -> Implement sendPacket(RobotPacket)
	TODO -> Implement sendPacket(ttsPacket)
	TODO -> Implement sendPacket(sttPacket)
*/
public class Controller {
	public TextToSpeech tts;
	public SpeechToText stt;
	public GUI gui;
	public Robot robot;
	public static void main(String[] args) {
		//Create the wrapper (Main program) object
		Controller controller = new Controller();
		
		//Start the code
		controller.initialize();
	}
	public void initialize() {
		tts = new TextToSpeech(this);
		stt = new SpeechToText(this);
		stt.startRecording();
		gui = new GUI(this);

	}

	/**
	 *	Method: sendPacket(guiPacket)
	 *	@author Robin McNally
	 *	@param gPack Takes a gui packet and decides what class to send it to
	 */
	public GUIPacket sendPacket(GUIPacket gPack){
		String toDecode = gPack.getMessage();

		//Temporary working code
		switch (toDecode){
			case "open vlc":
				RobotPacket openCmd = new RobotPacket("VLC", "Open", null);
				robot.sendPacket(openCmd);
			break;
			case "close vlc":
				RobotPacket closeCmd = new RobotPacket("VLC", "Close", null);
				robot.sendPacket(closeCmd);
			break;
			case "play":
				RobotPacket playCmd = new RobotPacket("VLC", "Play", null);
				robot.sendPacket(playCmd);
			break;
			default:
				GUIPacket err = new GUIPacket();
				
				//This may not work yet
				//gui.sendPacket(err);
			break;
		}
	}

	/**
	 *	Method: sendPacket(RobotPacket)
	 *	@author Robin McNally
	 *	@param gPack Takes a robot packet and decides what class to send it to
	 */
	/*
		TODO -> Application invalid
		TODO -> Application commands invalid
		TODO -> Application open fail
		TODO -> Display to user
		TODO -> Get Folder location
		TODO -> Bad Get Value
	*/
	public RobotPacket sendPacket(RobotPacket rPack){
		//Not much happens here
		//Error handling will pick up after demo
	}

	/**
	 *	Method: sendPacket(ttsPacket)
	 *	@author Robin McNally
	 *	@param gPack Takes a tts packet and decides what class to send it to
	 */
	public void sendPacket(TextToSpeechPacket tsPack){
		//Not much will happen here
	}

	/**
	 *	Method: sendPacket(sttPacket)
	 *	@author Robin McNally
	 *	@param gPack Takes a stt packet and decides what class to send it to
	 */
	public void sendPacket(STTPacket stPack){
		String toDecode = gPack.getMessage();
		switch (toDecode){
			case "open vlc":
				RobotPacket openCmd = new RobotPacket("VLC", "Open", null);
				robot.sendPacket(openCmd);
			break;
			case "close vlc":
				RobotPacket closeCmd = new RobotPacket("VLC", "Close", null);
				robot.sendPacket(closeCmd);
			break;
			case "play":
				RobotPacket playCmd = new RobotPacket("VLC", "Play", null);
				robot.sendPacket(playCmd);
			break;
			default:
				STTPacket err = new STTPacket();
				
				//This may not work yet
				//gui.sendPacket(err);
			break;
		}
	}
}
