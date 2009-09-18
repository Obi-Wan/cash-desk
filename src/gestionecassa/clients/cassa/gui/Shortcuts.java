/*
 * Shortcuts.java
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

import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;

/**
 *
 * @author ben
 */
public class Shortcuts {

    static public KeyStroke moreKeys[];
    static public KeyStroke lessKeys[];

    /**
     * It assigns the keys for fast selection of chosen articles in this displayedPanel
     */
    static {
        moreKeys = new KeyStroke[10];
        lessKeys = new KeyStroke[10];

        moreKeys[0] = KeyStroke.getKeyStroke(KeyEvent.VK_1,0);
        moreKeys[1] = KeyStroke.getKeyStroke(KeyEvent.VK_2,0);
        moreKeys[2] = KeyStroke.getKeyStroke(KeyEvent.VK_3,0);
        moreKeys[3] = KeyStroke.getKeyStroke(KeyEvent.VK_4,0);
        moreKeys[4] = KeyStroke.getKeyStroke(KeyEvent.VK_5,0);
        moreKeys[5] = KeyStroke.getKeyStroke(KeyEvent.VK_6,0);
        moreKeys[6] = KeyStroke.getKeyStroke(KeyEvent.VK_7,0);
        moreKeys[7] = KeyStroke.getKeyStroke(KeyEvent.VK_8,0);
        moreKeys[8] = KeyStroke.getKeyStroke(KeyEvent.VK_9,0);
        moreKeys[9] = KeyStroke.getKeyStroke(KeyEvent.VK_0,0);

        lessKeys[0] = KeyStroke.getKeyStroke(KeyEvent.VK_Q,0);
        lessKeys[1] = KeyStroke.getKeyStroke(KeyEvent.VK_W,0);
        lessKeys[2] = KeyStroke.getKeyStroke(KeyEvent.VK_E,0);
        lessKeys[3] = KeyStroke.getKeyStroke(KeyEvent.VK_R,0);
        lessKeys[4] = KeyStroke.getKeyStroke(KeyEvent.VK_T,0);
        lessKeys[5] = KeyStroke.getKeyStroke(KeyEvent.VK_Y,0);
        lessKeys[6] = KeyStroke.getKeyStroke(KeyEvent.VK_U,0);
        lessKeys[7] = KeyStroke.getKeyStroke(KeyEvent.VK_I,0);
        lessKeys[8] = KeyStroke.getKeyStroke(KeyEvent.VK_O,0);
        lessKeys[9] = KeyStroke.getKeyStroke(KeyEvent.VK_P,0);
    }
}
