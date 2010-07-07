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
import gestionecassa.ArticleGroup;
import gestionecassa.Cassiere;
import gestionecassa.order.Order;
import java.util.List;

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
        DebugDataProvider dataProvider = new DebugDataProvider();
        admins = dataProvider.getCopyAdmins();
        cassieres = dataProvider.getCopyCassieres();
        groups = dataProvider.getCopyGroups();
    }

}
