/*
 * GuiAbstrMoreLessPanel.java
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

package gestionecassa.clients.cassa.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author ben
 */
abstract public class GuiAbstrMoreLessPanel extends GuiAbstrSingleEntryPanel {

    /**
     * Number Model of the spinner. We do just interact with it by code.
     */
    SpinnerNumberModel spinnerModel;

    /**
     * Explicit constructor
     *
     * @param index
     * @param startNumber
     */
    public GuiAbstrMoreLessPanel(int index, int startNumber) {
        
        spinnerModel = new SpinnerNumberModel(startNumber, 0, 255, 1);

        if (index < 10) {
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                    Shortcuts.moreKeys[index], "MORE"+index);
            this.getActionMap().put("MORE"+index, new AbstractAction() {
                public void actionPerformed(ActionEvent e) { more(); }
            });
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                    Shortcuts.lessKeys[index], "LESS"+index);
            this.getActionMap().put("LESS"+index, new AbstractAction() {
                public void actionPerformed(ActionEvent e) { less(); }
            });
        }
    }

    /**
     * Resets the spinner to the minimum ( 0 ) value of the model
     */
    @Override
    public void clean() {
        spinnerModel.setValue(spinnerModel.getMinimum());
    }

    /**
     * Retrieves the value of the spinner
     * 
     * @return
     */
    public int getNumTot() {
        return spinnerModel.getNumber().intValue();
    }

    /**
     * Decreases the spinner by one
     */
    public void less() {
        Object previous = spinnerModel.getPreviousValue();
        if (previous != null) {
            spinnerModel.setValue(previous);
        }
    }

    /**
     * Increases the spinner by one
     */
    public void more() {
        Object next = spinnerModel.getNextValue();
        if (next != null) {
            spinnerModel.setValue(next);
        }
    }

}
