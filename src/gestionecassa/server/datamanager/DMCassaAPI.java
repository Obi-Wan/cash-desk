/*
 * DMCassaAPI.java
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

import gestionecassa.Ordine;

/**
 *
 * @author ben
 */
public interface DMCassaAPI extends DMCommonAPI {

    void createNewCassaSession(String identifier);

    void closeCassaSession(String identifier);

    void addNewOrder(String id, Ordine order);

    void delLastOrder(String id);
}
