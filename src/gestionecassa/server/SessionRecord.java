/*
 * SessionRecord.java
 *
 * Created on 26 gennaio 2007, 14.18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.Persona;
import java.io.Serializable;

/** This Class stores data of a session.
 *
 * @author ben
 */
class SessionRecord implements Serializable {
    
    /** Session ID of the user. */
    int clientId;
    
    /** Username of the user. */
    String username;
    
    /** Thread that is serving this user. */
    SharedServerService relatedThread;
    
    /** Reference to who is this user. */
    Persona user;
    
    /** Counter of time passed since last keep alive
     */
    int timeElapsed;
    
    /** the id of the user in the table */
    int idTabella;
    
    /** Creates a new instance of SessionRecord */
    public SessionRecord() {
        clientId = -1;
        username = new String("");
        relatedThread = null;
        user = null;
        timeElapsed = 0;
    }
    
    /**
     * Creates a new instance of SessionRecord.
     * 
     * @param nClId     Id for this new SessionRecord.
     * @param nUsername Username for this new SessionRecord.
     * @param nRelated  Reference to the thread related.
     * @param nUser     Reference to the user.
     * @param nTime     new indication for timeEtimeElapsed
     */
    public SessionRecord(final int nClId, final String nUsername, final SharedServerService nRelated, final Persona nUser, final int nTime) {
        clientId = nClId;
        username = nUsername;
        relatedThread = nRelated;
        user = nUser;
        timeElapsed = nTime;
    }
    
    /** Creates a new instance of SessionRecord.
     *
     * @param vecchio   the one from which make the copy.
     */
    public SessionRecord(final SessionRecord vecchio) {
        clientId = vecchio.clientId;
        username = vecchio.username;
        relatedThread = vecchio.relatedThread;
        user = vecchio.user;
        timeElapsed = vecchio.timeElapsed;
    }
    
    /** Overridden method that will be used by the search procedure
     *
     * @param   obj     reference to the other object.
     *
     * @return  true if it's the same.
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof SessionRecord) &&
                (this.username.equals(((SessionRecord)obj).username));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.clientId;
        hash = 53 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }
}
