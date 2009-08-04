/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.ArticlesList;
import gestionecassa.Person;
import gestionecassa.exceptions.ActorAlreadyExistingException;
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
    public ArticlesList requestArticlesList() throws RemoteException {
        return dataManager.getCurrentArticlesList();
    }

    /**
     * Method which both the clients use to register themselves in.
     *
     * @param   user    The user who want's to be registered.
     *
     * @throws RemoteException Throws a remote exception, because we are aon RMI context.
     * @throws ActorAlreadyExistingException
     * @throws WrongLoginException
     *
     * @return  The id of the user, which is used in comunication, once logged.
     */
    public void sendRMIDatiRegistrazione(Person user)
            throws    RemoteException, ActorAlreadyExistingException {

        //se lo username non e' presente lo posso registrare.
        if (dataManager.verifyUsername(user.getUsername()) == null) {
            dataManager.registerUser(user);
        } else {
            throw new ActorAlreadyExistingException();
        }
    }
}
