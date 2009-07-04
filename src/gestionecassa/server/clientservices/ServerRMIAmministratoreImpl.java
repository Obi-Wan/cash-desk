/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.ListaBeni;
import java.rmi.RemoteException;
import gestionecassa.server.SessionRecord;
import gestionecassa.server.datamanager.DMAmministrazioneAPI;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class ServerRMIAmministratoreImpl extends SharedServerService
        implements ServerRMIAmministratore {

    /**
     * 
     */
    DMAmministrazioneAPI dataManager;

    /**
     *
     * @param session
     * @param dataManager
     * @throws java.rmi.RemoteException
     */
    public ServerRMIAmministratoreImpl(SessionRecord session, DMAmministrazioneAPI dataManager,
            Logger logger)
            throws  RemoteException{
        super(session,logger);

        this.dataManager = dataManager;
    }

    /**
     * 
     * @return
     * @throws RemoteException
     */
    public ListaBeni requestListaBeni() throws RemoteException {
        return dataManager.getCurrentListaBeni();
    }
}
