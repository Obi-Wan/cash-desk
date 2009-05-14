/*
 * GuiLoginPanel.java
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
 * GuiLoginPanel.java
 *
 * Created on 12-mag-2009, 22.20.16
 */

package gestionecassa.clients;

import gestionecassa.exceptions.WrongLoginException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author ben
 */
public class GuiLoginPanel extends javax.swing.JPanel {

    /**
     * The owner to call for operations
     */
    ClientAPI owner;

    JFrame parent;

    /** Creates new form GuiLoginPanel */
    public GuiLoginPanel(JFrame parent,ClientAPI owner, String nomePostazione) {
        initComponents();
        jTextFieldLuogo.setText(nomePostazione);
        this.owner = owner;
        this.parent = parent;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanelInfo = new javax.swing.JPanel();
    jLabelUsername = new javax.swing.JLabel();
    jTextFieldUsername = new javax.swing.JTextField();
    jLabelPassword = new javax.swing.JLabel();
    jPasswordFieldPassword = new javax.swing.JPasswordField();
    jLabelPostazione = new javax.swing.JLabel();
    jTextFieldLuogo = new javax.swing.JTextField();
    jLabelServer = new javax.swing.JLabel();
    jTextFieldServer = new javax.swing.JTextField();
    jPanelButtons = new javax.swing.JPanel();
    jButtonLogin = new javax.swing.JButton();
    jButtonAnnulla = new javax.swing.JButton();
    jButtonPulisci = new javax.swing.JButton();

    jLabelUsername.setText("Username");

    jLabelPassword.setText("Password");

    jLabelPostazione.setText("Postazione");

    jTextFieldLuogo.setEditable(false);

    jLabelServer.setText("Server");

    javax.swing.GroupLayout jPanelInfoLayout = new javax.swing.GroupLayout(jPanelInfo);
    jPanelInfo.setLayout(jPanelInfoLayout);
    jPanelInfoLayout.setHorizontalGroup(
      jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelInfoLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabelUsername)
          .addComponent(jLabelPassword)
          .addComponent(jLabelPostazione)
          .addComponent(jLabelServer))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
          .addComponent(jTextFieldUsername, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
          .addComponent(jTextFieldLuogo, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
          .addComponent(jTextFieldServer, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
        .addContainerGap())
    );
    jPanelInfoLayout.setVerticalGroup(
      jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelInfoLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelUsername)
          .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelPassword)
          .addComponent(jPasswordFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelPostazione)
          .addComponent(jTextFieldLuogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelServer)
          .addComponent(jTextFieldServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jButtonLogin.setText("Login");
    jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonLoginActionPerformed(evt);
      }
    });

    jButtonAnnulla.setText("Annulla");
    jButtonAnnulla.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonAnnullaActionPerformed(evt);
      }
    });

    jButtonPulisci.setText("Pulisci");
    jButtonPulisci.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonPulisciActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanelButtonsLayout = new javax.swing.GroupLayout(jPanelButtons);
    jPanelButtons.setLayout(jPanelButtonsLayout);
    jPanelButtonsLayout.setHorizontalGroup(
      jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelButtonsLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButtonAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
        .addComponent(jButtonPulisci)
        .addContainerGap())
    );

    jPanelButtonsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonAnnulla, jButtonLogin, jButtonPulisci});

    jPanelButtonsLayout.setVerticalGroup(
      jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelButtonsLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
          .addComponent(jButtonLogin)
          .addComponent(jButtonAnnulla)
          .addComponent(jButtonPulisci))
        .addContainerGap())
    );

    jPanelButtonsLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonAnnulla, jButtonLogin, jButtonPulisci});

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jPanelInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jPanelButtons, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanelInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

    private void jButtonPulisciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPulisciActionPerformed
        jTextFieldUsername.setText("");
        jPasswordFieldPassword.setText("");
        jTextFieldServer.setText("");
}//GEN-LAST:event_jButtonPulisciActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed

        //waiting dialog!!
        JDialog waiting = new WaitingDialog(parent,false,"tring to login");
        waiting.setVisible(true);

        try {
            owner.login(jTextFieldUsername.getText(), 
                    new String(jPasswordFieldPassword.getPassword()),
                    jTextFieldServer.getText());
        } catch (WrongLoginException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Il nome e/o la password immessi non sono validi","Errore",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "RemoteException nel tentativo di connessione","Errore",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (MalformedURLException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "L'URL del server e' sbagliato","Errore",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (NotBoundException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "La classe non e' stata registrata sul server","Errore!",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            waiting.dispose();
        }
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnullaActionPerformed
        parent.dispose();
    }//GEN-LAST:event_jButtonAnnullaActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonAnnulla;
  private javax.swing.JButton jButtonLogin;
  private javax.swing.JButton jButtonPulisci;
  private javax.swing.JLabel jLabelPassword;
  private javax.swing.JLabel jLabelPostazione;
  private javax.swing.JLabel jLabelServer;
  private javax.swing.JLabel jLabelUsername;
  private javax.swing.JPanel jPanelButtons;
  private javax.swing.JPanel jPanelInfo;
  private javax.swing.JPasswordField jPasswordFieldPassword;
  private javax.swing.JTextField jTextFieldLuogo;
  private javax.swing.JTextField jTextFieldServer;
  private javax.swing.JTextField jTextFieldUsername;
  // End of variables declaration//GEN-END:variables

}
