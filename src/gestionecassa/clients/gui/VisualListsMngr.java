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

package gestionecassa.clients.gui;

import gestionecassa.clients.cassa.gui.*;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.ArrayList;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JPanel;

/**
 *
 * @author ben
 */
public class VisualListsMngr<PanelType extends GuiAbstrSingleEntryPanel, DataType> {

    /**
     * The managed panel
     */
    protected JPanel managedPanel;

    /**
     * List that associates a panel to each article
     */
    protected List<RecordPanels<PanelType, DataType>> panelsList;

    /**
     * Alternative way of reaching records.
     */
    protected SortedMap<PanelType, RecordPanels<PanelType, DataType>> panelsMap;

    /**
     * Visual effect of an initial blanck space in the list.
     */
    protected boolean initialGap;

    /**
     * Default constructor
     */
    public VisualListsMngr(JPanel managed) {
        this(managed, new ArrayList<RecordPanels<PanelType, DataType>>() );
    }

    /**
     * Explicit constructor
     *
     * @param panelses
     */
    public VisualListsMngr(JPanel managed,
            List<RecordPanels<PanelType, DataType>> panelses) {
        this(managed, panelses, false);
    }

    /**
     * Completely explicit constructor
     * 
     * @param managed
     * @param panelses
     * @param initGap
     */
    private VisualListsMngr(JPanel managed,
            List<RecordPanels<PanelType, DataType>> panelses, boolean initGap) {
        this.panelsList = panelses;
        this.managedPanel = managed;
        panelsMap = new TreeMap<PanelType, RecordPanels<PanelType, DataType>>();
        for (RecordPanels<PanelType, DataType> recordPanels : panelses) {
            panelsMap.put(recordPanels.displayedPanel, recordPanels);
        }
        this.initialGap = initGap;
    }

    public void setInitialGap(boolean initialGap) {
        this.initialGap = initialGap;
    }

    public boolean getInitialGap() {
        return this.initialGap;
    }

    /**
     * It actually displays what the method <code>buildContentsList()</code>
     * stored in the table of SoldArticle-"Panel showing it".
     */
    public void buildVisualList() {
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
        SequentialGroup tempSequGroup = layout.createSequentialGroup();

        /* If requested leave a gap before the first item */
        if (initialGap) {
            tempSequGroup.addContainerGap();
        }

        /* Ciclo in cui aggiungo i pannelli ai gruppi con le impostazioni
         * giuste */
        for (RecordPanels singleRecord : panelsList) {

            if(singleRecord.displayedPanel instanceof VariableVisualList) {
                ((VariableVisualList)singleRecord.displayedPanel)
                        .rebuildVisualList();
            }

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
    public void cleanDataFields() {
        for (RecordPanels singoloRecord : panelsList) {
            singoloRecord.displayedPanel.clean();
        }
        buildVisualList();
    }

    /**
     *
     */
    public void resetList() {
        panelsList.clear();
        panelsMap.clear();
        managedPanel.removeAll();
    }

    /**
     * 
     * @param panel
     */
    public void remove(PanelType panel) {
        RecordPanels<PanelType, DataType> record = panelsMap.get(panel);
        if (record != null) {
            panelsList.remove(record);
            panelsMap.remove(panel);
        }
        // FIXME i should throw an exception
    }

    /**
     * 
     * @param pan
     * @param data
     */
    public void addRecord(PanelType pan, DataType data) {
        RecordPanels<PanelType, DataType> record =
                new RecordPanels<PanelType, DataType>(pan, data);
        panelsList.add(record);
        panelsMap.put(pan, record);
    }

    /**
     *
     * @return
     */
    public List<RecordPanels<PanelType, DataType>> getRecords() {
        return panelsList;
    }

    /**
     * 
     * @return
     */
    public Collection<PanelType> getPanels() {
        return panelsMap.keySet();
    }
}
