/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author sjradach
 */
public class RecordIndicator extends javax.swing.JPanel {
    private boolean alt = true;
    private volatile DrawThread draw;
    /**
     * Creates new form NewJPanel
     */
    public RecordIndicator() {
        initComponents();
        draw = new DrawThread(this);
        draw.start();
    }
    
    @Override
    protected void paintComponent(Graphics g){
        int h = getHeight();
        int w = getWidth();
        super.paintComponent(g);
        
        int sw = (int) g.getFontMetrics().getStringBounds("Rec", g).getWidth();
        int sh = (int) g.getFontMetrics().getStringBounds("Rec", g).getHeight();

        if (alt){
            g.setColor(new Color(6684672));
        } else {
            g.setColor(Color.black);
        }
        g.fillOval(1, 1, w-2, h-2);
        g.setColor(Color.white);
        g.drawString("Rec", w/2 - sw/2, h/2 + sh/4 + sh/20);
    }
    
    class DrawThread extends Thread {
        RecordIndicator sup;
        /**
         * Method: DrawThread Description: constructor for DrawThread
         *
         * @author Stephen J Radachy
         */
        public DrawThread(RecordIndicator sup) {
            super("Draw");
            this.sup = sup;
        }

        /**
         * Method: run Description: asynchronous loop
         *
         * @author Stephen J Radachy
         */
        @Override
        public void run() {
            while (true){
            try{
                sup.alt = !sup.alt;
                sup.repaint();
                Thread.sleep(500);
                
            }   catch (InterruptedException e){
                e.printStackTrace();
                break;
            }
        }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}