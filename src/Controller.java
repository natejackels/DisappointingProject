/**
 *	Class: Control
 *	@author Robin McNally
 *	Description: This class handles the communication between the four other parts of the program
 */
/*
	TODO -> Implement main
	TODO -> Keep track of active program
	TODO -> keep track of the settings of the system
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
	 *	@param 	gPack 	Takes a gui packet and decides what class to send it to
	 *  @return	 		a string or set of strings to print out to the user
	 */
	public String[] sendPacket(GUIPacket gPack){
		String toDecode = gPack.getMessage();

		//There will be decision making code here eventually
		RobotPacket p = funnel.decodeVLC(toDecode);
		if (p==null) {return null;}
		p = robot.sendPacket(p);
		switch (p.getEvent()){
			case "BadPacket":
			break;
			case "FailedOpen":
			break;
			case "Display":
				String[] DisplayStrings = p.getInfo();
				TextToSpeechPacket Disp = new TextToSpeechPacket(DisplayStrings[0]);
				tts.send(Disp);
				return DisplayStrings;
			case "BadGetValue":
			break;
			case "GoodCommand":
				return null;
			case "CommandFailed":
			break;
			default:
			break;
		}
		return null;
		
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
