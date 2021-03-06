/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.administration;

import gestionecassa.Article;
import gestionecassa.Person;
import gestionecassa.clients.ClientAPI;
import gestionecassa.exceptions.ActorAlreadyExistingException;
import gestionecassa.exceptions.DuplicateArticleException;
import gestionecassa.exceptions.NotExistingGroupException;
import gestionecassa.exceptions.WrongLoginException;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface AdministrationAPI extends ClientAPI<AdminPrefs> {

    /**
     * Closes the remote server to which we are connected to
     *
     * @throws java.rmi.RemoteException
     */
    void stopServer() throws RemoteException;

    //--------- Users API ------------//

    /**
     * Method that makes LocalBusinessLogic send registration data
     * to the server.
     *
     * @param user Data of the user who wants to be registered.
     *
     * @throws gestionecassa.exceptions.ActorAlreadyExistingException
     * @throws gestionecassa.exceptions.WrongLoginException
     * @throws java.rmi.RemoteException
     */
    public void registerUser(Person user)
            throws ActorAlreadyExistingException, WrongLoginException,
                RemoteException;

    //--------- Articles API ---------//

    /**
     * Adds an article to the common list.
     *
     * @param group
     * @param article
     * @throws RemoteException
     * @throws DuplicateArticleException
     * @throws NotExistingGroupException
     */
    void addRMIArticle(int group, Article article)
            throws RemoteException, DuplicateArticleException,
                NotExistingGroupException;

    /**
     * Enables/disables an article at the index specified by position.
     *
     * @param position
     * @param enable
     * @param group 
     *
     * @throws RemoteException
     */
    void enableRMIArticle(int group, int position, boolean enable) throws RemoteException;

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
