/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.clients.cassa.gui.GuiAppFrameCassa;
import gestionecassa.Log;
import gestionecassa.order.Order;
import gestionecassa.clients.gui.GuiLoginPanel;
import gestionecassa.clients.BaseClient;
import gestionecassa.clients.cassa.printing.PrinterHelper;
import gestionecassa.exceptions.*;
import gestionecassa.server.ServerRMICommon;
import gestionecassa.server.clientservices.ServiceRMICassiereAPI;
import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class Cassa extends BaseClient<ServerRMICommon, CassaPrefs>
        implements CassaAPI {

    /**
     * Local reference of himself, with restrictions.
     */
    static CassaAPI businessLogicLocale;

    /**
     * Specific Server for this client
     */
    ServiceRMICassiereAPI server;

    /**
     * Logger dedicated to the gui messages
     */
    protected final Logger loggerGUI;
    
    /**
     * The main gui frame
     */
    protected GuiAppFrameCassa appFrame;

    /**
     * The main of the Cassiere Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {
        // Let's start the execution of the main thread
        Cassa.getInstance().startClient();
    }

    /**
     * Public method that grants the singleton
     * 
     * @return
     */
    public static synchronized CassaAPI getInstance() {
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
     * The Real Main of he application.
     */
    @Override
    public void run() {
        // preparing for execution
        loadPreferences();

        // startClient la fase di login
        appFrame = new GuiAppFrameCassa(this);
        // concludi fase preparatoria al login
        appFrame.setContentPanel(new GuiLoginPanel(appFrame, this));
        appFrame.setVisible(true);

        // esecuzione principale
        super.run();

        // fine esecuzione
        savePreferences();
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
        server = (ServiceRMICassiereAPI)
                sendDatiLogin(username, password, serverName);

        setupAfterLogin(username);
    }

    @Override
    protected void setupAfterLogin(String username) throws RemoteException {
        super.setupAfterLogin(username);

        appFrame.setupAfterLogin(username);
    }

    /**
     * Requests the list of sold goods
     *
     * @throws java.rmi.RemoteException
     */
    @Override
    public void fetchRMIArticlesList() throws RemoteException {
        try {
            articles = server.getEnabledArticlesList();
        } catch (RemoteException ex) {
            logger.warn("Il server non ha risposto alla richiesta della lista",
                    ex);
            throw ex;
        }
    }

    /**
     * Sends a new order to the server
     * @param newOrder the new order committed
     *
     * @throws RemoteException
     * @throws IOException
     * @throws WrongArticlesListException
     */
    @Override
    public void sendRMINewOrder(Order newOrder)
            throws RemoteException, IOException, WrongArticlesListException {
        try {
            server.sendOrder(newOrder);
            if (preferences.printOrder) {
                PrinterHelper.startPrintingOrder(newOrder);
            }
        } catch (RemoteException ex) {
            logger.warn("Errore nella comunicazione col server",ex);
            throw ex;
        } catch (IOException ex) {
            logger.warn("Errore nel salvataggio sul server",ex);
            throw ex;
        }
    }

    /**
     * Deletes the last order committed
     * @throws java.rmi.RemoteException
     * @throws IOException
     */
    @Override
    public void delRMILastOrder() throws RemoteException, IOException {
        try {
            server.delLastOrder();
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
    public Logger getLoggerUI() {
        return loggerGUI;
    }
}
