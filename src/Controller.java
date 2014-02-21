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
	public Funnel funnel;
	
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
		robot = new Robot(this);
		funnel = new Funnel(this);
	}

	/**
	 *	Method: sendPacket(guiPacket)
	 *	@author Robin McNally
	 *	@param gPack Takes a gui packet and decides what class to send it to
	 */
	public void sendPacket(GUIPacket gPack){
		String toDecode = gPack.getMessage();
		RobotPacket p = funnel.decodeVLC(toDecode);
		if (p==null) {return;}
		robot.sendPacket(p);
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
	public void sendPacket(RobotPacket rPack){
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
		String toDecode = stPack.getText();
		RobotPacket p = funnel.decodeVLC(toDecode);
		if (p==null) {return;}
		robot.sendPacket(p);
	}
}
