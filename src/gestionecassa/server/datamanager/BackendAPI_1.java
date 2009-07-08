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

    void saveListaOrdini(String id, List<Order> lista) throws IOException;

    void saveListaBeni(ArticlesList lista) throws IOException;

    List<Article> loadListaBeni() throws IOException;

    void saveListaAdmin(Collection<Admin> lista) throws IOException;

    List<Admin> loadListaAdmin() throws IOException;

    void saveListaCassiere(Collection<Cassiere> lista) throws IOException;

    List<Cassiere> loadListaCassiere() throws IOException;
}
