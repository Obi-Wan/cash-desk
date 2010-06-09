/*
 * Admin.java
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

package gestionecassa;

/**
 * It rapresents an Admin.
 *
 * @author ben
 */
public class Admin extends Person {

    /**
     * Copy constructor
     *
     * @param person old Person class from which we copy information
     */
    public Admin(Person person) {
        this(person.id, person.username, person.password, person.enabled);
    }

    /**
     * Creates an Admin from from specified fields
     *
     * @param idAdmin The id of Admin
     * @param username The username of the Admin
     * @param password The password of the Admin
     * @param enabled Wheather he is enabled or not
     */
    public Admin(int idAdmin, String username, String password, boolean enabled) {
        super(idAdmin, username, password,enabled);
    }

    /**
     * Creates an Admin from from specified fields
     *
     * @param idAdmin The id of Admin
     * @param username The username of the Admin
     * @param password The password of the Admin
     */
    public Admin(int idAdmin, String username, String password) {
        super(idAdmin, username, password,true);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (obj instanceof Admin) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
