/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.Article;
import gestionecassa.ArticlesList;
import gestionecassa.Person;
import gestionecassa.exceptions.ActorAlreadyExistingException;
import gestionecassa.exceptions.DuplicateArticleException;
import gestionecassa.exceptions.NotExistingGroupException;
import java.rmi.RemoteException;
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
     * @throws RemoteException
     */
    public ServiceRMIAdminImpl(DMAmministrazioneAPI dataManager, Logger logger)
            throws  RemoteException {
        super(logger);

        this.dataManager = dataManager;
    }

    /**
     * Method to get the complete list of Articles
     * @return the list of articles
     * @throws jRemoteException
     */
    @Override
    public ArticlesList getRMIArticlesList() throws RemoteException {
        return dataManager.getArticlesList();
    }



    //--------- Users API ---------//

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
    @Override
    public void sendRMIDatiRegistrazione(Person user)
            throws    RemoteException, ActorAlreadyExistingException {

        //se lo username non e' presente lo posso registrare.
        if (dataManager.verifyUsername(user.getUsername()) == null) {
            dataManager.registerUser(user);
        } else {
            throw new ActorAlreadyExistingException();
        }
    }

    //--------- Articles API ---------//

    /**
     * Adds an article to the common list.
     *
     * @param article
     *
     * @throws RemoteException
     */
    @Override
    public void addRMIArticle(int group, Article article)
            throws RemoteException, DuplicateArticleException,
                NotExistingGroupException {
        dataManager.addArticle(group, article);
    }

    /**
     * Adds an article to the common list.
     *
     * @param article
     *
     * @throws RemoteException
     */
    @Override
    public void addRMIArticle(String group, Article article)
            throws RemoteException, DuplicateArticleException,
                NotExistingGroupException {
        dataManager.addArticle(group, article);
    }

    /**
     * Enables/disables an article at the index specified by position.
     *
     * @param position
     * @param enable
     *
     * @throws RemoteException
     */
    @Override
    public void enableRMIArticle(int group, int position, boolean enable)
            throws RemoteException {
        dataManager.enableArticle(group, position, enable);
    }

    /**
     * Enables/disables the article.
     *
     * @param article
     * @param enable
     *
     * @throws RemoteException
     */
    @Override
    public void enableRMIArticle(Article article, boolean enable)
            throws RemoteException {
        dataManager.enableArticle(article, enable);
    }

//    /**
//     * Moves an article
//     *
//     * @param oldPos Old position
//     * @param newPos New position
//     *
//     * @throws RemoteException
//     */
//    public void moveRMIArticle(int oldPos, int newPos) throws RemoteException {
//        dataManager.moveArticle(oldPos, newPos);
//    }
//
//    /**
//     * Moves the specified article
//     *
//     * @param article Article to move
//     * @param newPos New position
//     *
//     * @throws RemoteException
//     */
//    public void moveRMIArticle(Article article, int newPos)
//            throws RemoteException {
//        dataManager.moveArticle(article, newPos);
//    }
}
