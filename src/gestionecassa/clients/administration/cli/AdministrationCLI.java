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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Command Line Interface driven application for administering data and users
 * on the server.
 * 
 * @author ben
 */
public class AdministrationCLI extends Administration {

    /**
     * The system console, that makes it possible to interact with the user.
     */
    Console con = System.console();

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
    public static AdministrationAPI getInstance() {
        return new AdministrationCLI(System.getenv("HOSTNAME"));
    }

    /**
     * The main of the Amministratore Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {

        // cominciamo l'esecuzione del thread principale
        AdministrationCLI.getInstance().startClient();

    }


    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        // login / greeting stuff goes here.
        printGreeting();
        printMainMenu();
    }

    /**
     * Does the class specific operations after a successful login.
     *
     * @param username The username of the just logged in Admin.
     *
     * @throws RemoteException
     */
    @Override
    protected void setupAfterLogin(String username) throws RemoteException {
        super.setupAfterLogin(username);

        printMainAdminMenu();
    }

    /**
     * Once logged in, it shows the admin possibilities of the admin, and
     * manages them.
     * 
     * @throws RemoteException
     */
    private void printMainAdminMenu() throws RemoteException {
        String menu = "You can now choose between these alternatives:\n" +
                " - a - Show/Modify list of Articles\n" +
                " - u - Show/Modify list of Users\n" +
                " - l - Logout\n" +
                "Choice: ";
        char choice;
        do {
            choice = con.readLine(menu).charAt(0);
            switch (choice) {
                case 'a': {
                    CLIAdminArtices temp = new CLIAdminArtices(this, con);
                    temp.printMenuGroups();
                    break;
                }
                case 'l': {
                    con.writer().print("Logging out.. ");
                    break;
                }
                default: {
                    con.writer().println("Wrong input.\n");
                }
            }
        } while(choice != 'l');

        this.logout();

        con.writer().println("done!\n");
    }

    /**
     * Prints the main menu, and reacts on user input.
     */
    private void printMainMenu() {
        String menu = "You can now choose between these alternatives:\n" +
                " - l - Login\n" +
                " - o - Options\n" +
                " - q - Quit\n" +
                "Choice: ";
        char choice;
        do {
            choice = con.readLine(menu).charAt(0);
            switch (choice) {
                case 'l': {
                    startLogin();
                    break;
                }
                case 'o': {
                    break;
                }
                default: {
                    
                }
            }
        } while(choice != 'q');

        this.stopClient();

    }

    /**
     * Collects info and logs into the server.
     */
    private void startLogin() {
        String inUsername = con.readLine("\nPlease log in.\nUsername: ");
        String password = new String(con.readPassword("Password: "));
        String serverName = con.readLine("Servername: ");

        try {
            login(inUsername, password, serverName);

            //super.run();
        } catch (WrongLoginException ex) {
            String error = "Wrong login!";
            logger.warn(error, ex);
            con.writer().println(error);
        } catch (RemoteException ex) {
            String error = "Error in comunicating with the server";
            logger.error(error, ex);
            con.writer().println(error);
        } catch (NotBoundException ex) {
            String error = "Not existing bound on the server";
            logger.error(error, ex);
            con.writer().println(error);
        }
    }

    /**
     * Just prints the Greeting message on the screen.
     */
    private void printGreeting() {
        String greeting = "This is the CLI administrative client of Cash-Desk" +
                " project.";
        con.writer().println(greeting);
    }
}
