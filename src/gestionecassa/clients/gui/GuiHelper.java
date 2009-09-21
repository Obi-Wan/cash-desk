/*
 * GuiHelper.java
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

package gestionecassa.clients.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JComponent;

/**
 *
 * @author ben
 */
public class GuiHelper {

    /**
     * Packs and centers the windows
     * 
     * @param wind
     */
    static public void packAndCenter(Window wind) {
        wind.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = wind.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        wind.setSize(frameSize);
        wind.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    /**
     * Adapts the preferred size of the container to the one of the content
     * 
     * @param container
     * @param content
     */
    static public void adaptContainerSize(JComponent container, JComponent content) {

        Dimension tempDim = content.getPreferredSize();
        tempDim.height = tempDim.height + 10;
        tempDim.width = tempDim.width + 10;

        container.setPreferredSize(tempDim);
    }
}
