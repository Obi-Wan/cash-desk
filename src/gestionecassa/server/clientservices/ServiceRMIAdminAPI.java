/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.Person;
import gestionecassa.exceptions.ActorAlreadyExistingException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface ServiceRMIAdminAPI
        extends ServiceRMICommon, Remote, Serializable {

    /** Method which both the clients use to register themselves in.
     *
     * @param   user    The user who want's to be registered.
     *
     * @throws RemoteException Throws a remote exception, because we are aon RMI context.
     * @throws ActorAlreadyExistingException Signals if the actor already exists.
     * @throws WrongLoginException
     */
    public void sendRMIDatiRegistrazione(Person user)
            throws   RemoteException, ActorAlreadyExistingException;
}
