
public class Controller {
	public TextToSpeech tts;
	public SpeechToText stt;
	public GUI gui;
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
}
