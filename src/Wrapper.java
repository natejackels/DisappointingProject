
public class Wrapper {
	public TextToSpeech tts;
	public SpeechToText stt;
	public PacketController packetController;
	public GUI gui;
	public static void main(String[] args) {
		//Create the wrapper (Main program) object
		Wrapper wrapper = new Wrapper();
		
		//Start the code
		wrapper.initialize();
	}
	public void initialize() {
		tts = new TextToSpeech(this);
		stt = new SpeechToText(this);
		packetController = new PacketController(this);
		gui = new GUI(this);
	}
}
