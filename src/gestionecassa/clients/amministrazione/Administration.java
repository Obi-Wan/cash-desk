/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.amministrazione;

import gestionecassa.clients.amministrazione.gui.GuiAppFrameAdministration;
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
     * Local store of himself, with restrictions
     */
    static AdministrationAPI businessLogicLocale;

    /**
     *
     */
    ServiceRMIAdminAPI server;

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
        super(nomeLuogo, Log.GESTIONECASSA_AMMINISTRAZIONE,
                Log.GESTIONECASSA_AMMINISTRAZIONE_GUI);
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
        
        super.run();
    }

    /**
     * Registers the user, logs in and returns a usable environment
     *
     * @param user
     * @param serverName
     *
     * @throws gestionecassa.exceptions.ActorAlreadyExistingException
     * @throws gestionecassa.exceptions.WrongLoginException
     * @throws java.rmi.RemoteException
     * @throws java.net.MalformedURLException
     * @throws java.rmi.NotBoundException
     */
    public void registra(Person user, String serverName)
            throws ActorAlreadyExistingException, WrongLoginException,
                RemoteException, MalformedURLException, NotBoundException
    {
        server = (ServiceRMIAdminAPI)
                sendDatiRegistrazione(user, serverName);

        setupAfterLogin(user.getUsername());
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
    public void requestListaBeni() throws RemoteException {
        try {
            listaBeni = server.requestListaBeni();
        } catch (RemoteException ex) {
            logger.warn("Il server non ha risposto alla richiesta della lista",
                    ex);
            throw ex;
        }
    }
}
