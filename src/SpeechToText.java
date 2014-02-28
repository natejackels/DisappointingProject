
/* Mitch - Remove later
 * Remember to add
 * "lib/sphinx4.jar"
 * "lib/jsapi-1.0-base.jar"
 * "lib/WSJ_8gau_13dCep_8kHz_31mel_200Hz_3500Hz.jar"
 * "lib/WSJ_8gau_13dCep_16kHz_40mel_130Hz_6800Hz.jar"
 *  to classpath
 */
import edu.cmu.sphinx.decoder.ResultListener;
import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.instrumentation.AccuracyTracker;
import edu.cmu.sphinx.instrumentation.SpeedTracker;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.NISTAlign;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;
import edu.cmu.sphinx.util.props.PropertySheet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class: SpeechToText
 * Description: provides an easy to use interface for the Sphinx Speech to Text API
 * @author Stephen J Radachy
 */
public class SpeechToText {

    // link to parent controller
    private Controller parent;
    // instantiation of a recognizer for sphinx
    private STTService sttRecognizer;
    // thread for asynchrous decoding
    private volatile DecodingThread decode;

    /**
     * Method: SpeechToText(Controller w)
     * Description: constructor for SpeechToText class
     * @param w a reference to the parent controller
     * @author Stephen J Radachy
     */
    public SpeechToText(Controller w) {
        parent = w;
        // instantiate and allocate recognizer
        sttRecognizer = new STTService(this.getClass().getResource("hellongram.config.xml"),this.getClass().getResource("hellongram.test"),false);
        sttRecognizer.allocate();
    }

    /**
     * Method: decode()
     * Description: creates and starts a new thread to decode mic input
     * @author Stephen J Radachy
     */
    public void decode() {
        decode = new DecodingThread();
        decode.start();
        //(new DecodingThread()).start();
    }

     /**
     * Method: terminate()
     * Description: properly disposes of all resource needed for Speech to Text
     * @author Stephen J Radachy
     */
    public void terminate() {
        stopRecording();
        sttRecognizer.deallocate();
        decode = null;
        System.gc();
    }
    /**
     * Method: startRecording()
     * Description: Attempts to start recording
     * @return true if recording started successfully, false if it did not
     * @author Stephen J Radachy
     */
    public boolean startRecording() {
        // look into volume control - see bookmarked github repo
        sttRecognizer.getMicrophone().clear();
        sttRecognizer.getMicrophone().startRecording();
        // fix multiple decoderthread stuff
        decode();
        return sttRecognizer.getMicrophone().isRecording();
    }

    /**
     * Method: isRecording()
     * Description: Checks to see if recording
     * @return true if is recording, false if not
     * @author Stephen J Radachy
     */
    public boolean isRecording(){
        return sttRecognizer.getMicrophone().isRecording();
    }


    /**
     * Method: stopRecording()
     * Description: stops recording (only temporary until startrecording is invoked )
     * @author Stephen J Radachy
     */
    public void stopRecording() {
        sttRecognizer.getMicrophone().stopRecording();
        // look into volume control - see bookmarked github repo
    }

    /**
     * Method: sendPacket()
     * Description: sends a STTPacket to the parent (Controller)
     * @param aligner a Sphinx object which pertinent decoded information is stored
     * @author Stephen J Radachy
     */
    private void sendPacket(NISTAlign aligner) {
        String out = aligner.getHypothesis();
        parent.sendPacket(new STTPacket(out));

        // stuff for debugging - eventually integrate into the STTPacket
        /*
        System.err.print(out + " ");
        float wordAccuracy = (aligner.getTotalWordAccuracy() * 100);
        System.err.print(wordAccuracy + "% ");
        float sentenceAccuracy = (aligner.getTotalSentenceAccuracy() * 100);
        System.err.println(sentenceAccuracy + "%");*/

    }

    /**
     * Class: STTService
     * Description: A custom recognizer for the Sphinx API to interface with
     * @author Stephen J Radachy
     */
    class STTService {

        // file locations
        private String configName;
        private String testFile;
        // Sphinx config object
        private ConfigurationManager cm;
        // generic Sphinx stuff
        private Recognizer recognizer;
        private Microphone microphone;
        private SpeedTracker speedTracker;
        private boolean allocated;
        // if true reference text is in a random order
        private boolean randomReferenceOrder;
        // Sphinx output object
        private NISTAlign aligner;

        //recognition stuff
        private List<String> referenceList;
        private Iterator<String> iterator;

        /**
         * Method: STTService(URL configName, URL testFile, boolean randomReferenceOrder)
         * Description: Constructor for STTService
         * @param configName the config file for the recognizer
         * @param testFile the test utterance file
         * @param randomReferenceOrder if true reference text is presented in a random order
         * @author Stephen J Radachy
         */
        public STTService(URL configName, URL testFile, boolean randomReferenceOrder) {
            this.configName = configName.getFile();
            this.testFile = testFile.getFile();
            this.randomReferenceOrder = randomReferenceOrder;
            allocated = false;
        }

