import java.util.Set;

import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;
/*If the above line is giving you an error, that is because
 * you need to add "lib/freetts.jar" to your classpath
 */
public class TextToSpeech {
	public Controller parent;
	private MaryInterface marytts;
	
	public TextToSpeech(Controller w){
		parent = w;
		try {
			marytts = new LocalMaryInterface();
			Set<String> voices = marytts.getAvailableVoices();
			for (String s : voices) {
				marytts.setVoice(s);
				System.out.println("Loaded voice for MaryTTS");
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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
		try {
			AudioInputStream audio = marytts.generateAudio(s);
			AudioPlayer player = new AudioPlayer(audio);
			player.start();
			player.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
