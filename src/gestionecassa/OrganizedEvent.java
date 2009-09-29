/*
 * OrganizedEvent.java
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

import java.util.List;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class OrganizedEvent {

    /**
     * Titolo dell'evento.
     */
    final public String name;

    /**
     * Lista delle date dell'evento.
     */
    public List<EventDate> datesList;

    public OrganizedEvent(String title) {
        this.name = new String(title);
        this.datesList = new Vector<EventDate>();
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof OrganizedEvent) &&
                (this.name.equals(((OrganizedEvent)obj).name)) &&
                (this.datesList.equals(((OrganizedEvent)obj).datesList))
                );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + (this.datesList != null ? this.datesList.hashCode() : 0);
        return hash;
    }
}
