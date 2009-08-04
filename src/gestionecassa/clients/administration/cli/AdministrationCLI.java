/*
 * AdministrationCLI.java
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

package gestionecassa.clients.administration.cli;

import gestionecassa.clients.administration.Administration;
import gestionecassa.clients.administration.AdministrationAPI;
import gestionecassa.exceptions.WrongLoginException;
import java.io.Console;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public class AdministrationCLI extends Administration {

    /**
     * 
     */
    Console c = System.console();

    /**
     * Constructor
     * 
     * @param nomeLuogo
     */
    protected AdministrationCLI(String nomeLuogo) {
        super(nomeLuogo);
    }

    /**
     * Creator of the administration process
     *
     * @return reference to the main class
     */
    public static AdministrationAPI crea() {
        return new AdministrationCLI(System.getenv("HOSTNAME"));
    }

    /**
     * The main of the Amministratore Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {

        // cominciamo l'esecuzione del thread principale
        AdministrationCLI.crea().avvia();

    }


    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        // login / greeting stuff goes here.
        String inUsername = c.readLine("Please log in.\nUsername: ");
        String password = new String(c.readPassword("Password: "));
        String serverName = c.readLine("Servername: ");

        try {
            login(inUsername, password, serverName);

            //super.run();
        } catch (WrongLoginException ex) {
            String error = "Wrong login!";
            logger.warn(error, ex);
            c.writer().println(error);
        } catch (RemoteException ex) {
            String error = "Error in comunicating with the server";
            logger.error(error, ex);
            c.writer().println(error);
        } catch (MalformedURLException ex) {
            String error = "Wrong URL of the server";
            logger.error(error, ex);
            c.writer().println(error);
        } catch (NotBoundException ex) {
            String error = "Not existing bound on the server";
            logger.error(error, ex);
            c.writer().println(error);
        }
    }

    @Override
    protected void setupAfterLogin(String username) throws RemoteException {
        super.setupAfterLogin(username);

        printMainMenu();
    }

    private void printMainMenu() {
        String mainMenu = "You can now choose between these alternatives:\n" +
                " - a - Show/Modify list of Articles\n" +
                " - q - Quit\n" +
                "Choice: ";
        char choice;
        do {
            choice = c.readLine(mainMenu).charAt(0);
            switch (choice) {
                case 'a': {
                    break;
                }
            }
        } while(choice != 'q');

        this.stopClient();
    }
}
