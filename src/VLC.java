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

	/**
	 * Method: VLC(Robot e, Keyboard k, Mouse m)
	 * @author Nathan Jackels
	 * @param k The reference to the keyboard class
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
		if(!e.getApplication().toLowerCase().equals("vlc")){
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
<<<<<<< HEAD
		System.out.println("Passed for loop");
=======
		System.out.println("Reached VLC");
>>>>>>> d006d659eacdcbaa56506ee287c0bb21a646dee0
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
		case("ListSongs"):
			if(args.length == 1){
				return listSongs(cmd, args);
			} else if(args.length%2 == 1){
				String[] arg = {args[0]};
				String[] data = listSongs(cmd, arg).getInfo();
				return playParams(cmd, args, data);
			} else {
				//Error
			}
		case("Close"):
			return close(cmd, args);
		case("Open"):
			return open(cmd, args);
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
	
	/**
	 * Method: playParams(String cmd, String[] args, String[] data)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet 
	 * @param args The list of parameters songs have to meet in order to be played
	 * @param data The compilation of metadata for songs in the folder in question.  
	 * @return A list of song paths that meet the criteria given in args.
	 * Description: A method that returns a RobotPacket to be interpreted for playing the songs that meet the required parameters.
	 */
	private RobotPacket playParams(String cmd, String[] args, String[] data){
		if((data == null) ||(data.length%5 != 0)){
			return this.failed(cmd, args);
		}
		ArrayList<String> toPlay = new ArrayList<String>();
		
		for(int i = 0; i < (data.length/5); i++){ //For each file in data
			for(int j = 1; j < args.length; j+=2){ //For each param in args
				if((args[j] != null) && (args[j+1] != null) && (data[i] != null) &&(data[i+1] != null)){
					if((args[j].equals("artist")) && (data[i+1].contains(args[j+1]))){
						toPlay.add(args[0] + data[i]);
					}
				} else if ((args[j] != null) && (args[j+1] != null) && (data[i] != null) &&(data[i+2] != null)){
					if((args[j].equals("song")) && (data[i+2].contains(args[j+1]))){
						toPlay.add(args[0] + data[i]);
					}
				} else if ((args[j] != null) && (args[j+1] != null) && (data[i] != null) &&(data[i+3] != null)){
					if((args[j].equals("album")) && (data[i+3].contains(args[j+1]))){
						toPlay.add(args[0] + data[i]);
					}
				} else if ((args[j] != null) && (args[j+1] != null) && (data[i] != null) &&(data[i+4] != null)){
					if((args[j].equals("genre")) && (data[i+3].contains(args[j+1]))){
						toPlay.add(args[0] + data[i]);
					}
				}
			}
		}
		
		if(toPlay.size() == 0){
			return this.sucessful(cmd, args);
		}
		
		//Now convert to play command
		String[] songs = toPlay.toArray(null);
		RobotPacket playSongs = new RobotPacket("VLC", "Play", songs);
		return this.interpret(playSongs);
	}

	/**
	 * Method: what()
	 * @author Nathan Jackels
	 * @return A RobotPacket that returns the commands the user can control VLC with.
	 * Description: A method that contains a description of what commands the user can control VLC with.
	 */
	private RobotPacket what(){
		String[] canDo = {"The Commands for VLC are" + "Play or Pause", "Play song or artist", "What songs do I have - this will list the songs in your library.", "Open VLC", "Close VLC"};
		return new RobotPacket("Robot", "Display",canDo);
	}
	
	/**
	 * Method: whatIs()
	 * @author Nathan Jackels
	 * @return A RobotPacket that returns a description of what VLC does.
	 * Description: A method that contains a description of what VLC does.
	 */
	private RobotPacket whatIs(){
		String[] vlcIs = {"VLC is a music and video player.", "Unlike other programs, VLC can play nearly any song or video you have on your computer.", "It can also play DVDs and CDs that you put in your computer."};
		return new RobotPacket("Robot", "Display", vlcIs);
	}
	
	/**
	 * Method: close(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param args The arguments to be returned within the response packet.
	 * @return A RobotPacket that reflects the result of calling this method.
	 * Description: A method that closes all instances of VLC.
	 */
	private RobotPacket close(String cmd, String[] args){
		try{
			Runtime.getRuntime().exec("Taskkill /F /IM vlc.exe");
			return this.sucessful(cmd, args);
		} catch (IOException e){
			return this.failed(cmd, args);
		}
	}
	
	/**
	 * Method: play(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param args The list of songs to be added to the playlist of VLC.
	 * @return A RobotPacket that reflects the result of calling this method.
	 * Descriptions: A method that adds songs to an instance of VLC, or creates it if none are found.
	 */
	private RobotPacket play(String cmd, String[] args){
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
	
	/**
	 * Method: listSongs(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param args The String array that includes the folder location.
	 * @return A RobotPacket that contains a String array of metadata for files in the folder from args
	 * Description: A helper method to get the metadata of files within a folder.
	 */
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
	
	/**
	 * Method: open(String cmd, String[] args)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param args The arguments to be returned within the response packet.
	 * @return A RobotPacket that corresponds to the result of calling this method.
	 * Description: A method that opens VLC and reports on its success.
	 */
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

	/**
	 * Method: vlcExists()
	 * @author Nathan Jackels
	 * @return Returns true if a process that contains 'vlc.exe' is running, false otherwise.
	 * Description: A helper method for determining if VLC is already running.
	 */
	private boolean vlcExists(){
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
			String directory = System.getProperty("user.dir");
			Runtime.getRuntime().exec("cmd.exe /C start " + directory + "\\src\\RobotScripts\\pause.vbs");
			System.out.println("Success");
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
		infoResult[0] = "VLC";
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
		infoResult[0] = "VLC";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "CommandFailed", infoResult);
	}
	
	/**
	 * Method: getMediaInfo(File song)
	 * @author Nathan Jackels
	 * @param song The song whose metadata will be extracted
	 * @return A map of the metadata within the song
	 * Description: A helper method to get the metadata from a song.
	 */
	private static Map<String, String> getMediaInfo(File song){
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
