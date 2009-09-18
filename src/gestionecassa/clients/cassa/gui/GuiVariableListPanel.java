/*
 * GuiVariableListPanel.java
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
 * GuiVariableListPanel.java
 *
 * Created on 4-set-2009, 18.18.59
 */

package gestionecassa.clients.cassa.gui;

import java.util.List;
import java.util.Vector;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

/**
 *
 * @author ben
 */
abstract public class GuiVariableListPanel extends javax.swing.JPanel {

    /**
     * List that associates a panel to each article
     */
    protected List<RecordPanels> panelsTable;

    /**
     * Default constructor
     */
    public GuiVariableListPanel() {
        panelsTable = new Vector<RecordPanels>();
    }

    /**
     * Explicit constructor
     *
     * @param panelses
     */
    public GuiVariableListPanel(List<RecordPanels> panelses) {
        this.panelsTable = panelses;
    }

    /**
     * Popolates the list of the panels related to each article sold.
     */
    abstract void buildContentsList();

    /**
     * It actually displays what the method <code>buildContentsList()</code>
     * stored in the table of SoldArticle-"Panel showing it".
     */
    void buildVisualList() {
        /* Prima di tutto rimuoviamo i pannelli di prima che se no incasinano
         * tutto */
        this.removeAll();

        /* Creo il nuovo layout in cui organizzerò i nuovi pannelli */
        javax.swing.GroupLayout layout =
                new javax.swing.GroupLayout(this);
        this.setLayout(layout);

        /* Creo i due gruppi con cui organizzare i pannelli */
        ParallelGroup tempHorizGroup = layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING);
        SequentialGroup tempSequGroup = layout.createSequentialGroup()
            .addContainerGap();

        /* Ciclo in cui aggiungo i pannelli ai gruppi con le impostazioni
         * giuste */
        for (RecordPanels singleRecord : panelsTable) {

            tempHorizGroup.addComponent(singleRecord.displayedPanel,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

            tempSequGroup
                .addComponent(singleRecord.displayedPanel,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        }

        /* Ultimo spazio del gruppo verticale */
        tempSequGroup.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                      Short.MAX_VALUE);

        /* infine aggiungiamo il gruppo di elementi al layout della pagina
         * principale. */
        layout.setHorizontalGroup(
          layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tempHorizGroup)
                .addContainerGap() )
        );
        layout.setVerticalGroup(
          layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tempSequGroup)
        );
    }

    /**
     * Cleans the gui.
     */
    void cleanDataFields() {
        for (RecordPanels singoloRecord : panelsTable) {
            singoloRecord.displayedPanel.clean();
        }
    }

    /**
     * Inner class that defines a record of panels and other useful info.
     *
     * @author ben
     */
    protected class RecordPanels {

        /**
         * Reference to the panel that will show up in the gui
         */
        final GuiAbstrSingleArticlePanel displayedPanel;

        /**
         * Explicit constructor
         *
         * @param article
         * @param displayedPanel
         */
        public RecordPanels(GuiAbstrSingleArticlePanel pan) {
            this.displayedPanel = pan;
        }
    }
}
