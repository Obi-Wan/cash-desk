/*
 * SessionManager.java
 *
 * Created on 26 gennaio 2007, 21.11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.exceptions.NotExistingSessionException;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import org.apache.log4j.Logger;

/**
 * This class is responsible for threads' life, and sessions management.
 *
 * @author ben
 */
class SessionManager extends Thread {

    /**
     * List of the opened sessions
     */
    Map<Integer, SessionRecord> sessions;

    /**
     * List of session ids we could reuse.
     */
    PriorityQueue<Integer> recycleIds;

    /**
     * Semaphore for the list of opened sessions
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String sessionListSemaphore =
            new String("SessionsSemaphore" + System.currentTimeMillis());
    
    /**
     * boolean that says when to stop the app.
     */
    private boolean stopApp;

    /**
     * 
     */
    Logger logger;
    
    /**
     * Creates a new instance of SessionManager
     */
    public SessionManager(Logger logger) {
        sessions = new TreeMap<Integer, SessionRecord>();
        recycleIds = new PriorityQueue<Integer>();
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
                    updateTimeElapsed();
                }
            }
            System.out.println("Exiting");
        } catch (InterruptedException ex) {
            logger.warn("Il server e' stato interrottoda una " +
                    "InterruptedException",ex);
        }
    }

    /** The stopping Method */
    public void stopServer() {
        stopApp = true;
    }
    
    /** This method cyclicly updates passing of time, since the last
     * "keepAlive" call from the client, and determinates the
     * timeout, when necessary. Timeout implies that the connection
     * is no more useful, and it is erased. (set to -1)
     */
    private void updateTimeElapsed() {
        logger.debug("faccio il check delle sessioni attive.");
        synchronized (sessionListSemaphore) {
            for (SessionRecord elem : sessions.values()) {
                /*se supera il timeout distrugge il thread e rimuove la sessione*/
                logger.debug("elemento con session id: "+
                        elem.sessionId + "ed elapsedtime: "+elem.timeElapsed);
                if (++elem.timeElapsed > 14) {
                    logger.debug("eliminato sess con id: "+
                            elem.sessionId);
                    eraseSession(elem);
                }
            }
            /*se l'ultimo corrisponde a una sessione non piu' valida
             lo rimuovo, cosi' ad ogni minuto comprimo piano la
             lista delle sessioni, senza toglier troppi record, nel
             caso possa esserci un rapido picco di login*/
            int lastPos = sessions.size()-1;
            if (lastPos >= 0) {
                SessionRecord last = sessions.get(lastPos);
                if (last.sessionId == -1) {
                    sessions.remove(lastPos);
                }
            }
        }
        logger.debug("finito il check delle sessioni attive.");
    }

    /** This method looks for the first free number in sessions list.
     *
     * @param newRecord  the record to verify.
     *
     * @return new sessionId.
     */
    int newSession(SessionRecord newRecord) {
        synchronized (sessionListSemaphore) {
            /*vedo se esistono posti intermedi liberi.
              infatti se un thread implode lascia uno spazio libero.*/
            int id = 0;
            for (SessionRecord elem : sessions.values()) {
                if (elem.sessionId == -1) {
                    break;
                }
                id++;
            }

            sessions.put(id, newRecord);
            return id;
        }
    }

    /** This method destoryes a record in the sessions' list.
     *
     * @param   session     the session to destroy.
     */
    private void eraseSession(SessionRecord session) {
        synchronized (sessionListSemaphore) {
            session.sessionId = -1;
            session.user = null;
            session.serviceThread.stopThread();
        }
        logger.debug("Eliminata la sessione scaduta o terminata");
    }

    /**
     * Method that tell's the server that the client still
     * lives and is connected.
     */
    public void keepAlive(int sessionID) throws NotExistingSessionException {
        synchronized (sessionListSemaphore) {
            SessionRecord record = sessions.get(sessionID);
            if (record != null) {
                record.timeElapsed = 0;
            } else {
                throw new NotExistingSessionException("Nessuna sessione con" +
                        " id: " + sessionID);
            }
        }
    }

    /**
     * Method that tell's to the thread to shut down.
     */
    public void closeService(int sessionID) {
        synchronized (sessionListSemaphore) {
            eraseSession(sessions.get(sessionID));
        }
    }

    public boolean verifySession(SessionRecord record) {
        synchronized (sessionListSemaphore) {
            return sessions.containsValue(record);
        }
    }
}
