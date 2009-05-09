/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients;

import gestionecassa.server.ServerRMICommon;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class Luogo extends Thread  {
    
    /** Variable that tells to the main thread he has to
     * stop working.
     */
    protected volatile static boolean stopApp;

    /** Reference to the server */
    protected static ServerRMICommon serverCentrale;
    
    /**
     * The ID returned from the server, that we will use
     * to comunicate with it.
     */
    protected static int sessionID;

    /**
     * Nome identificativo del luogo (hostname)
     */
    final String nome;

    /**
     * Chosen Logger for this application
     */
    final Logger logger;

    /**
     * Costruttore esplicito che assegna subito il nome al luogo
     *
     * @param nome
     */
    protected Luogo(String nome, Logger logger) {
        this.nome = nome;
        this.logger = logger;
        Luogo.stopApp = false;
    }

    /**
     * Starts the thread
     */
    public void avvia() {
        start();
    }

    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        // avvia la fase di login
        //this.showFormLogin();
        // Comincia l'esecuzione normale
        try {
            while (stopApp == false) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.dumpStack();
        }
        //exit
        System.out.println("sto uscendo dal client");
    }

    /**
     * The stopping Method
     */
    public void stopClient() {
        try {
            /*Let's stop the service on the server.*/
            if (serverCentrale != null && sessionID >= 0) {
                serverCentrale.closeService(sessionID);
            }
        } catch (RemoteException ex) {
            //TODO andrebbe segnalato anche all'utente con un avviso
            logger.warn("Client interrotto in modo brusco", ex);
        }
        stopApp = true;
    }

    /**
     * Returns chosen logger for the application
     *
     * @return
     */
    public Logger getLogger() {
        return logger;
    }
}
