/*
 * SessionRecord.java
 *
 * Copyright (C) 2009 Nicola Roberto Viganò
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * SessionRecord.java
 *
 * Created on 26 gennaio 2007, 14.18
 */

package gestionecassa.server;

import gestionecassa.Person;
import gestionecassa.server.clientservices.SharedServerService;
import java.io.Serializable;

/** This Class stores data of a session.
 *
 * @author ben
 */
public class SessionRecord implements Serializable {
    
    /** Session ID of the user. */
    int sessionId;
    
    /** Username of the user. */
    String username;
    
    /** Thread that is serving this user. */
    SharedServerService serviceThread;
    
    /** Reference to who is this user. */
    Person user;
    
    /** Counter of time passed since last keep alive */
    int timeElapsed;
    
    /** the id of the user in the table */
    int userId;
    
    /**
     * Default constructor.
     * Creates a new instance of SessionRecord.
     */
    public SessionRecord() {
        sessionId = -1;
        username = new String("");
        serviceThread = null;
        user = null;
        timeElapsed = 0;
    }
    
    /**
     * Explicit constructor
     * Creates a new instance of SessionRecord.
     * 
     * @param nClId     Id for this new SessionRecord.
     * @param nUsername Username for this new SessionRecord.
     * @param nRelated  Reference to the thread related.
     * @param nUser     Reference to the user.
     * @param nTime     new indication for timeEtimeElapsed
     */
    public SessionRecord(final int nClId, final String nUsername,
            final SharedServerService nRelated, final Person nUser,
            final int nTime) {
        sessionId = nClId;
        username = nUsername;
        serviceThread = nRelated;
        user = nUser;
        timeElapsed = nTime;
    }
    
    /**
     * Copy constructor.
     * Creates a new instance of SessionRecord.
     *
     * @param record the one from which make the copy.
     */
    public SessionRecord(final SessionRecord record) {
        sessionId = record.sessionId;
        username = record.username;
        serviceThread = record.serviceThread;
        user = record.user;
        timeElapsed = record.timeElapsed;
    }
    
    /**
     * Overridden method that will be used by the search procedure
     *
     * @param   obj     reference to the other object.
     *
     * @return <code>true</code> if it's the same, <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof SessionRecord) &&
                (this.username.equals(((SessionRecord)obj).username));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.sessionId;
        hash = 53 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getUsername() {
        return username;
    }
}
