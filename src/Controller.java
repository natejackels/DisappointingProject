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
	private String lastProgram;
	public static void main(String[] args) {
		//Create the wrapper (Main program) object
		Controller controller = new Controller();
		
		//Start the code
		controller.initialize();
	}
	public void initialize() {
		tts = new TextToSpeech(this);
		stt = new SpeechToText(this);
		gui = new GUI(this);
	}

	/**
	 *	Method: sendPacket(guiPacket)
	 *	@author Robin McNally
	 *	@param gPack Takes a gui packet and decides what class to send it to
	 */
	public void sendPacket(GUIPacket gPack){
		//Take apart packet
		//Derive meaning
		//Forward to one of the other three
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
		switch (rPack.getApplication()) {
			case "Robot":
				switch (rPack.getEvent()){
					case "BadPacket":
						if (rPack.getInfo() == null){

						} else {
							
						}
					break;
					case "Display":
					break;
					case "NeedLocation":
					break;
					case "BadGetValue":
					break;
					default:
					break;
				}
				break;
			default:
			break;
		}
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
	public void sendPacket(SpeechToTextPacket stPack){
		//Pull apart packet
		//Send string to Funnel
		//Send result to Robot/whatever
	}
}
