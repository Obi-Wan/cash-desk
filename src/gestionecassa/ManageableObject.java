/*
 * ManageableObject.java
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

import java.io.Serializable;

/**
 *
 * @author ben
 */
public abstract class ManageableObject implements Serializable {

    /**
     * Id of the object
     */
    private final int id;

    /**
     * Name of the object
     */
    private final String name;

    /**
     * Whether it is enabled or not.
     */
    private boolean enabled;

    /**
     * Whether it is currently availabe or not.
     */
    private boolean currentlyAvailabe;

    /**
     * Explicit Constructor
     * @param id Id of the object
     * @param name String containing the name of the object
     * @param enabled Boolean telling it's enabled state
     */
    public ManageableObject(int id, String name, boolean enabled) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.currentlyAvailabe = true;
    }

    /**
     * Copy constructor
     * @param obj The {@code ManageableObject} to copy from
     */
    public ManageableObject(ManageableObject obj) {
        this(obj.id, obj.name, obj.enabled);
        this.currentlyAvailabe = obj.currentlyAvailabe;
    }

    /**
     * Getter for the id of the object
     * @return Int containing the Id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of this object
     * @return A String containing the name
     */
    public final String getName() {
        return name;
    }

    /**
     * Sort of toString, but leaving that fully functional
     * @return The string describing the good.
     */
    public abstract String getPrintableFormat();

    /**
     * Tells whether this article is enabled or not
     * @return <code>true</code> if it's enbaled, <code>false</code> if not.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enables or disables this Object
     * @param enabled Boolean telling whether to enable or disable
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    /**
     * Tells whether this article is currently available or not
     * @return <code>true</code> if it's available, <code>false</code> if not.
     */
    public boolean isAvailable() {
        return currentlyAvailabe;
    }

    /**
     * Marks this Object currently available or not
     * @param available Boolean: {@code true} stands for available
     */
    public void setAvailabe(boolean available) {
        this.currentlyAvailabe = available;
    }
}
