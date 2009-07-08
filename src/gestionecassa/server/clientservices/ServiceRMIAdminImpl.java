/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.ArticlesList;
import java.rmi.RemoteException;
import gestionecassa.server.SessionRecord;
import gestionecassa.server.datamanager.DMAmministrazioneAPI;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class ServiceRMIAdminImpl extends SharedServerService
        implements ServiceRMIAdminAPI {

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
    public ServiceRMIAdminImpl(SessionRecord session, DMAmministrazioneAPI dataManager,
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
    public ArticlesList requestListaBeni() throws RemoteException {
        return dataManager.getCurrentArticlesList();
    }
}
