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

import gestionecassa.clients.ClientAPI;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @param <ClientTypeAPI> Specifies the type of the client
 * @author ben
 */
abstract public class GuiAppFrame<ClientTypeAPI extends ClientAPI>
        extends javax.swing.JFrame {

    /**
     * The baseClient of this frame.
     */
    protected ClientTypeAPI baseClient;

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
     * @param baseClient
     */
    public GuiAppFrame(ClientTypeAPI baseClient) {
        initComponents();
        this.baseClient = baseClient;

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
        baseClient.getLoggerUI().info("sto chiudendo l'interfaccia.");

        baseClient.stopClient();
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

    public JPanel getContentPanel() {
        return (JPanel)jScrollPanelMain.getViewport().getView();
    }
    
    /**
     * Enables or disables logout button.
     *
     * @param value
     */
    public final void enableLogout(boolean value) {
        toolbar.enableSessionButtons(value);
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
            baseClient.logout();
        } catch (RemoteException ex) {
            this.showMessageDialog( "Il server non ha risposto nel tentativo " +
                   "di chiuder la connessione", MessageType.ErrorComunication);
        }
    }

    /**
     * Sets up the gui after a successful login
     * @param username String containing the username of the logged user
     */
    public void setupAfterLogin(String username) {
        this.enableLogout(true);
    }

    /**
     * Resets the components that are no longer legal in that state after logout
     */
    public void setdownAfterLogout() {
        this.enableLogout(false);
        this.setContentPanel(new GuiLoginPanel(this, baseClient));
    }


    /* Messages to the user */

    public enum MessageType {
        ErrorGeneric,
        ErrorComunication,
        ErrorServerBackend,
        ErrorList,
        WarningGeneric,
        InformationGeneric,
        InformationTerminatedOp,
    }

    /**
     * Method to show messages to the user
     * @param msg message to show
     * @param type type of the message
     */
    public void showMessageDialog(String msg, MessageType type) {
        switch (type) {
            case ErrorGeneric:
                javax.swing.JOptionPane.showMessageDialog(this,
                    msg, "Errore", javax.swing.JOptionPane.ERROR_MESSAGE);
                break;
            case ErrorComunication:
                javax.swing.JOptionPane.showMessageDialog(this,
                    msg, "Errore di comunicazione",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                break;
            case ErrorServerBackend:
                javax.swing.JOptionPane.showMessageDialog(this,
                    msg, "Errore nel Backend del server",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                break;
            case ErrorList:
                javax.swing.JOptionPane.showMessageDialog(this,
                    msg, "Errore nella lista degli articoli",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                break;
            case WarningGeneric:
                javax.swing.JOptionPane.showMessageDialog(this,
                    msg, "Attenzione",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
                break;
            case InformationGeneric:
                javax.swing.JOptionPane.showMessageDialog(this,
                    msg, "Informazione",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                break;
            case InformationTerminatedOp:
                javax.swing.JOptionPane.showMessageDialog(this,
                    msg, "Operazione terminata",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                throw new RuntimeException("wrong message type");
        }
    }

    /**
     * Method to ask questions to the user
     * @param msg message to show
     * @param title title of the window
     * @param type type of the message
     * @return the answer of the user
     */
    public int showConfirmDialog(String msg, String title, MessageType type) {
        switch (type) {
            case WarningGeneric:
                return javax.swing.JOptionPane.showConfirmDialog(this,
                    msg, title, javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            default:
                throw new AssertionError();
        }
    }
}
