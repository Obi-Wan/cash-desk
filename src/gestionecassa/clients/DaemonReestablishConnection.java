/*
 * DaemonReestablishConnection.java
 *
 * Created on 26 gennaio 2007, 22.28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gestionecassa.clients;

import gestionecassa.ConnectionDetails;
import gestionecassa.exceptions.NotExistingSessionException;
import java.rmi.RemoteException;
import gestionecassa.server.ServerRMICommon;

/**
 * This class has the only scope to keep alive connection.
 * It's a daemon so it dies when the app dies.
 *
 * @author ben
 */
public class DaemonReestablishConnection extends Thread {

    /**
     * Details of the connection of the client
     */
    private ConnectionDetails connectionDetails;
    /**
     * Server
     */
    private ServerRMICommon server;

    /**
     * Error state of the daemon
     */
    private DaemonRuntimeState errorState;
    
    /**
     * Saves the exception in case of error, for later reading.
     */
    private Exception exception;

    /**
     * Semaphore for the error state of the daemon
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    private static final String errorStateSemaphore =
            "ErrorStateSemaphore" + System.currentTimeMillis();
    
    /**
     * Creates a new instance of DaemonReestablishConnection
     *
     * @param server 
     * @param connDet
     */
    public DaemonReestablishConnection(ServerRMICommon server,
            ConnectionDetails connDet) {
        /*The only way to destroy it, without having care of anything*/
        this.setDaemon(true);

        this.connectionDetails = new ConnectionDetails(connDet);
        this.server = server;
        this.errorState = DaemonRuntimeState.Running;
        this.exception = new Exception();
    }
    
    /**
     * Keeps alive connection
     */
    @Override
    public void run() {
        while (getDaemonState() == DaemonRuntimeState.Running) {
            keepAlive();
        }
    }
    
    void keepAlive() {
        try {
            server.keepAlive(connectionDetails.sessionID); //FIXME Add hash handling
//            logger.info("keepalive mandato al server.");

            /*sleeps for 30 seconds*/
            Thread.sleep(connectionDetails.timeout/2);
        } catch (NotExistingSessionException ex) {
            synchronized (errorStateSemaphore) {
                errorState = DaemonRuntimeState.NotExistingSessionError;
                exception = ex;
            }
            
        } catch (RemoteException ex) {
            synchronized (errorStateSemaphore) {
                errorState = DaemonRuntimeState.RemoteError;
                exception = ex;
            }
            
        } catch (InterruptedException ex) {
            synchronized (errorStateSemaphore) {
                errorState = DaemonRuntimeState.Stopped;
                exception = ex;
            }
        }
    }

    public DaemonRuntimeState getDaemonState() {
        synchronized (errorStateSemaphore) {
            return errorState;
        }
    }

    public Exception getErrorException() {
        synchronized (errorStateSemaphore) {
            return exception;
        }
    }

    public enum DaemonRuntimeState {
        Running,
        Stopped,
        NotExistingSessionError,
        RemoteError,
    }
}
