/*
 * WaitingDialog.java
 *
 * Created on 15 gennaio 2007, 11.07
 */

package gestionecassa.clients.gui;

/**
 *
 * @author  ben
 */
public class WaitingDialog extends javax.swing.JDialog {
    
    /** Creates new form WaitingDialog
     * @param parent reference to the parent frame
     * @param modal Whether it's modal or not
     * @param message massage to display
     */
    public WaitingDialog(java.awt.Frame parent, boolean modal,
            String message) {
        super(parent, modal);
        initComponents();
        jLabelMessage.setText(message);
        jProgressBarWorking.setIndeterminate(true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabelMessage = new javax.swing.JLabel();
    jProgressBarWorking = new javax.swing.JProgressBar();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
          .add(org.jdesktop.layout.GroupLayout.LEADING, jProgressBarWorking, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
          .add(org.jdesktop.layout.GroupLayout.LEADING, jLabelMessage, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(layout.createSequentialGroup()
        .addContainerGap()
        .add(jLabelMessage, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(jProgressBarWorking, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabelMessage;
  private javax.swing.JProgressBar jProgressBarWorking;
  // End of variables declaration//GEN-END:variables

}
