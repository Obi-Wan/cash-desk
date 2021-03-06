/*
 * AdministrationGUI.java
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

package gestionecassa.clients.administration.gui;

import gestionecassa.clients.ClientAPI;
import gestionecassa.clients.administration.AdminPrefs;
import gestionecassa.clients.gui.GuiLoginPanel;
import gestionecassa.clients.administration.Administration;
import gestionecassa.clients.administration.AdministrationAPI;
import gestionecassa.clients.gui.GuiAppFrame.MessageType;
import java.net.UnknownHostException;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public class AdministrationGUI extends Administration implements ClientAPI<AdminPrefs> {

    /**
     *
     */
    protected GuiAppFrameAdministration appFrame;

    protected AdministrationGUI(String nomeLuogo) {
        super(nomeLuogo);
    }

    /**
     * Creator of the administration process
     *
     * @return reference to the main class
     */
    public static AdministrationAPI crea() {
        String hostname = new String();
        try {
            hostname = java.net.InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            hostname = System.getProperty("user.name") + "@" +
                    System.getProperty("os.name");
        }
        return new AdministrationGUI(hostname);
    }

    /**
     * The main of the Amministratore Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {

        // cominciamo l'esecuzione del thread principale
        AdministrationGUI.crea().startClient();

    }

    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        // preparing for execution
        loadPreferences();

        // startClient la fase di login
        appFrame = new GuiAppFrameAdministration(this);

        // concludi fase preparatoria al login
        appFrame.setContentPanel(new GuiLoginPanel(appFrame, this));
        appFrame.setVisible(true);

        // esecuzione principale
        super.run();

        // fine esecuzione
        savePreferences();
    }

    @Override
    protected void setupAfterLogin(String username) throws RemoteException {
        super.setupAfterLogin(username);
        
        appFrame.setupAfterLogin(username);
    }

    @Override
    public void logout() throws RemoteException {
        super.logout();
        
        appFrame.setdownAfterLogout();
    }

    @Override
    protected void showMessage(String msg, MessageType type) {
        appFrame.showMessageDialog(msg, type);
    }
}
