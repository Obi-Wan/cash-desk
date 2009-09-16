/*
 * OfflineAdminForm.java
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
 * OfflineAdminForm.java
 *
 * Created on 16-set-2009, 19.20.13
 */

package gestionecassa.offlineAdministration;

/**
 *
 * @author ben
 */
public class OfflineAdminForm extends javax.swing.JFrame {

    OfflineAdmin blogic;

    /** Creates new form OfflineAdminForm */
    public OfflineAdminForm(OfflineAdmin blogic) {
        initComponents();
        this.blogic = blogic;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jToolBar1 = new javax.swing.JToolBar();
    jButtonConnect = new javax.swing.JButton();
    jTextFieldUrl = new javax.swing.JTextField();
    jLabelStatus = new javax.swing.JLabel();
    jScrollPaneMain = new javax.swing.JScrollPane();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jToolBar1.setRollover(true);

    jButtonConnect.setText("Connect");
    jButtonConnect.setFocusable(false);
    jButtonConnect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButtonConnect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar1.add(jButtonConnect);

    jTextFieldUrl.setMaximumSize(new java.awt.Dimension(400, 100));
    jToolBar1.add(jTextFieldUrl);
    jToolBar1.add(jLabelStatus);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
      .addComponent(jScrollPaneMain, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    @Override
    public void dispose() {
        blogic.stopClient();
        super.dispose();
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonConnect;
  private javax.swing.JLabel jLabelStatus;
  private javax.swing.JScrollPane jScrollPaneMain;
  private javax.swing.JTextField jTextFieldUrl;
  private javax.swing.JToolBar jToolBar1;
  // End of variables declaration//GEN-END:variables

}
