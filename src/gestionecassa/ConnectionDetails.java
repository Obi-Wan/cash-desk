/*
 * ConnectionDetails.java
 * 
 * Copyright (C) 2010 Nicola Roberto Viganò
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package gestionecassa;

import java.io.Serializable;

/**
 * Stores the details about the current connection with the server.
 * @author ben
 */
public class ConnectionDetails implements Serializable {

    /**
     * The ID of the current session
     */
    public int sessionID;

    /**
     * The hash, or unique ID for the current session
     */
    public int sessionHash;

    /**
     * Expiration Time of the current session
     */
    public int timeout;

    public ConnectionDetails(int sessionID, int sessionHash, int timeout) {
        this.sessionID = sessionID;
        this.sessionHash = sessionHash;
        this.timeout = timeout;
    }

    public ConnectionDetails() {
        this(-1, -1, -1);
    }

    public ConnectionDetails(ConnectionDetails connDet) {
        this.sessionID = connDet.sessionID;
        this.sessionHash = connDet.sessionHash;
        this.timeout = connDet.timeout;
    }
}
