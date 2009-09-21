/*
 * RecordPanels.java
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

/*
 * RecordPanels.java
 *
 * Created on 4-set-2009, 18.18.59
 */

package gestionecassa.clients.gui;

import gestionecassa.clients.cassa.gui.GuiAbstrSingleEntryPanel;

/**
 * Class that defines a record of panels and other useful info.
 *
 * @author ben
 */
public class RecordPanels<PanelType extends GuiAbstrSingleEntryPanel, DataType> {

    /**
     *
     */
    public final DataType data;

    /**
     * Reference to the panel that will show up in the gui
     */
    public final PanelType displayedPanel;

    /**
     * Explicit constructor
     * 
     * @param pan
     * @param data
     */
    public RecordPanels(PanelType pan, DataType data) {
        this.displayedPanel = pan;
        this.data = data;
    }
}
