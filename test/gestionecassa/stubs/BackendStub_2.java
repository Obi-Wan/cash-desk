/*
 * BackendStub_2.java
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
import gestionecassa.ArticleGroup;
import gestionecassa.Cassiere;
import gestionecassa.EventDate;
import gestionecassa.OrganizedEvent;
import gestionecassa.order.Order;
import gestionecassa.server.datamanager.BackendAPI_2;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ben
 */
public class BackendStub_2 extends BackendStub implements BackendAPI_2 {

    public void init(String url) throws IOException {
        throw new IOException("use BackendStub_1 for now");
    }

    public void addArticleToListAt(int group, Article article, int position) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addArticleToList(int group, Article article) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void enableArticleFromList(Article article, boolean enable) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void moveArticleAt(Article article, int position) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addAdmin(Admin admin) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void enableAdmin(Admin admin, boolean enable) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addCassiere(Cassiere cassiere) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void enableCassiere(Cassiere cassiere, boolean enable) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addNewOrder(Order order) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delLastOrder(Order order) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ArticleGroup> loadArticlesList() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Admin> loadAdminsList() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Cassiere> loadCassiereList() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<EventDate> getDatesOfOrgEvent(String name) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<OrganizedEvent> getOrganizedEvents() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addOrganizedEvent(OrganizedEvent ev) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addDateToOrgEvent(EventDate evd, String title) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
