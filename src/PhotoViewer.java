import java.awt.event.InputEvent;
import java.io.IOException;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

/**
 * Class: PhotoViewer
 * @author Nathan Jackels
 * Description: The class that implements controls for Windows Photo Viewer
 */
public class PhotoViewer extends Application{

	/**
	 * Method: PhotoViewer(Keyboard k)
	 * @author Nathan Jackels
	 * @param k The reference to the keyboard class
	 * Description: The constructor for PhotoViewer
	 */
	public PhotoViewer(Keyboard k) {
		super("PhotoViewer");
		this.keyboard = k;
	}

	/**
	 * Method: interpret(RobotPacket e)
	 * @author Nathan Jackels
	 * @param e The packet that will be interpreted by PhotoViewer
	 * @return The result of interpreting e [Usually a success or failure packet]
	 * @see TSP/Control/Robot_Packet_Design
	 */
	@Override
	public RobotPacket interpret(RobotPacket e) { //TODO JavaDocs
		switch(e.getEvent()){
		case("Open"):
			return open(e.getEvent(), e.getInfo());
		case("Next"):
			return next(e.getEvent(), e.getInfo());
		case("Prev"):
			return prev(e.getEvent(), e.getInfo());
		case("Slideshow"):
			return slideShow(e.getEvent(), e.getInfo());
		case("EndShow"):
			return endShow(e.getEvent(), e.getInfo());
		case("Pause"):
			return pause(e.getEvent(), e.getInfo());
		case("Desktop"):
			return desktop(e.getEvent(), e.getInfo());
		default: return failed(e.getEvent(), e.getInfo());
		}
	}
	
	/**
	 * Method: open(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args The array containing the path for the photo to be opened.
	 * @return A success packet if photo is found and opened, a failure packet otherwise.
	 */
	private RobotPacket open(String cmd, String[] args) {
		if((args == null) || (args.length == 0)){
			//Open Selected in Explorer or on Desktop
			//TODO: Scrapped Feature
			return this.failed(cmd, args);
		} else {
			//Open args[0]
			String viewerPath = System.getenv("WINDIR") + "\\system32\\rundll32.exe";
			ProcessBuilder pb = new ProcessBuilder(viewerPath, "C:\\Program Files\\Windows Photo Viewer\\PhotoViewer.dll", "ImageView_Fullscreen", args[0]);
			try {
				pb.start();
				return this.sucessful(cmd, args);
			} catch (IOException e) {
				return this.failed(cmd, args);
			}
		}
	}

	/**
	 * Method: desktop(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args The arguments to be returned within the response packet
	 * @return A success packet if photo is found and opened, a failure packet otherwise.
	 */
	private RobotPacket desktop(String cmd, String[] args) { //TODO Check Monitor Bounds, JavaDocs
		RECT startLocation = wpv();
		if(startLocation != null){	//TODO: Change right click location to middle of window
			//DO THINGS
			//Dimension location = getLocationWPV();
//			keyboard.keyboard.mouseMove(startLocation.left, startLocation.top);
//			keyboard.keyboard.mousePress(InputEvent.BUTTON1_MASK);
//			keyboard.keyboard.mouseRelease(InputEvent.BUTTON1_MASK);
//			keyboard.releaseKeys();
//			keyboard.press(java.awt.event.KeyEvent.VK_ESCAPE);
//			keyboard.releaseKeys();
			keyboard.keyboard.mouseMove(startLocation.left + 100, startLocation.top + 100);
			keyboard.keyboard.mousePress(InputEvent.BUTTON3_MASK);
			keyboard.keyboard.mouseRelease(InputEvent.BUTTON3_MASK);
			keyboard.keyboard.mouseMove(startLocation.left + 190, startLocation.top + 150);
			keyboard.keyboard.mousePress(InputEvent.BUTTON1_MASK);
			keyboard.keyboard.mouseRelease(InputEvent.BUTTON1_MASK);
		} else {
			return this.failed(cmd, args);
		}
		return this.sucessful(cmd, args);
	}

