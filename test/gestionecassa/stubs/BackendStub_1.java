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
import gestionecassa.ArticleGroup;
import gestionecassa.ArticlesList;
import gestionecassa.Cassiere;
import gestionecassa.order.Order;
import gestionecassa.backends.BackendAPI_1;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ben
 */
public class BackendStub_1 extends BackendStub implements BackendAPI_1 {

    @Override
    public void saveListOfOrders(String id, Collection<Order> list) throws IOException {
        ordersList = new ArrayList<Order>(list);
    }

    @Override
    public void saveArticlesList(ArticlesList list) throws IOException {
        groups = new ArrayList<ArticleGroup>(list.getGroupsList());
    }

    @Override
    public List<ArticleGroup> loadArticlesList() throws IOException {
        return groups;
    }

    @Override
    public void saveAdminsList(Collection<Admin> list) throws IOException {
        admins = new ArrayList<Admin>(list);
    }

    @Override
    public List<Admin> loadAdminsList() throws IOException {
        return admins;
    }

    @Override
    public void saveCassiereList(Collection<Cassiere> list) throws IOException {
        cassieres = new ArrayList<Cassiere>(list);
    }

    @Override
    public List<Cassiere> loadCassiereList() throws IOException {
        return cassieres;
    }

}
