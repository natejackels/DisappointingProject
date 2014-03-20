/**
 *	Class: Robot
 *	@author Nathan Jackels
 *	Description: The only seen by the application's control class. All Keyboard/Mouse/App work is delegated through this class by Control.
 */
public class Robot {
	private Application[] apps = new Application[0];
	private Keyboard keyboard = null;
	private Controller control = null;
	
	/**
	 *	Method: Robot(Control control)
	 *	@author Nathan Jackels
	 *	@param control The reference to control so that packets can be sent back to control.
	 *  Description: The constructor for the class
	 */
	public Robot(Controller control){
		//TODO Create Programs and Classes
		this.control = control;
		this.keyboard = new Keyboard();
		if(keyboard.getKeyboard() == null){
			this.keyboard = null;
		}
		apps = new Application[2];
		apps[0] = new VLC(keyboard);
		apps[1] = new Computer(keyboard);
		apps[2] = new Chrome(keyboard);
		apps[3] = new PhotoViewer(keyboard);
	}
	
	/**
	 *	Method: sendPacket(RobotPacket e)
	 *	@author Nathan Jackels
	 *	@param e The RobotPacket that will be interpreted by the Robot class
	 *  @return The resulting packet from the command (most likely either something to display, or null)
	 *  @see TSP/Control/Robot_Packet_Design
	 *  Description: The method used by Control to delegate work to the Robot class
	 */
	public RobotPacket sendPacket(RobotPacket e){
		System.out.println("Got packet");
		if((e.getApplication() == null) || (e.getApplication().length() == 0) || (e.getEvent() == null) || (e.getEvent().length() == 0)){
			return new RobotPacket("Robot", "BadPacket", null);
		}
		
		if(this.keyboard == null){
			this.keyboard = new Keyboard();
			if(this.keyboard == null){
				String[] infos = new String[e.getInfo().length + 2];
				infos[0] = e.getApplication();
				infos[1] = e.getEvent();
				for(int i = 0; i < e.getInfo().length; i++){
					infos[2+i] = e.getInfo()[i];
				}
				return new RobotPacket("Robot", "CommandFailed", infos);
			} else {
				for(Application t : apps){
					t.setKeyboard(this.keyboard);
				}
			}
		}
		
		for(Application app : apps){
			if(app.getName().toLowerCase().equals(e.getApplication().toLowerCase())){
				System.out.println("Found an app");
				return app.interpret(e);
				
			}
		}
		return new RobotPacket("Robot", "BadPacket", null);
	}
	
}
