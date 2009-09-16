/*
 * BackendAPI_1.java
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

package gestionecassa.backends;

import gestionecassa.Admin;
import gestionecassa.ArticleGroup;
import gestionecassa.Cassiere;
import gestionecassa.ArticlesList;
import gestionecassa.order.Order;
import java.io.IOException;
import java.util.Collection;

/**
 * This interface defines the basc functionalities a databackend should provide
 * More useful functionalities would be ensured by the version 2 of he backend.
 *
 * @author ben
 */
public interface BackendAPI_1 {

    /**
     * Saves the whole list for the session id
     *
     * @param id Id of the session
     * @param list List to save
     *
     * @throws IOException
     */
    void saveListOfOrders(String id, Collection<Order> list) throws IOException;

    /**
     * Saves the list of old articles
     *
     * @param list List of articles
     *
     * @throws IOException
     */
    void saveArticlesList(ArticlesList list) throws IOException;

    /**
     * Loads and returns the list of sold articles.
     *
     * @return the list of sold articles
     *
     * @throws IOException
     */
    Collection<ArticleGroup> loadArticlesList() throws IOException;

    /**
     * Saves the list of all the Admins
     *
     * @param list Listo of admins to be saved
     *
     * @throws IOException
     */
    void saveAdminsList(Collection<Admin> list) throws IOException;

    /**
     * Loads the list of admins and returns it.
     *
     * @return
     *
     * @throws IOException
     */
    Collection<Admin> loadAdminsList() throws IOException;

    /**
     * Saves the cassieres list
     * 
     * @param list
     *
     * @throws IOException
     */
    void saveCassiereList(Collection<Cassiere> list) throws IOException;

    /**
     * Loads the list of all the cassieres and returns it
     *
     * @return 
     *
     * @throws IOException
     */
    Collection<Cassiere> loadCassiereList() throws IOException;
}
