/*
 * GuiOrderSingleOptionPanel.java
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
 * GuiOrderSingleOptionPanel.java
 *
 * Created on 23-mag-2009, 14.11.41
 */

package gestionecassa.clients.cassa.gui;

/**
 *
 * @author ben
 */
public class GuiOrderSingleOptionPanel extends GuiAbstrSingleEntryPanel {

    /**
     * 
     */
    int quantity;

    /**
     *
     */
    GuiOrderSingleArticleWOptionsPanel parent;

    /**
     * Creates new form GuiOrderSingleOptionPanel
     *
     * @param parent
     * @param opzioni
     */
    public GuiOrderSingleOptionPanel(GuiOrderSingleArticleWOptionsPanel parent,
            String option, int quantity) {
        initComponents();
        this.parent = parent;
        this.quantity = quantity;

        this.setNumTot(quantity);
        jTextFieldOptionName.setText(option);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jButtonRemove = new javax.swing.JButton();
    jLabelDots = new javax.swing.JLabel();
    jTextFieldQuantity = new javax.swing.JTextField();
    jTextFieldOptionName = new javax.swing.JTextField();

    jButtonRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestionecassa/resources/dialog-no.png"))); // NOI18N
    jButtonRemove.setText("Rimuovi");
    jButtonRemove.setPreferredSize(new java.awt.Dimension(113, 25));
    jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonRemoveActionPerformed(evt);
      }
    });

    jLabelDots.setText(":");

    jTextFieldQuantity.setEditable(false);
    jTextFieldQuantity.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jTextFieldQuantity.setPreferredSize(new java.awt.Dimension(30, 21));

    jTextFieldOptionName.setEditable(false);
    jTextFieldOptionName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jTextFieldOptionName.setText("Nome");
    jTextFieldOptionName.setPreferredSize(new java.awt.Dimension(200, 21));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jButtonRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
        .addComponent(jTextFieldOptionName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabelDots)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jButtonRemove, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(jTextFieldQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(jLabelDots)
        .addComponent(jTextFieldOptionName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
  }// </editor-fold>//GEN-END:initComponents

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        parent.removeOptionPanel(this);
        parent.updateAfterModify();
    }//GEN-LAST:event_jButtonRemoveActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonRemove;
  private javax.swing.JLabel jLabelDots;
  private javax.swing.JTextField jTextFieldOptionName;
  private javax.swing.JTextField jTextFieldQuantity;
  // End of variables declaration//GEN-END:variables

    /**
     * Gets the count of items for the selected option
     *
     * @return
     */
    public int getNumTot() {
        return quantity;
    }

    public void setNumTot(int quantity) {
        this.quantity = quantity;
        jTextFieldQuantity.setText(String.format("%02d", quantity));
    }

    /**
     * Getter for the selected item in combo box
     *
     * @return The selected Option
     */
    public String getComboChoice() {
        return jTextFieldOptionName.getText();
    }

    /**
     * 
     * @param option
     * @return
     */
    public boolean hasSelected(String option) {
        return jTextFieldOptionName.getText().equals(option);
    }
}
