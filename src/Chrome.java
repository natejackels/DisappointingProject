import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
		case("SaveBookmark"):
			return saveBookmark(e.getEvent(), e.getInfo());
		default:
			System.out.println("Invalid Command");
			String[] info = {"Invalid Command", "Chrome"};
			return new RobotPacket("Robot", "BadPacket", info);
		}
	}

	/**
	 * Method: saveBookmark(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param args null or empty if edit mode, contains Force in args[0] if press enter after ctrl+d
	 * @return A success packet if there aren't any errors, failure packet otherwise
	 * Description: A method to help bookmark a page.
	 */
	private RobotPacket saveBookmark(String cmd, String[] args) {
		if(chromeExists()){
			this.openURL("");
			try{
				Thread.sleep(500); //To account for slow computers
			} catch (Exception e){}
			keyboard.ctrlCMD(java.awt.event.KeyEvent.VK_W);
			try{
				Thread.sleep(500);
			} catch (Exception e){}
			keyboard.ctrlCMD(java.awt.event.KeyEvent.VK_D);
			if((args!=null) && (args.length > 0)){
				if(args[0].equals("Force")){
					try{
						Thread.sleep(500);
					} catch (Exception e){}
					keyboard.pressEnter();
				}
			}
		} else {
			return this.failed(cmd, args);
		}
		return this.sucessful(cmd, args);
	}

	/**
	 * Method: chromeExists()
	 * @author Nathan Jackels
	 * @return True if chrome.exe was found, False otherwise
	 * Description: A method that checks the currently running processes and checks for chrome.exe.
	 */
	private boolean chromeExists(){
		try{
			String line;
			Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
			Scanner in = new Scanner(p.getInputStream());
			while(in.hasNext()){
				line = in.nextLine();
				if(line.indexOf("chrome.exe") != -1){
					in.close();
					return true;
				}
			}
			in.close();
		} catch (Exception e){
			return false;
		}
		return false;
	}

	/**
	 * Methodname: search(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args args[0]: Search Engine args[1]: Text to be searched for
	 * @return Returns a success packet if chrome opened successfully on good input, or a Failure Packet otherwise
	 * Description: A method to search for a text string with some fairly common search engines
	 */
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
		case("Youtube"):
			if(openURL("http://www.youtube.com/results?search_query=" + parsedTerm + "&sm=")){
				return this.sucessful(cmd,  args);
			} else {
				return this.failed(cmd, args);
			}
		case("Wikipedia"):
			if(openURL("http://en.wikipedia.org/w/index.php?search=" + parsedTerm + "&title=Special%3ASearch&go=Go")){
				return this.sucessful(cmd, args);
			} else {
				return this.failed(cmd, args);
			}
		case("GoogleNews"):
			if(openURL("http://www.google.com/search?hl=en&gl=us&tbm=nws&authuser=0&q=" + parsedTerm + "&oq=&gs_l=")){
				return this.sucessful(cmd, args);
			} else {
				return this.failed(cmd, args);
			}
		case("YahooNews"):
			if(openURL("https://search.yahoo.com/search;_ylt=Ao9LigmTGtI7b3egIYiJ..is0NUE;_ylu=X3oDMTByaTN1NDdhBHNlYwNVSCAzIERlc2t0b3AgU2VhcmNoIDEwMA--;_ylg=X3oDMTBsdWsyY2FpBGxhbmcDZW4tVVMEcHQDMgR0ZXN0Aw--;_ylv=3?p=" + parsedTerm + "&type=2button&fr=uh3_news_web_gs")){
				return this.sucessful(cmd, args);
			} else {
				return this.failed(cmd, args);
			}
		case("MSNNews"):
			if(openURL("http://news.msn.com/searchresults?q=" + parsedTerm + "&form=MSNNWS&mkt=en-us&scope=")){
				return this.sucessful(cmd, args);
			} else {
				return this.failed(cmd, args);
			}
		case("CNN"):
			if(openURL("http://www.cnn.com/search/?query=" + parsedTerm + "&primaryType=mixed&sortBy=relevance&intl=false&x=0&y=0")){
				return this.sucessful(cmd, args);
			} else {
				return this.failed(cmd, args);
			}			
		default:
			return this.failed(cmd, args);
		}
	}

	/**
	 * Method: parseTerm(String term)
	 * @author Nathan Jackels
	 * @param term The string that needs to be parsed into the URL encoding standards
	 * @return The correctly parsed string (with the exception of unsupported characters)
	 * Description: A helper method that parses a string so that it meets URL encoding standards.
	 */
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
				case('-'): result += "%2D"; break;
				case('.'): result += "%2E"; break;
				case('/'): result += "%2F"; break;
				case(':'): result += "%3A"; break;
				case(';'): result += "%3B"; break;
				case('<'): result += "%3C"; break;
				case('='): result += "%3D"; break;
				case('>'): result += "%3E"; break;
				case('?'): result += "%3F"; break;
				case('@'): result += "%40"; break;
				case('['): result += "%5B"; break;
				case('\\'): result += "%5C"; break;
				case(']'): result += "%5D"; break;
				case('^'): result += "%5E"; break;
				case('_'): result += "%5F"; break;
				case('`'): result += "%60"; break;
				case('{'): result += "%7B"; break;
				case('|'): result += "%7C"; break;
				case('}'): result += "%7D"; break;
				case('~'): result += "%7E"; break;

				default:
					System.out.println("###Character not supported:[" + t + "]###");
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

	/**
	 * Method: openURL(String url)
	 * @author Nathan Jackels
	 * @param url The url to be opened in chrome
	 * @return True if thought to be successful, False otherwise
	 * Description: A helper method that attempts to open a URL in Chrome
	 */
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

	/**
	 * Method: openBookmark(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet
	 * @param args args[0]: The string that is being compared with bookmark Names and URLs to try to find one to open
	 * @return True if operation thought to have been successful, False otherwise
	 * Description: A method that searches through the list of bookmarks, and opens the first one that matches the search term
	 */
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
		return failed(cmd, args);
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
		infoResult[0] = "Chrome";
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
		infoResult[0] = "Chrome";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "CommandFailed", infoResult);
	}

	/**
	 * Method: Map<String, String> getBookmarks()
	 * @author Nathan Jackels
	 * @return A map of the bookmarks for the current user in the form <Name, URL>
	 * Description: A helper method to retrieve all of the current users chrome bookmarks
	 */
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

	/**
	 * Method: ArrayList<JsonElement> extract(JsonArray bookmarks)
	 * @author Nathan Jackels
	 * @param bookmarks The reference to a folder/array of bookmarks and other folders
	 * @return An ArrayList of JsonElements, for which each element  represents a single bookmark
	 * Description: A recursive helper method that retrieves JsonElements which represent individual Bookmarks
	 */
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
