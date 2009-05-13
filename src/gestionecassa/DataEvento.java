/*
 * GuiNuovoOrdinePanel.java
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

package gestionecassa;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ben
 */
public class DataEvento {

    /**
     * Title of this specific date.
     */
    String titoloData;

    /**
     * Data
     */
    Date data;

    /**
     * Lista degli ordini avuti all'evento
     */
    List<Ordine> listaOrdini;
}
