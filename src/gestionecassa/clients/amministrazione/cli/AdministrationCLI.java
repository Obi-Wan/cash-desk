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

package gestionecassa.clients.amministrazione.cli;

import gestionecassa.clients.amministrazione.Administration;
import gestionecassa.clients.amministrazione.AdministrationAPI;
import gestionecassa.exceptions.WrongLoginException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ben
 */
public class AdministrationCLI extends Administration {

    BufferedWriter ow = new BufferedWriter(new OutputStreamWriter(System.out));
    BufferedReader ir = new BufferedReader(new InputStreamReader(System.in));

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
        try {
            // login / greeting stuff goes here.
            ow.write("Please log in.");
            ow.newLine();
            ow.write("Username: ");
            String inUsername = ir.readLine();
            ow.newLine();
            ow.write("Password: ");
            String password = ir.readLine();
            ow.newLine();
            ow.write("Servername: ");
            String serverName = ir.readLine();
            
            try {
                login(inUsername, password, serverName);

                super.run();
            } catch (WrongLoginException ex) {
                String error = "Wrong login!";
                logger.warn(error, ex);
                ow.newLine();
                ow.write(error);
            } catch (RemoteException ex) {
                String error = "Error in comunicating with the server";
                logger.error(error, ex);
                ow.newLine();
                ow.write(error);
            } catch (MalformedURLException ex) {
                String error = "Wrong URL of the server";
                logger.error(error, ex);
                ow.newLine();
                ow.write(error);
            } catch (NotBoundException ex) {
                String error = "Not existing bound on the server";
                logger.error(error, ex);
                ow.newLine();
                ow.write(error);
            }
        } catch (IOException ex) {
            logger.warn("error in initializing the console: quitting", ex);
        }
    }
}
