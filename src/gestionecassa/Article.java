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

/**
 * Class rapresenting an Article sold. Not synchronized.
 * If accessing concurrently you need to externally synchronize it.
 *
 * @author ben
 */
public class Article extends ManageableObject {

    /**
     * Price of a single piece of this article
     */
    final double price;

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
        super(id, name, enabled);
        this.price = price;
    }

    /**
     * Copy construct
     * @param art The article to copy from
     */
    public Article(Article art) {
        super(art);
        this.price = art.price;
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
    @Override
    public String getPrintableFormat() {
        return String.format("- %s - %10s -€ %05.2f",
                     (isEnabled() ? "Enabled " : "Disabled"), getName(), price);
    }

    /**
     *
     * @return A double containing the price of this article
     */
    final public double getPrice() {
        return price;
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
                (this.getName().equals(((Article)obj).getName())) &&
                (this.price == (((Article)obj).price));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.getId();
        hash = 37 * hash + (this.getName() != null ? this.getName().hashCode() : 0);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.price) ^
                                (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }
}
