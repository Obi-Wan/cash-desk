/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.ListaBeni;
import gestionecassa.Log;
import gestionecassa.Persona;
import gestionecassa.clients.GuiAppFrame;
import gestionecassa.clients.Luogo;
import gestionecassa.exceptions.*;
import gestionecassa.server.ServerRMICassiere;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ben
 */
public class Cassa extends Luogo implements CassaAPI {

    /**
     * Local store of himself, with restrictions
     */
    static CassaAPI businessLogicLocale;

    /**
     * Specific Server
     */
    ServerRMICassiere server;

    /**
     * Public method that grants the singleton
     * 
     * @return
     */
    public static synchronized CassaAPI crea() {
        // Fase di set-up
        if (businessLogicLocale == null) {
            Cassa tempClient = new Cassa(System.getenv("HOSTNAME"));
            businessLogicLocale = tempClient;
        }
        return businessLogicLocale;
    }

    /**
     * Creates a new instance of Cassa.
     */
    private Cassa(String nomeLuogo) {
        super(nomeLuogo, Log.GESTIONECASSA_CASSA, Log.GESTIONECASSA_CASSA_GUI);
    }

    /**
     * The main of the Cassiere Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {

        // cominciamo l'esecuzione del thread principale
        Cassa.crea().avvia();

    }
    
    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        // avvia la fase di login
        appFrame = new GuiAppFrameCassa(this);

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
    public void registra(Persona user, String serverName)
            throws ActorAlreadyExistingException, WrongLoginException,
                RemoteException, MalformedURLException, NotBoundException
    {
        server = (ServerRMICassiere)
                sendDatiRegistrazione(user, serverName);

        setupAfterLogin();
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
        server = (ServerRMICassiere)
                sendDatiLogin(username, password, serverName);

        setupAfterLogin();
    }

    @Override
    protected void setupAfterLogin() {
        super.setupAfterLogin();

        appFrame.setContentPanel(new GuiNuovoOrdinePanel());
    }

    /**
     * Requests the list of sold goods
     *
     * @return List of goods
     *
     * @throws java.rmi.RemoteException
     */
    public ListaBeni requestListaBeni() throws RemoteException {
        try {
            return server.requestListaBeni();
        } catch (RemoteException ex) {
            logger.warn("Il server non ha risposto alla richiesta della lista",
                    ex);
            throw ex;
        }
    }
}
