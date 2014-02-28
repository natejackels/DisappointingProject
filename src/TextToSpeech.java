import marytts.client.MaryClient;
import marytts.client.MaryGUIClient;
import marytts.server.Mary;
import marytts.server.MaryServer;
import marytts.util.http.Address;

import com.sun.speech.freetts.*;
/*If the above line is giving you an error, that is because
 * you need to add "lib/freetts.jar" to your classpath
 */
public class TextToSpeech {
	public Controller parent;
	private Voice voice;
	
	public TextToSpeech(Controller w){
		parent = w;
		//Load up the freeTTS voice manager
		
		//Start the server for tts
		Mary mary = new Mary();
		try {
			mary.main(new String[0]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*MaryServer server = new MaryServer();
		server.run();*/
		//server.run();
		
		//Start the client
		/*MaryGUIClient client;
		try {
			client = new MaryGUIClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}*/
		/*
		String path = System.getProperty("user.dir")+"\\src\\mbrola";
		System.out.println("Searching " + path + " for voices");
		System.setProperty("mbrola.base", path);
		
		VoiceManager voiceManager = VoiceManager.getInstance();
		
		Voice[] voices = voiceManager.getVoices();

		//I like kevin16, so we will use that for right now
		for (int i = 0; i < voices.length; i++) {
			System.out.println("Loaded Voice: " + voices[i].getName());
			if (voices[i].getName().equals("mbrola_us1")) {
				voice = voices[i];
				
				//Preapare the voice
				voice.allocate();
			}
		}
		
		//say("Voices are working for the most part");*/
	}
	
	/**Send a packet to TTS.  The message of the packet will be spoken
	 * 
	 * @param packet TextToSpeechPacket with a message to be said.
	 */
	public void send(TextToSpeechPacket packet) {
		//Say the message
		say(packet.getMessage());
	}
	
	/**Say the string that is passed in
	 * @param s the string to say
	 */
	private void say(String s) {
		voice.speak(s);
	}
	
}
