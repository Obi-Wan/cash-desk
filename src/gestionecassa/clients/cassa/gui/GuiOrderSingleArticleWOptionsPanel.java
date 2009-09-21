/*
 * GuiOrderSingleArticleWOptionsPanel.java
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
 * GuiOrderSingleArticleWOptionsPanel.java
 *
 * Created on 23-mag-2009, 14.06.45
 */

package gestionecassa.clients.cassa.gui;

import gestionecassa.clients.gui.VisualListsMngr;
import gestionecassa.clients.gui.GuiOkCancelDialog;
import gestionecassa.ArticleWithOptions;
import gestionecassa.clients.gui.RecordPanels;
import gestionecassa.order.EntrySingleOption;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;

/**
 *
 * @author ben
 */
public class GuiOrderSingleArticleWOptionsPanel extends GuiAbstrSingleEntryPanel {

    ArticleWithOptions article;

    VisualListsMngr<GuiOrderSingleOptionPanel, String> listMngr;

    GuiNewOrderPanel orderPanel;

    /**
     * Creates new form GuiOrderSingleArticleWOptionsPanel
     *
     * @param orderPanel
     * @param art 
     * @param index 
     */
    public GuiOrderSingleArticleWOptionsPanel(GuiNewOrderPanel orderPanel,
            ArticleWithOptions art, int index) {
        initComponents();

        this.orderPanel = orderPanel;
        this.article = art;
        this.jLabelName.setText(art.getName());
        this.jLabelPrice.setText("€ " + art.getPrice());

        listMngr = new VisualListsMngr<GuiOrderSingleOptionPanel, String>(jPanelOpzioni);
        
        if (index < 10) {
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                    Shortcuts.moreKeys[index], "MORE"+index);
            this.getActionMap().put("MORE"+index, new AbstractAction() {
                public void actionPerformed(ActionEvent e) { modifyOptions(); }
            });
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                    Shortcuts.lessKeys[index], "LESS"+index);
            this.getActionMap().put("LESS"+index, new AbstractAction() {
                public void actionPerformed(ActionEvent e) { modifyOptions(); }
            });
        }

        jLabelNum.setText((index + 1) + ".");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabelName = new javax.swing.JLabel();
    jLabelPrice = new javax.swing.JLabel();
    jButtonNuovo = new javax.swing.JButton();
    jPanelOpzioni = new javax.swing.JPanel();
    jLabelNum = new javax.swing.JLabel();

    setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jLabelName.setText("Nome");

    jLabelPrice.setText("Prezzo");

    jButtonNuovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestionecassa/resources/dialog-yes.png"))); // NOI18N
    jButtonNuovo.setText("Modifica");
    jButtonNuovo.setPreferredSize(new java.awt.Dimension(102, 25));
    jButtonNuovo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonNuovoActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanelOpzioniLayout = new javax.swing.GroupLayout(jPanelOpzioni);
    jPanelOpzioni.setLayout(jPanelOpzioniLayout);
    jPanelOpzioniLayout.setHorizontalGroup(
      jPanelOpzioniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 342, Short.MAX_VALUE)
    );
    jPanelOpzioniLayout.setVerticalGroup(
      jPanelOpzioniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addComponent(jButtonNuovo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabelNum)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanelOpzioni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
              .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 289, Short.MAX_VALUE)
                .addComponent(jLabelPrice)))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelPrice)
          .addComponent(jLabelNum)
          .addComponent(jLabelName))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanelOpzioni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButtonNuovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuovoActionPerformed
        modifyOptions();
    }//GEN-LAST:event_jButtonNuovoActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonNuovo;
  private javax.swing.JLabel jLabelName;
  private javax.swing.JLabel jLabelNum;
  private javax.swing.JLabel jLabelPrice;
  private javax.swing.JPanel jPanelOpzioni;
  // End of variables declaration//GEN-END:variables


    /**
     *
     */
    @Override
    public void clean() {
        listMngr.resetList();
    }

    /**
     * Opens a dialog that makes it possible to add/delete/modify options for
     * the selected <code>ArticleWithOptions</code>
     */
    private void modifyOptions() {
        GuiAccelOptionsPanel panel = new GuiAccelOptionsPanel(this);
        GuiOkCancelDialog dialog =
                new GuiOkCancelDialog(orderPanel.frame, article.getName(), panel);
        dialog.setVisible(true);
    }

    /**
     * Adds a new panel with the selected choice and the partial number specified
     * It doesnt update the visual list. The method <code>updateAfterModify()</code>
     * will do it.
     *
     * @param choice
     * @param num
     */
    void addNewOptionPanel(String choice, int num) {
        GuiOrderSingleOptionPanel tempPanel =
                new GuiOrderSingleOptionPanel(this, choice, num);
        listMngr.addRecord(tempPanel, choice);
    }

    /**
     * Removes the panel from the list of panels to show.
     * It doesnt update the visual list. The method <code>updateAfterModify()</code>
     * will do it.
     *
     * @param panel
     */
    void removeOptionPanel(GuiOrderSingleOptionPanel panel) {
        listMngr.remove(panel);
    }

    /**
     * Updates the visual list after a modification of the options of an
     * <code>ArticleWithOptions</code>
     */
    void updateAfterModify() {
        listMngr.buildVisualList();
        triggerUpdateCurrentOrder();
    }

    /**
     * 
     * @return
     */
    public List<EntrySingleOption> getPatialsList() {
        List<EntrySingleOption> tempLista
                = new ArrayList<EntrySingleOption>();
        for (RecordPanels<GuiOrderSingleOptionPanel, String> recordPanels
                : listMngr.getRecords()) {
            if (recordPanels.displayedPanel.getNumTot() != 0) {
                EntrySingleOption tempArray =
                    new EntrySingleOption(
                        recordPanels.data,
                        recordPanels.displayedPanel.getNumTot());
                tempLista.add(tempArray);
            }
        }
        return tempLista;
    }

    /**
     * 
     * @return
     */
    @Override
    public int getNumTot() {
        int tot = 0;
        for (GuiOrderSingleOptionPanel panel : listMngr.getPanels()) {
            tot += panel.getNumTot();
        }
        return tot;
    }

    /**
     * 
     */
    void triggerUpdateCurrentOrder() {
        orderPanel.updateCurrentOrder();
    }

    /**
     * 
     * @param option
     * @return
     */
    public GuiOrderSingleOptionPanel getSingleOptionPanel( String option ) {
        for (GuiOrderSingleOptionPanel panel : listMngr.getPanels()) {
            if (panel.hasSelected(option)) {
                return panel;
            }
        }
        return null;
    }
}
