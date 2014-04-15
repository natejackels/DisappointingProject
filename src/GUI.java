/**
 *
 * @author njfisher
 */
public class GUI extends javax.swing.JFrame {

    // EDIT 3/1/14 by Stephen J Radachy
    // removed boolean recording
    public Controller parent;
    String commandToSend = "";  //String to be passed to control after typed in
    boolean debugMode = true;

    public GUI(Controller W) {
        parent = W;
        
        initComponents();
        this.setVisible(true);
         if (debugMode == false){
             debugBorder.setVisible(false);
             debugLabel.setVisible(false);
             debugLabelOutput.setVisible(false);
    }
        
    }

   
    // EDIT 3/1/14 by Stephen J Radachy
    // added method
    /**
     * Method: sendDebugText(String text) 
     * Description: used to set debug text externally ( for speech to text )
     * @param text Text to be used
     * @author Stephen J Radachy
     */
    public void sendDebugText(String text) {
        debugLabelOutput.setText(text);
    }

    
     /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exitButton = new javax.swing.JButton();
        debugBorder = new javax.swing.JPanel();
        debugLabel = new javax.swing.JLabel();
        debugLabelOutput = new javax.swing.JLabel();
        mainBorder = new javax.swing.JPanel();
        titleHeader = new javax.swing.JLabel();
        inputText = new javax.swing.JLabel();
        commandField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        recordToggleButton = new javax.swing.JToggleButton();
        settingsJB = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        debugBorder.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        debugLabel.setText("DEBUG: Last command sent =");

        javax.swing.GroupLayout debugBorderLayout = new javax.swing.GroupLayout(debugBorder);
        debugBorder.setLayout(debugBorderLayout);
        debugBorderLayout.setHorizontalGroup(
            debugBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(debugBorderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(debugLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(debugLabelOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        debugBorderLayout.setVerticalGroup(
            debugBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(debugBorderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(debugBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(debugBorderLayout.createSequentialGroup()
                        .addComponent(debugLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(debugLabelOutput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        mainBorder.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        titleHeader.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        titleHeader.setText("Empower");

        inputText.setText("Input Command");

        commandField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandFieldActionPerformed(evt);
            }
        });

        sendButton.setText("Send Command");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        recordToggleButton.setText("Start Recording");
        recordToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordToggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainBorderLayout = new javax.swing.GroupLayout(mainBorder);
        mainBorder.setLayout(mainBorderLayout);
        mainBorderLayout.setHorizontalGroup(
            mainBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainBorderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainBorderLayout.createSequentialGroup()
                        .addComponent(recordToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 90, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainBorderLayout.createSequentialGroup()
                        .addGroup(mainBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(commandField)
                            .addGroup(mainBorderLayout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(titleHeader)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton)))
                .addContainerGap())
        );
        mainBorderLayout.setVerticalGroup(
            mainBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainBorderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainBorderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(commandField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(recordToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        settingsJB.setText("Settings");
        settingsJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsJBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(settingsJB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exitButton)
                .addContainerGap())
            .addComponent(debugBorder, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainBorder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainBorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitButton)
                    .addComponent(settingsJB))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(debugBorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void commandFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandFieldActionPerformed
        sendButton.doClick();
    }//GEN-LAST:event_commandFieldActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        commandToSend = commandField.getText();
        debugLabelOutput.setText(commandToSend);
        commandField.setText("");
        parent.sendPacket(new GUIPacket(commandToSend));
        
        // EDIT 3/1/14 by Stephen J Radachy
        // I think you shouldn't need to differentiate between recorded and text commands
        /*if (parent.stt.isRecording()) {
         recordToggleButton.doClick();
         debugLabelOutput.setText("Command sent while still recording");
         }*/

        //TODO send commandToSend to control
    }//GEN-LAST:event_sendButtonActionPerformed

    private void recordToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recordToggleButtonActionPerformed
        // EDIT 3/1/14 by Stephen J Radachy
        // added recording functionality and replaced boolean "recording" with an internal speech to text version
        if (!parent.stt.isRecording()) {
            
            if (parent.stt.startRecording()){
                recordToggleButton.setText("Stop Recording");
                debugLabelOutput.setText("Recording Started");
            }
        } else {

            parent.stt.stopRecording();
            if (!parent.stt.isRecording()){
                recordToggleButton.setText("Start Recording");
                debugLabelOutput.setText("Recording Ended");
            }
            //TODO Send recorded information to control
        }
    }//GEN-LAST:event_recordToggleButtonActionPerformed
    //EXIT PROGRAM
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        // EDIT 3/1/14 by Stephen J Radachy
        // terminate Speech to text properly
        parent.stt.terminate();
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void settingsJBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsJBActionPerformed
        parent.sendPacket(new GUIPacket("settings"));
    }//GEN-LAST:event_settingsJBActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField commandField;
    private javax.swing.JPanel debugBorder;
    private javax.swing.JLabel debugLabel;
    private javax.swing.JLabel debugLabelOutput;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel inputText;
    private javax.swing.JPanel mainBorder;
    private javax.swing.JToggleButton recordToggleButton;
    private javax.swing.JButton sendButton;
    private javax.swing.JButton settingsJB;
    private javax.swing.JLabel titleHeader;
    // End of variables declaration//GEN-END:variables
}
