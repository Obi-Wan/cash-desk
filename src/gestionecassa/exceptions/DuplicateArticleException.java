/*
 * DuplicateArticleException.java
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

package gestionecassa.exceptions;

/**
 *
 * @author ben
 */
public class DuplicateArticleException extends Exception {

    /**
     * Creates a new instance of <code>DuplicateArticleException</code> without
     * detail message.
     */
    public DuplicateArticleException() {
    }


    /**
     * Constructs an instance of <code>DuplicateArticleException</code> with the
     * specified detail message.
     * @param msg the detail message.
     */
    public DuplicateArticleException(String msg) {
        super(msg);
    }
}