	/**
	 * Method: pause(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args The arguments to be returned within the response packet
	 * @return A success packet if photo is found and opened, a failure packet otherwise.
	 */
	private RobotPacket pause(String cmd, String[] args) {
		if(this.slideShow() != null){
			keyboard.releaseKeys();
			keyboard.press(java.awt.event.KeyEvent.VK_SPACE);
			keyboard.releaseKeys();
			return this.sucessful(cmd, args);
		} else {
			return this.failed(cmd, args);
		}
	}

	/**
	 * Method: endShow(String cmd, String[] args
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args The arguments to be returned within the response packet
	 * @return A success packet if photo is found and opened, a failure packet otherwise.
	 */
	private RobotPacket endShow(String cmd, String[] args) {
		if(this.slideShow() != null){
			keyboard.releaseKeys();
			keyboard.press(java.awt.event.KeyEvent.VK_ESCAPE);
			keyboard.releaseKeys();
			return this.sucessful(cmd, args);
		} else {
			return this.failed(cmd, args);
		}
	}

	/**
	 * Method: slideShow(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args The arguments to be returned within the response packet
	 * @return A success packet if photo is found and opened, a failure packet otherwise.
	 */
	private RobotPacket slideShow(String cmd, String[] args) {
		if(wpv() != null){
			keyboard.releaseKeys();
			keyboard.press(java.awt.event.KeyEvent.VK_F11);
			keyboard.releaseKeys();
			return this.sucessful(cmd, args);
		} else {
			return this.failed(cmd, args);
		}
	}

	/**
	 * Method: prev(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args The arguments to be returned within the response packet
	 * @return A success packet if photo is found and opened, a failure packet otherwise.
	 */
	private RobotPacket prev(String cmd, String[] args) {
		if(this.slideShow() != null){
			keyboard.releaseKeys();
			keyboard.press(java.awt.event.KeyEvent.VK_LEFT);
			keyboard.releaseKeys();
			return this.sucessful(cmd, args);
		} else if (wpv() != null){
			keyboard.releaseKeys();
			keyboard.press(java.awt.event.KeyEvent.VK_LEFT);
			keyboard.releaseKeys();
			return this.sucessful(cmd, args);
		} else {
			return this.failed(cmd, args);
		}
	}

	/**
	 * Method next(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args The arguments to be returned within the response packet
	 * @return A success packet if photo is found and opened, a failure packet otherwise.
	 */
	private RobotPacket next(String cmd, String[] args) {
		if(this.slideShow() != null){
			keyboard.releaseKeys();
			keyboard.press(java.awt.event.KeyEvent.VK_RIGHT);
			keyboard.releaseKeys();
			return this.sucessful(cmd, args);
		} else if (wpv() != null){
			keyboard.releaseKeys();
			keyboard.press(java.awt.event.KeyEvent.VK_RIGHT);
			keyboard.releaseKeys();
			return this.sucessful(cmd, args);
		} else {
			return this.failed(cmd, args);
		}
	}
	
	/**
	 * Method: wpv()
	 * @author Nathan Jackels
	 * @return null if not Windows Photo Viewer instance, RECT representing window size if instance is found. 
	 */
	public RECT wpv(){
		return this.existsSetForground("system32\\dllhost.exe", " - Windows Photo Viewer");
	}
	
	/**
	 * Method: slideShow()
	 * @author Nathan Jackels
	 * @return null if a slideshow instance doesn't exist, the size of the slideshow if one does
	 */
	public RECT slideShow(){
		return this.existsSetForground("system32\\dllhost.exe", "Photo Viewer Slide Show");
	}
	
	/**
	 * Method: sucessful(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param params The parameters to be returned within the response packet.
	 * @return The response packet that corresponds to cmd and params.
	 * Description: A helper method to get a response packet that corresponds to cmd and params in the case of a sucessful execution.
	 */
	private RobotPacket sucessful(String cmd, String[] params){
		if(params == null){
			params = new String[0];
		}
		String[] infoResult = new String[2 + params.length];
		infoResult[0] = "PhotoViewer";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "GoodCommand", infoResult);
	}

	/**
	 * Method: failed(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param params The parameters to be returned within the response packet.
	 * @return The response packet that corresponds to cmd and params.
	 * Description: A helper method to get a response packet that corresponds to cmd and params in the case of a failed execution.
	 */
	private RobotPacket failed(String cmd, String[] params){
		if(params == null){
			params = new String[0];
		}
		String[] infoResult = new String[2 + params.length];
		infoResult[0] = "PhotoViewer";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "CommandFailed", infoResult);
	}
	
