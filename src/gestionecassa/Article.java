/*
 * Article.java
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

import java.io.Serializable;

/**
 * Class rapresenting an Article sold. Not synchronized.
 * If accessing concurrently you need to externally synchronize it.
 *
 * @author ben
 */
public class Article implements Serializable {

    /**
     * Id of the article
     */
    final int id;

    /**
     * Name of the article
     */
    final String name;

    /**
     * Price of a single piece of this article
     */
    final double price;

    /**
     * Whether it is enabled or not.
     */
    boolean enabled;

    /**
     * Explicit constructor
     *
     * @param id Id of this article
     * @param name Name of the article
     * @param price Price
     */
    public Article(int id, String name, double price) {
        this(id, name, price, true);
    }

    /**
     * Most explicit constructor
     *
     * @param id Id of this article
     * @param name Name of the article
     * @param price
     * @param enabled
     */
    public Article(int id, String name, double price, boolean enabled) {
        this.name = new String(name);
        this.price = price;
        this.enabled = enabled;
        this.id = id;
    }

    /**
     * Tells us wheter it has options or not.
     *
     * @return false
     */
    public boolean hasOptions() {
        return false;
    }

    /**
     * Sort of toString, but leaving that fully functional
     *
     * @return The string describing the good.
     */
    public String getPrintableFormat() {
        return String.format("- %s - %10s -€ %05.2f",
                             (enabled ? "Enabled " : "Disabled"), name, price);
    }

    /**
     *
     * @return A string containing the name of this article
     */
    final public String getName() {
        return name;
    }

    /**
     *
     * @return A double containing the price of this article
     */
    final public double getPrice() {
        return price;
    }

    /**
     * Sets the new value for this article
     *
     * @param enabled New value for the field enabled
     *
     * @return A reference to this article
     */
    public Article setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    /**
     * Tells whether this article is enabled or not
     *
     * @return <code>true</code> if it's enbaled, <code>false</code> if not.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Getter for the id of the article
     *
     * @return Int containing the Id
     */
    public int getId() {
        return id;
    }

    /**
     * Tells whether this <code>Article</code> is the same of another.
     *
     * @param obj The other article to compare
     *
     * @return <code>true</code> if they are the same, <code>false</code> if not.
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Article) &&
                (this.name.equals(((Article)obj).name)) &&
                (this.price == (((Article)obj).price));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.price) ^
                                (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }
}
