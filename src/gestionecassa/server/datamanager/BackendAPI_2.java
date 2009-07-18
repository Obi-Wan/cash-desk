/*
 * BackendAPI_2.java
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

import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.Cassiere;
import gestionecassa.ordine.Order;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ben
 */
public interface BackendAPI_2 {
    
    public void init(String url) throws IOException;

    //--------------------//

    void addArticleToListAt(Article article, int position) throws IOException;

    void addArticleToList(Article article) throws IOException;

    void enableArticleFromList(Article article, boolean enable) throws IOException;

    //--------------------//

    void addAdmin(Admin admin) throws IOException;

    void enableAdmin(Admin admin, boolean enable) throws IOException;

    //--------------------//

    void addCassiere(Cassiere cassiere) throws IOException;

    void enableCassiere(Cassiere cassiere, boolean enable) throws IOException;
    
    //--------------------//

    /**
     * Adds a new order to the specified session by id, and if none present
     * creates a new entry.
     *
     * @param id Id of the session
     * @param order The order to add
     *
     * @throws IOException
     */
    void addNewOrder(String id, Order order) throws IOException;

    /**
     * Deletes the last saved order of the session id
     *
     * @param id
     *
     * @throws IOException
     */
    void delLastOrder(String id) throws IOException;

    //--------------------//

    /**
     * Loads and returns the list of sold articles.
     *
     * @return the list of sold articles
     *
     * @throws IOException
     */
    List<Article> loadArticlesList() throws IOException;

    /**
     * Loads the list of admins and returns it.
     *
     * @return
     *
     * @throws IOException
     */
    List<Admin> loadAdminsList() throws IOException;

    /**
     * Loads the list of all the cassieres and returns it
     *
     * @return
     *
     * @throws IOException
     */
    List<Cassiere> loadCassiereList() throws IOException;
}
