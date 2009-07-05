/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.clients.cassa.gui.GuiAppFrameCassa;
import gestionecassa.clients.cassa.gui.GuiNuovoOrdinePanel;
import gestionecassa.Log;
import gestionecassa.ordine.Ordine;
import gestionecassa.Persona;
import gestionecassa.clients.Luogo;
import gestionecassa.exceptions.*;
import gestionecassa.server.clientservices.ServerRMICassiere;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.dom4j.DocumentException;

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
            String hostname;
            try {
                hostname = java.net.InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException ex) {
                hostname = System.getProperty("user.name") + "@" +
                        System.getProperty("os.name");
            }
            Cassa tempClient = new Cassa(hostname);
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
        XmlCassaHandler xmlHandler = new XmlCassaHandler();
        try {
            xmlHandler.loadOptions(options);

            logger.debug("letto dal file:\nUsername: " + options.defaultUsername
                    + " \tServer: " + options.defaultServer + "\n");
        } catch (IOException ex) {
            logger.warn("Unable to read data from configfile", ex);
        } catch (DocumentException ex) {
            logger.warn("Parse exception in conf file", ex);
        }
        // avvia la fase di login
        appFrame = new GuiAppFrameCassa(this);

        // esecuzione principale
        super.run();

        // fine esecuzione
        try {
            xmlHandler.saveOptions(options);
        } catch (IOException ex) {
            logger.warn("Unable to write data to configfile", ex);
        }
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
        server = (ServerRMICassiere)
                sendDatiLogin(username, password, serverName);

        setupAfterLogin(username);
    }

    @Override
    protected void setupAfterLogin(String username) throws RemoteException {
        super.setupAfterLogin(username);

        ((GuiAppFrameCassa)appFrame).enableListaBeni(true);
        appFrame.setContentPanel(new GuiNuovoOrdinePanel(this));
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

    /**
     *
     * @param nuovoOrdine
     *
     * @throws java.rmi.RemoteException
     */
    public void sendNuovoOrdine(Ordine nuovoOrdine) throws RemoteException {
        try {
            server.sendOrdine(nuovoOrdine);
        } catch (RemoteException ex) {
            logger.warn("Errore nella comunicazione col server",ex);
            throw ex;
        }
    }

    /**
     *
     *
     * @throws java.rmi.RemoteException
     */
    public void annullaUltimoOrdine() throws RemoteException {
        try {
            server.annullaUltimoOrdine();
        } catch (RemoteException ex) {
            logger.warn("Errore nella comunicazione col server",ex);
            throw ex;
        }
    }

    @Override
    public void logout() throws RemoteException {
        ((GuiAppFrameCassa)appFrame).enableListaBeni(false);
        listaBeni = null;
        super.logout();
    }
}
