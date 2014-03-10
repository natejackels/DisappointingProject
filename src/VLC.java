import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Class: VLC
 * @author Nathan Jackels
 * Description: The class that implements controls for VLC 
 */

public class VLC extends Application{
	private Process vlc;
	private Keyboard keyboard;
	private String vlcFolder;

	/**
	 * Method: VLC(Robot e, Keyboard k, Mouse m)
	 * @author Nathan Jackels
	 * @param e The reference to the robot class
	 * @param k The reference to the keyboard class
	 * @param m The reference to the mouse class
	 * Description: The constructor for VLC
	 */
	public VLC(Keyboard k) {
		super("VLC");
		this.keyboard = k;
	}

	/**
	 * Method: interpret(RobotPacket e)
	 * @author Nathan Jackels
	 * @param e The packet that will be interpreted by VLC
	 * @return The result of interpreting e [Usually a Display Command]
	 * @see TSP/Control/Robot_Packet_Design
	 */
	@Override
	public RobotPacket interpret(RobotPacket e){
		if(e.getApplication() != "VLC"){
			return this.failed(e.getEvent(), e.getInfo());
		}
		return vlc(e.getEvent(), e.getInfo());
	}

	/**
	 *	Method: vlc(String cmd, String[] args)
	 *	@author Nathan Jackels
	 *	@param cmd The command for the VLC program.
	 *	@param args An array of arguments for interpretation in relation to the command for vlc.
	 *	Description: A method that interprets commands for the VLC program and carries them out or delegates them further if necessary.
	 */
	private RobotPacket vlc(String cmd, String[] args){
		for (String s : args) {
			System.out.println("ARgs: " + s);
		}

		switch(cmd){
		case("What"):
			return what();
		case("WhatIs"):
			return whatIs();
		case("Play"):	//DONE
			if((args == null) || (args.length == 0)){
				//Pause if initialized
				if(this.vlcExists()){
					return this.pause(cmd, args);
				}
			}
		return play(cmd, args);
		case("Pause"):	//DONE
			return pause(cmd, args);
		case("ListSongs"):	//TODO Sprint 2
			if(args.length == 1){
				return listSongs(cmd, args);
			} else {
				String[] arg = {args[0]};
				String[] data = listSongs(cmd, arg).getInfo();
				this.playParams(cmd, args, data);
			}
		case("Close"):
			return close(cmd, args);
		case("Open"):
			return open(cmd, args);
		case("MusicFolder"):
			File tempDir = new File(args[0]);
		if(!tempDir.isDirectory()){
			String[] e = {"NeedLocation", "VLCmusic"};
			return new RobotPacket("Robot", "BadGetValue", e);		//TODO UPDATE
		} else {
			this.vlcFolder = args[0]; //TODO Implement
		}
		return this.sucessful(cmd, args);
		case("Next"):
			return next(cmd, args);
		case("Prev"):
			return prev(cmd, args);
		case("Stop"):
			return stop(cmd, args);

		default:
			String[] info = {"InvalidCommand", "VLC"};
			return new RobotPacket("Robot", "BadPacket", info);
		}

	}

	private void playParams(String cmd, String[] args, String[] data) {
		
	}

	private RobotPacket what(){	//TODO JavaDocs
		String[] canDo = {"The Commands for VLC are" + "Play or Pause", "Play song or artist", "What songs do I have - this will list the songs in your library.", "Open VLC", "Close VLC"};
		return new RobotPacket("Robot", "Display",canDo);
	}

	private RobotPacket whatIs(){ //TODO JavaDocs
		String[] vlcIs = {"VLC is a music and video player.", "Unlike other programs, VLC can play nearly any song or video you have on your computer.", "It can also play DVDs and CDs that you put in your computer."};
		return new RobotPacket("Robot", "Display", vlcIs);
	}

	private RobotPacket close(String cmd, String[] args){ //TODO JavaDocs
		try{
			Runtime.getRuntime().exec("Taskkill /F /IM vlc.exe");
			return this.sucessful(cmd, args);
		} catch (IOException e){
			return this.failed(cmd, args);
		}
	}

	//open single file or multiple files or empty playlist
	private RobotPacket play(String cmd, String[] args){ //TODO JavaDocs
		if(args == null){
			args = new String[0];
		}
		String[] vlcparams = new String[args.length + 3];
		vlcparams[0] = "C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe";
		for(int i = 0; i < args.length; i++){
			vlcparams[i+1] = args[i];
		}
		vlcparams[args.length+1] = "--one-instance";
		vlcparams[args.length+2] = "--playlist-enqueue";
		ProcessBuilder pb = new ProcessBuilder(vlcparams);
		try{
			pb.start();
			return this.sucessful(cmd, args);
		} catch (Exception e){
			String[] info = {e.getMessage()};
			return new RobotPacket("VLC", "FailedOpen", info);
		}
	}

