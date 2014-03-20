import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;

/**
 * Class: Keyboard
 * @author Nathan Jackels
 * Description: The class that runs the virtual keyboard.
 */
public class Keyboard {
	java.awt.Robot keyboard;
	ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
	String supportedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String moreChars = "01234567890";
	
	/**
	 * Method: Keyboard()
	 * @author Nathan Jackels
	 * Description: The constructor for Keyboard
	 */
	public Keyboard(){
		try {
			keyboard = new java.awt.Robot();
		} catch (AWTException e) {
			keyboard = null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Robot getKeyboard(){
		return keyboard;
	}
	
	public void delay(int i){
		keyboard.delay(i);
	}
	
	public void releaseKeys(){
		for(int e : pressedKeys){
			keyboard.keyRelease(e);
			pressedKeys.remove(e);
		}
	}
	
	public void ctrlCMD(int key){
		this.press(java.awt.event.KeyEvent.VK_CONTROL);
		this.press(key);
		this.releaseKeys();
	}
	
	public void typeString(String e){
		char[] chars = e.toCharArray();
		for(char temp : chars){
			typeChar(temp);
		}
	}
	
	private void typeShiftChar(char e){
		String capitalAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if(capitalAlphabet.contains(e + "")){
			press(java.awt.event.KeyEvent.VK_SHIFT);
			typeChar(e);
			release(java.awt.event.KeyEvent.VK_SHIFT);
		}
	}
	
	public void press(int vk) {
		pressedKeys.add(vk);
		keyboard.keyPress(vk);
	}
	
	private void release(Integer vk){
		for(int i = 0; i < pressedKeys.size(); i++){
			if(pressedKeys.get(i) == vk){
				pressedKeys.remove(i);
				if(pressedKeys.contains(vk)){
					release(vk);
				}
			}
		}
		keyboard.keyRelease(vk);
	}
	
	private void typeKey(Integer vk){
		press(vk);
		release(vk);
	}
	
	public void pressEnter(){
		press(java.awt.event.KeyEvent.VK_ENTER);
		release(java.awt.event.KeyEvent.VK_ENTER);
	}

	public void typeChar(char e){
		String capitalAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if(capitalAlphabet.contains(e + "")){
			typeShiftChar(e);
			return;
		}
		switch(e){
		case 'a': typeKey(java.awt.event.KeyEvent.VK_A); return;
		case 'b': typeKey(java.awt.event.KeyEvent.VK_B); return;
		case 'c': typeKey(java.awt.event.KeyEvent.VK_C); return;
		case 'd': typeKey(java.awt.event.KeyEvent.VK_D); return;
		case 'e': typeKey(java.awt.event.KeyEvent.VK_E); return;
		case 'f': typeKey(java.awt.event.KeyEvent.VK_F); return;
		case 'g': typeKey(java.awt.event.KeyEvent.VK_G); return;
		case 'h': typeKey(java.awt.event.KeyEvent.VK_H); return;
		case 'i': typeKey(java.awt.event.KeyEvent.VK_I); return;
		case 'j': typeKey(java.awt.event.KeyEvent.VK_J); return;
		case 'k': typeKey(java.awt.event.KeyEvent.VK_K); return;
		case 'l': typeKey(java.awt.event.KeyEvent.VK_L); return;
		case 'm': typeKey(java.awt.event.KeyEvent.VK_M); return;
		case 'n': typeKey(java.awt.event.KeyEvent.VK_N); return;
		case 'o': typeKey(java.awt.event.KeyEvent.VK_O); return;
		case 'p': typeKey(java.awt.event.KeyEvent.VK_P); return;
		case 'q': typeKey(java.awt.event.KeyEvent.VK_Q); return;
		case 'r': typeKey(java.awt.event.KeyEvent.VK_R); return;
		case 's': typeKey(java.awt.event.KeyEvent.VK_S); return;
		case 't': typeKey(java.awt.event.KeyEvent.VK_T); return;
		case 'u': typeKey(java.awt.event.KeyEvent.VK_U); return;
		case 'v': typeKey(java.awt.event.KeyEvent.VK_V); return;
		case 'w': typeKey(java.awt.event.KeyEvent.VK_W); return;
		case 'x': typeKey(java.awt.event.KeyEvent.VK_X); return;
		case 'y': typeKey(java.awt.event.KeyEvent.VK_Y); return;
		case 'z': typeKey(java.awt.event.KeyEvent.VK_Z); return;
		case '0': typeKey(java.awt.event.KeyEvent.VK_0); return;
		case '1': typeKey(java.awt.event.KeyEvent.VK_1); return;
		case '2': typeKey(java.awt.event.KeyEvent.VK_2); return;
		case '3': typeKey(java.awt.event.KeyEvent.VK_3); return;
		case '4': typeKey(java.awt.event.KeyEvent.VK_4); return;
		case '5': typeKey(java.awt.event.KeyEvent.VK_5); return;
		case '6': typeKey(java.awt.event.KeyEvent.VK_6); return;
		case '7': typeKey(java.awt.event.KeyEvent.VK_7); return;
		case '8': typeKey(java.awt.event.KeyEvent.VK_8); return;
		case '9': typeKey(java.awt.event.KeyEvent.VK_9); return;
		case '.': typeKey(java.awt.event.KeyEvent.VK_PERIOD); return;
		case ' ': typeKey(java.awt.event.KeyEvent.VK_SPACE); return;
		case '\n': typeKey(java.awt.event.KeyEvent.VK_ENTER); return;
		case '/': typeKey(java.awt.event.KeyEvent.VK_SLASH); return;
		}
	}

	public void releaseAll() {
		int max = 1000;
		for(int i = 0; i < max; i++){
			try{
				keyboard.keyRelease(i);
			} catch (Exception e){
				//Do nothing
			}
		}
		keyboard.mouseRelease(0);
		keyboard.mouseRelease(1);
		keyboard.mouseRelease(2);
	}

}
