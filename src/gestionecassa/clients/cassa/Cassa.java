/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.Log;
import gestionecassa.Persona;
import gestionecassa.clients.Luogo;
import gestionecassa.exceptions.*;
import gestionecassa.server.ServerRMICassiere;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

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
        super(nomeLuogo, Log.GESTIONECASSA_CASSA);
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
        //this.showFormLogin();
        // Comincia l'esecuzione normale
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
        logger.info("Connessione avvenuta con id: " + sessionID);

        avviaDemoneConnessione();
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
        logger.info("Connessione avvenuta con id: " + sessionID);

        avviaDemoneConnessione();
    }
}
