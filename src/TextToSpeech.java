import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sound.sampled.AudioInputStream;

import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.SynthesisException;
import marytts.util.data.audio.AudioPlayer;
/*If the above line is giving you an error, that is because
 * you need to add "lib/freetts.jar" to your classpath
 */
public class TextToSpeech implements Runnable{
	public Controller parent;
	private MaryInterface marytts;
	private String toSay;
	public boolean enabled = true;
	
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
		toSay = packet.getMessage();
		//Start the thread
		new Thread(this).start();
	}
	
	/**Say the string that is passed in
	 * @param s the string to say
	 */
	private void say(String s) {
		//Load this into the list of things to say
		
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
	
	@Override
	public void run() {
		say(toSay);
	}
	
}
