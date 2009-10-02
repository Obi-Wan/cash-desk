/*
 * ServerPrefs.java
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

package gestionecassa.server;

import gestionecassa.Preferences;

/**
 *
 * @author ben
 */
public class ServerPrefs extends Preferences {

    /**
     *
     */
    public int securityLevel;

    /**
     *
     */
    public String dbUrl;

    /**
     * 
     */
    public boolean trustOrders;

    /**
     * Specifies whether to kick off preexistent sessions on multiple login of
     * the same user.
     */
    public boolean kickOffOnNewSession;

    /**
     * Default constructor
     */
    public ServerPrefs() {
        this(0, "jdbc:postgresql://localhost:5432/GCDB", true, false);
    }

    public ServerPrefs(Integer securityLevel, String dbUrl, Boolean trustOrders,
            Boolean kickoff) {
        this.securityLevel = new Integer(securityLevel);
        this.dbUrl = new String(dbUrl);
        this.trustOrders = new Boolean(trustOrders);
        this.kickOffOnNewSession = new Boolean(kickoff);
    }

    /**
     *
     * @return
     */
    @Override
    public String getApplication() {
        return "server";
    }

    /**
     *
     * @return
     */
    @Override
    public String getVersion() {
        return "1.0";
    }

}
