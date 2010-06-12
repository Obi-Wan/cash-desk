/*
 * WrongArticlesListException.java
 * 
 * Copyright (C) 2010 Nicola Roberto Viganò
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package gestionecassa.exceptions;

/**
 *
 * @author ben
 */
public class WrongArticlesListException extends Exception {

    /**
     * Creates a new instance of <code>WrongArticlesListException</code> without
     * detail message.
     */
    public WrongArticlesListException() {
    }


    /**
     * Constructs an instance of <code>WrongArticlesListException</code> with
     * the specified detail message.
     * @param msg the detail message.
     */
    public WrongArticlesListException(String msg) {
        super(msg);
    }
}
