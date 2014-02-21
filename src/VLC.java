import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class: VLC
 * @author Nathan Jackels
 * Description: The class that implements controls for VLC 
 */

public class VLC extends Application{
	private Robot robot; //Used to throw information needed things.
	private Process vlc;
	private Keyboard keyboard;
	private Mouse mouse;
	private String vlcFolder;

	/**
	 * Method: VLC(Robot e, Keyboard k, Mouse m)
	 * @author Nathan Jackels
	 * @param e The reference to the robot class
	 * @param k The reference to the keyboard class
	 * @param m The reference to the mouse class
	 * Description: The constructor for VLC
	 */
	public VLC(Robot r, Keyboard k, Mouse m) {
		super("VLC", 1);
		this.robot = r;
		this.keyboard = k;
		this.mouse = m;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Method: interpret(RobotPacket e)
	 * @author Nathan Jackels
	 * @param e The packet that will be interpreted by VLC
	 * @return The result of interpreting e [Usually a Display Command]
	 */
	@Override
	public RobotPacket interpret(RobotPacket e){
		//TODO Check Application
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
		//TODO: Check Process List for VLC
		switch(cmd){
		case("What"):
			String[] canDo = {"The Commands for VLC are" + "Play or Pause", "Play song or artist", "What songs do I have - this will list the songs in your library.", "Open VLC", "Close VLC"};
			return new RobotPacket("Robot", "Display",canDo);
		case("WhatIs"):
			String[] vlcIs = {"VLC is a music and video player.", "Unlike other programs, VLC can play nearly any song or video you have on your computer.", "It can also play DVDs and CDs that you put in your computer."};
			return new RobotPacket("Robot", "Display", vlcIs);
		case("Play"):
			//TODO Full implementation
			if(vlc != null) this.keyboard.mediaPlayButton();
			return null;
		case("Pause"):
			if(vlc != null) this.keyboard.mediaPauseButton();
			return null;
		case("ListSongs"):
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
				Process kill = Runtime.getRuntime().exec("Taskkill /F /IM vlc.exe");	//Kills all VLC processes TODO refine
			} catch (IOException e1) {
			}
			return null;
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
				return new RobotPacket("Robot", "BadGetValue", e);
			} else {
				this.vlcFolder = args[0];
			}
			return null;
		default:
				String[] info = {"InvalidCommand", "VLC"};
				return new RobotPacket("Robot", "BadPacket", info);
		}
		
	}
}
