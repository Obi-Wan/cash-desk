/*
 * ServerDisposer.java
 * 
 * Copyright (C) 2011 Nicola Roberto Viganò
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gestionecassa.server;

import gestionecassa.Log;
import gestionecassa.XmlPreferencesHandler;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class ServerDisposer implements Runnable {
    
    SessionManager sessionManager;
    
    ServerPrefs prefs;
    
    static Logger logger;
    
    static {
        logger = Log.GESTIONECASSA_SERVER;
    }

    ServerDisposer(SessionManager sessionManager, ServerPrefs prefs) {
        this.sessionManager = sessionManager;
        this.prefs = prefs;
    }

    @Override
    public void run() {
        System.out.print("Server Shutting Down:\n - Closing services... ");

        sessionManager.temrinate();

        System.out.print("done.\n - Saving preferences... ");

        XmlPreferencesHandler<ServerPrefs> xmlHandler =
                new XmlPreferencesHandler<ServerPrefs>(logger);
        try {
            xmlHandler.savePrefs(prefs);
            System.out.println("done.");

        } catch (IOException ex) {
            logger.warn("Error while saving preferences", ex);
            System.out.println("error (read in the log).");
        }


        System.out.print(" - Unbinding from RMI register... ");
        try {
            Naming.unbind("ServerRMI");
            System.out.println("done.");

        } catch (MalformedURLException ex) {
            logger.warn("Error while accessing registry", ex);
            System.out.println("error (read in the log).");
        } catch (AccessException ex) {
            logger.warn("Error while accessing registry", ex);
            System.out.println("error (read in the log).");
        } catch (NotBoundException ex) {
            logger.warn("Error in registry", ex);
            System.out.println("error (read in the log).");
        } catch (RemoteException ex) {
            logger.warn("Error while accessing registry", ex);
            System.out.println("error (read in the log).");
        }

        Server.localBLogic = null;

        java.util.Calendar tempCal = java.util.Calendar.getInstance();
        tempCal.setTime(new java.util.Date());
        final String stringaData = String.format("%1$tY-%1$tm-%1te",tempCal);
        logger.info("Server closed:  " + stringaData);
        System.out.println("Finished.");
    }
    
}
