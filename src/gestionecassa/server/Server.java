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

import gestionecassa.server.datamanager.DataManager;
import gestionecassa.Admin;
import gestionecassa.Cassiere;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import gestionecassa.Log;
import gestionecassa.exceptions.WrongLoginException;
import gestionecassa.server.clientservices.*;
import gestionecassa.backends.BackendAPI_1;
import gestionecassa.backends.BackendAPI_2;
import gestionecassa.backends.PostgreSQLDataBackend;
import gestionecassa.backends.XmlDataBackend;
import org.apache.log4j.Logger;

/** This is the main class of the server side application.
 *
 * @author ben
 */
public class Server extends UnicastRemoteObject 
        implements ServerRMICommon, ServerRMIAdmin {
    
    /**
     * reference to the main local business logic
     */
    public static Server localBLogic;
    
    /**
     * List of the opened sessions
     */
    List<SessionRecord> sessionList;

    /**
     * Semaphore for the list of opened sessions
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String sessionListSemaphore = 
            new String("SessionsSemaphore" + System.currentTimeMillis());
    
    /**
     * reference to the timer and sessions manager.
     */
    ServerTimer timer;
    
    /**
     * reference to the dataManager
     */
    DataManager dataManager;

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
    public synchronized static Server avvia() {
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
        sessionList = new ArrayList<SessionRecord> ();
        timer = new ServerTimer(logger);
        timer.start();

        // this is implementation specific. I will change it if necessary
        BackendAPI_1 fallbackXML = new XmlDataBackend();
        BackendAPI_2 dataBackend = new PostgreSQLDataBackend();
        
        dataManager = new DataManager(dataBackend,fallbackXML);

        java.util.Calendar tempCal = java.util.Calendar.getInstance();
        tempCal.setTime(new java.util.Date());
        final String stringaData = String.format("%1$tY-%1$tm-%1te",tempCal);
        logger.info("Server created: " + stringaData);
    }
    
    /**
     * The stopping Method
     */
    void stopServer() {
        timer.stopServer();
        localBLogic = null;
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
    
    /**
     * The main that initializes and starts the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Server.avvia();
        
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            
            try {
                Naming.rebind("ServerRMI",localBLogic);

                // ora avvia gli altri servizi
                System.out.println("Service Up and Running");

            } catch (MalformedURLException ex) {
                logger.error("l'indirizzo e' sbagliato: ",ex);

                localBLogic.stopServer();
            } catch (RemoteException ex) {
                logger.error("Impossibile accedere al registry: ",ex);

                localBLogic.stopServer();
            }
        } catch (Exception ex) {
            logger.warn("Impossibile avviare un nuovo"+
                    " registry (e' forse gia' in esecuzione?)",ex);
            
            localBLogic.stopServer();
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
    public int sendRMILoginData(String username, String password) 
            throws    RemoteException, WrongLoginException{
        
        SessionRecord record = new SessionRecord();

        /* Controlla che i dati dell'utente siano presenti nel
         * database degli utenti registrati*/
        record.user = dataManager.verifyUsername(username);
        
        if (record.user == null) {
            //questo indica che l'utente non e' stato trovato nel db
            throw new WrongLoginException();
        } //se non esiste restituisco un errore.
        
        /* Prima constrolla che le password coincidano, poi guarda nella lista
         * degli utenti collegati e determina un nuovo session id
         */
        try {
            if (record.user instanceof Cassiere) {
                
                // se la password non e' giusta lo dico al client
                if (!((Cassiere)record.user).getPassword().equals(password))
                    throw new WrongLoginException();
                
                record.userId = ((Cassiere)record.user).getId();
                record.username = new String(username);

                record.serviceThread =
                        new ServiceRMICassiereImpl(record,dataManager,logger);
            } else if(record.user instanceof Admin){
                
                // se la password non e' giusta lo dico al client
                if (!((Admin)record.user).getPassword().equals(password))
                    throw new WrongLoginException();
                
                record.userId = ((Admin)record.user).getId();
                record.username = new String(username);

                record.serviceThread =
                        new ServiceRMIAdminImpl(record,dataManager,logger);
            } else {
                // Se non appartiene a nessuna delle classi di client, errore.
                throw new WrongLoginException();
            }
        } catch (RemoteException ex) {
            logger.error("Errore nell'instanziazione dell'" +
                    "oggetto del working thread",ex);
            throw ex;
        }
        
        record.sessionId = newSession(record);
        
        try {
            Naming.rebind("Server" + record.sessionId, record.serviceThread);
            logger.debug("Registrato nuovo working thread" +
                    " raggiungibile a: /Server" + record.sessionId);
        } catch (MalformedURLException ex) {
            logger.error("L'indirzzo verso cui fare il" +
                    " bind del working thread e' sbagliato",ex);
        } catch (RemoteException ex) {
            logger.error("non e' stato possible registrare" +
                    " la classe del working thread: remote exception",ex);
            throw ex;
        }
        record.serviceThread.start();
        
        return record.sessionId;
    }
    
    /** This method looks for the first free number in sessions list.
     *
     * @param newRecord  the record to verify.
     *
     * @return new sessionId.
     */
    final int newSession(SessionRecord newRecord) {
        synchronized (sessionListSemaphore) {
            int id = sessionList.indexOf(newRecord);
            /* se non lo trova, mi restituisce -1 */
            if (id == -1) {
                /*vedo se esistono posti intermedi liberi.
                  infatti se un thread implode lascia uno spazio libero.*/
                int count = 0;
                for (SessionRecord elem : sessionList) {
                    if (elem.sessionId == -1) {
                        break;
                    }
                    count++;
                }
                id = count;

                /*gli do l'ultimo posto se non trovo posti in mezzo*/
                if (id == sessionList.size()) {
                    sessionList.add(newRecord);
                } else {
                    sessionList.set(id, newRecord);
                }
            } else {
                /*se lo trova vuol dire che si sta riconnettendo..
                 gli assegno lo stesso id*/
                sessionList.set(id, newRecord);
            }
            return id;
        }
    }
    
    /** This method destoryes a record in the sessions' list.
     *
     * @param   session     the session to destroy.
     */
    final void eraseSession(SessionRecord session) {
        synchronized (sessionListSemaphore) {
            session.sessionId = -1;
            session.user = null;
            session.username = new String("");
            session.serviceThread.stopThread();
        }
        logger.debug("Eliminata la sessione scaduta o terminata");
    }
    
    /** Method that tell's the server that the client still
     * lives and is connected.
     *
     * @throws  RemoteException because we are in RMI context.
     */
    @Override
    public void keepAlive(int sessionID) throws  RemoteException{
        synchronized (sessionListSemaphore) {
            sessionList.get(sessionID).timeElapsed = 0;
        }
    }
    
    /** Method that tell's to the thread to shut down.
     *
     * @throws  RemoteException because we are in RMI context.
     */
    @Override
    public void closeService(int sessionID) throws  RemoteException{
        /*timer will do the rest.*/
        synchronized (sessionListSemaphore) {
            eraseSession(sessionList.get(sessionID));
        }
    }
}
