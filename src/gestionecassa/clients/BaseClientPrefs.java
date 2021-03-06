/*
 * BaseClientPrefs.java
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

package gestionecassa.clients;

import gestionecassa.Preferences;

/**
 *
 * @author ben
 */
abstract public class BaseClientPrefs extends Preferences {

    /**
     *
     */
    public String defaultServer = "";
    
    /**
     * 
     */
    public String defaultUsername = "";

    /**
     * Explicit constructor, just for children classes
     * @param server
     * @param username
     */
    protected BaseClientPrefs(String server, String username) {
        this.defaultServer = server;
        this.defaultUsername = username;
    }

}
