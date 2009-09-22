/*
 * GuiAppFrame.java
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
 * GuiAppFrame.java
 *
 * Created on 14-mag-2009, 21.18.51
 */

package gestionecassa.clients.gui;

import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author ben
 */
abstract public class GuiAppFrame<Owner extends GUIClientAPI>
        extends javax.swing.JFrame {

    /**
     * The owner of this frame.
     */
    protected Owner owner;

    /**
     * 
     */
    protected JScrollPane jScrollPanelMain;
    /**
     *
     */
    protected GuiToolbarPanel toolbar;
    
    /**
     * Creates new form GuiAppFrame
     * 
     * @param owner
     */
    public GuiAppFrame(Owner owner) {
        initComponents();
        this.owner = owner;

        jScrollPanelMain = new javax.swing.JScrollPane();

        toolbar = new GuiToolbarPanel(this);

        enableLogout(false);
        
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

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 533, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 417, Short.MAX_VALUE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables


    /**
     * This method destroyes his form and tells the app to close.
     */
    @Override
    public void dispose() {
        owner.getLoggerGUI().info("sto chiudendo l'interfaccia.");

        owner.stopClient();
        super.dispose();
    }

    /**
     * Sets the new panel into the work area
     *
     * @param content
     */
    public void setContentPanel(JPanel content) {
        
        jScrollPanelMain.setViewportView(content);
        GuiHelper.adaptContainerSize(jScrollPanelMain, content);
        GuiHelper.packAndCenter(this);
    }

    /**
     * re applies the panel to the ScrollPane to make it react on changes
     */
    public void refreshContentPanel() {
        jScrollPanelMain.setViewportView(
                jScrollPanelMain.getViewport().getView());
    }
    
    /**
     * Enables or disables logout button.
     *
     * @param value
     */
    public void enableLogout(boolean value) {
        toolbar.enableLogout(value);
    }

    /**
     *
     */
    abstract public void selectedDialogOptions();

    /**
     *
     */
    public void logout() {
        try {
            owner.logout();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Il server non ha risposto nel tentativo di chiuder la connessione",
                "Errore di comunicazione",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     */
    public void setdownAfterLogout() {
        this.enableLogout(false);
        this.setContentPanel(new GuiLoginPanel(this, owner));
    }
}
