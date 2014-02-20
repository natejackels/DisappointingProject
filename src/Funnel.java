/**
 *  Class: Funnel
 *  @author Robin McNally
 *  Description: This class funnels the text or speech string into comprehensible commands
 *
 */

public class Funnel {

	/*
		YE OLDE ABSURD COMMAND LIST (valid inputs)

		--more later--
		open vlc
		close vlc
		play
	*/
	public static RobotPacket decodeVLC(String toInterpret){
		String tempString = toInterpret;
		switch (toDecode){
			case "open vlc":
				RobotPacket openCmd = new RobotPacket("VLC", "Open", null);
				return openCmd;
			break;
			case "close vlc":
				RobotPacket closeCmd = new RobotPacket("VLC", "Close", null);
				return closeCmd;
			break;
			case "play":
				RobotPacket playCmd = new RobotPacket("VLC", "Play", null);
				return playCmd;
			break;
			default:	
			break;
		}
	}
}