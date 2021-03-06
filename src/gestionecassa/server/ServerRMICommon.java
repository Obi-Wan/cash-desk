/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.ConnectionDetails;
import gestionecassa.exceptions.NotExistingSessionException;
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
    public ConnectionDetails doRMILogin(String username, String password)
            throws   RemoteException, WrongLoginException;
    
    /**
     * Method that tell's the server that the client still
     * lives and is connected.
     *
     * @param sessionID Id of the session to keep alive
     * @throws  RemoteException because we are in RMI context.
     * @throws NotExistingSessionException 
     */
    public void keepAlive(int sessionID)
            throws RemoteException, NotExistingSessionException;

    /**
     * Method that tell's to the thread to shut down.
     *
     * @param sessionID Id of the session to close
     * @throws  RemoteException because we are in RMI context.
     */
    public void closeService(int sessionID) throws RemoteException;
}
