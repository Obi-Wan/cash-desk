/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.exceptions.WrongLoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface ServerRMICommon extends Remote {

    /** Method which both the clients use to log themselves in.
     *
     * @param   username    username through which they can access
     * to their own data.
     * @param   password    the password used by the user.
     *
     * @throws RemoteException Throws a remote exception, because we are aon RMI context.
     * @throws WrongLoginException 
     *
     * @return  The id of the user, which is used in comunication, once logged.
     */
    public int sendRMILoginData(String username, String password) 
            throws   RemoteException, WrongLoginException;
    
    /**
     * Method that tell's the server that the client still
     * lives and is connected.
     *
     * @throws  RemoteException because we are in RMI context.
     */
    public void keepAlive(int sessionID) throws RemoteException;

    /**
     * Method that tell's to the thread to shut down.
     *
     * @throws  RemoteException because we are in RMI context.
     */
    public void closeService(int sessionID) throws RemoteException;

    /**
     * @param sessionID 
     * 
     * @throws RemoteException
     *
     * returns the id of the user in the table
     */
    public int getIdTable(int sessionID) throws RemoteException;
}
