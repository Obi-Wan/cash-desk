/*
 * GuiOpenTablePanel.java
 * 
 * Copyright (C) 2010 Nicola Roberto Viganò
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
 * GuiOpenTablePanel.java
 *
 * Created on 23-giu-2010, 14.41.46
 */

package gestionecassa.clients.administration.gui;

import javax.swing.DefaultListModel;

/**
 *
 * @author ben
 */
public class GuiOpenTablePanel extends javax.swing.JPanel {

    /**
     * Base model for the visual list
     */
    DefaultListModel listModel;

    /**
     * Refernece to the tabbed pane, for new tabs manipulation
     */
    GuiAppFrameAdministration frame;

    /** Creates new form GuiOpenTablePanel
     * @param frame
     */
    public GuiOpenTablePanel(GuiAppFrameAdministration frame) {
        initComponents();

        this.frame = frame;

        listModel = new DefaultListModel();
        listModel.addElement("Articles");
        listModel.addElement("Events");

        jListTables.setModel(listModel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabelDescript = new javax.swing.JLabel();
    jButtonOpen = new javax.swing.JButton();
    jScrollPaneList = new javax.swing.JScrollPane();
    jListTables = new javax.swing.JList();

    jLabelDescript.setText("Select tables to open");

    jButtonOpen.setText("Apri");

    jListTables.setModel(new javax.swing.AbstractListModel() {
      String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
      public int getSize() { return strings.length; }
      public Object getElementAt(int i) { return strings[i]; }
    });
    jScrollPaneList.setViewportView(jListTables);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPaneList, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabelDescript)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
            .addComponent(jButtonOpen)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelDescript)
          .addComponent(jButtonOpen))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jScrollPaneList, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonOpen;
  private javax.swing.JLabel jLabelDescript;
  private javax.swing.JList jListTables;
  private javax.swing.JScrollPane jScrollPaneList;
  // End of variables declaration//GEN-END:variables

}
