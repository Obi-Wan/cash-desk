/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients;

import gestionecassa.ArticlesList;
import gestionecassa.Person;
import gestionecassa.exceptions.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public interface ClientAPI {

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
     * Returns the chosen logger for the GUI objects
     *
     * @return
     */
    Logger getLoggerGUI();

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
     * Method that makes LocalBusinessLogic send registration data
     * to the server.
     *
     * @param user Data of the user who wants to be registered.
     * @param serverName Hostname of the server
     *
     * @throws gestionecassa.exceptions.ActorAlreadyExistingException
     * @throws gestionecassa.exceptions.WrongLoginException
     * @throws java.rmi.RemoteException
     * @throws java.net.MalformedURLException
     * @throws java.rmi.NotBoundException
     */
    public void registra(Person user, String serverName)
            throws ActorAlreadyExistingException, WrongLoginException,
                RemoteException, MalformedURLException, NotBoundException;

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
     * @throws java.net.MalformedURLException
     * @throws java.rmi.NotBoundException
     */
    public void login(String username, String password, String serverName)
            throws WrongLoginException, RemoteException, MalformedURLException,
                NotBoundException;

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
     *
     *
     * @throws java.rmi.RemoteException
     */
    public void requestListaBeni() throws RemoteException;

    /**
     * 
     * @return
     */
    public ArticlesList getListaBeni();

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
    LuogoOptions getOptions();

    /**
     *
     * @param options
     */
    void setOptions(LuogoOptions options);
}
