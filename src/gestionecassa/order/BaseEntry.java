/*
 * BaseEntry.java
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

package gestionecassa.order;

import java.io.Serializable;

/**
 *
 * @author ben
 */
public class BaseEntry<DataType> implements Serializable {

    /**
     * Reference to the data object.
     */
    public DataType data;

    /**
     * Num of requests for this object
     */
    public int numTot;

    /**
     * Explicit constructor
     * 
     * @param data
     * @param numTot
     */
    public BaseEntry(DataType data, int numTot) {
        this.data = data;
        this.numTot = numTot;
    }
}
