/*
 * GuiToolbarAdministrationPanel.java
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
 * GuiToolbarAdministrationPanel.java
 *
 * Created on 6-lug-2009, 15.51.25
 */

package gestionecassa.clients.administration.gui;

/**
 *
 * @author ben
 */
public class GuiToolbarAdministrationPanel extends javax.swing.JPanel {

    GuiAppFrameAdministration owner;

    /** Creates new form GuiToolbarAdministrationPanel */
    public GuiToolbarAdministrationPanel(GuiAppFrameAdministration owner) {
        initComponents();
        this.owner = owner;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jToolBarMain = new javax.swing.JToolBar();
    jButtonLogout = new javax.swing.JButton();
    jSeparator1 = new javax.swing.JToolBar.Separator();
    jButtonOptions = new javax.swing.JButton();

    jToolBarMain.setFloatable(false);
    jToolBarMain.setRollover(true);

    jButtonLogout.setText("Logout");
    jButtonLogout.setFocusable(false);
    jButtonLogout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButtonLogout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButtonLogout.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonLogoutActionPerformed(evt);
      }
    });
    jToolBarMain.add(jButtonLogout);
    jToolBarMain.add(jSeparator1);

    jButtonOptions.setText("Opzioni");
    jButtonOptions.setFocusable(false);
    jButtonOptions.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButtonOptions.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButtonOptions.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonOptionsActionPerformed(evt);
      }
    });
    jToolBarMain.add(jButtonOptions);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jToolBarMain, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jToolBarMain, javax.swing.GroupLayout.PREFERRED_SIZE, 25, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents

    private void jButtonLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogoutActionPerformed
        owner.logout();
}//GEN-LAST:event_jButtonLogoutActionPerformed

    private void jButtonOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOptionsActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_jButtonOptionsActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  protected javax.swing.JButton jButtonLogout;
  protected javax.swing.JButton jButtonOptions;
  protected javax.swing.JToolBar.Separator jSeparator1;
  protected javax.swing.JToolBar jToolBarMain;
  // End of variables declaration//GEN-END:variables

    /**
     * Enables or disables logout button.
     *
     * @param value
     */
    public void enableLogout(boolean value) {
        jButtonLogout.setEnabled(value);
    }
}