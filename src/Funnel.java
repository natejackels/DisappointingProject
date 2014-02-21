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
			default:
			break;
		}
		return new RobotPacket("N/A", null, null);
	}
}