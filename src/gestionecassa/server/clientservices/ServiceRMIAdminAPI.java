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
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface ServiceRMIAdminAPI extends Remote, Serializable {

    /** Method which both the clients use to register themselves in.
     *
     * @param   user    The user who want's to be registered.
     *
     * @throws RemoteException Throws a remote exception, because we are aon RMI context.
     * @throws ActorAlreadyExistingException Signals if the actor already exists.
     */
    public void sendRMIRegistrationData(Person user)
            throws RemoteException, ActorAlreadyExistingException;


    //--------- Articles API ---------//

    /**
     * Method to get the complete list of Articles
     * @return the list of articles
     * @throws RemoteException
     */
    public ArticlesList getRMIArticlesList() throws RemoteException;

    /**
     * Adds an article to the common list.
     *
     * @param group
     * @param article
     *
     * @throws RemoteException
     * @throws DuplicateArticleException
     * @throws NotExistingGroupException
     */
    void addRMIArticle(int group, Article article) throws RemoteException,
            DuplicateArticleException, NotExistingGroupException;

    /**
     * Adds an article to the common list.
     *
     * @param group
     * @param article
     *
     * @throws RemoteException
     * @throws DuplicateArticleException
     * @throws NotExistingGroupException
     */
    void addRMIArticle(String group, Article article) throws RemoteException,
            DuplicateArticleException, NotExistingGroupException;

    /**
     * Enables/disables an article at the index specified by position.
     *
     * @param group
     * @param position
     * @param enable
     *
     * @throws RemoteException
     */
    void enableRMIArticle(int group, int position, boolean enable)
            throws RemoteException;

    /**
     * Enables/disables the article.
     *
     * @param article
     * @param enable
     *
     * @throws RemoteException
     */
    void enableRMIArticle(Article article, boolean enable) throws RemoteException;

//    /**
//     * Moves an article
//     *
//     * @param oldPos Old position
//     * @param newPos New position
//     *
//     * @throws RemoteException
//     */
//    void moveRMIArticle(int oldPos, int newPos) throws RemoteException;
//
//    /**
//     * Moves the specified article
//     *
//     * @param article Article to move
//     * @param newPos New position
//     *
//     * @throws RemoteException
//     */
//    void moveRMIArticle(Article article, int newPos) throws RemoteException;
}
