import java.util.*;
import java.io.*;


/**
 * This class handles the settings for the Empower program
 * @author Robin
 */

/*
    List of currnet settings keys

    VLC_PATH        the path to VLC (remember "\" is an escape character)
    CHROME_PATH     the path to chrome (remember "\" is an escape character)
    SEARCH_ENGINE   the current search engine
    MUSIC_LIBRARY   path to music library (remember "\" is an escape character)

    will put in handling for \ later
*/
public class Setting {
    private HashMap<String, ArrayList<String>> Settings = new HashMap<>();
    public Controller parent;
    
    /**
     * Simple constructor for the class.  Sets up the parent and reads
     * the old value of the hashmap at last write.
     * @param w The controller that is creating this object
     */
    public Setting(Controller w) {
        parent = w;
        readSettings();
    }
    
    /**
     * A direct port from the hashmap's get function
     * @param key the key of the value to be retrieved
     * @return The value at that key or null if there is no mapping
     */
    public ArrayList<String> get(String key){
        return Settings.get(key);
    }
    
    /**
     * Near direct port from the hashmap's put function
     * Also writes to file on each alteration of the file
     * 
     * Justification: Settings will not be changed often at all and we can
     *      handle the write delay at those times for the added security of the
     *      file always being up to date
     * @param key The key to put the value at
     * @param val The value to put in the key location
     * @return the previous value or null if there was not mapping for that key
     */
    public void put(String key, ArrayList<String> val){
        Settings.put(key, val);
        writeSettings();
        return;
    }
    
    /**
     * This method writes the contents of the hashmap into the save file.
     * It writes it as a serializeable object to the file.
     * The file is named settings.ser and should always be in the /src directory
     * @return true if the write was successful false if not.
     */
    private boolean writeSettings(){
        try ( OutputStream fOut = new FileOutputStream("settings.ser");
            OutputStream buffer = new BufferedOutputStream(fOut);
            ObjectOutput out = new ObjectOutputStream(buffer);
            ) {
            out.writeObject(Settings);
            out.close();
            buffer.close();
            fOut.close();
        } catch (IOException i){
            i.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * This method reads the contents of the hashmap from the save file
     * @return true if the read was successful false if not
     */
    private boolean readSettings(){
        try (FileInputStream fIn = new FileInputStream("settings.ser");
            InputStream buffer = new BufferedInputStream(fIn);
            ObjectInputStream in = new ObjectInputStream(buffer);
            ) {
            Settings = (HashMap<String, ArrayList<String>>) in.readObject();
            in.close();
            buffer.close();
            fIn.close();
        } catch (IOException | ClassNotFoundException i){
            return false;
        }
        return true;
    }
    
    
}
