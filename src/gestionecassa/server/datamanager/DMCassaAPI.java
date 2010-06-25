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
import gestionecassa.exceptions.WrongArticlesListException;
import gestionecassa.order.Order;
import java.io.IOException;

/**
 * Interface to the DataManager class used by the Cassa Service
 * @author ben
 */
public interface DMCassaAPI {

    /**
     * Creates a new session for the identifier supplied as argument
     * @param identifier the identifier to which attach a session
     */
    void createNewCassaSession(String identifier);

    /**
     * Closes the specified session by the identifier
     * @param identifier Specifies uniquely the session to close
     */
    void closeCassaSession(String identifier);

    /**
     * Adds a new order to the specified session
     * @param id identifier of the session
     * @param order New order to add
     * @throws IOException
     * @throws WrongArticlesListException 
     */
    void addNewOrder(String id, Order order)
            throws IOException, WrongArticlesListException;

    /**
     * Deletes the last order submitted for this session
     * @param id
     * @throws IOException
     */
    void delLastOrder(String id) throws IOException;


    /**
     * Used to ask "n" progressive numbers to associate to the
     * <code>ArticleWithPreparation</code>
     *
     * @param artName Name of the article with multiple progressive numbers
     * @param n How many progressive numbers to associate to the article
     *
     * @return the first of the n progressive numbers
     */
    int getNProgressive(String artName,int n);

    /**
     * Method to get the list of all the enabled Articles
     * @return the list of all enabled articles
     */
    public ArticlesList getEnabledArticlesList();
}
