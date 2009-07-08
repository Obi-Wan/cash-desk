/*
 * DMServerAPI.java
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

package gestionecassa.server.datamanager;

import gestionecassa.Person;

/**
 *
 * @author ben
 */
public interface DMServerAPI {

    /**
     *
     * @param user
     * @return
     */
    void registraUtente(Person user);

    /**
     * Verifies a username exists, and if is the case,
     * it creates a new read only copy of the user to prevent problems in syncronization
     *
     * @param username
     *
     * @return
     */
    Person verificaUsername(String username);

    /**
     * Executes the last tasks before quitting (like flushing ad Co.)
     */
    void terminate();

}
