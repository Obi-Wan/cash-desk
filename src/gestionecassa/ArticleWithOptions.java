/*
 * ArticleWithOptions.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author ben
 */
public class ArticleWithOptions extends Article implements ArticleWithPreparation {

    /**
     * Lista delle options disponibili per questo bene.
     */
    List<String> options;

    /**
     * Explicit Constructor
     *
     * @param name 
     * @param price
     * @param options
     */
    public ArticleWithOptions(int id, String name, double price, Collection<String> options) {
        super(id,name, price);
        this.options = new ArrayList<String>(options);
    }

    /**
     * Explicit Constructor
     *
     * @param name 
     * @param price
     * @param options
     */
    public ArticleWithOptions(int id, String name, double price, Collection<String> options, boolean b) {
        super(id, name, price, b);
        this.options = new ArrayList<String>(options);
    }

    /**
     * Explicit base constructor
     *
     * @param name
     * @param price 
     */
    public ArticleWithOptions(int id, String name, double price) {
        this(id, name, price, new ArrayList<String>());
    }

    /**
     * Tells us wether it has opsions or not.
     *
     * @return true
     */
    @Override
    public boolean hasOptions() {
        return true;
    }

    @Override
    public String getPrintableFormat() {
        String output = new String(super.getPrintableFormat());
        for (String option : options) {
            output += String.format("\n\t      . %10s", option);
        }
        return output;
    }

    /**
     * 
     * @return
     */
    public List<String> getOptions() {
        return options;
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof ArticleWithOptions) &&
                (options.equals(((ArticleWithOptions)obj).options)) &&
                super.equals(obj));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.options != null ? this.options.hashCode() : 0);
        return hash + super.hashCode();
    }
}
