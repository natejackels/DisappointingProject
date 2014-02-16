/**
	Class: Control
	@author Robin McNally
	Description: This class handles the communication between the four other parts of the program
*/
/*
	TODO -> decide on args for main
	TODO -> Implement main
	TODO -> Implement sendPacket(guiPacket)
	TODO -> Implement sendPacket(RobotPacket)
	TODO -> Implement sendPacket(ttsPacket)
	TODO -> Implement sendPacket(sttPacket)
*/

public class Control.java {




	/**
		Method: main
		@author Robin McNally
		@param args
	*/
	public static void main(String[] args) {
		//Pass proirity to GUI
	}

	/**
		Method: sendPacket(guiPacket)
		@author Robin McNally
		@param gPack Takes a gui packet and decides what class to send it to
	*/
	public void sendPacket(guiPacket gPack){
		
	}

	/**
		Method: sendPacket(RobotPacket)
		@author Robin McNally
		@param gPack Takes a robot packet and decides what class to send it to
	*/
	/*
		TODO -> Application invalid
		TODO -> Application commands invalid
		TODO -> Application open fail
		TODO -> Display to user
		TODO -> Get Folder location
		TODO -> Bad Get Value
	*/
	public void sendPacket(RobotPacket rPack){
		switch (rPack.getApplication()) {
			case "Robot":
				switch (rPack.getEvent()){
					case "BadPacket":
						if (rPack.getInfo() == null){

						} else {
							
						}
					break;
					case "Display":
					break;
					case "NeedLocation":
					break;
					case "BadGetValue":
					break;
					default:
					break;
				}
				break;
			default:
			break;
		}
	}

	/**
		Method: sendPacket(ttsPacket)
		@author Robin McNally
		@param gPack Takes a tts packet and decides what class to send it to
	*/
	public void sendPacket(ttsPacket tsPack){
		
	}

	/**
		Method: sendPacket(sttPacket)
		@author Robin McNally
		@param gPack Takes a stt packet and decides what class to send it to
	*/
	public void sendPacket(sttPacket stPack){
		
	}
}