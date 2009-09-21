/*
 * VisualListsMngr.java
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
 * VisualListsMngr.java
 *
 * Created on 4-set-2009, 18.18.59
 */

package gestionecassa.clients.cassa.gui;

import java.util.List;
import java.util.Vector;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JPanel;

/**
 *
 * @author ben
 */
public class VisualListsMngr<Data> {

    protected JPanel managedPanel;

    /**
     * List that associates a panel to each article
     */
    protected List<RecordPanels> panelsTable;

    /**
     * Default constructor
     */
    public VisualListsMngr(JPanel managed) {
        this.managedPanel = managed;
        panelsTable = new Vector<RecordPanels>();
    }

    /**
     * Explicit constructor
     *
     * @param panelses
     */
    public VisualListsMngr(JPanel managed, List<RecordPanels> panelses) {
        this.panelsTable = panelses;
        this.managedPanel = managed;
    }

    /**
     * It actually displays what the method <code>buildContentsList()</code>
     * stored in the table of SoldArticle-"Panel showing it".
     */
    void buildVisualList() {
        /* Prima di tutto rimuoviamo i pannelli di prima che se no incasinano
         * tutto */
        managedPanel.removeAll();

        /* Creo il nuovo layout in cui organizzerò i nuovi pannelli */
        javax.swing.GroupLayout layout =
                new javax.swing.GroupLayout(managedPanel);
        managedPanel.setLayout(layout);

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
    public class RecordPanels {

        /**
         * 
         */
        final Data data;

        /**
         * Reference to the panel that will show up in the gui
         */
        final GuiAbstrSingleEntryPanel displayedPanel;

        /**
         * Explicit constructor
         *
         * @param data 
         * @param displayedPanel
         */
        public RecordPanels(GuiAbstrSingleEntryPanel pan, Data data) {
            this.displayedPanel = pan;
            this.data = data;
        }
    }

    /**
     * 
     * @param pan
     * @param data
     */
    void addRecord(GuiAbstrSingleEntryPanel pan, Data data) {
        panelsTable.add(new RecordPanels(pan, data));
    }
}
