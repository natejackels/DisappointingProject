import com.sun.speech.freetts.*;
/*If the above line is giving you an error, that is because
 * you need to add "lib/freetts.jar" to your classpath
 */
public class TextToSpeech {
	public Controller parent;
	private Voice voice;
	
	public TextToSpeech(Controller w) {
		parent = w;
		//Load up the freeTTS voice manager
		VoiceManager voiceManager = VoiceManager.getInstance();
		
		Voice[] voices = voiceManager.getVoices();

		//I like kevin16, so we will use that for right now
		for (int i = 0; i < voices.length; i++) {
			if (voices[i].getName().equals("kevin16")) {
				voice = voices[i];
				
				//Preapare the voice
				voice.allocate();
			}
		}
		
		say("HUG");
	}
	
	public void send(TextToSpeechPacket packet) {
		//Say the message
		say(packet.getMessage());
	}
	
	public void say(String s) {
		voice.speak(s);
	}
	
}
