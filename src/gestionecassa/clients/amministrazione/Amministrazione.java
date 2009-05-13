/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.amministrazione;

import gestionecassa.Log;
import gestionecassa.Persona;
import gestionecassa.clients.Luogo;
import gestionecassa.exceptions.ActorAlreadyExistingException;
import gestionecassa.exceptions.WrongLoginException;
import gestionecassa.server.ServerRMIAmministratore;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public class Amministrazione extends Luogo implements AmministrazioneAPI {

    /**
     * Local store of himself, with restrictions
     */
    static AmministrazioneAPI businessLogicLocale;

    /**
     *
     */
    ServerRMIAmministratore server;

    /**
     * Creator of the singleton
     *
     * @return reference to the singleton
     */
    public static synchronized AmministrazioneAPI crea() {
        // Fase di set-up
        if (businessLogicLocale == null) {
            Amministrazione tempClient = new Amministrazione(System.getenv("HOSTNAME"));
            businessLogicLocale = tempClient;
        }
        return businessLogicLocale;
    }
    /**
     * Creates a new instance of Amministrazione.
     */
    private Amministrazione(String nomeLuogo) {
        super(nomeLuogo, Log.GESTIONECASSA_AMMINISTRAZIONE);
    }

    /**
     * The main of the Amministratore Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {

        // cominciamo l'esecuzione del thread principale
        Amministrazione.crea().avvia();

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
        server = (ServerRMIAmministratore)
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
        server = (ServerRMIAmministratore)
                sendDatiLogin(username, password, serverName);
        logger.info("Connessione avvenuta con id: " + sessionID);

        avviaDemoneConnessione();
    }
}
