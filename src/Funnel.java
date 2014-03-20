import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *  Class: Funnel
 *  @author Robin McNally
 *  Description: This class funnels the text or speech string into comprehensible commands
 *
 */

public class Funnel {
	public Controller parent;
	private final String saveFile = "funnel.sav";
	private Map<String,String[]> commandMap;
	
	public Funnel(Controller p) {
		parent = p;
	
		commandMap = new HashMap<String,String[]>();
		
		try {
			//Create the save file if it doesnt exist
			File save = new File(saveFile);
			if (!save.exists()) {
				System.out.println("EXTREME ERROR: No funnel.sav found, no commands can be decoded.");
				save.createNewFile();
			}
			
			//Load the save file
			Scanner s = new Scanner(save);
			int command = 0;
			while (s.hasNext()) {
				try {
					command++;
					String line = s.nextLine();
					if (line.length() < 1 || line.charAt(0) == '#') { continue; }
					//Split by tabs
					String[] exploded = line.split("\t");
					
					//Get the key, and value
					String human = exploded[0].toLowerCase();
					String[] arguments = new String[exploded.length-1];
					for (int i = 0; i < exploded.length-1; i++) {
						arguments[i] = exploded[i+1];
					}
					
					commandMap.put(human, arguments);
				} catch (Exception e) {
					System.out.println("Error loading command on line " + command);
				}
			}
			
			if (command < 10) {
				System.out.println("Extreme error: Little to no commands were loaded.");
				System.out.println("Commands may not be understood.  Update funnel.sav");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  This method decodes a string into a packet readable by RobotPacket
	 *	@param		toInterpret		The string to transform into a viable RobotPacket
	 *  @return 					The RobotPacket that details robot's job
	 */
	public RobotPacket decodeVLC(String toInterpret) {
		//Check if this is in the map
		String[] command = commandMap.get(toInterpret.toLowerCase());
		if (command == null) {
			System.out.println("Command not found: " + toInterpret);
			return null;
		}
		
		//Convert this to a robot packet
		String[] args = null;
		if (command.length > 2) {
			args = new String[command.length-2];
			for (int i = 0; i < args.length; i++) {
				args[i] = command[i+2];
			}
		}
		
		System.out.println("Program: \"" + command[0] + "\"");
		System.out.println("Command: \"" + command[1] + "\"");
		
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				System.out.println("Arg[" + i + "]: " + args[i]);
			}
		} else {
			System.out.println("Args is null");
		}
		
		RobotPacket packet = new RobotPacket(command[0],command[1],args);
		return packet;
		/*
		switch (toInterpret){
			case "open vlc":
				RobotPacket openCmd = new RobotPacket("VLC", "Open", null);
				return openCmd;
			case "close vlc":
				RobotPacket closeCmd = new RobotPacket("VLC", "Close", null);
				return closeCmd;
			case "play":
				RobotPacket playCmd = new RobotPacket("VLC", "Play", null);
				return playCmd;
			case "greet":
				TextToSpeechPacket p = new TextToSpeechPacket("Hello! How are you doing");
				parent.tts.send(p);
				return null;
			case "pause":
				RobotPacket pauseCmd = new RobotPacket("VLC", "Pause", null);
				return pauseCmd;
			case "what can i do?":
				RobotPacket whatCmd = new RobotPacket("VLC", "What", null);
				return whatCmd;
			case "what is vlc":
				RobotPacket whatelseCmd = new RobotPacket("VLC", "WhatIs", null);
				return whatelseCmd;
			case "next":
				RobotPacket nextCmd = new RobotPacket("VLC", "Next", null);
				return nextCmd;
			case "prev":
				RobotPacket	prevCmd = new RobotPacket("VLC", "Next", null);
			default:
				if (tempString.contains("play")){
					tempString = tempString.substring(5);
					String[] sent = new String[1];
					sent[0] = tempString;
					RobotPacket alternatePlay = new RobotPacket("VLC", "Play", sent);
					return alternatePlay;
				}
			break;
		}*/
	}


	/**
	 *  This is an implementation of the Levenschtein Distance algorithm which tells us how similar two strings are
	 *	in a quantifiable integer form
	 *  <p>
	 *  For more information see this wiki article (http://en.wikipedia.org/wiki/Levenshtein_distance)
	 *  @param	stringOne	The first of the two strings that will be compared
	 *	@param	stringTwo	The second of the two strings that will be compared
	 *  @return 			an integer that is greater than or equal to zero that denotes the difference between the two strings (the greater the number the larger the difference) 
	 */
	private int LevenschteinDistance(String stringOne, String stringTwo){
		int[][] distArr = new int[stringOne.length()][stringTwo.length()];
		int[] minArr = new int[3];
		for (int i = 0; i < stringOne.length(); i++){
			for (int j = 0; i < stringTwo.length(); j++){
				distArr[i][j] = 0;
			}
		}

		for (int i = 1; i < stringOne.length(); i++){
			distArr[i][0] = i;
		}

		for (int j = 1; j < stringTwo.length(); j++){
			distArr[0][j] = j;
		}

		for (int j = 1; j < stringTwo.length(); j++){
			for (int i = 1; i < stringOne.length(); i++){
				if (stringOne.charAt(i) == stringTwo.charAt(j)){
					distArr[i][j] = distArr[i-1][j-1];
				} else {
					minArr[0] = distArr[i-1][j] + 1;
					minArr[1] = distArr[i][j-1] + 1;
					minArr[2] = distArr[i-1][j-1] + 1;
					distArr[i][j] = minimum(minArr);
				}
			}
		}

		return distArr[stringOne.length() - 1][stringTwo.length() - 1];
	}


	/**
	 *  Extremely simple method.  This takes an integer array and returns the lowest value in it
	 *	@param 	minArr 	an array that we want to get the minimum value of
	 *	@return 		the minimum value in the array
	 */
	private int minimum(int[] minArr){
		int min = minArr[0];
		for (int i = 1; i < minArr.length; i++){
			if (minArr[i-1] > minArr[i]){
				min = minArr[i];
			}
		}
		return min;
	}
}
