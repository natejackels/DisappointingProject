import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
		switch(cmd){
		case("What"):	//DONE
			String[] canDo = {"The Commands for VLC are" + "Play or Pause", "Play song or artist", "What songs do I have - this will list the songs in your library.", "Open VLC", "Close VLC"};
		return new RobotPacket("Robot", "Display",canDo);
		case("WhatIs"):	//DONE
			String[] vlcIs = {"VLC is a music and video player.", "Unlike other programs, VLC can play nearly any song or video you have on your computer.", "It can also play DVDs and CDs that you put in your computer."};
		return new RobotPacket("Robot", "Display", vlcIs);
		case("Play"):	//DONE
			if((args == null) || (args.length == 0)){
				//Pause if initialized
				if(this.vlc != null){
					return this.pause(cmd, args);
				}
			}
			{
			//open single file or multiple files or empty playlist
			if(args == null){
				args = new String[0];
			}
			String[] vlcparams = new String[args.length + 1];
			vlcparams[0] = "C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe";
			for(int i = 0; i < args.length; i++){
				vlcparams[i+1] = args[i];
			}
			ProcessBuilder pb = new ProcessBuilder(vlcparams);
			try {
				vlc = pb.start();
				return this.sucessful(cmd, args);
			} catch (IOException e) {
				String[] info = {e.getMessage()};
				return new RobotPacket("VLC", "FailedOpen", info);
			}
			}
		case("Pause"):	//DONE
			return pause(cmd, args);
		case("ListSongs"):	//TODO
			if(this.vlcFolder.length() == 0){	//Music location not yet loaded
				String[] need = {"VLCmusic"};
				return new RobotPacket("Robot", "NeedLocation", need);
			}
		if(this.vlcFolder.length() == 0){
			//Music Location not initialized in Control
			String[] e = {"NeedLocation", "VLCmusic"};
			return new RobotPacket("Robot", "BadGetValue", e);
		}
		File folder = new File(this.vlcFolder);
		if(folder.isDirectory()){
			ArrayList<String> e = new ArrayList<String>();
			for(File temp : folder.listFiles()){	//TODO filter files by filetype
				e.add(temp.getName());
			}
			return new RobotPacket("Robot", "Display", (String[])e.toArray());	//TODO Get song Artist / Title from music info
		}

		return null;
		case("Close"):
			if(vlc != null) vlc.destroy();
		//Search for process and kill if not initialized through robot.
		try {
			Runtime.getRuntime().exec("Taskkill /F /IM vlc.exe");	//Kills all VLC processes TODO refine
			this.vlc = null;
			return this.sucessful(cmd, args);
		} catch (IOException e1) {
			return this.failed(cmd, args);
		}
		case("Open"):
			if(vlc == null){
				try{
					ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe");
					vlc = pb.start();
				} catch (Exception e){
					String[] info = {e.getMessage()};
					return new RobotPacket("VLC", "FailedOpen", info);
				}
			}
		return null;
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
	 * Method: successful(String cmd, String[] params)
	 * @author Nathan Jackels
	 * @param cmd The command to be returned within the response packet.
	 * @param params The parameters to be returned within the response packet.
	 * @return The response packet that corresponds to cmd and params.
	 * Description: A helper method to get a response packet that corresponds to cmd and params in the case of a successful execution.
	 */
	private RobotPacket sucessful(String cmd, String[] params){
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
		String[] infoResult = new String[2 + params.length];
		infoResult[0] = "Computer";
		infoResult[1] = cmd;
		for(int i = 0; i < params.length; i++){
			infoResult[i+2] = params[i];
		}
		return new RobotPacket("Robot", "CommandFailed", infoResult);
	}
}
