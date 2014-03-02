
import edu.cmu.sphinx.result.Result;

/**
 * Class: STTPacket
 * Description: A simple wrapper for the output from the Speech to Text API
 * @author Stephen J Radachy
 */
public class STTPacket {
    private Result ref;
    
    /**
     * Method: STTPacket( NISTAlign ref )
     * Description: Constructor for STTPacket class
     * @param ref object to get relevant
     * @author Stephen J Radachy
     */
    public STTPacket(Result ref){
        this.ref = ref;
    }
    
    /**
     * Method: toString()
     * Description: decoded text
     * @return decoded text
     * @author Stephen J Radachy
     */
    @Override
    public String toString(){
        return ref.getBestFinalResultNoFiller();
    }
    
    /**
     * Method: getText()
     * Description: decoded text
     * @return decoded text
     * @author Stephen J Radachy
     */
    public String getText(){
        return ref.getBestFinalResultNoFiller();
    }
    
    /**
     * Method: getPronunciations()
     * Description: gets decoded text plus pronunciations 
     * @return decoded text plus pronunciations
     * @author Stephen J Radachy
     */
    public String getPronunciations(){
        return ref.getBestPronunciationResult();
    }
    
}