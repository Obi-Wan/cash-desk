/*
 * BackendAPI_1_5.java
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

import gestionecassa.ordine.Order;
import java.io.IOException;

/**
 * Intermediate revision of the data backend interface that should provide a
 * better working interface for more functionalities. In particular it should
 * make easier to store orders while session of the client is open.
 * 
 * @author ben
 */
public interface BackendAPI_1_5 extends BackendAPI_1 {

    /**
     * Adds a new order to the specified session by id, and if none present
     * creates a new entry.
     *
     * @param id Id of the session
     * @param order The order to add
     *
     * @throws IOException
     */
    void saveNewOrder(String id, Order order) throws IOException;

    /**
     * Deletes the last saved order of the session id
     *
     * @param id
     *
     * @throws IOException
     */
    void delLastOrder(String id) throws IOException;
}
