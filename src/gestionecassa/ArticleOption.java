/*
 * ArticleOption.java
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

package gestionecassa;

/**
 *
 * @author ben
 */
public class ArticleOption extends ManageableObject {

    /**
     * Specific description of this option
     */
    private final String description;

    /**
     * Constructor
     * @param id The id of this option
     * @param name Name of this option
     * @param enabled If it's enabled or not
     */
    public ArticleOption(int id, String name, boolean enabled) {
        this(id, name, enabled, "");
    }

    /**
     * Constructor
     * @param id The id of this option
     * @param name Name of this option
     * @param enabled If it's enabled or not
     * @param descr
     */
    public ArticleOption(int id, String name, boolean enabled, String descr) {
        super(id, name, enabled);
        this.description = descr;
    }

    @Override
    public String getPrintableFormat() {
        return getName();
    }

    /**
     * Gets the description of this option
     * @return A String containing the description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ArticleOption) &&
                (this.getName().equals(((ArticleOption)obj).getName())) &&
                (this.description.equals(((ArticleOption)obj).description));
    }
}
