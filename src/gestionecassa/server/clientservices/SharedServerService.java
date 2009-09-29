/*
 * SharedServerService.java
 *
 * Created on 26 gennaio 2007, 14.57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.apache.log4j.Logger;

/** This class rapresents a common base for ServiceRMICassiereImpl
 * and ServiceRMIAdminImpl. It manages just keeps the service alive
 *
 * @author ben
 */
public class SharedServerService extends UnicastRemoteObject 
        implements Serializable, Runnable {
    
    /** Thread that rapresents myself */
    private Thread runner;
    
    /** Boolean that tells if the thread has to die */
    private boolean stopThread;

    /**
     * Reference to the logger that eats our messages.
     */
    Logger logger;
    
    /** Creates a new instance of SharedServerService */
    SharedServerService(Logger logger)
                throws  RemoteException{
        this.logger = logger;
    }
    
    /**
     *starts the server
     */
    public void start(){
        //creo un nuovo thread, che mi rappresenta
        runner = new Thread(this); 
        /*questo thread non puo' sopravvivere dopo la chiusura del padre*/
        runner.setDaemon(true);
        runner.start();
    }
    
    
    /** Main of the thread.
     */
    @Override
    public void run(){
        try {
            while(!stopThread) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            logger.warn("Working thread sopped, is this" +
                    " your will!?",e);
        }
    }
    
    /**
     * This method stops the thread.
     */
    public void stopThread() {
        stopThread = true;
    }
}
