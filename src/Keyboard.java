import java.awt.AWTException;


public class Keyboard {
	java.awt.Robot keyboard = null;
	
	public Keyboard(){
		try {
			this.keyboard = new java.awt.Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mediaPlayButton() {
		// TODO Auto-generated method stub
		
	}

	public void mediaPauseButton() {
		// TODO Auto-generated method stub
		
	}

}
