/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients;

import gestionecassa.ArticlesList;
import gestionecassa.exceptions.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 * Base interface of clients
 * @author ben
 */
public interface ClientAPI<PrefsType extends BaseClientPrefs> {

    /**
     * Function that starts the thread.
     */
    void startClient();

    /**
     * Returns the chosen logger for the application
     * @return a reference to the logger for the client
     */
    Logger getLogger();

    /**
     * Returns the hostname
     * @return A String containing the Hostname of the server
     */
    String getHostname();

    /**
     * The stopping Method
     */
    void stopClient();

    /**
     * Method that makes LocalBusinessLogic send login data
     * to the server.
     *
     * @param username Username of the user who wants to login.
     * @param password Password of the user who wants to login.
     * @param serverName Hostname of the server.
     *
     * @throws gestionecassa.exceptions.WrongLoginException
     * @throws java.rmi.RemoteException
     * @throws java.rmi.NotBoundException
     */
    public void login(String username, String password, String serverName)
            throws WrongLoginException, RemoteException, NotBoundException;

    /**
     * Logs out the current user from the remote app-server
     * @throws RemoteException
     */
    public void logout() throws RemoteException;
    
    /**
     * Starts the thread deputated to keep connection alive
     */
    public void startDaemonConnection();

    /**
     * Stops the thread deputated to keep connection alive
     */
    public void stopDaemonConnection();

    /**
     * Fetches the remote list of articles
     * @throws RemoteException
     */
    public void fetchRMIArticlesList() throws RemoteException;

    /**
     * Getter for the local copy of the list of articles
     * @return Reference to the local list
     */
    public ArticlesList getArticlesList();

    /**
     * Returns the username of the logged user.
     * @return A String contain the username of the logged user
     */
    String getUsername();

    /**
     * Method to get the preferences of this app
     * @return The object (subclass os BaseClientPrefs) containing the preferences
     */
    PrefsType getPrefs();

    /**
     * Sets the new preferences
     * @param prefs Subclass of BaseClientPrefs containing the new prefs
     */
    void setPrefs(PrefsType prefs);

    /**
     * Returns the chosen logger for the UI objects
     * @return reference to the logger for the UI
     */
    Logger getLoggerUI();
}
