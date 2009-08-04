/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.amministrazione;

import gestionecassa.Person;
import gestionecassa.clients.ClientAPI;
import gestionecassa.exceptions.ActorAlreadyExistingException;
import gestionecassa.exceptions.WrongLoginException;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface AdministrationAPI extends ClientAPI {

    /**
     * Closes the remote server to which we are connected to
     *
     * @throws java.rmi.RemoteException
     */
    void stopServer() throws RemoteException;

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
    public void registerUser(Person user)
            throws ActorAlreadyExistingException, WrongLoginException,
                RemoteException;
}
