/*
 * BackendStub_1.java
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

package gestionecassa.stubs;

import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.ArticlesList;
import gestionecassa.Cassiere;
import gestionecassa.ordine.Order;
import gestionecassa.server.datamanager.BackendAPI_1;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author ben
 */
public class BackendStub_1 extends BackendStub implements BackendAPI_1 {

    public void saveListOfOrders(String id, ConcurrentLinkedQueue<Order> list) throws IOException {
        ordersList = new Vector<Order>(list);
    }

    public void saveArticlesList(ArticlesList list) throws IOException {
        articles = new Vector<Article>(list.list);
    }

    public List<Article> loadArticlesList() throws IOException {
        return articles;
    }

    public void saveAdminsList(Collection<Admin> list) throws IOException {
        admins = new Vector<Admin>(list);
    }

    public List<Admin> loadAdminsList() throws IOException {
        return admins;
    }

    public void saveCassiereList(Collection<Cassiere> list) throws IOException {
        cassieres = new Vector<Cassiere>(list);
    }

    public List<Cassiere> loadCassiereList() throws IOException {
        return cassieres;
    }

}
