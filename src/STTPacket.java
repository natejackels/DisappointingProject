/**
 * Class: STTPacket
 * Description: A simple wrapper for the output from the Speech to Text API
 * @author Stephen J Radachy
 */
public class STTPacket {
    private String text;
    
    /**
     * Method: STTPacket( String text )
     * Description: Constructor for STTPacket class
     * @param text the output from the Speech to Text API
     * @author Stephen J Radachy
     */
    public STTPacket(String text){
        this.text = text;
    }
    
    /**
     * Method: toString()
     * Description: override for toString method using STT output
     * @return the output from the Speech to Text API
     * @author Stephen J Radachy
     */
    @Override
    public String toString(){
        return text;
    }
    /**
     * Method getText()
     * Description: get STT output text
     * @return the output from the Speech to Text API
     * @author Stephen J Radachy
     */
    public String getText(){
        return text;
    }
}