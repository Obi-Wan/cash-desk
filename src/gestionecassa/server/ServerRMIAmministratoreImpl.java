/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public class ServerRMIAmministratoreImpl extends SharedServerService
        implements ServerRMIAmministratore {

    /**
     *
     * @param session
     * @param dataManager
     * @throws java.rmi.RemoteException
     */
    ServerRMIAmministratoreImpl(SessionRecord session, DataManager dataManager)
            throws  RemoteException{
        super(session,dataManager);
    }
}
