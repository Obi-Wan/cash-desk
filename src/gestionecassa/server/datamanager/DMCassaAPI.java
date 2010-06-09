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

import gestionecassa.ArticlesList;
import gestionecassa.order.Order;
import java.io.IOException;

/**
 *
 * @author ben
 */
public interface DMCassaAPI {

    void createNewCassaSession(String identifier);

    void closeCassaSession(String identifier);

    void addNewOrder(String id, Order order) throws IOException;

    void delLastOrder(String id) throws IOException;


    /**
     * Used to ask "n" progressive numbers to associate to the
     * <code>ArticleWithPreparation</code>
     *
     * @param artName
     *
     * @return the first of the n progressive numbers
     */
    int getNProgressive(String artName,int n);

    /**
     * Method to get the list of all the enabled Articles
     * @return the list of all enabled articles
     * @throws RemoteException
     */
    public ArticlesList getEnabledArticlesList();
}
