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

package gestionecassa.server.datamanager;

import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.Cassiere;
import gestionecassa.ArticlesList;
import gestionecassa.ordine.Order;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ben
 */
public interface BackendAPI_1 {

    void saveListOfOrders(String id, List<Order> list) throws IOException;

    void saveArticlesList(ArticlesList list) throws IOException;

    List<Article> loadArticlesList() throws IOException;

    void saveAdminsList(Collection<Admin> list) throws IOException;

    List<Admin> loadAdminsList() throws IOException;

    void saveCassiereList(Collection<Cassiere> list) throws IOException;

    List<Cassiere> loadCassiereList() throws IOException;
}
