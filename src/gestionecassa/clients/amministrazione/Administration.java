/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.amministrazione;

import gestionecassa.clients.amministrazione.gui.GuiAppFrameAdministration;
import gestionecassa.Log;
import gestionecassa.Person;
import gestionecassa.clients.GuiLoginPanel;
import gestionecassa.clients.Luogo;
import gestionecassa.exceptions.ActorAlreadyExistingException;
import gestionecassa.exceptions.WrongLoginException;
import gestionecassa.server.clientservices.ServiceRMIAdminAPI;
import gestionecassa.server.ServerRMIAdmin;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class Administration extends Luogo implements AdministrationAPI {

    /**
     * Local store of himself, with restrictions
     */
    static AdministrationAPI businessLogicLocale;

    /**
     *
     */
    ServiceRMIAdminAPI server;
    
    /**
     * 
     */
    protected final Logger loggerGUI;

    /**
     * 
     */
    protected GuiAppFrameAdministration appFrame;

    /**
     * Creator of the singleton
     *
     * @return reference to the singleton
     */
    public static synchronized AdministrationAPI crea() {
        // Fase di set-up
        if (businessLogicLocale == null) {
            Administration tempClient = new Administration(System.getenv("HOSTNAME"));
            businessLogicLocale = tempClient;
        }
        return businessLogicLocale;
    }

    /**
     * Creates a new instance of Administration.
     */
    private Administration(String nomeLuogo) {
        super(nomeLuogo, Log.GESTIONECASSA_AMMINISTRAZIONE);
        loggerGUI = Log.GESTIONECASSA_AMMINISTRAZIONE_GUI;
    }

    /**
     * The main of the Amministratore Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {

        // cominciamo l'esecuzione del thread principale
        Administration.crea().avvia();

    }

    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        // avvia la fase di login
        appFrame = new GuiAppFrameAdministration(this);

        // concludi fase preparatoria al login
        appFrame.setContentPanel(new GuiLoginPanel(appFrame, this, hostname));
        appFrame.setVisible(true);
        
        super.run();
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
        appFrame.enableLogout(true);
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

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void logout() throws RemoteException {
        appFrame.enableLogout(false);
        listaBeni = null;
        super.logout();
    }

    /**
     * 
     * @return
     */
    public Logger getLoggerGUI() {
        return loggerGUI;
    }
}
