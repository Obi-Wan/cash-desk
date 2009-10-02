/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients;

import gestionecassa.ArticlesList;
import gestionecassa.exceptions.WrongLoginException;
import gestionecassa.server.ServerRMICommon;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
abstract public class BaseClient<PrefsType extends BaseClientPrefs> extends Thread
        implements ClientAPI<PrefsType> {
    
    /** Variable that tells to the main thread he has to
     * stop working.
     */
    protected volatile boolean stopApp;

    /**
     * Reference to the central server
     */
    protected ServerRMICommon serverCentrale;
    
    /**
     * The ID returned from the server, that we will use
     * to comunicate with it.
     */
    protected int sessionID;

    /**
     * Nome identificativo del luogo (hostname)
     */
    protected final String hostname;

    /**
     * Nome identificativo dell'utente
     */
    protected String username;

    /**
     * Chosen Logger for this application
     */
    protected final Logger logger;

    /**
     * Daemon that keeps connection with server alive.
     */
    protected DaemonReestablishConnection threadConnessione;

    /**
     *
     */
    protected ArticlesList articles;

    /**
     *
     */
    protected PrefsType preferences;

    /**
     * Costruttore esplicito che assegna subito il hostname al luogo
     *
     * @param hostname
     * @param prefs 
     * @param logger
     */
    protected BaseClient(String hostname, PrefsType prefs, Logger logger) {
        this.hostname = hostname;
        this.logger = logger;
        this.stopApp = false;
        this.sessionID = -1;
        this.serverCentrale = null;
        this.articles = null;
        this.username = "";
        this.preferences = prefs;
    }

    /**
     * Starts the thread
     */
    @Override
    public void avvia() {
        start();
    }

    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        
        // Comincia l'esecuzione normale
        try {
            while (stopApp == false) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.dumpStack();
        }
        //exit
        System.out.println("sto uscendo dal client");
    }

    /**
     * The stopping Method
     */
    @Override
    public void stopClient() {
        try {
            logout();
        } catch (RemoteException ex) {
            /* mando il messaggio all'utente */
            logger.warn("Disconnessione avvenuta con errore", ex);
            ex.printStackTrace();
        }
        stopApp = true;
    }

    /**
     * Returns chosen logger for the application
     *
     * @return
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns the hostname
     *
     * @return
     */
    @Override
    final public String getHostname() {
        return hostname;
    }

    /**
     * Returns the username of the logged user.
     *
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }
    
    /**
     * Method that makes LocalBusinessLogic send login data
     * to the server.
     * 
     * @param username Username of the user who wants to login.
     * @param password Password of the user who wants to login.
     * @param serverName Hostname of the server.
     * 
     * @return reference to the server.
     * 
     * @throws gestionecassa.exceptions.WrongLoginException
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     */
    protected Remote sendDatiLogin(String username, String password, String serverName)
            throws WrongLoginException, RemoteException, NotBoundException
    {
        try {
            /* Login phase */
            java.rmi.registry.Registry registry =
                    java.rmi.registry.LocateRegistry.getRegistry(serverName);
            
            serverCentrale = (ServerRMICommon) registry.lookup("ServerRMI");

            sessionID = serverCentrale.doRMILogin(username, password);

            /* quando il client si connette e il server crea il sessionID, il
             * server creer un nuovo thread che si chiama sessionID, il client
             * far poi la lookup al suo sessionID e cos comunica cn il suo thread
             */
            return registry.lookup("Server" + sessionID);

        } catch (RemoteException ex) {

            logger.warn("RemoteException nel tentativo di connessione",ex);
            throw ex;
        } catch (NotBoundException ex) {
            
            logger.warn("NotBoundException nel tentativo di connessione",ex);
            throw ex;
        }
    }

    /**
     * Sets up the work environment at the end of the login
     *
     * @throws java.rmi.RemoteException
     */
    protected void setupAfterLogin(String username) throws RemoteException {
        logger.info("Connessione avvenuta con id: " + sessionID);

        this.username = new String(username);
        avviaDemoneConnessione();
        try {
            getRMIArticlesList();
        } catch (RemoteException ex) {
            logger.warn("Il server non ha risposto alla richiesta della " +
                    "lista beni, subio dopo la connessione", ex);
            try {
                logout();
            } catch (RemoteException ex1) {
                logger.warn("Neanche la comunicazione per la disconnessione" +
                        " è andata a buon fine", ex1);
            } finally {
                throw ex;
            }
        }
    }

    /**
     * Starts the thread deputated to keep connection alive
     */
    @Override
    public void avviaDemoneConnessione() {
        threadConnessione =
                new DaemonReestablishConnection(serverCentrale,sessionID,logger);
        threadConnessione.start();
    }

    /**
     * Stops the thread deputated to keep connection alive
     */
    @Override
    public void stopDemoneConnessione() {
        if (threadConnessione != null)
            threadConnessione.interrupt();
    }

    /**
     * Logs out the current user from the remote app-server
     *
     * @throws java.rmi.RemoteException
     */
    @Override
    public void logout() throws RemoteException {
        try {
            /*Let's stop the service on the server.*/
            if (serverCentrale != null && sessionID >= 0) {
                serverCentrale.closeService(sessionID);
            }
        } catch (RemoteException ex) {
            logger.warn("Connessione interrotta in modo brusco", ex);
            throw ex;
        } finally {
            stopDemoneConnessione();
            sessionID = -1;
            serverCentrale = null;
            articles = null;
            username = "";
        }
    }

    /**
     *
     *
     * @return List of goods
     */
    @Override
    public ArticlesList getArticlesList() {
        return articles;
    }

    /**
     *
     * @return
     */
    @Override
    public PrefsType getPrefs() {
        return preferences;
    }

    /**
     *
     * @param preferences
     */
    @Override
    public void setPrefs(PrefsType options) {
        this.preferences = options;
    }
}
