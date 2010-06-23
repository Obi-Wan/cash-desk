/*
 * GuiStatusCassaPanel.java
 * 
 * Copyright (C) 2009 Nicola Roberto Viganò
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * GuiStatusCassaPanel.java
 *
 * Created on 6-lug-2009, 16.16.23
 */

package gestionecassa.clients.administration.gui;

/**
 *
 * @author ben
 */
public class GuiStatusAdministrationPanel extends javax.swing.JPanel {

    GuiAppFrameAdministration frame;

    /** Creates new form GuiStatusCassaPanel
     * @param frame reference to the frame owning this panel
     */
    public GuiStatusAdministrationPanel(GuiAppFrameAdministration frame) {
        initComponents();
        this.frame = frame;

        enableButtons(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jCheckBoxModifyState = new javax.swing.JCheckBox();
    jSeparator1 = new javax.swing.JSeparator();
    jButtonOpen = new javax.swing.JButton();

    jCheckBoxModifyState.setText("Modalità Modifica");
    jCheckBoxModifyState.setEnabled(false);

    jButtonOpen.setText("Apri..");
    jButtonOpen.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonOpenActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jCheckBoxModifyState, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
          .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
          .addComponent(jButtonOpen))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jCheckBoxModifyState)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButtonOpen)
        .addContainerGap(374, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

    private void jButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenActionPerformed
        frame.openTabelsViewChioce();
    }//GEN-LAST:event_jButtonOpenActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonOpen;
  private javax.swing.JCheckBox jCheckBoxModifyState;
  private javax.swing.JSeparator jSeparator1;
  // End of variables declaration//GEN-END:variables


    /**
     * Enables/Disables all the buttons related to order committing at once
     *
     * @param b true is enalbe, false disable
     */
    final void enableButtons(boolean b) {
        jButtonOpen.setEnabled(b);
    }
}
