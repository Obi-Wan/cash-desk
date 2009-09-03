/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.administration;

import gestionecassa.Article;
import gestionecassa.Log;
import gestionecassa.Person;
import gestionecassa.clients.Luogo;
import gestionecassa.exceptions.ActorAlreadyExistingException;
import gestionecassa.exceptions.WrongLoginException;
import gestionecassa.server.clientservices.ServiceRMIAdminAPI;
import gestionecassa.server.ServerRMIAdmin;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public class Administration extends Luogo implements AdministrationAPI {

    /**
     *
     */
    ServiceRMIAdminAPI server;

    /**
     * Creates a new instance of Administration.
     */
    protected Administration(String nomeLuogo) {
        super(nomeLuogo, Log.GESTIONECASSA_AMMINISTRAZIONE);
    }

    /**
     * Registers the user into the Server's list
     *
     * @param user Data of the user who wants to be registered.
     *
     * @throws gestionecassa.exceptions.ActorAlreadyExistingException
     * @throws java.rmi.RemoteException
     */
    public void registerUser(Person user)
            throws ActorAlreadyExistingException, RemoteException
    {
        try {
            server.sendRMIDatiRegistrazione(user);
            // FIXME ora dovre recuperar la nuova lista di utenti e mostrarla

        } catch (RemoteException e) {

            logger.warn("RemoteException nel tentativo di connessione",e);
            throw e;
        }
    }

    /**
     * Method that makes LocalBusinessLogic send login data
     * to the server.
     *
     * @param username Username of the user who wants to login.
     * @param password Password of the user who wants to login.
     * @param serverName Hostname of the server.
     *
     * @throws gestionecassa.exceptions.WrongLoginException
     * @throws java.rmi.RemoteException
     * @throws java.net.MalformedURLException
     * @throws java.rmi.NotBoundException
     */
    public void login(String username, String password, String serverName)
            throws WrongLoginException, RemoteException, MalformedURLException,
                NotBoundException
    {
        server = (ServiceRMIAdminAPI)
                sendDatiLogin(username, password, serverName);

        setupAfterLogin(username);
    }

    /**
     * 
     * @param username
     * @throws RemoteException
     */
    @Override
    protected void setupAfterLogin(String username) throws RemoteException {
        super.setupAfterLogin(username);

        // fai quel che devi fare
    }

    /**
     * Closes the remote server to which we are connected to
     *
     * @throws java.rmi.RemoteException
     */
    public void stopServer() throws RemoteException {
        if (this.serverCentrale != null && sessionID >= 0) {
            logout();
            try {
                ((ServerRMIAdmin) serverCentrale).remotelyStopServer();
            } catch (RemoteException ex) {
                logger.warn("Errore nella chiusura del server remoto", ex);
            }
        }
    }
    
    /**
     * Requests the list of sold goods
     *
     * @throws java.rmi.RemoteException
     */
    public void getRMIArticlesList() throws RemoteException {
        try {
            listaBeni = server.requestArticlesList();
        } catch (RemoteException ex) {
            logger.warn("Il server non ha risposto alla richiesta della lista",
                    ex);
            throw ex;
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
    public void addRMIArticle(Article article) throws RemoteException {
        server.addRMIArticle(article);
    }

    /**
     * Enables/disables an article at the index specified by position.
     *
     * @param position
     * @param enable
     *
     * @throws RemoteException
     */
    public void enableRMIArticle(int position, boolean enable) throws RemoteException {
        server.enableRMIArticle(position, enable);
    }

    /**
     * Enables/disables the article.
     *
     * @param article
     * @param enable
     *
     * @throws RemoteException
     */
    public void enableRMIArticle(Article article, boolean enable) throws RemoteException {
        server.enableRMIArticle(article, enable);
    }

    /**
     * Moves an article
     *
     * @param oldPos Old position
     * @param newPos New position
     *
     * @throws RemoteException
     */
    public void moveRMIArticle(int oldPos, int newPos) throws RemoteException {
        server.moveRMIArticle(oldPos, newPos);
    }

    /**
     * Moves the specified article
     *
     * @param article Article to move
     * @param newPos New position
     *
     * @throws RemoteException
     */
    public void moveRMIArticle(Article article, int newPos) throws RemoteException {
        server.moveRMIArticle(article, newPos);
    }
}