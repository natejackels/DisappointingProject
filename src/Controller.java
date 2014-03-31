/**
 *	Class: Controller
 *	@author Robin McNally
 *	Description: This class handles the communication between the four other parts of the program
 */
public class Controller {
	public TextToSpeech tts;
	public SpeechToText stt;
	public GUI gui;
	public Robot robot;
	public Funnel funnel;
        // this swaps between the old and new UI implementation.
        private boolean newui = false;//true;
        private UX ux;
	
	public static void main(String[] args) {
		//Create the wrapper (Main program) object
		Controller controller = new Controller();
		
		//Start the code
		controller.initialize();
	}
	
	public void initialize() {
		tts = new TextToSpeech(this);
		stt = new SpeechToText(this,true);
		if (!newui){
                gui = new GUI(this);
                } else {
                ux = new UX();
                stt.startRecording();
                }
		robot = new Robot(this);
		funnel = new Funnel(this);
	}

	/**
	 *	Method: sendPacket(guiPacket)
	 *	@author Robin McNally
	 *	@param 	gPack 	Takes a gui packet and decides what class to send it to
	 *  @return	 		a string or set of strings to print out to the user
	 */
	public GUIPacket sendPacket(GUIPacket gPack){
		String toDecode = gPack.getMessage();

		//There will be decision making code here eventually
		RobotPacket p = funnel.decodeVLC(toDecode);
		if (p==null) {return null;}
		p = robot.sendPacket(p);
		System.out.println("Sent");
		switch (p.getEvent()){
			case "BadPacket":
				if (p.getInfo() == null){
					TextToSpeechPacket Disp = new TextToSpeechPacket("I'm sorry we don't support a program that does that job");
					GUIPacket GUIDisplay = new GUIPacket("NULL");
					tts.send(Disp);
					return GUIDisplay;
				} else {
					//More complex improper command to come during the second sprint
					TextToSpeechPacket Disp = new TextToSpeechPacket("I didn't understand you");
					GUIPacket GUIDisplay = new GUIPacket("NULL");
					tts.send(Disp);
					return GUIDisplay;
				}
			case "FailedOpen":
					TextToSpeechPacket Disp = new TextToSpeechPacket("the program did not open");
					GUIPacket GUIDisplay = new GUIPacket("NULL");
					tts.send(Disp);
					return GUIDisplay;
			case "Display":
				String[] DisplayStrings = p.getInfo();
				String returnString = "";
				if (p != null){
					for (int i = 0; i < DisplayStrings.length; i++){
						returnString += DisplayStrings[i];
					}
				}
				return null;
			case "BadGetValue":
					//Implement later
				return null;
			case "GoodCommand":
				return null;
			case "CommandFailed":
				return null;
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
	public void sendPacket(RobotPacket rPack){
	}

	/**
	 *	Method: sendPacket(ttsPacket)
	 *	@author Robin McNally
	 *	@param gPack Takes a tts packet and decides what class to send it to
	 */
	public void sendPacket(TextToSpeechPacket tsPack){
	}

	/**
	 *	Method: sendPacket(sttPacket)
	 *	@author Robin McNally
	 *	@param gPack Takes a stt packet and decides what class to send it to
	 */
	public void sendPacket(STTPacket stPack){
		String toDecode = stPack.getText();
		RobotPacket p = funnel.decodeVLC(toDecode);
		if (!newui){
			gui.sendDebugText(toDecode);
		} else {
			ux.setInputText(toDecode);
		}
		if (p==null) {return;}
		TextToSpeechPacket Disp;
		p = robot.sendPacket(p);
		switch (p.getEvent()){
			case "BadPacket":
				if (p.getInfo() == null){
					Disp = new TextToSpeechPacket("I'm sorry we don't support a program that does that job");
					tts.send(Disp);
					return;
				} else {
					//More complex improper command to come during the second sprint
					Disp = new TextToSpeechPacket("I didn't understand you");
					tts.send(Disp);
					return GUIDisplay;
				}
			case "FailedOpen":
					Disp = new TextToSpeechPacket("the program did not open");
					tts.send(Disp);
					return GUIDisplay;
			case "Display":
			default:
			break;
		}
	}
}
