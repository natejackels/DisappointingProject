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
	 * Method: getKeyboard()
	 * @author Nathan Jackels
	 * @return The robot stored within this class
	 * Description: A method that returns the robot for the keyboard
	 */
	public Robot getKeyboard(){
		return keyboard;
	}
	
	/**
	 * Method: delay(int i)
	 * @author Nathan Jackels
	 * @param i The time to sleep in milliseconds
	 * Description: A wrapper method for Robot.delay(i)
	 */
	public void delay(int i){
		keyboard.delay(i);
	}
	
	/**
	 * Method: releaseKeys()
	 * @author Nathan Jackels
	 * Description: A method that releases all the keys that are currently pressed down by Keyboard.
	 */
	public void releaseKeys(){
		for(int e : pressedKeys){
			keyboard.keyRelease(e);
			pressedKeys.remove(e);
		}
	}
	
	/**
	 * Method: ctrlCMD(int key)
	 * @author Nathan Jackels
	 * @param key The key code that will be pressed down
	 * Description: A method that presses ctrl, then the key, then releases both of them.
	 */
	public void ctrlCMD(int key){
		this.press(java.awt.event.KeyEvent.VK_CONTROL);
		this.press(key);
		this.releaseKeys();
	}
	
	/**
	 * Method: typeString(String e)
	 * @author Nathan Jackels
	 * @param e The String that will be typed
	 * Description: A method that types a string through the virtual Robot Keyboard.
	 */
	public void typeString(String e){
		char[] chars = e.toCharArray();
		for(char temp : chars){
			typeChar(temp);
		}
	}
	
	/**
	 * Method: typeShift(int key)
	 * @author Nathan Jackels
	 * @param key The key that will be typed while shift is held down
	 * Description: A method that holds down shift, types the key, and then releases both keys.
	 */
	public void typeShift(int key){
		this.press(java.awt.event.KeyEvent.VK_SHIFT);
		this.press(key);
		this.releaseKeys();
	}
	
	/**
	 * Method: typeShiftChar(char e)
	 * @author Nathan Jackels
	 * @param e The character being typed
	 * Description: A method that presses shift, then types a character, then releases both keys.
	 */
	private void typeShiftChar(char e){
		String capitalAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if(capitalAlphabet.contains(e + "")){
			press(java.awt.event.KeyEvent.VK_SHIFT);
			typeChar(e);
			release(java.awt.event.KeyEvent.VK_SHIFT);
		}
	}
	
	/**
	 * Method: press(int vk)
	 * @author Nathan Jackels
	 * @param vk The key code that will be pressed (not typed)
	 * Description: A method that presses but doesn't release a key.
	 */
	public void press(int vk) {
		pressedKeys.add(vk);
		keyboard.keyPress(vk);
	}
	
	/**
	 * Method: release(Integer vk)
	 * @author Nathan Jackels
	 * @param vk The key to be released
	 * Description: A method that releases a key whether it was pressed by robot or not, then removes it from Keyboards list of pressed keys
	 */
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
	
	/**
	 * Method: typeKey(Integer vk)
	 * @author Nathan Jackels
	 * @param vk The key code that will be typed.
	 * Description: A convenience method that will type a key code.
	 */
	private void typeKey(Integer vk){
		press(vk);
		release(vk);
	}
	
	/**
	 * Method: pressEnter()
	 * @author Nathan Jackels
	 * Description: A method that will press and release the enter key.
	 */
	public void pressEnter(){
		press(java.awt.event.KeyEvent.VK_ENTER);
		release(java.awt.event.KeyEvent.VK_ENTER);
	}

	/**
	 * Method: typeChar(char e)
	 * @author Nathan Jackels
	 * @param e The character to be typed
	 * Description: A helper method for the typeString(String e) method
	 */
	public void typeChar(char e){
		String capitalAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if(capitalAlphabet.contains(e + "")){
			typeShiftChar(e); //Capital Letters
			return;
		}
		switch(e){
		//Lower Case
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
		
		//Numbers
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
		
		//Symbols:
		case '.': typeKey(java.awt.event.KeyEvent.VK_PERIOD); return;
		case ' ': typeKey(java.awt.event.KeyEvent.VK_SPACE); return;
		case '\n': typeKey(java.awt.event.KeyEvent.VK_ENTER); return;
		case '/': typeKey(java.awt.event.KeyEvent.VK_SLASH); return;
		case '-': typeKey(java.awt.event.KeyEvent.VK_MINUS); return;
        case '=': typeKey(java.awt.event.KeyEvent.VK_EQUALS); return;
        case '~': typeShift(java.awt.event.KeyEvent.VK_BACK_QUOTE); return;
        case '!': typeKey(java.awt.event.KeyEvent.VK_EXCLAMATION_MARK); return;
        case '@': typeKey(java.awt.event.KeyEvent.VK_AT); return;
        case '#': typeKey(java.awt.event.KeyEvent.VK_NUMBER_SIGN); return;
        case '$': typeKey(java.awt.event.KeyEvent.VK_DOLLAR); return;
        case '%': typeShift(java.awt.event.KeyEvent.VK_5); return;
        case '^': typeKey(java.awt.event.KeyEvent.VK_CIRCUMFLEX); return;
        case '&': typeKey(java.awt.event.KeyEvent.VK_AMPERSAND); return;
        case '*': typeKey(java.awt.event.KeyEvent.VK_ASTERISK); return;
        case '(': typeKey(java.awt.event.KeyEvent.VK_LEFT_PARENTHESIS); return;
        case ')': typeKey(java.awt.event.KeyEvent.VK_RIGHT_PARENTHESIS); return;
        case '_': typeKey(java.awt.event.KeyEvent.VK_UNDERSCORE); return;
        case '+': typeKey(java.awt.event.KeyEvent.VK_PLUS); return;
        case '\t': typeKey(java.awt.event.KeyEvent.VK_TAB); return;
        case '[': typeKey(java.awt.event.KeyEvent.VK_OPEN_BRACKET); return;
        case ']': typeKey(java.awt.event.KeyEvent.VK_CLOSE_BRACKET); return;
        case '\\': typeKey(java.awt.event.KeyEvent.VK_BACK_SLASH); return;
        case '{': typeShift(java.awt.event.KeyEvent.VK_OPEN_BRACKET); return;
        case '}': typeShift(java.awt.event.KeyEvent.VK_CLOSE_BRACKET); return;
        case '|': typeShift(java.awt.event.KeyEvent.VK_BACK_SLASH); return;
        case ';': typeKey(java.awt.event.KeyEvent.VK_SEMICOLON); return;
        case ':': typeKey(java.awt.event.KeyEvent.VK_COLON); return;
        case '\'': typeKey(java.awt.event.KeyEvent.VK_QUOTE); return;
        case '"': typeKey(java.awt.event.KeyEvent.VK_QUOTEDBL); return;
        case ',': typeKey(java.awt.event.KeyEvent.VK_COMMA); return;
        case '<': typeKey(java.awt.event.KeyEvent.VK_LESS); return;
        case '>': typeKey(java.awt.event.KeyEvent.VK_GREATER); return;
        case '?': typeShift(java.awt.event.KeyEvent.VK_SLASH); return;
		}
	}

	/**
	 * Method: releaseAll()
	 * @author Nathan Jackels
	 * Description: A method that releases just about every keyboard key, and three mouse buttons. Used in preparation for some commands.
	 */
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
