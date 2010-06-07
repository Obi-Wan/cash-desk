/*
 * GuiPreferencesPanel.java
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
 * GuiPreferencesPanel.java
 *
 * Created on 3-lug-2009, 21.31.33
 */

package gestionecassa.clients.gui;

import gestionecassa.clients.ClientAPI;
import gestionecassa.clients.BaseClientPrefs;

/**
 *
 * @author ben
 */
public class GuiPreferencesPanel<PrefsType extends BaseClientPrefs> extends OkCancelPanel {

    /**
     * Local temporary storage of options
     */
    PrefsType tempPrefs;

    /**
     * Reference to the class that ownes this panel
     */
    ClientAPI owner;

    /**
     * Creates new form GuiPreferencesPanel
     * @param owner
     * @param tempPrefs
     */
    public GuiPreferencesPanel(ClientAPI owner, PrefsType tempPrefs) {
        initComponents();

        this.owner = owner;
        this.tempPrefs = tempPrefs;

        init();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanelUser = new javax.swing.JPanel();
    jLabelUser = new javax.swing.JLabel();
    jTextFieldUser = new javax.swing.JTextField();
    jPanelServer = new javax.swing.JPanel();
    jLabelServer = new javax.swing.JLabel();
    jTextFieldServer = new javax.swing.JTextField();

    jLabelUser.setText("Default Username");

    javax.swing.GroupLayout jPanelUserLayout = new javax.swing.GroupLayout(jPanelUser);
    jPanelUser.setLayout(jPanelUserLayout);
    jPanelUserLayout.setHorizontalGroup(
      jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelUserLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabelUser)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jTextFieldUser, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanelUserLayout.setVerticalGroup(
      jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelUserLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelUser)
          .addComponent(jTextFieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jLabelServer.setText("Default Server");

    javax.swing.GroupLayout jPanelServerLayout = new javax.swing.GroupLayout(jPanelServer);
    jPanelServer.setLayout(jPanelServerLayout);
    jPanelServerLayout.setHorizontalGroup(
      jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelServerLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabelServer)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jTextFieldServer, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanelServerLayout.setVerticalGroup(
      jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelServerLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelServerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelServer)
          .addComponent(jTextFieldServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jPanelServer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jPanelUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanelUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanelServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabelServer;
  private javax.swing.JLabel jLabelUser;
  private javax.swing.JPanel jPanelServer;
  private javax.swing.JPanel jPanelUser;
  private javax.swing.JTextField jTextFieldServer;
  private javax.swing.JTextField jTextFieldUser;
  // End of variables declaration//GEN-END:variables

    @Override
    public void apply() {
        tempPrefs.defaultServer = jTextFieldServer.getText();
        tempPrefs.defaultUsername = jTextFieldUser.getText();
        owner.setPrefs(tempPrefs);
    }

    @Override
    public void init() {
        this.jTextFieldServer.setText(tempPrefs.defaultServer);
        this.jTextFieldUser.setText(tempPrefs.defaultUsername);
    }
}