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

import gestionecassa.BeneVenduto;
import gestionecassa.ListaBeni;
import gestionecassa.ordine.Ordine;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author ben
 */
public interface BackendAPI_1 {

    void saveListaOrdini(String id, List<Ordine> lista) throws IOException;

    void saveListaBeni(ListaBeni lista) throws IOException;

    List<BeneVenduto> loadListaBeni() throws IOException;
}
