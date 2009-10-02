/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.clients.cassa.gui.GuiAppFrameCassa;
import gestionecassa.Log;
import gestionecassa.XmlPreferencesHandler;
import gestionecassa.order.Order;
import gestionecassa.clients.gui.GuiLoginPanel;
import gestionecassa.clients.BaseClient;
import gestionecassa.clients.cassa.printing.PrinterHelper;
import gestionecassa.exceptions.*;
import gestionecassa.server.clientservices.ServiceRMICassiere;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

/**
 *
 * @author ben
 */
public class Cassa extends BaseClient<CassaPrefs> implements CassaAPI {

    /**
     * Local store of himself, with restrictions
     */
    static CassaAPI businessLogicLocale;

    /**
     * Specific Server
     */
    ServiceRMICassiere server;

    /**
     *
     */
    protected final Logger loggerGUI;
    
    /**
     * 
     */
    protected GuiAppFrameCassa appFrame;

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
        super(nomeLuogo, new CassaPrefs(), Log.GESTIONECASSA_CASSA);
        loggerGUI = Log.GESTIONECASSA_CASSA_GUI;
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
        XmlPreferencesHandler<CassaPrefs> genericXml =
                    new XmlPreferencesHandler<CassaPrefs>(logger);
        try {
            genericXml.loadPrefs(preferences);

            logger.debug("letto dal file:\nUsername: " + preferences.defaultUsername
                    + " \tServer: " + preferences.defaultServer + "\n");
        } catch (IOException ex) {
            logger.warn("Unable to read data from configfile", ex);
        } catch (DocumentException ex) {
            logger.warn("Parse exception in conf file", ex);
        }
        // avvia la fase di login
        appFrame = new GuiAppFrameCassa(this);
        // concludi fase preparatoria al login
        appFrame.setContentPanel(new GuiLoginPanel(appFrame, this));
        appFrame.setVisible(true);

        // esecuzione principale
        super.run();

        // fine esecuzione
        try {
            genericXml.savePrefs(preferences);

        } catch (IOException ex) {
            logger.warn("Unable to write data to configfile", ex);
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
     * @throws java.rmi.NotBoundException
     */
    @Override
    public void login(String username, String password, String serverName)
            throws WrongLoginException, RemoteException, NotBoundException
    {
        server = (ServiceRMICassiere)
                sendDatiLogin(username, password, serverName);

        setupAfterLogin(username);
    }

    @Override
    protected void setupAfterLogin(String username) throws RemoteException {
        super.setupAfterLogin(username);

        appFrame.setupAfterLogin(this,username);
    }

    /**
     * Requests the list of sold goods
     *
     * @throws java.rmi.RemoteException
     */
    @Override
    public void getRMIArticlesList() throws RemoteException {
        try {
            articles = server.requestArticlesList();
        } catch (RemoteException ex) {
            logger.warn("Il server non ha risposto alla richiesta della lista",
                    ex);
            throw ex;
        }
    }

    /**
     *
     * @param newOrder 
     *
     * @throws java.rmi.RemoteException
     */
    @Override
    public void sendRMINewOrder(Order newOrder) throws RemoteException, IOException {
        try {
            server.sendOrder(newOrder);
            PrinterHelper.startPrintingOrder(newOrder);
        } catch (RemoteException ex) {
            logger.warn("Errore nella comunicazione col server",ex);
            throw ex;
        } catch (IOException ex) {
            logger.warn("Errore nel salvataggio sul server",ex);
            throw ex;
        }
    }

    /**
     *
     *
     * @throws java.rmi.RemoteException
     */
    @Override
    public void delRMILastOrder() throws RemoteException, IOException {
        try {
            server.cancelLastOrder();
        } catch (RemoteException ex) {
            logger.warn("Errore nella comunicazione col server",ex);
            throw ex;
        } catch (IOException ex) {
            logger.warn("Errore nella cancellazione sul DB del server",ex);
            throw ex;
        }
    }

    /**
     * Asks to the server for a new bunch of progressive numbers.
     *
     * @param nome Name of the goods.
     * @param n number of the progressive numbers.
     *
     * @return the first of the "n" progressive numbers.
     *
     * @throws RemoteException
     */
    @Override
    public int getNProgressivo(String nome, int n) throws RemoteException {
        return server.getNProgressive(nome, n);
    }

    /**
     * 
     * @throws RemoteException
     */
    @Override
    public void logout() throws RemoteException {
        super.logout();

        appFrame.setdownAfterLogout();
    }

    /**
     * 
     * @return
     */
    @Override
    public Logger getLoggerGUI() {
        return loggerGUI;
    }
}
