/*
 * CassaOptions.java
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

import gestionecassa.clients.LuogoOptions;

/**
 *
 * @author ben
 */
public class CassaOptions extends LuogoOptions {

    @Override
    public String getApplication() {
        return "cassa";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    /**
     * Default constructor
     */
    public CassaOptions() {
        this("","");
    }

    /**
     * Explicit constructor
     * @param server
     * @param username
     */
    public CassaOptions(String server, String username) {
        super(new String(server), new String(username));
    }

    /**
     * Copy constructor
     * @param old
     */
    public CassaOptions(CassaOptions old) {
        this(old.defaultServer, old.defaultUsername);
    }
}
