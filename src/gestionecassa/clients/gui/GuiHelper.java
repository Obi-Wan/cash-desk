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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

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
    static public void adaptContainerSize(Container container, Component content) {

        Dimension tempDim = content.getPreferredSize();
        tempDim.height = tempDim.height + 10;
        tempDim.width = tempDim.width + 10;

        container.setPreferredSize(tempDim);
    }

    /**
     * 
     * @author ben
     */
    static public class MngBorderLayout {

        static public void init(Container container) {
            if (!(container.getLayout() instanceof BorderLayout)) {
                container.setLayout(new BorderLayout());
            }
        }

        static public void putTop(Container cont, Component comp) {
            cont.add(comp, java.awt.BorderLayout.PAGE_START);
        }

        static public void putBottom(Container cont, Component comp) {
            cont.add(comp, java.awt.BorderLayout.PAGE_END);
        }

        static public void putCenter(Container cont, Component comp) {
            cont.add(comp, java.awt.BorderLayout.CENTER);
        }

        static public void putLeft(Container cont, Component comp) {
            cont.add(comp, java.awt.BorderLayout.LINE_START);
        }

        static public void putRight(Container cont, Component comp) {
            cont.add(comp, java.awt.BorderLayout.LINE_END);
        }
    }

}
