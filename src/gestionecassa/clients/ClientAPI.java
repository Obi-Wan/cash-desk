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
 *
 * @author ben
 */
public interface ClientAPI<PrefsType extends BaseClientPrefs> {

    /**
     * Function that starts the thread.
     */
    void avvia();

    /**
     * Returns the chosen logger for the application
     *
     * @return
     */
    Logger getLogger();

    /**
     * Returns the hostname
     *
     * @return
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
     *
     * @throws java.rmi.RemoteException
     */
    public void logout() throws RemoteException;
    
    /**
     * Starts the thread deputated to keep connection alive
     */
    public void avviaDemoneConnessione();

    /**
     * Stops the thread deputated to keep connection alive
     */
    public void stopDemoneConnessione();

    /**
     * Fetches the remote list of articles
     * @throws RemoteException
     */
    public void fetchRMIArticlesList() throws RemoteException;

    /**
     * 
     * @return
     */
    public ArticlesList getArticlesList();

    /**
     * Returns the username of the logged user.
     *
     * @return
     */
    String getUsername();

    /**
     *
     * @return
     */
    PrefsType getPrefs();

    /**
     *
     * @param options
     */
    void setPrefs(PrefsType options);

    /**
     * Returns the chosen logger for the GUI objects
     *
     * @return
     */
    Logger getLoggerUI();
}
