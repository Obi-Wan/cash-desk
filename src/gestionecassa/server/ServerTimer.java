/*
 * ServerTimer.java
 *
 * Created on 26 gennaio 2007, 21.11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.Log;

/** This class is a sort of timer: it manages threads' life.
 *
 * @author ben
 */
class ServerTimer extends Thread {
    
    /** boolean that says when to stop the app. */
    private boolean stopApp;
    
    /**
     * Creates a new instance of ServerTimer
     */
    public ServerTimer() {
        stopApp = false;
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
            Log.GESTIONECASSA_SERVER.warn("Il server e' stato interrotto" +
                    "da una InterruptedException",ex);
        }
    }
    
    /** This method cyclicly updates passing of time, since the last
     * "keepAlive" call from the client, and determinates the
     * timeout, when necessary. Timeout implies that the connection
     * is no more useful, and i is erased. (set to -1)
     */
    private void aggiornaTimeElapsed() {
        Log.GESTIONECASSA_SERVER.debug("faccio il check delle sessioni attive.");
        synchronized (Server.sessionListSemaphore) {
            for (SessionRecord elem : Server.sessionList) {
                /*se supera il timeout distrugge il thread e rimuove la sessione*/
                Log.GESTIONECASSA_SERVER.debug("elemento con session id: "+
                        elem.clientId + "ed elapsedtime: "+elem.timeElapsed);
                if (++elem.timeElapsed > 14) {
                    Log.GESTIONECASSA_SERVER.debug("eliminato sess con id: "+
                            elem.clientId);
                    Server.businessLogicLocale.eraseSession(elem);
                }
            }
            /*se l'ultimo corrisponde a una sessione non piu' valida
             lo rimuovo, cosi' ad ogni minuto comprimo piano la
             lista delle sessioni, senza toglier troppi record, nel
             caso possa esserci un rapido picco di login*/
            int lastPos = Server.sessionList.size()-1;
            if (lastPos >= 0) {
                SessionRecord last = Server.sessionList.get(lastPos);
                if (last.clientId == -1) {
                    Server.sessionList.remove(lastPos);
                }
            }
        }
        Log.GESTIONECASSA_SERVER.debug("finito il check delle sessioni attive.");
    }
    
    /** The stopping Method */
    public void stopServer() {
        stopApp = true;
    }
}