	private RobotPacket listSongs(String cmd, String[] args){
		if((args == null) || (args.length != 1)){
			return this.failed(cmd, args);
		}
		File folder = new File(args[0]);
		//Check folder:
		if(!folder.exists()){
			return this.failed(cmd, args);
		}
		if(!folder.isDirectory()){
			return this.failed(cmd, args);
		}
		File[] children = folder.listFiles();
		String[] result = new String[children.length * 5];
		for(int i = 0; i < result.length; i++){
			Map<String, String> fileData = getMediaInfo(children[i]);
			result[i*5] = children[i].getName();
			result[(i*5)+1] = null; 
			result[(i*5)+2] = null;
			result[(i*5)+3] = null;
			result[(i*5)+4] = null;
			if(fileData != null){
				result[(i*5)+1] = fileData.get("artist"); 
				result[(i*5)+2] = fileData.get("title");
				result[(i*5)+3] = fileData.get("album");
				result[(i*5)+4] = fileData.get("genre");
			}
		}
		RobotPacket songs = new RobotPacket("VLC", "FolderContents", result);
		return songs;
	}



	private RobotPacket open(String cmd, String[] args){
		if(vlcExists()){
			return this.failed(cmd, args);
		} else {
			try{
				ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe", "--one-instance");
				pb.start();
			} catch (Exception e){
				String[] info = {e.getMessage()};
				return new RobotPacket("VlC", "FailedOpen", info);
			}
			return this.sucessful(cmd, args);
		}
	}

	private boolean vlcExists(){ //TODO JavaDocs
		try{
			String line;
			Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
			Scanner in = new Scanner(p.getInputStream());
			while(in.hasNext()){
				line = in.nextLine();
				if(line.indexOf("vlc.exe") != -1){
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
	 * Method: next(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param params The parameters to be returned within the response packet.
	 * @return The response packet that corresponds to cmd and params.
	 * Description: A helper method to click the media next key.
	 */
	private RobotPacket next(String cmd, String[] params){
		try {
			Runtime.getRuntime().exec("cmd.exe /C start ./RobotScripts/next.vbs");
			return sucessful(cmd, params);
		} catch (IOException e) {
			return failed(cmd, params);
		}
	}

	/**
	 * Method: prev(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param params The parameters to be returned within the response packet.
	 * @return The response packet that corresponds to cmd and params.
	 * Description: A helper method to click the media previous key.
	 */
	private RobotPacket prev(String cmd, String[] params){
		try {
			Runtime.getRuntime().exec("cmd.exe /C start ./RobotScripts/prev.vbs");
			return sucessful(cmd, params);
		} catch (IOException e) {
			return failed(cmd, params);
		}
	}

	/**
	 * Method: stop(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param params The parameters to be returned within the response packet.
	 * @return The response packet that corresponds to cmd and params.
	 * Description: A helper method to click the media stop key.
	 */
	private RobotPacket stop(String cmd, String[] params){
		try {
			Runtime.getRuntime().exec("cmd.exe /C start ./RobotScripts/stop.vbs");
			return sucessful(cmd, params);
		} catch (IOException e) {
			return failed(cmd, params);
		}
	}

	/**
	 * Method: pause(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param params The parameters to be returned within the response packet.
	 * @return The response packet that corresponds to cmd and params.
	 * Description: A helper method to click the media pause key.
	 */
	private RobotPacket pause(String cmd, String[] params){
		try {
			Runtime.getRuntime().exec("cmd.exe /C start ./RobotScripts/pause.vbs");
			return sucessful(cmd, params);
		} catch (IOException e) {
			return failed(cmd, params);
		}
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

	public static Map<String, String> getMediaInfo(File song){
		{
			File data = new File("out");
			if(data.exists()){
				if(!data.delete()){
					//Thread will hang
					return null;
				}
			}
		}
		HashMap<String, String> result = new HashMap<String, String>();
		try{
			ProcessBuilder decode = new ProcessBuilder("./RobotScripts/MediaMetaData/ffmpeg.exe", "-i", song.getAbsolutePath(), "-f", "ffmetadata", "out");
			Process d = decode.start();
			d.waitFor();
			File data = new File("out");
			Scanner in = new Scanner(data);
			while(in.hasNext()){
				String temp = in.nextLine();
				if(temp.contains("=")){
					int equalsIndex = temp.indexOf("=");
					String key = temp.substring(0, equalsIndex);
					String value = temp.substring(equalsIndex+1);
					result.put(key, value);
				}
			}
			in.close();
			if(!data.delete()){
				//Possible problems on next call of this method.
			}
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return result;
	}
}































