/*
 * CassaPrefs.java
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

package gestionecassa.clients.cassa;

import gestionecassa.clients.BaseClientPrefs;

/**
 *
 * @author ben
 */
public class CassaPrefs extends BaseClientPrefs {

    public Boolean printOrder;

    @Override
    final public String getApplication() {
        return "cassa";
    }

    @Override
    final public String getVersion() {
        return "1.0";
    }

    /**
     * Default constructor
     */
    public CassaPrefs() {
        this("","", true);
    }

    /**
     * Explicit constructor
     * @param server
     * @param username
     * @param printOrder
     */
    public CassaPrefs(String server, String username, Boolean printOrder) {
        super(server, username);
        this.printOrder = printOrder;
    }

    /**
     * Copy constructor
     * @param old
     */
    public CassaPrefs(CassaPrefs old) {
        this(old.defaultServer, old.defaultUsername, old.printOrder);
    }
}
