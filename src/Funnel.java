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
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Funnel {
	public Controller parent;
	private final String saveFile = "funnel.sav";
	private Map<String,String[]> commandMap;
	private boolean debugMode = true;
	private MaxentTagger tagger;
	
	/*Stanford POS tagger
	 * 
        CC Coordinating conjunction
        CD Cardinal number
        DT Determiner
        EX Existential there
        FW Foreign word
        IN Preposition or subordinating conjunction
        JJ Adjective
        JJR Adjective, comparative
        JJS Adjective, superlative
        LS List item marker
        MD Modal
        NN Noun, singular or mass
        NNS Noun, plural
        NNP Proper noun, singular
        NNPS Proper noun, plural
        PDT Predeterminer
        POS Possessive ending
        PRP Personal pronoun
        PRP$ Possessive pronoun
        RB Adverb
        RBR Adverb, comparative
        RBS Adverb, superlative
        RP Particle
        SYM Symbol
        TO to
        UH Interjection
        VB Verb, base form
        VBD Verb, past tense
        VBG Verb, gerund or present participle
        VBN Verb, past participle
        VBP Verb, non�3rd person singular present
        VBZ Verb, 3rd person singular present
        WDT Wh�determiner
        WP Wh�pronoun
        WP$ Possessive wh�pronoun
        WRB Wh�adverb
        
        
        WHAT SHREYA SAID ABOUT TESTING
        Strategy - Not just clicking around
        	usablility
        		what are you testing 
        		who is your user
        		what did they do / how did they do for asked tasks
        Description of how tests are conducted and what came out of it
        	Actual script of what was said (For sprint 3)
        	Sprint 2: Just a paraphrased version
        	
       	Testing in sprint 2: MORE THAN 50% OF GRADE!!!
        
	 */
	
	public Funnel(Controller p) {
		parent = p;
	
		//Stanford tagger
		String directory = System.getProperty("user.dir");
		String taggerModel = directory + "\\src\\lib\\english-bidirectional-distsim.tagger";
		System.out.println(taggerModel);
		tagger = new MaxentTagger(taggerModel);
		
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
					
					//Parse all the commands;
					String[] commandList = human.split(",");
					for (String comm : commandList) {
						commandMap.put(comm, arguments);
					}
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
	
	public void turnDebugMessagesOn() {
		debugMode = true;
		System.out.println("Debug mode turned ON");
	}
	
	public void turnDebugMessagesOff(){
		debugMode = false;
		System.out.println("Debug mode turned OFF");
	}
	
	public void toggleDebugMode() {
		if (debugMode) {
			turnDebugMessagesOff();
		} else {
			turnDebugMessagesOn();
		}
	}
	
	/**Convert a raw string into a string that can be interpreted
	 * by funnel
	 * @param raw Text, that comes from a human mouth
	 * @return a command that can be used with funnel.sav
	 */
	private String convertToCommand(String raw) {
		String tagged = tagger.tagString(raw);
		
		//replace search_NN with search_VB
		tagged = tagged.replace("search_NN", "search_VB");
		
		//if they typed "for" after search, get rid of it
		tagged = tagged.replace("search_VB for_IN", "search_VB");
		raw = raw.replace("search for", "search");
		
		if (debugMode) {
			System.out.println(tagged);
		}
		
		ArrayList<String> verbs = new ArrayList<String>();
		ArrayList<String> subjects = new ArrayList<String>();
		ArrayList<String> prepositions = new ArrayList<String>();
		
		//Split the tagged string into words
		String[] split = tagged.split(" ");
		
		for (String s : split) {
			//Add it to the verbs list if it is a verb
			String[] parts = s.split("_");
			String tag = parts[parts.length-1].toUpperCase();
			String word = parts[0];
			if (tag.equals("VB") || tag.equals("RB")) {
				verbs.add(word);
			} else if (tag.equals("PRP")) {
				prepositions.add(word);
			} else if (tag.equals("NN") || tag.equals("NNS")
					 || tag.equals("NNP")  || tag.equals("NNPS")) {
				subjects.add(word);
			}
		}
		
		System.out.println("Verbs:");
		for (String v : verbs) {
			System.out.println("-" + v);
		}
		System.out.println("Subjects:");
		for (String s : subjects) {
			System.out.println("-" + s);
		}
		System.out.println("Prepositions:");
		for (String p : prepositions) {
			System.out.println("-" + p);
		}
		
		if (debugMode) {
			System.out.println("Possible commands: ");
			for (String s : verbs) {
				System.out.println("-" + s);
			}
		}
		
		/*Check each of the verbs to see if one matches a command*/
		for (String v : verbs) {
			if (debugMode) {
				System.out.println("Attempting command: " + v);
			}
			if (commandMap.get(v)!=null) {
				//Everything after the verb is the arguments
				raw = raw.toLowerCase();
				int verbindex = raw.indexOf(v)+v.length();
				return v + raw.substring(verbindex);
			}
		}
		
		/*At this point, if it didn't find anything, search for ANY keyword that is a command*/
		for (String v : raw.toLowerCase().split(" ")) {
			if (debugMode) {
				System.out.println("Attempting verbless command: " + v);
			}
			if (commandMap.get(v)!=null) {
				return v;
			}
		}
		if (verbs.size() > 0) {
			return verbs.get(0);
		}
		return "";
	}
	
	/**
	 *  This method decodes a string into a packet readable by RobotPacket
	 *	@param		toInterpret		The string to transform into a viable RobotPacket
	 *  @return 					The RobotPacket that details robot's job
	 */
	public RobotPacket decodeVLC(String toInterpret) {
		if (toInterpret.toLowerCase().equals("debug")) {
			toggleDebugMode();
			return null;
		}
		
//		if  (toInterpret.contains("search")) {
//			//If "for" comes after search, remove it
//			toInterpret = toInterpret.replace("search for", "");
//			toInterpret = toInterpret.replace("search","");
//			String[] arg = new String[2];
//			arg[0] = "Google";
//			arg[1] = toInterpret;
//			RobotPacket packet = new RobotPacket("Chrome","Search",arg);
//			return packet;
//		}
		
		if (debugMode) {
			System.out.println("Trying command: " + toInterpret);
		}
		
		toInterpret = convertToCommand(toInterpret);
		String[] splitInterpret = toInterpret.split(" ");
		String action = splitInterpret[0];
		String[] arguments = new String[splitInterpret.length-1];
		for (int i = 0; i < splitInterpret.length-1; i++) {
			arguments[i] = splitInterpret[i+1];
		}
		
		
		/*TODO FINISH PARSING ARGUMETNS LIKE PLAY AND FIND*/
		//Check if this is in the map
		String[] command = commandMap.get(action.toLowerCase());
		if (command == null) {
			System.out.println("Command not found: " + toInterpret);
			System.out.println("Available Commands:");
			for (String sss : commandMap.keySet()) {
				System.out.println(" " + sss);
			}
			return null;
		}
		
		//Combine aruments from template (funnel.sav) and what the user
		//typed in
		String[] args = null;
		if (command.length > 2) {
			
			int numArgsFromFunnelSav = command.length-2;
			int numArgsFromInput = arguments.length;
			
			args = new String[numArgsFromFunnelSav];
			for (int i = 0; i < numArgsFromFunnelSav; i++) {
				args[i] = command[i+2];
			}
			
			//Try to find the file for play, and make that the argument
			if (command[1].equals("Play")) {
				boolean foundFile = false;
				for (String s : arguments) {
					//Try withoug adding .mp3
					if (debugMode) {
						System.out.println("Searching for " + s);
					}
					File f = new File(s);
					if (f.exists()) {
						arguments = new String[] {s};
						foundFile = true;
						break;
					}
					
					//try adding .mp3
					if (debugMode) {
						System.out.println("Searching for " + s + ".mp3");
					}
					f = new File(s + ".mp3");
					if (f.exists()) {
						arguments = new String[] {s + ".mp3"};
						foundFile = true;
						break;
					}
				}
				if (!foundFile) {
					if (debugMode) {
						System.out.println("No files found");
					}
					arguments = new String[0];
				} else {
					if (debugMode) {
						System.out.println("Song: " + arguments[0]);
					}
				}
			}
			
			//If the last argument is a *, replace it with
			//user arguments
			if (args[args.length-1].equals("*")) {
				String combined = "";
				for (int i = 0; i < arguments.length; i++) {
					combined += " " + arguments[i];
				}
				if (combined.length() > 2)
					combined = combined.substring(1);
				
				args[args.length-1] = combined;
			}
		}

		
		if (debugMode) {
			System.out.println("Program: \"" + command[0] + "\"");
			System.out.println("Command: \"" + command[1] + "\"");

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					System.out.println("Arg[" + i + "]: " + args[i]);
				}
			} else {
				System.out.println("Args is null");
			}
		}
		
		RobotPacket packet = new RobotPacket(command[0],command[1],args);
		return packet;
	}

}
