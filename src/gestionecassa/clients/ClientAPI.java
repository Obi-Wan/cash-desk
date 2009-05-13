/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients;

import gestionecassa.Persona;
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
    public void registra(Persona user, String serverName)
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
     * Starts the thread deputated to keep connection alive
     */
    public void avviaDemoneConnessione();

    /**
     * Stops the thread deputated to keep connection alive
     */
    public void stopDemoneConnessione();
}
