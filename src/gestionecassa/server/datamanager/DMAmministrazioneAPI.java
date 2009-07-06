/*
 * DMAmministrazioneAPI.java
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

import gestionecassa.ListaBeni;

/**
 *
 * @author ben
 */
public interface DMAmministrazioneAPI extends DMCommonAPI {

    /**
     * Saves the new list of goods sold.
     * 
     * @param lista
     */
    void saveNewListaBeni(ListaBeni lista);

}