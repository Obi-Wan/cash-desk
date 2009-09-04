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

import gestionecassa.ArticleWithOptions;
import gestionecassa.order.EntrySingleOption;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

/**
 *
 * @author ben
 */
public class GuiOrderSingleArticleWOptionsPanel extends GuiAbstrSingleArticlePanel {

    ArticleWithOptions article;

    List<GuiOrderSingleOptionPanel> optionsPanels;

    GuiNewOrderPanel parent;

    /**
     * Creates new form GuiOrderSingleArticleWOptionsPanel
     *
     * @param parent
     * @param bene
     * @param i
     */
    public GuiOrderSingleArticleWOptionsPanel(GuiNewOrderPanel parent, 
            ArticleWithOptions art, int index) {
        initComponents();

        this.parent = parent;
        this.article = art;
        this.jLabelName.setText(art.getName());
        this.jLabelPrice.setText("€ " + art.getPrice());

        optionsPanels = new ArrayList<GuiOrderSingleOptionPanel>();

        if (index < 10) {
            jButtonNuovo.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                    parent.moreKeys[index], "MORE"+index);
            jButtonNuovo.getActionMap().put("MORE"+index, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
//                    addNewOptionPanel();
                    modifyOptions();
                }
            });
            this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(parent.lessKeys[index], "LESS"+index);
            this.getActionMap().put("LESS"+index, new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
//                    removeLastOptionPanel();
                    modifyOptions();
                }
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
//        addNewOptionPanel();
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
    public void clean() {
        optionsPanels.removeAll(optionsPanels);
        rebuildListaOpzioni();
    }

    private void modifyOptions() {
        GuiOptionsHelperDialog dialog =
                new GuiOptionsHelperDialog(parent.parent, this);
        dialog.setVisible(true);
    }

    /**
     *
     */
    void addNewOptionPanel(String choice, int num) {
        GuiOrderSingleOptionPanel tempPanel =
                new GuiOrderSingleOptionPanel(this,article.getOptions());
        tempPanel.spinnerModel.setValue(num);
        tempPanel.comboModel.setSelectedItem(choice);
        
        optionsPanels.add(tempPanel);
        rebuildListaOpzioni();
    }

    /**
     *
     * @param panel
     */
    void removeOptionPanel(GuiOrderSingleOptionPanel panel) {
        optionsPanels.remove(panel);
        rebuildListaOpzioni();
        triggerUpdateCurrentOrder();
    }

    /**
     *
     */
    private void rebuildListaOpzioni() {
        jPanelOpzioni.removeAll();
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jPanelOpzioni);
        jPanelOpzioni.setLayout(layout);

        ParallelGroup tempHorizGroup = layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING);
        SequentialGroup tempSequGroup = layout.createSequentialGroup()
            .addContainerGap();

        for (GuiOrderSingleOptionPanel singolaOpzionePanel : optionsPanels) {

            tempHorizGroup.addComponent(singolaOpzionePanel,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

            tempSequGroup
                .addComponent(singolaOpzionePanel,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        }
        tempSequGroup.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

        // infine aggiungiamo il gruppo di elementi alla pagina principale.
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
     * 
     * @return
     */
    public List<EntrySingleOption> getPatialsList() {
        List<EntrySingleOption> tempLista
                = new ArrayList<EntrySingleOption>();
        for (GuiOrderSingleOptionPanel singolaOpzionePanel : optionsPanels) {
            if (singolaOpzionePanel.getNumParziale() != 0) {
                EntrySingleOption tempArray =
                    new EntrySingleOption(
                        singolaOpzionePanel.getComboChoice(),
                        singolaOpzionePanel.getNumParziale());
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
        for (GuiOrderSingleOptionPanel singolaOpzionePanel : optionsPanels) {
            tot += singolaOpzionePanel.getNumParziale();
        }
        return tot;
    }

    /**
     * 
     */
    void triggerUpdateCurrentOrder() {
        parent.updateCurrentOrder();
    }

    public GuiOrderSingleOptionPanel getSingleOptionPanel( String option ) {
        for (GuiOrderSingleOptionPanel panel : optionsPanels) {
            if (panel.hasSelected(option)) {
                return panel;
            }
        }
        return null;
    }
}
