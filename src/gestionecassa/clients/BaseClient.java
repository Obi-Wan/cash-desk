/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients;

import gestionecassa.ArticlesList;
import gestionecassa.ConnectionDetails;
import gestionecassa.XmlPreferencesHandler;
import gestionecassa.exceptions.NotExistingSessionException;
import gestionecassa.exceptions.WrongLoginException;
import gestionecassa.server.ServerRMICommon;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

/**
 *
 * @param <ServerType> Type of the server the client will be comunicating with
 * @param <PrefsType> Type of the preferences class that will store preferences
 * @author ben
 */
abstract public class BaseClient
        <ServerType extends ServerRMICommon,
         PrefsType extends BaseClientPrefs>
        extends Thread implements ClientAPI<PrefsType> {
    
    /** Variable that tells to the main thread he has to
     * stop working.
     */
    protected volatile boolean stopApp;

    /**
     * Reference to the central server
     */
    protected ServerType serverCentrale;
    
    /**
     * The ID returned from the server, that we will use
     * to comunicate with it.
     */
    protected ConnectionDetails connectionDetails;

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
     * locally saved list
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
        this.connectionDetails = new ConnectionDetails();
        this.serverCentrale = null;
        this.articles = null;
        this.username = "";
        this.preferences = prefs;
    }

    /**
     * Starts the thread. It's just a wrapper.
     */
    @Override
    public void startClient() {
        super.start();
    }

    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        
        // Start the normal execution
        try {
            while (stopApp == false) {
                Thread.sleep(100);
                this.checkErrors();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
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
            System.out.println(ex.getMessage());
        }
        stopApp = true;
    }

    /**
     * Loads from a file the preferences into the class used at runtime
     */
    protected void loadPreferences() {
        XmlPreferencesHandler<PrefsType> genericXml =
                    new XmlPreferencesHandler<PrefsType>(logger);
        try {
            genericXml.loadPrefs(preferences);

            logger.debug("letto dal file:\nUsername: " + preferences.defaultUsername
                    + " \tServer: " + preferences.defaultServer + "\n");
        } catch (IOException ex) {
            logger.warn("Unable to read data from configfile", ex);
        } catch (DocumentException ex) {
            logger.warn("Parse exception in conf file", ex);
        }
    }

    /**
     * Saves the preferences to a file
     */
    protected void savePreferences() {
        XmlPreferencesHandler<PrefsType> genericXml =
                    new XmlPreferencesHandler<PrefsType>(logger);
        try {
            genericXml.savePrefs(preferences);

        } catch (IOException ex) {
            logger.warn("Unable to write data to configfile", ex);
        }
    }

    /**
     * Returns chosen logger for the application
     * @return a reference to the logger
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns the hostname
     * @return a String containing the hostname
     */
    @Override
    final public String getHostname() {
        return hostname;
    }

    /**
     * Returns the username of the logged user.
     * @return a String containing the username
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
     * @throws WrongLoginException
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    @SuppressWarnings("unchecked")
    protected Remote sendLoginData(String username, String password, String serverName)
            throws WrongLoginException, RemoteException, NotBoundException,
                      MalformedURLException
    {
        try {
            /* Login phase */
            serverCentrale = (ServerType)
                    Naming.lookup("//" + serverName + "/ServerRMI");

            connectionDetails = serverCentrale.doRMILogin(username, password);

            /* quando il client si connette e il server crea il sessionID, il
             * server creer un nuovo thread che si chiama sessionID, il client
             * far poi la lookup al suo sessionID e cos comunica cn il suo
             * thread
             */
            
            return Naming.lookup("//" + serverName + "/Server"
                    + connectionDetails.sessionID);

        } catch (RemoteException ex) {

            logger.warn("RemoteException while connecting",ex);
            throw ex;
        } catch (MalformedURLException ex) {

            logger.warn("The name of the server is wrong: ",ex);
            throw ex;
        } catch (NotBoundException ex) {
            
            logger.warn("NotBoundException while connecting",ex);
            throw ex;
        }
    }

    /**
     * Sets up the work environment at the end of the login
     * @param username The username of the user just connected
     * @throws RemoteException
     */
    protected void setupAfterLogin(String username) throws RemoteException {
        logger.info("Connected with ID: " + connectionDetails.sessionID);

        this.username = username;
        startDaemonConnection();
        try {
            fetchRMIArticlesList();
        } catch (RemoteException ex) {
            logger.warn("Il server non ha risposto alla richiesta della " +
                    "lista beni, subio dopo la connessione", ex);
            
            logout();
        }
    }

    /**
     * Starts the thread deputated to keep connection alive
     */
    private void startDaemonConnection() {
        threadConnessione =
                new DaemonReestablishConnection(serverCentrale,
                                                connectionDetails);
        threadConnessione.start();
    }

    /**
     * Stops the thread deputated to keep connection alive
     */
    private void stopDaemonConnection() {
        if (threadConnessione != null) {
            threadConnessione.interrupt();
        }
    }

    private void cleanConnection() {
        stopDaemonConnection();
        connectionDetails = new ConnectionDetails();
        serverCentrale = null;
        articles = null;
        username = "";
    }

    /**
     * Logs out the current user from the remote app-server
     * @throws RemoteException
     */
    @Override
    public void logout() throws RemoteException {
        try {
            /*Let's stop the service on the server.*/
            if (serverCentrale != null && connectionDetails != null
                    && connectionDetails.sessionID >= 0)
            {
                serverCentrale.closeService(connectionDetails.sessionID);
            }
        } catch (RemoteException ex) {
            logger.warn("Main server down, while trying to close the connection"
                    + "to session with ID: "
                    + Integer.toString(connectionDetails.sessionID), ex);
            throw ex;
        } finally {
            cleanConnection();
        }
    }

    /**
     * Getter for the list of <code>Article</code>s
     * @return List of articles
     */
    @Override
    public ArticlesList getArticlesList() {
        return articles;
    }

    /**
     * Getter for the prefs
     * @return the Object containing the prefs
     */
    @Override
    public PrefsType getPrefs() {
        return preferences;
    }

    /**
     * Setter for the prefs
     * @param prefs the new preferences to set
     */
    @Override
    public void setPrefs(PrefsType prefs) {
        this.preferences = prefs;
    }

    /**
     * 
     * @throws NotExistingSessionException
     * @throws RemoteException 
     */
    protected void checkConnectionErrors()
            throws NotExistingSessionException, RemoteException
    {
        if (threadConnessione != null) {
            switch (threadConnessione.getDaemonState()) {
                case RemoteError: {
                    cleanConnection();
                    
                    RemoteException ex = (RemoteException)
                            threadConnessione.getErrorException();
                    logger.warn("RemoteException nel tentativo di inviare il "
                            + "keepalive", ex);
                    throw ex;
                }
                case NotExistingSessionError: {
                    cleanConnection();
                    
                    NotExistingSessionException ex =
                            (NotExistingSessionException)
                            threadConnessione.getErrorException();
                    logger.warn("KeepAlive not possible: the session in the "
                            + "server has expired", ex);
                    throw ex;
                }
            }
        }
    }
    
}
