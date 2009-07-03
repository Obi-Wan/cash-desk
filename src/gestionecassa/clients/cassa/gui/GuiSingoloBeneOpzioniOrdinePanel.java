/*
 * GuiSingoloBeneOpzioniOrdinePanel.java
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
 * GuiSingoloBeneOpzioniOrdinePanel.java
 *
 * Created on 23-mag-2009, 14.06.45
 */

package gestionecassa.clients.cassa.gui;

import gestionecassa.BeneConOpzione;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

/**
 *
 * @author ben
 */
public class GuiSingoloBeneOpzioniOrdinePanel extends GuiAbstrSingoloBenePanel {

    BeneConOpzione bene;

    List<GuiSingolaOpzionePanel> pannelliopzioni;

    /** Creates new form GuiSingoloBeneOpzioniOrdinePanel */
    public GuiSingoloBeneOpzioniOrdinePanel(BeneConOpzione bene) {
        initComponents();

        this.bene = bene;
        this.jLabelNome.setText(bene.getNome());
        this.jLabelPrezzo.setText("€ " + bene.getPrezzo());

        pannelliopzioni = new ArrayList<GuiSingolaOpzionePanel>();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jLabelNome = new javax.swing.JLabel();
    jLabelPrezzo = new javax.swing.JLabel();
    jButtonNuovo = new javax.swing.JButton();
    jPanelOpzioni = new javax.swing.JPanel();

    setBorder(javax.swing.BorderFactory.createEtchedBorder());

    jLabelNome.setText("Nome");

    jLabelPrezzo.setText("Prezzo");

    jButtonNuovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gestionecassa/resources/dialog-yes.png"))); // NOI18N
    jButtonNuovo.setText("Nuovo");
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
      .addGap(0, 360, Short.MAX_VALUE)
    );
    jPanelOpzioniLayout.setVerticalGroup(
      jPanelOpzioniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 0, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
            .addGap(24, 24, 24)
            .addComponent(jPanelOpzioni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jButtonNuovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
                .addComponent(jLabelPrezzo)))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelNome)
          .addComponent(jLabelPrezzo))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanelOpzioni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButtonNuovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

    private void jButtonNuovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuovoActionPerformed
        addNewOpzionePanel();
    }//GEN-LAST:event_jButtonNuovoActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonNuovo;
  private javax.swing.JLabel jLabelNome;
  private javax.swing.JLabel jLabelPrezzo;
  private javax.swing.JPanel jPanelOpzioni;
  // End of variables declaration//GEN-END:variables


    /**
     *
     */
    public void clean() {
        pannelliopzioni.removeAll(pannelliopzioni);
        rebuildListaOpzioni();
    }

    /**
     *
     */
    private void addNewOpzionePanel() {
        GuiSingolaOpzionePanel tempPanel =
                new GuiSingolaOpzionePanel(this,bene.getOpzioni());
        pannelliopzioni.add(tempPanel);
        rebuildListaOpzioni();
    }

    /**
     *
     * @param panel
     */
    void removeOpzionePanel(GuiSingolaOpzionePanel panel) {
        pannelliopzioni.remove(panel);
        rebuildListaOpzioni();
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

        for (GuiSingolaOpzionePanel singolaOpzionePanel : pannelliopzioni) {

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
    public List<int[]> getListaParziali() {
        List<int[]> tempLista = new ArrayList<int[]>();
        for (GuiSingolaOpzionePanel singolaOpzionePanel : pannelliopzioni) {
            int[] tempArray = 
                {singolaOpzionePanel.getComboNum(),
                 singolaOpzionePanel.getNumParziale()};
            tempLista.add(tempArray);
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
        for (GuiSingolaOpzionePanel singolaOpzionePanel : pannelliopzioni) {
            tot += singolaOpzionePanel.getNumParziale();
        }
        return tot;
    }
}