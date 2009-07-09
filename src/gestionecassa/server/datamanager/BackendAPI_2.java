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
import java.io.IOException;

/**
 *
 * @author ben
 */
public interface BackendAPI_2 extends BackendAPI_1_5 {
    
    void init() throws IOException;

    //--------------------//

    void addArticleToListAt(Article article, int position) throws IOException;

    void addArticleToList(Article article) throws IOException;

    void delArticleFromListAt(Article article) throws IOException;

    //--------------------//

    void addAdmin(Admin admin) throws IOException;

    void delAdmin(Admin admin) throws IOException;

    //--------------------//

    void addCassiere(Cassiere cassiere) throws IOException;

    void delCassiere(Cassiere cassiere) throws IOException;
}
