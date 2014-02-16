
public abstract class Application {
	private String name = "";
	private int maxInstance = 0;	//[<-0:oo], [>0:#]
	
	public Application(String name){
		this.name = name;
	}
	public Application(String name, int maxInstance){
		this.name = name;
		this.maxInstance = maxInstance;
	}
	public String getName(){
		return name;
	}
	public abstract RobotPacket interpret(RobotPacket e);
}
