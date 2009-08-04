/*
 * ServerTimer.java
 *
 * Created on 26 gennaio 2007, 21.11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gestionecassa.server;

import org.apache.log4j.Logger;

/** This class is a sort of timer: it manages threads' life.
 *
 * @author ben
 */
class ServerTimer extends Thread {
    
    /** boolean that says when to stop the app. */
    private boolean stopApp;

    Logger logger;
    
    /**
     * Creates a new instance of ServerTimer
     */
    public ServerTimer(Logger logger) {
        stopApp = false;
        this.logger = logger;
    }
    
    /** Main of the thread: cycle that every minute updates
     * passing of time */
    @Override
    public void run() {
        try {
            int count = 1;
            while (stopApp != true) {
                sleep(100);
                if (count < 600) {
                    count++;
                } else {
                    count = 1;
                    aggiornaTimeElapsed();
                }
            }
            System.out.println("Exiting");
        } catch (InterruptedException ex) {
            logger.warn("Il server e' stato interrottoda una " +
                    "InterruptedException",ex);
        }
    }
    
    /** This method cyclicly updates passing of time, since the last
     * "keepAlive" call from the client, and determinates the
     * timeout, when necessary. Timeout implies that the connection
     * is no more useful, and i is erased. (set to -1)
     */
    private void aggiornaTimeElapsed() {
        logger.debug("faccio il check delle sessioni attive.");
        synchronized (Server.sessionListSemaphore) {
            for (SessionRecord elem : Server.localBLogic.sessionList) {
                /*se supera il timeout distrugge il thread e rimuove la sessione*/
                logger.debug("elemento con session id: "+
                        elem.clientId + "ed elapsedtime: "+elem.timeElapsed);
                if (++elem.timeElapsed > 14) {
                    logger.debug("eliminato sess con id: "+
                            elem.clientId);
                    Server.localBLogic.eraseSession(elem);
                }
            }
            /*se l'ultimo corrisponde a una sessione non piu' valida
             lo rimuovo, cosi' ad ogni minuto comprimo piano la
             lista delle sessioni, senza toglier troppi record, nel
             caso possa esserci un rapido picco di login*/
            int lastPos = Server.localBLogic.sessionList.size()-1;
            if (lastPos >= 0) {
                SessionRecord last = Server.localBLogic.sessionList.get(lastPos);
                if (last.clientId == -1) {
                    Server.localBLogic.sessionList.remove(lastPos);
                }
            }
        }
        logger.debug("finito il check delle sessioni attive.");
    }
    
    /** The stopping Method */
    public void stopServer() {
        stopApp = true;
    }
}
