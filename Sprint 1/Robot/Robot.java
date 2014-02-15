import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *	Class: Robot
 *	@author Nathan Jackels
 *	Description: The only seen by the application's control class. All Keyboard/Mouse/App work is delegated through this class by Control.
 */
public class Robot {
	private Process vlc = null;
	private String vlcFolder = "";
	private Keyboard keyboard;
	private Mouse mouse;
	private Control control;
	
	/**
	 *	Method: Robot(Control control)
	 *	@author Nathan Jackels
	 *	@param control The reference to control so that packets can be sent back to control.
	 *	@see TSP/Control/Robot_Packet_Design
	 *  Description: The constructor for the class
	 */
	public Robot(Control control){
		//TODO
		this.control = control;
		//Create Keyboard
		//Create Mouse
	}
	
	/**
	 *	Method: sendPacket(RobotPacket e)
	 *	@author Nathan Jackels
	 *	@param e The RobotPacket that will be interpreted by the Robot class
	 *  Description: The method used by Control to delegate work to the Robot class
	 */
	public void sendPacket(RobotPacket e){
		interpret(e.getApplication(), e.getEvent(), e.getInfo());
	}
	
	/**
	 *	Method: sendPacket(String app, String event, String arg)
	 *	@author Nathan Jackels
	 *	@param app The application being referenced in the packet that will be sent
	 *	@param event The event that will be used in the packet that will be sent
	 *	@param arg The argument that will be added to the packet that will be sent
	 *	Description: A helper method to send a packet to control.
	 */
	private void sendPacket(String app, String event, String arg){
		String[] e = {arg};
		control.sendRobotPacket(new RobotPacket(app, event, e));
	}
	
	/**
	 *	Method: sendPacket(String app, String event, String[] args)
	 *	@author Nathan Jackels
	 *	@param app The application being referenced in the packet that will be sent
	 *	@param event The event that will be used in the packet that will be sent
	 *	@param args The array of arguments that will be used in the packet sent to control
	 *	Description: A helper method to send a packet to control.
	 */
	private void sendPacket(String app, String event, String[] args){
		control.sendRobotPacket(new RobotPacket(app, event,args));
	}
	
	/**
	 *	Method: interpret(String prgm, String cmd, String[] args)
	 *	@author Nathan Jackels
	 *	@param prgm The relevant program that the packet was sent for.
	 *	@param cmd The command for the program.
	 *	@param args An array of arguments for interpretation in relation to the program and command.
	 *	Description: A method that sends information from the packets to relevent methods within the class.
	 */
	private void interpret(String prgm, String cmd, String[] args){
		if((prgm == null) || (prgm.length() == 0) || (cmd == null) || (cmd.length() == 0)){
			sendPacket("Robot", "BadPacket", null);
			return;
		}
		if(prgm.equals("VLC")){
			vlc(cmd, args);
		}
	}
	
	/**
	 *	Method: vlc(String cmd, String[] args)
	 *	@author Nathan Jackels
	 *	@param cmd The command for the VLC program.
	 *	@param args An array of arguments for interpretation in relation to the command for vlc.
	 *	Description: A method that interprets commands for the VLC program and carries them out or delegates them further if necessary.
	 */
	private void vlc(String cmd, String[] args){
		//TODO: Check Process List for VLC
		switch(cmd){
		case("What"):
			String[] canDo = {"The Commands for VLC are" + "Play or Pause", "Play song or artist", "What songs do I have - this will list the songs in your library.", "Open VLC", "Close VLC"};
			sendPacket("Robot", "Display",canDo);
			return;
		case("WhatIs"):
			String[] vlcIs = {"VLC is a music and video player.", "Unlike other programs, VLC can play nearly any song or video you have on your computer.", "It can also play DVDs and CDs that you put in your computer."};
			sendPacket("Robot", "Display", vlcIs);
			return;
		case("Play"):
			if(vlc != null) this.keyboard.mediaPlayButton();
			return;
		case("Pause"):
			if(vlc != null) this.keyboard.mediaPauseButton();
			return;
		case("ListSongs"):
			if(this.vlcFolder.length() == 0){	//Music location not yet loaded
				String[] need = {"VLCmusic"};
				sendPacket("Robot", "NeedLocation", need);
			}
			if(this.vlcFolder.length() == 0){
				//Music Location not initialized in Control
				String[] e = {"NeedLocation", "VLCmusic"};
				sendPacket("Robot", "BadGetValue", e);
				return;
			}
			File folder = new File(this.vlcFolder);
			if(folder.isDirectory()){
				ArrayList<String> e = new ArrayList<String>();
				for(File temp : folder.listFiles()){	//TODO filter files by filetype
					e.add(temp.getName());
				}
				sendPacket("Robot", "Display", (String[])e.toArray());	//TODO Get song Artist / Title from music info
			}
			
			return;
		case("Close"):
			if(vlc != null) vlc.destroy();
			//TODO Search for process and kill if not initialized through robot.
			return;
		case("Open"):
			if(vlc == null){
				try{
					ProcessBuilder pb = new ProcessBuilder("C:\\Program Files (x86)\\VideoLAN\\VLC\\vlc.exe");
					vlc = pb.start();
				} catch (Exception e){
					String[] info = {e.getMessage()};
					sendPacket("VLC", "FailedOpen", info);
				}
			}
			return;
		case("MusicFolder"):
			File tempDir = new File(args[0]);
			if(!tempDir.isDirectory()){
				String[] e = {"NeedLocation", "VLCmusic"};
				sendPacket("Robot", "BadGetValue", e);
			} else {
				this.vlcFolder = args[0];
			}
			return;
		default:
				String[] info = {"InvalidCommand", "VLC"};
				sendPacket("Robot", "BadPacket", info);
		}
		
	}
	
}
