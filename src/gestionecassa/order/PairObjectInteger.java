/*
 * PairObjectInteger.java
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
 * This generic class is used for many purposes. It's a sort of
 * <code>pair</code> template in C++, but a bit specialized.
 *
 * Not synchronized. do synchronize it externally
 *
 * @param <DataType>
 * @author ben
 */
public class PairObjectInteger<DataType> implements Serializable {

    /**
     * Reference to the object object.
     */
    public DataType object;

    /**
     * Num of requests for this object
     */
    public int numTot;

    /**
     * Explicit constructor
     * 
     * @param object Object referenced in this entry
     * @param numTot Quantity of the specified object
     */
    public PairObjectInteger(DataType object, int numTot) {
        this.object = object;
        this.numTot = numTot;
    }
}
