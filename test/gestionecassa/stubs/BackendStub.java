/*
 * BackendStub.java
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
import gestionecassa.ArticleWithOptions;
import gestionecassa.Cassiere;
import gestionecassa.order.Order;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class BackendStub {

    public List<Order> ordersList;

    public List<ArticleGroup> groups;

    public List<Admin> admins;

    public List<Cassiere> cassieres;

    public BackendStub() {
        admins = new Vector<Admin>();
        admins.add(new Admin(admins.size(), "admin", "password"));
        cassieres = new Vector<Cassiere>();
        cassieres.add(new Cassiere(cassieres.size(), "bene", "male"));

        List<Article> articles = new Vector<Article>();
        Collection<String> listaOpzioni = new Vector<String>();
        listaOpzioni.add("cacca secca");
        listaOpzioni.add("cacca liquida");
        articles.add(new Article(1,"fagiolo", 25));
        articles.add(new Article(2,"ameba", 35));
        articles.add(new Article(3,"merda dello stige", 5.5));
        articles.add(new ArticleWithOptions(4,"panino alla", 10.25, listaOpzioni));

        groups = new Vector<ArticleGroup>();
        groups.add(new ArticleGroup(1, "group", articles));
    }

}
