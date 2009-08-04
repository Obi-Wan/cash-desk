/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients;

import gestionecassa.ArticlesList;
import gestionecassa.exceptions.WrongLoginException;
import gestionecassa.server.ServerRMICommon;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
abstract public class Luogo extends Thread implements ClientAPI {
    
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
    protected ArticlesList listaBeni;

    /**
     *
     */
    protected LuogoOptions options;

    /**
     * Costruttore esplicito che assegna subito il hostname al luogo
     *
     * @param hostname
     */
    protected Luogo(String nome, Logger logger) {
        this.hostname = nome;
        this.logger = logger;
        this.stopApp = false;
        this.sessionID = -1;
        this.serverCentrale = null;
        this.listaBeni = null;
        this.username = "";
        options = new LuogoOptions();
    }

    /**
     * Starts the thread
     */
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
    public Logger getLogger() {
        return logger;
    }

    /**
     * Returns the hostname
     *
     * @return
     */
    final public String getHostname() {
        return hostname;
    }

    /**
     * Returns the username of the logged user.
     *
     * @return
     */
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
     * @throws java.net.MalformedURLException
     * @throws java.rmi.NotBoundException
     */
    protected Remote sendDatiLogin(String username, String password, String serverName)
            throws WrongLoginException, RemoteException, MalformedURLException,
                NotBoundException
    {
        try {
            /* Login phase */
            serverCentrale = (ServerRMICommon)
                Naming.lookup("//" + serverName + "/ServerRMI");
            /* faccio il raise dell'id solo a scopo di debug. */
            sessionID = serverCentrale.sendRMILoginData(username,password);

            /* quando il client si connette e il server crea il sessionID, il
             * server creer un nuovo thread che si chiama sessionID, il client
             * far poi la lookup al suo sessionID e cos comunica cn il suo thread
             */
            return Naming.lookup("//" + serverName + "/Server" + sessionID);

        } catch (RemoteException ex) {

            logger.warn("RemoteException nel tentativo di connessione",ex);
            throw ex;
        } catch (MalformedURLException ex) {

            logger.warn("MalformedURLException nel tentativo di connessione",ex);
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
    public void avviaDemoneConnessione() {
        threadConnessione =
                new DaemonReestablishConnection(serverCentrale,sessionID,logger);
        threadConnessione.start();
    }

    /**
     * Stops the thread deputated to keep connection alive
     */
    public void stopDemoneConnessione() {
        if (threadConnessione != null)
            threadConnessione.interrupt();
    }

    /**
     * Logs out the current user from the remote app-server
     *
     * @throws java.rmi.RemoteException
     */
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
            username = "";
        }
    }

    /**
     *
     *
     * @return List of goods
     */
    public ArticlesList getArticlesList() {
        return listaBeni;
    }

    /**
     *
     * @return
     */
    public LuogoOptions getOptions() {
        return options;
    }

    /**
     *
     * @param options 
     */
    public void setOptions(LuogoOptions options) {
        this.options = options;
    }
}
