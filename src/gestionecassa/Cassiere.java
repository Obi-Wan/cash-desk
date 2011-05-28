/*
 * Cassiere.java
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
 *
 * @author ben
 */
public class Cassiere extends Person {

    /**
     * Copy constructor
     *
     * @param old the Cassiere to copy from.
     */
    public Cassiere(Cassiere old) {
        this(old.id, old.username, old.password, old.enabled);
    }

    /**
     * Copy constructor
     *
     * @param old the Cassiere to copy from.
     * @param newPassword The new Password 
     */
    public Cassiere(Cassiere old, String newPassword) {
        this(old.id, old.username, newPassword, old.enabled);
    }

    /**
     * Creates a Cassiere from from specified fields
     *
     * @param idCassiere The id of cassiere
     * @param username The username of cassiere
     * @param password The password of cassiere.
     */
    public Cassiere(int idCassiere, String username, String password) {
        this(idCassiere, username, password, true);
    }

    /**
     * Creates a Cassiere from from specified fields
     *
     * @param idCassiere the id of cassiere
     * @param username the username of cassiere
     * @param password the password of cassiere.
     * @param enabled if it's enabled or not.
     */
    public Cassiere(int idCassiere, String username, String password,
            boolean enabled) {
        super(idCassiere, username, password, enabled);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Cassiere) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
