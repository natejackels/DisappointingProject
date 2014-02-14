public class RobotPacket {
	private String application = ""; 
	private String event = "";
	private String[] info = { "", "" };

	public RobotPacket(String application, String event, String[] info) {
		this.application = application;
		this.event = event;
		this.info = info;
	}
	
	public String getApplication(){
		return application;
	}
	
	public String getEvent(){
		return event;
	}
	
	public String[] getInfo(){
		return info;
	}

}
