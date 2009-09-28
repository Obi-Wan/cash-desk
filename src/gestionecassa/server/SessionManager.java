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
     * Logger that takes account of logging messages.
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
                    invalidateSession(elem);
                }
            }
            cleanupSessions();
        }
        logger.debug("finito il check delle sessioni attive.");
    }

    /**
     * Removes invalidated sessions on the tail of the sessions list
     */
    private void cleanupSessions() {
        synchronized (sessionListSemaphore) {
            for (int i = (sessions.size()-1);
                i >= 0 && sessions.get(i) != null && sessions.get(i).sessionId == -1;
                i--)
            {
                sessions.remove(i);
                recycleIds.remove(i);
            }
        }
    }

    /**
     * This method finds the first free session id in sessions list, and then
     * adds the given session, assigning it that session id.
     *
     * It assumes there are no duplicates, so you need to externally verify this
     * session is unique.
     *
     * @param newRecord  the record to verify.
     * @return new sessionId.
     */
    int newSession(SessionRecord newRecord) {
        synchronized (sessionListSemaphore) {
            /*vedo se esistono posti intermedi liberi.
              infatti se un thread implode lascia uno spazio libero.*/
            int id = 0;
            if (recycleIds.size() > 0) {
                id = recycleIds.poll();
            } else {
                id = sessions.size();
            }

            sessions.put(id, newRecord);
            return id;
        }
    }

    /**
     * This method destoryes a record in the sessions' list.
     * @param   session     the session to destroy.
     */
    private void invalidateSession(SessionRecord session) {
        synchronized (sessionListSemaphore) {
            recycleIds.add(session.sessionId);
            
            session.sessionId = -1;
            session.user = null;
            session.serviceThread.stopThread();
        }
        logger.debug("Invalidata la sessione scaduta o terminata");
    }

    /**
     * Verifies whether an already existing session is in the sessions list
     * @param record The session to verify
     * @return <code>true</code> if the session is already in the sessions list
     */
    public boolean verifySession(SessionRecord record) {
        synchronized (sessionListSemaphore) {
            return sessions.containsValue(record);
        }
    }

    /**
     * Method that tell's the server that the client still
     * lives and is connected.
     */
    public void keepAlive(int sessionID) throws NotExistingSessionException {
        synchronized (sessionListSemaphore) {
            getSession(sessionID).timeElapsed = 0;
        }
    }

    /**
     * Method that tell's to the thread to shut down.
     * @param sessionID The session id, of the session to invalidate.
     * @throws NotExistingSessionException In case the id is no more associated to any session
     */
    public void closeService(int sessionID) throws NotExistingSessionException {
        invalidateSession(getSession(sessionID));
    }

    /**
     * Safe session retriver.
     * If the session is not found, or is invalidated, it gracefully fails
     * throwing an exception.
     *
     * @param sessionID The id of the session needed
     * @return The <code>SessionRecord</code> corresponding to the id
     * @throws NotExistingSessionException In case the id is no more associated to any session
     */
    private SessionRecord getSession(int sessionID) throws NotExistingSessionException {
        synchronized (sessionListSemaphore) {
            SessionRecord record = sessions.get(sessionID);
            if (record != null && record.sessionId >= 0) {
                return record;
            } else {
                throw new NotExistingSessionException("Nessuna sessione con" +
                        " id: " + sessionID);
            }
        }
    }
}