//	private void setForegroundWindow(HWND handle){
//		com.sun.jna.platform.win32.User32.INSTANCE.SetForegroundWindow(handle);
//	}
//	
//	private String getForegroundWindowTitle(){
//		char[] title = new char[512];
//		HWND hwnd = com.sun.jna.platform.win32.User32.INSTANCE.GetForegroundWindow();
//		int len = com.sun.jna.platform.win32.User32.INSTANCE.GetWindowTextLength(hwnd);
//		com.sun.jna.platform.win32.User32.INSTANCE.GetWindowText(hwnd, title, len);
//		return Native.toString(title);
//	}
	
	/**
	 * Variable: temp
	 * @author Nathan Jackels
	 * Usage: Used by method existsSetForeground in order to extract information from nested method callback(HWND hWnd, Pointer arg1)
	 */
	private RECT temp;
	
	/**
	 * Method: existsSetForground(final String exe, final String title)
	 * @author Nathan Jackels
	 * @param exe The string to compare to each process's exe source
	 * @param title The string to compare to each process's Window Text
	 * @return RECT if the relating instance exists, null otherwise.
	 */
	public RECT existsSetForground(final String exe, final String title){
		final User32 user32 = User32.INSTANCE;
		final PsApi psapi = (PsApi) Native.loadLibrary("psapi", PsApi.class);
		temp = null;
		user32.EnumWindows(new WNDENUMPROC(){
			@Override
			public boolean callback(HWND hWnd, Pointer arg1) {
				//Save runtime
				byte[] buff = new byte[512];
				user32.getWindowA(hWnd, buff, 512);
				String wText = Native.toString(buff);
				if(wText.isEmpty()){
					return true;
				}
				
				//Get EXE:
				IntByReference pid = new IntByReference();
				com.sun.jna.platform.win32.User32.INSTANCE.GetWindowThreadProcessId(hWnd, pid);
				HANDLE process = Kernel32.INSTANCE.OpenProcess(0x0400 | 0x0010, false, pid.getValue()); //How does this work?
				psapi.GetModuleFileNameExA(process, null, buff, 512);
				String winExe = Native.toString(buff);
				if(winExe.contains(exe)){ //Get Title:
					//Get Title:
					int titleLength = com.sun.jna.platform.win32.User32.INSTANCE.GetWindowTextLength(hWnd) + 1;
					char[] tBuff = new char[titleLength];
					com.sun.jna.platform.win32.User32.INSTANCE.GetWindowText(hWnd, tBuff, titleLength);
					String wTitle = Native.toString(tBuff);
					if(wTitle.contains(title)){
						//Get Dimensions:
						com.sun.jna.platform.win32.User32.INSTANCE.SetForegroundWindow(hWnd);
						temp = new RECT();
						com.sun.jna.platform.win32.User32.INSTANCE.GetWindowRect(hWnd, temp);
					}
				}
				return true;
			}
		}, null);
		return temp; //MAY BE NULL!
	}
	
	/**
	 * Interface: User32 extends StdCallLibrary
	 * @author Nathan Jackels
	 * Usage: Used by method existsSetForeground(final String exe, final String title) to access Win32 API
	 */
	private interface User32 extends StdCallLibrary {
		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);
		boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
		int getWindowA(HWND hWnd, byte[] lpString, int nMaxCount);
	}
	
	/**
	 * Interface: PsApi extends StdCallLibrary
	 * @author Nathan Jackels
	 * Usage: Used by method existsSetForeground(final String exe, final String title) to access Win32 API
	 */
	private interface PsApi extends StdCallLibrary {
		int GetModuleFileNameExA(HANDLE process, HANDLE module, byte[] name, int i);
	}

}