        /**
         * Method: getTestFile()
         * Description: Gets the test file in use by this recognizer
         * @return the test file
         * @author Stephen J Radachy
         */
        String getTestFile() {
            return testFile;
        }

        /**
         * Method: allocate
         * Description: Allocates this recognizer
         * @return true if has been allocated, false otherwise
         * @author Stephen J Radachy
         */
        boolean allocate() {
            try {
                if (!allocated) {
                    URL url = new File(configName).toURI().toURL();
                    cm = new ConfigurationManager(url);

                    recognizer = (Recognizer) cm.lookup("recognizer");
                    microphone = (Microphone) cm.lookup("microphone");
                    speedTracker = (SpeedTracker) cm.lookup("speedTracker");
                    aligner = ((AccuracyTracker) cm.lookup("accuracyTracker")).getAligner();
                    recognizer.allocate();
                    setTestFile(testFile);
                    recognizer.addResultListener(new ResultListener() {
                        public void newResult(Result result) {
                            if (result.isFinal()) {
                                sendPacket(sttRecognizer.getAligner());
                                System.out.println(result.getBestToken().getWordUnitPath());
                            }
                        }

                        public void newProperties(PropertySheet ps)
                                throws PropertyException {
                            return;
                        }
                    });
                    allocated = true;
                }

            } catch (PropertyException pe) {
                System.err.println("Can't configure recognizer " + pe);
            } catch (IOException ioe) {
                System.err.println("Can't allocate recognizer " + ioe);
            }
            return allocated;
        }

        /**
         * Deallocates this recognizer
         *
         */
        void deallocate() {
            if (allocated) {
                recognizer.deallocate();
                allocated = false;
            }
        }

        /**
         * Retrieves the microphone object in use by this recognizer
         *
         * @return the microphone
         */
        Microphone getMicrophone() {
            return microphone;
        }

        /**
         * Returns the actual recognizer
         *
         * @return the recognizer
         */
        Recognizer getRecognizer() {
            return recognizer;
        }

        /**
         * Determines if this recognzier has been allocated
         *
         * @return true if the recognizer has been allocated
         */
        boolean isAllocated() {
            return allocated;
        }

        /**
         * Gets the aligner (which tracks accuracy statistics)
         *
         * @return the aligner used by this recognizer
         */
        NISTAlign getAligner() {
            return aligner;
        }

        /**
         * Returns the cumulative speed of this recognizer as a fraction of real
         * time.
         *
         * @return the cumulative speed of this recognizer
         */
        public float getCumulativeSpeed() {
            return speedTracker.getCumulativeSpeed();
        }

        /**
         * Returns the current speed of this recognizer as a fraction of real
         * time.
         *
         * @return the current speed of this recognizer
         */
        public float getSpeed() {
            return speedTracker.getSpeed();
        }

        /**
         * Resets the speed statistics
         *
         */
        public void resetSpeed() {
            speedTracker.reset();
        }

        /**
         * Returns the next utterance in the test file. If its at the last
         * utterance already, it will cycle back to the first utterance. If
         * there is no utterance in the file at all, it will return an empty
         * string.
         *
         * @return the next utterance in the test file; if no utterance, it will
         * return an empty string
         */
        public String getNextReference() {
            String next = "";
            if (randomReferenceOrder) {
                int index = (int) (Math.random() * referenceList.size());
                next = referenceList.get(index);
            } else {
                if (iterator == null || !iterator.hasNext()) {
                    iterator = referenceList.listIterator();
                }
                if (iterator.hasNext()) {
                    next = iterator.next();
                }
            }
            if (next == null) {
                next = "";
            }
            return next;
        }

        /**
         * Sets the file of test utterances to be the given file
         * @param testFile the name of the test file
         */
        void setTestFile(String testFile)  {
            try {
                this.testFile = testFile;
                referenceList = new ArrayList<String>();
                BufferedReader reader = new BufferedReader(new FileReader(testFile));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    referenceList.add(line);
                }
                iterator = referenceList.listIterator();
                reader.close();
            } catch (FileNotFoundException e) {
                System.err.println("Can't find  file " + e);
            } catch (IOException e) {
                System.err.println("Can't read  file " + e);
            }
        }
    }

    class DecodingThread extends Thread {

        /**
         * Constructs a DecodingThread.
         */
        public DecodingThread() {
            super("Decoding");
        }

        /**
         * Implements the run() method of this thread.
         */
        public void run() {
            Microphone microphone = sttRecognizer.getMicrophone();
            Recognizer recognizer = sttRecognizer.getRecognizer();

            while (microphone.hasMoreData()) {
                // just sleep for 500ms so that it won't appear to
                // be flipping through so quickly
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                try {
                recognizer.recognize(sttRecognizer.getNextReference());
                } catch (Exception e){

                }
            }

        }
    }
}
