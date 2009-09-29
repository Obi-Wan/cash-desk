/*
 * EventDate.java
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

import java.util.Date;

/**
 *
 * @author ben
 */
public class EventDate {

    /**
     * Title of this specific date.
     */
    final public String titleDate;

    /**
     * Time/date at which the event date will start
     */
    final public Date startDate;

    /**
     * Time/date at which the event date will stop
     */
    final public Date endDate;

    /**
     * Explicit constructor
     * 
     * @param title
     * @param startDate
     * @param endDate
     */
    public EventDate(String title, long startDate, long endDate) {
        this.titleDate = new String(title);
        this.startDate = new Date(startDate);
        this.endDate = new Date(endDate);
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof EventDate) &&
                (this.titleDate.equals(((EventDate)obj).titleDate)) &&
                (this.startDate.equals(((EventDate)obj).startDate)) &&
                (this.endDate.equals(((EventDate)obj).endDate))
                );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + (this.titleDate != null ? this.titleDate.hashCode() : 0);
        hash = 73 * hash + (this.startDate != null ? this.startDate.hashCode() : 0);
        hash = 73 * hash + (this.endDate != null ? this.endDate.hashCode() : 0);
        return hash;
    }
}
