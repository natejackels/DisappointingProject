/**
 *  Class: Funnel
 *  @author Robin McNally
 *  Description: This class funnels the text or speech string into comprehensible commands
 *
 */

public class Funnel {
	public Controller parent;
	
	public Funnel(Controller p) {
		parent = p;
	}
	
	/*
		YE OLDE ABSURD COMMAND LIST (valid inputs)

		--more later--
		open vlc
		close vlc
		play
	*/
	public RobotPacket decodeVLC(String toInterpret){
		String tempString = toInterpret;
		switch (tempString){
			case "open vlc":
				RobotPacket openCmd = new RobotPacket("VLC", "Open", null);
				return openCmd;
			case "close vlc":
				RobotPacket closeCmd = new RobotPacket("VLC", "Close", null);
				return closeCmd;
			case "play":
				RobotPacket playCmd = new RobotPacket("VLC", "Play", null);
				return playCmd;
			case "greet":
				TextToSpeechPacket p = new TextToSpeechPacket("Hello! How are you doing");
				parent.tts.send(p);
			default:
			break;
		}
		return null;
	}
}