/**
 *	Class: RobotPacket
 *	@author Nathan Jackels
 *	Description: The packet class used for communication between the Control and Robot classes.
 */
public class RobotPacket {
	private String application = ""; 
	private String event = "";
	private String[] info = { "", "" };

	/**
	 *	Method: RobotPacket(String application, String event, String[] info)
	 *	@author Nathan Jackels
	 *	@param application The source application for event or destination for event
	 *	@param event The event that happened with source or command for destination
	 *	@param info An array of information relevant to the source or arguments for a destination
	 *	Description The constructor for the class
	 */
	public RobotPacket(String application, String event, String[] info) {
		this.application = application;
		this.event = event;
		this.info = info;
	}
	
	/**
	 *	Method: getApplication()
	 *	@author Nathan Jackels
	 *	@return The application that the packet concerns.
	 *	Description: A getter method for the packet's Application.
	 */
	public String getApplication(){
		return application;
	}
	
	/**
	 *	Method: getEvent()
	 *	@author Nathan Jackels
	 *	@return The event that the packet's application concerns.
	 *	Description: A getter method for the packet's event.
	 */
	public String getEvent(){
		return event;
	}
	
	/**
	 *	Method: getInfo()
	 *	@author Nathan Jackels
	 *	@return An array of information relevant to the source or arguments for a destination
	 *	Description: A getter method for the packet's arguments/info.
	 */
	public String[] getInfo(){
		return info;
	}

}
