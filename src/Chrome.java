import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Class: Chrome
 * @author Nathan Jackels
 * Description: The class that implements controls for Chrome
 *
 */
public class Chrome extends Application {

	/**
	 * Method: Chrome(Keyboard k)
	 * @author Nathan Jackels
	 * @param k The reference to the keyboard class
	 * Description: The constructor for Chrome
	 */
	public Chrome(Keyboard k) {
		super("Chrome");
		this.keyboard = k;
	}

	/**
	 * Method: interpret(RobotPacket e)
	 * @author Nathan Jackels
	 * @param e The packet that will be interpreted by Chrome
	 * @return The result of interpreting e
	 * @see TSP/Control/Robot_Packet_Design
	 */
	@Override
	public RobotPacket interpret(RobotPacket e) {
		System.out.println(e.getEvent());
		switch(e.getEvent()){
		case("History"):
			return history(e.getEvent(), e.getInfo());
		case("Bookmark"):
			return openBookmark(e.getEvent(), e.getInfo());
		case("Search"):
			return search(e.getEvent(), e.getInfo());
		default:
			System.out.println("Invalid Command");
			String[] info = {"Invalid Command", "Chrome"};
			return new RobotPacket("Robot", "BadPacket", info);
		}
	}

	private RobotPacket search(String cmd, String[] args) {
		if(args == null){
			return this.failed(cmd, args);
		}
		if(args.length != 2){
			return this.failed(cmd, args);
		}
		String parsedTerm = parseTerm(args[1]);
		switch(args[0]){
		case("Google"):
			if(openURL("https://www.google.com/search?output=search&sclient=psy-ab&q=" + parsedTerm + "&btnG=&oq=&gs_l=&pbx=1")){
				return this.sucessful(cmd, args);
			} else {
				return this.failed(cmd, args);
			}
		//case(""):
			
		default:
			return this.failed(cmd, args);
		}
	}
	
	private String parseTerm(String term){
		String result = "";
		String normal = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		for(char t : term.toCharArray()){
			if(normal.contains(t + "")){
				result+=t;
			} else {
				switch(t){
				case(' '): result += "%20"; break;
				case('!'): result += "%21"; break;
				case('\"'): result += "%22"; break;
				case('#'): result += "%23"; break;
				case('$'): result += "%24"; break;
				case('%'): result += "%25"; break;
				case('&'): result += "%26"; break;
				case('\''): result += "%27"; break;
				case('('): result += "%28"; break;
				case(')'): result += "%29"; break;
				case('*'): result += "%2A"; break;
				case('+'): result += "%2B"; break;
				case(','): result += "%2C"; break;
				default:
					System.out.println("###Character not supported###");
				}
			}
		}
		return result;
	}

	/**
	 * Method: history(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args The string array of length 1 that will contain the string to be searched for within history
	 * @return Either a success or a failure packet
	 * Description: A method that will search Chrome's history for a string
	 */
	private RobotPacket history(String cmd, String[] args) {
		System.out.println("History!");
		if((args == null) || (args.length == 0)){
			return this.failed(cmd, args);
		}
		String history = "chrome%3A//history/";
		history = "";
		System.out.println(history);
		ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");//, history);
		try{
			pb.start();
		} catch (IOException e){
			return this.failed(cmd, args);
		}
		//Type string.
		keyboard.delay(2000);
		keyboard.ctrlCMD(java.awt.event.KeyEvent.VK_H);
		keyboard.delay(3000);
		keyboard.typeString(args[0]);
		keyboard.pressEnter();
		return this.sucessful(cmd, args);
	}
	
	private boolean openURL(String url){
		ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe", url);
		try{
			pb.start();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private RobotPacket openBookmark(String cmd, String[] args){
		if(args == null){
			return failed(cmd, args);
		}
		if(args.length == 0){
			return failed(cmd, args);
		}
		Map<String, String> bookmarks = getBookmarks();
		for(String t : bookmarks.keySet()){
			if(t.contains(args[0])){
				if(openURL(bookmarks.get(t))){
					return sucessful(cmd, args);
				}
			} else if (bookmarks.get(t).contains(args[0])){
				if(openURL(bookmarks.get(t))){
					return sucessful(cmd, args);
				}
			}
		}
		return null;
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
		infoResult[0] = "Computer";
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
		infoResult[0] = "Computer";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "CommandFailed", infoResult);
	}
	
	//TODO JavaDocs
	private Map<String, String> getBookmarks(){
		String path = System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data\\Default\\Bookmarks";
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			JsonElement jse = new JsonParser().parse(in);
			ArrayList<JsonElement> sites = new ArrayList<JsonElement>();
			JsonArray bookmarks = jse.getAsJsonObject().get("roots").getAsJsonObject().get("bookmark_bark").getAsJsonObject().get("children").getAsJsonArray();
			sites.addAll(extract(bookmarks));
			Map<String, String> result = new HashMap<String, String>();
			for(JsonElement t : sites){
				result.put(t.getAsJsonObject().get("name").getAsString(), t.getAsJsonObject().get("url").getAsString());
			}
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	//TODO JavaDocs
	private ArrayList<JsonElement> extract(JsonArray bookmarks) {
		ArrayList<JsonElement> result = new ArrayList<JsonElement>();
		for(int i = 0; i < bookmarks.size(); i++){
			JsonObject e = bookmarks.get(i).getAsJsonObject();
			boolean folder = false;
			for(Entry<String, JsonElement> t : e.entrySet()){
				if(t.getKey().equals("children")){
					result.addAll(extract(e.get("children").getAsJsonArray()));
					folder = true;
					break;
				}
			}
			if(!folder){
				result.add(e);
			}
		}
		return result;
	}
}
