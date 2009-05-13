/*
 * ServiceThreadCommon.java
 *
 * Created on 26 gennaio 2007, 14.57
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.Log;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/** This class rapresents a common base for ServerRMILaureatoImpl
 * and ServerRMIAziendaImpl. It manages session and session-timer.
 *
 * @author ben
 */
public class ServiceThreadCommon extends UnicastRemoteObject implements Serializable, Runnable {
    
    /** Thread that rapresents myself */
    private Thread runner;
    
    /** Boolean that tells if the thread has to die */
    private boolean stopThread;
    
    /** Reference to his entry in sessions table */
    SessionRecord myself;

    /**
     * Reference to the manager of data.
     */
    DataManager dataManager;
    
    /** Creates a new instance of ServiceThreadCommon */
    ServiceThreadCommon(SessionRecord nMySelf, DataManager dataMgr)
            throws  RemoteException{
        myself = nMySelf;
        dataManager = dataMgr;
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
    public void run(){
        try {
            while(stopThread == false) {
                Thread.sleep(100);

            }
        } catch (InterruptedException e) {
            Log.GESTIONECASSA_SERVER.warn("Working thread sopped, is this" +
                    " your will!?",e);
        }
    }
    
    /** This method stops the thread. */
    public void stopThread() {
        stopThread = true;
    }
}
