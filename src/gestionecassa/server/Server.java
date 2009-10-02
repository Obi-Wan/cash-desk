/*
 * Server.java
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
 * Server.java
 *
 * Created on 17 gennaio 2007, 13.12
 */

package gestionecassa.server;

import gestionecassa.exceptions.NotExistingSessionException;
import gestionecassa.server.datamanager.DataManager;
import gestionecassa.Admin;
import gestionecassa.Cassiere;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import gestionecassa.Log;
import gestionecassa.XmlPreferencesHandler;
import gestionecassa.exceptions.WrongLoginException;
import gestionecassa.server.clientservices.*;
import gestionecassa.backends.BackendAPI_1;
import gestionecassa.backends.BackendAPI_2;
import gestionecassa.backends.PostgreSQLDataBackend;
import gestionecassa.backends.XmlDataBackend;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

/** This is the main class of the server side application.
 *
 * @author ben
 */
public class Server extends UnicastRemoteObject 
        implements ServerRMICommon, ServerRMIAdmin, Runnable {
    
    /**
     * reference to the main local business logic
     */
    public static Server localBLogic;

    /**
     * boolean that says when to stop the app.
     */
    private boolean stopApp;
    
    /**
     * reference to the sessionMngr and sessions manager.
     */
    SessionManager sessionMngr;
    
    /**
     * reference to the dataManager
     */
    DataManager dataManager;

    /**
     * Server stored prefs
     */
    ServerPrefs prefs;

    /**
     * 
     */
    static Registry registry;

    /**
     * 
     */
    static Logger logger;

    static {
        logger = Log.GESTIONECASSA_SERVER;
    }
    
    /**
     * Method that grants the creation of a singleton.
     */
    public synchronized static Server getInstance() {
        if (localBLogic == null) {
            try {
                localBLogic = new Server();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        return localBLogic;
    }
    
    /**
     * Creates a new instance of Server
     */
    private Server() throws  RemoteException{
        prefs = new ServerPrefs();
        XmlPreferencesHandler<ServerPrefs> xmlHandler =
                new XmlPreferencesHandler<ServerPrefs>(logger);
        try {
            xmlHandler.loadPrefs(prefs);
        } catch (IOException ex) {
            logger.warn("Error while loading preferences", ex);
        } catch (DocumentException ex) {
            logger.warn("Error while processing preferences", ex);
        }

        sessionMngr = new SessionManager(logger);

        // this is implementation specific. I will change it if necessary
        BackendAPI_1 fallbackXML = new XmlDataBackend();
        BackendAPI_2 dataBackend = new PostgreSQLDataBackend();
        
        dataManager = new DataManager(dataBackend, prefs.dbUrl, fallbackXML);

        java.util.Calendar tempCal = java.util.Calendar.getInstance();
        tempCal.setTime(new java.util.Date());
        final String stringaData = String.format("%1$tY-%1$tm-%1te",tempCal);
        logger.info("Server created: " + stringaData);
    }

    /**
     * Stops the server on this machine.
     *
     * @throws java.rmi.RemoteException
     */
    @Override
    public void remotelyStopServer() throws RemoteException {
        stopServer();
    }

    /** Main of the thread: cycle that every minute updates
     * passing of time */
    @Override
    public void run() {
        try {
            while (stopApp != true) {
                Thread.sleep(1000);
                sessionMngr.tick();
            }
        } catch (InterruptedException ex) {
            String errorMsg = "Il server e' stato interrottoda una " +
                    "InterruptedException";
            logger.warn(errorMsg, ex);
            System.out.println(errorMsg);
        } finally {
            System.out.print("Server Shutting Down.. ");

            XmlPreferencesHandler<ServerPrefs> xmlHandler =
                    new XmlPreferencesHandler<ServerPrefs>(logger);
            try {
                xmlHandler.savePrefs(prefs);
            } catch (IOException ex) {
                logger.warn("Error while saving preferences", ex);
            }

            System.out.println("Done.");
        }
    }

    /** The stopping Method */
    void stopServer() {
        stopApp = true;
        localBLogic = null;
    }
    
    /**
     * The main that initializes and starts the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (localBLogic == null) {

            try {
                registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

                try {
                    registry.rebind("ServerRMI", Server.getInstance());

                    new Thread(Server.getInstance()).start();

                    // We are now done.
                    System.out.println("Service Up and Running");
                    
                } catch (RemoteException ex) {
                    logger.error("Impossibile accedere al registry: ",ex);

                    localBLogic.stopServer();
                }
            } catch (Exception ex) {
                String errorMsg = "Impossibile avviare un nuovo"+
                        " registry (e' forse gia' in esecuzione?)";
                logger.warn(errorMsg, ex);
                System.out.println(errorMsg);
            }
        } else {
            System.out.println("Another Instance of the Server is running");
        }
    }
    
    /**
     * Method which both the clients use to log themselves in.
     *
     * @param   username    username through which they can access to their own data.
     * @param   password    the password used by the user.
     *
     * @throws RemoteException Throws a remote exception, because we are aon RMI context.
     * @throws WrongLoginException
     *
     * @return  The id of the user, which is used in comunication, once logged.
     */
    @Override
    public int doRMILogin(String username, String password)
            throws RemoteException, WrongLoginException{
        
        SessionRecord record = new SessionRecord();

        /* Checks whether user's data are in the DB or not.
         */
        record.user = dataManager.verifyUsername(username);

        /* Tests if this connection should be rejected
         */
        if (record.user == null
                || !record.user.getPassword().equals(password)
                || (sessionMngr.verifySession(record) && !prefs.kickOffOnNewSession)
            )
        {
            /* questo indica che l'utente non e' stato trovato nel db,
             * che la password non è giusta oppure che l'utente è già loggato
             * e noi non permettiamo il kick off, quindi restituisco un errore.
             */
            throw new WrongLoginException();
        }
        
        /* Instantiates the service thread that will serve the client's requests
         */
        instantiateServiceThread(record);

        /* Register new session, and if the case kick off any preceding session
         */
        if (prefs.kickOffOnNewSession) {
            sessionMngr.kickOff(record);
        }
        record.sessionId = sessionMngr.newSession(record);

        /* Bind the new service to an RMI socket, and finally start the service
         */
        bindService(record);
        record.serviceThread.start();
        
        return record.sessionId;
    }

    /**
     * Tries to instantiate the working thread that serves the requests of the
     * clients
     *
     * @param record Session record describing the service
     * @throws WrongLoginException
     * @throws RemoteException
     */
    private void instantiateServiceThread(SessionRecord record)
            throws WrongLoginException, RemoteException {
        try {
            if (record.user instanceof Cassiere) {

                record.serviceThread =
                        new ServiceRMICassiereImpl(record.getUsername(),
                                                   prefs.trustOrders,
                                                   dataManager, logger);
            } else if (record.user instanceof Admin){

                record.serviceThread =
                        new ServiceRMIAdminImpl(dataManager, logger);
            } else {
                // Se non appartiene a nessuna delle classi di client, errore.
                throw new WrongLoginException();
            }
        } catch (RemoteException ex) {
            logger.error("Errore nell'instanziazione dell'" +
                    "oggetto del working thread",ex);
            throw ex;
        }
    }

    /**
     * Tries to bind the service thread of the given session to an RMI socket
     * 
     * @param record SessionRecord that describes the new service.
     * @throws RemoteException In case it fails
     */
    private void bindService(SessionRecord record) throws RemoteException {
        try {
            registry.rebind("Server" + record.sessionId, record.serviceThread);
//            logger.debug("Registrato nuovo working thread" +
//                    " raggiungibile a: /Server" + record.sessionId);
        } catch (RemoteException ex) {
            logger.error("non e' stato possible registrare" +
                    " la classe del working thread: remote exception",ex);
            throw ex;
        }
    }
    
    /** Method that tell's the server that the client still
     * lives and is connected.
     *
     * @throws  RemoteException because we are in RMI context.
     */
    @Override
    public void keepAlive(int sessionID) throws  RemoteException {
        try {
            sessionMngr.keepAlive(sessionID);
        } catch (NotExistingSessionException ex) {
            logger.warn("Sessione con quell'id inesistente", ex);
            throw new RemoteException("Sessione con quell'id inesistente", ex);
        }
    }
    
    /** Method that tell's to the thread to shut down.
     *
     * @throws  RemoteException because we are in RMI context.
     */
    @Override
    public void closeService(int sessionID) throws  RemoteException {
        try {
            sessionMngr.closeService(sessionID);
        } catch (NotExistingSessionException ex) {
            logger.warn("Sessione con quell'id inesistente, era magari già" +
                    " stata chiusa", ex);
        }
    }
}
