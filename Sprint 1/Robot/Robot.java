import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *	Class: Robot
 *	@author Nathan Jackels
 *	Description: The only seen by the application's control class. All Keyboard/Mouse/App work is delegated through this class by Control.
 */
public class Robot {
	private Application[] apps = new Application[0];
	private Keyboard keyboard = null;
	private Mouse mouse = null;
	private Control control = null;
	
	/**
	 *	Method: Robot(Control control)
	 *	@author Nathan Jackels
	 *	@param control The reference to control so that packets can be sent back to control.
	 *	@see TSP/Control/Robot_Packet_Design
	 *  Description: The constructor for the class
	 */
	public Robot(Control control){
		//TODO Create Programs and Classes
		this.control = control;
		this.keyboard = new Keyboard();
		this.mouse =new Mouse();
		
	}
	
	/**
	 *	Method: sendPacket(RobotPacket e)
	 *	@author Nathan Jackels
	 *	@param e The RobotPacket that will be interpreted by the Robot class
	 *  Description: The method used by Control to delegate work to the Robot class
	 */
	public void sendPacket(RobotPacket e){
		if((e.getApplication() == null) || (e.getApplication().length() == 0) || (e.getEvent() == null) || (e.getEvent().length() == 0)){
			control.sendRobotPacket(new RobotPacket("Robot", "BadPacket", null));
			return;
		}
		for(Application app : apps){
			if(app.getName().equals(e.getApplication())){
				RobotPacket temp = app.interpret(e);
				if(temp != null){
					//App needs Info (TODO)
				}
			}
		}
	}
	
}
