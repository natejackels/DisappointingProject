/**
 *  Class: Funnel
 *  @author Robin McNally
 *  Description: This class funnels the text or speech string into comprehensible commands
 *
 */

public class Funnel {
	public Controller parent;
	
	public Funnel(Controller p) {
		parent = p;
	}
	
	/*
		YE OLDE ABSURD COMMAND LIST (valid inputs)

		--more later--
		open vlc
		close vlc
		play
	*/
	public RobotPacket decodeVLC(String toInterpret){
		String tempString = toInterpret;
		switch (tempString){
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
			case "pause":
				RobotPacket pauseCmd = new RobotPacket("VLC", "Pause", null);
				return pauseCmd;
			case "what can i do?":
				RobotPacket whatCmd = new RobotPacket("VLC", "What", null);
				return whatCmd;
			case "what is vlc":
				RobotPacket whatelseCmd = new RobotPacket("VLC", "WhatIs", null);
				return whatelseCmd;
			default:
				if (tempString.contains("play")){
					tempString = tempString.substring(5);
					String[] sent = new String[1];
					sent[0] = tempString;
					RobotPacket alternatePlay = new RobotPacket("VLC", "Play", sent);
					return alternatePlay;
				}
			break;
		}
		return null;
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