/*
 * GuiNuovoOrdinePanel.java
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
 * GuiNuovoOrdinePanel.java
 *
 * Created on 12-mag-2009, 22.16.39
 */

package gestionecassa.clients.cassa.gui;

import gestionecassa.clients.cassa.*;
import gestionecassa.BeneConOpzione;
import gestionecassa.BeneVenduto;
import gestionecassa.ListaBeni;
import gestionecassa.ordine.Ordine;
import gestionecassa.ordine.recordSingoloBene;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.KeyStroke;

/**
 *
 * @author ben
 */
public class GuiNuovoOrdinePanel extends javax.swing.JPanel {

    /**
     * Reference alla classe della business logic
     */
    CassaAPI owner;

    GuiAppFrameCassa parent;

    /**
     * Local Reference to the goods list.
     */
    ListaBeni listaBeni;

    /**
     * Lista appoggio che per ogni bene associa un pannello
     */
    List<recordListaBeni> tabellaBeni;

    KeyStroke moreKeys[];
    KeyStroke lessKeys[];

    /** 
     * Creates new form GuiNuovoOrdinePanel
     *
     * @param owner
     */
    public GuiNuovoOrdinePanel(CassaAPI owner, GuiAppFrameCassa parent) {
        initComponents();
        this.owner = owner;
        this.parent = parent;

        initKeysShortcuts();
        fetchListaBeni();
        buildContentsList();
        buildVisualList();

        this.setPreferredSize(new Dimension(800, 450));

        jButtonConferma.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        jButtonConferma.getActionMap().put("ENTER", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                confirmAndSendNewOrder();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanelListaBeni = new javax.swing.JPanel();
    jPanelBottoni1 = new javax.swing.JPanel();
    jButtonConferma = new javax.swing.JButton();
    jButtonPulisci = new javax.swing.JButton();
    jPanelBottoni2 = new javax.swing.JPanel();
    jButtonAnnulla = new javax.swing.JButton();
    jButtonAggiorna = new javax.swing.JButton();

    setMinimumSize(new java.awt.Dimension(150, 150));

    javax.swing.GroupLayout jPanelListaBeniLayout = new javax.swing.GroupLayout(jPanelListaBeni);
    jPanelListaBeni.setLayout(jPanelListaBeniLayout);
    jPanelListaBeniLayout.setHorizontalGroup(
      jPanelListaBeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 559, Short.MAX_VALUE)
    );
    jPanelListaBeniLayout.setVerticalGroup(
      jPanelListaBeniLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 315, Short.MAX_VALUE)
    );

    jButtonConferma.setText("Conferma");
    jButtonConferma.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonConfermaActionPerformed(evt);
      }
    });

    jButtonPulisci.setText("Pulisci");
    jButtonPulisci.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonPulisciActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanelBottoni1Layout = new javax.swing.GroupLayout(jPanelBottoni1);
    jPanelBottoni1.setLayout(jPanelBottoni1Layout);
    jPanelBottoni1Layout.setHorizontalGroup(
      jPanelBottoni1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelBottoni1Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jButtonConferma)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 342, Short.MAX_VALUE)
        .addComponent(jButtonPulisci)
        .addContainerGap())
    );
    jPanelBottoni1Layout.setVerticalGroup(
      jPanelBottoni1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelBottoni1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelBottoni1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButtonConferma)
          .addComponent(jButtonPulisci))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jButtonAnnulla.setText("Annulla Ultimo Ordine");
    jButtonAnnulla.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonAnnullaActionPerformed(evt);
      }
    });

    jButtonAggiorna.setText("Aggiorna Lista");
    jButtonAggiorna.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonAggiornaActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanelBottoni2Layout = new javax.swing.GroupLayout(jPanelBottoni2);
    jPanelBottoni2.setLayout(jPanelBottoni2Layout);
    jPanelBottoni2Layout.setHorizontalGroup(
      jPanelBottoni2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBottoni2Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jButtonAggiorna)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 212, Short.MAX_VALUE)
        .addComponent(jButtonAnnulla)
        .addContainerGap())
    );
    jPanelBottoni2Layout.setVerticalGroup(
      jPanelBottoni2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelBottoni2Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelBottoni2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jButtonAnnulla)
          .addComponent(jButtonAggiorna))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanelBottoni2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanelBottoni1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
      .addComponent(jPanelListaBeni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jPanelListaBeni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jPanelBottoni1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanelBottoni2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

    private void jButtonAggiornaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAggiornaActionPerformed
        requestListaBeni();
        fetchListaBeni();
        buildContentsList();
        buildVisualList();
    }//GEN-LAST:event_jButtonAggiornaActionPerformed

    private void jButtonPulisciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPulisciActionPerformed
        pulisci();
    }//GEN-LAST:event_jButtonPulisciActionPerformed

    private void jButtonConfermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfermaActionPerformed
        confirmAndSendNewOrder();
    }//GEN-LAST:event_jButtonConfermaActionPerformed

    private void jButtonAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnullaActionPerformed
        annullaUltimoOrdine();
    }//GEN-LAST:event_jButtonAnnullaActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonAggiorna;
  private javax.swing.JButton jButtonAnnulla;
  private javax.swing.JButton jButtonConferma;
  private javax.swing.JButton jButtonPulisci;
  private javax.swing.JPanel jPanelBottoni1;
  private javax.swing.JPanel jPanelBottoni2;
  private javax.swing.JPanel jPanelListaBeni;
  // End of variables declaration//GEN-END:variables

    /**
     * It assigns the keys for fast selection of chosen articles in this panel
     */
    private void initKeysShortcuts() {
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

    /**
     * Popolates the list of the panels related to each article sold.
     */
    private void buildContentsList() {
        tabellaBeni = new ArrayList<recordListaBeni>();
        int i = 0;
        for (BeneVenduto bene : listaBeni.lista) {
            GuiAbstrSingoloBenePanel tempPanel;
            if (bene instanceof BeneConOpzione) {
                tempPanel = new GuiSingoloBeneOpzioniOrdinePanel(this,(BeneConOpzione)bene);
            } else {
                tempPanel = new GuiSingoloBeneOrdinePanel(this,bene,i);
            }
            tabellaBeni.add(new recordListaBeni(bene, tempPanel));
            i++;
        }
    }

    /**
     * It actually displays what the method <code>buildContentsList()</code>
     * stored in the table of SoldArticle-"Panel showing it".
     */
    private void buildVisualList() {

        /* Prima di tutto rimuoviamo i pannelli di prima che se no incasinano
         * tutto
         */
        jPanelListaBeni.removeAll();

        /* Creo il nuovo layout in cui organizzerò i nuovi pannelli
         */
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(jPanelListaBeni);
        jPanelListaBeni.setLayout(layout);

        /* Creo i due gruppi con cui organizzare i pannelli
         */
        ParallelGroup tempHorizGroup = layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING);
        SequentialGroup tempSequGroup = layout.createSequentialGroup()
            .addContainerGap();

        /* Ciclo in cui aggiungo i pannelli ai gruppi con le impostazioni giuste
         */
        for (recordListaBeni singoloRecord : tabellaBeni) {

            tempHorizGroup.addComponent(singoloRecord.pannello,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

            tempSequGroup
                .addComponent(singoloRecord.pannello,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        }
        
        /* Ultimo spazio del gruppo verticale
         */
        tempSequGroup.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);

        /* infine aggiungiamo il gruppo di elementi al layout della pagina
         * principale.
         */
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
     * Gets the ArticleList and stores it locally.
     */
    private void fetchListaBeni() {
        listaBeni = owner.getListaBeni();
    }

    /**
     * it fetches the ArticleList from the server (through the CassaAPI)
     * and makes the client store it locally
     */
    private void requestListaBeni() {
        try {
            owner.requestListaBeni();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Il server non ha risposto alla richiesta della lista",
                "Errore di comunicazione",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * If the new order is not empty, it sends it to the server and cleans the
     * gui, ready for compiling a new order.
     */
    private void confirmAndSendNewOrder() {
        try {
            Ordine nuovoOrdine = creaNuovoOrdine();
            if (nuovoOrdine.getTotalPrize() != 0) {
                owner.sendNuovoOrdine(nuovoOrdine);
                parent.updateNewOrder(computeOrderPrize(nuovoOrdine));
                this.pulisci();
            }
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Il server non ha risposto alla richiesta dell'invio del " +
                "nuovo ordine",
                "Errore di comunicazione",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Cleans the gui.
     */
    private void pulisci() {
        for (recordListaBeni singoloRecord : tabellaBeni) {
            singoloRecord.pannello.clean();
        }
        parent.updateCurrentOrder(0);
    }

    /**
     * Inner class that defines a record of the table of goods.
     *
     * @author ben
     */
    class recordListaBeni {

        /**
         *
         */
        final BeneVenduto bene;

        /**
         *
         */
        final GuiAbstrSingoloBenePanel pannello;

        /**
         * Explicit constructor
         *
         * @param bene
         * @param pannello
         */
        public recordListaBeni(BeneVenduto bene, GuiAbstrSingoloBenePanel pannello) {
            this.bene = bene;
            this.pannello = pannello;
        }
    }

    /**
     * Creates a new order from the chosen Articles
     *
     * @return the created order
     * 
     * @throws RemoteException
     */
    private Ordine creaNuovoOrdine() throws RemoteException {
        int tempNumTot = 0;
        Ordine tempOrd = new Ordine(owner.getUsername(), owner.getHostname());

        for (recordListaBeni singoloRecord : tabellaBeni) {
            tempNumTot = singoloRecord.pannello.getNumTot();

            if (singoloRecord.pannello.getNumTot() != 0) {

                if (singoloRecord.bene.hasOptions()) {

                    int progressive = owner.getNProgressivo(
                            singoloRecord.bene.getNome(), tempNumTot);
                    tempOrd.addBeneConOpzione(
                            (BeneConOpzione)singoloRecord.bene,
                            tempNumTot, progressive,
                            ((GuiSingoloBeneOpzioniOrdinePanel)
                                (singoloRecord.pannello)).getListaParziali());
                } else {
                    tempOrd.addBeneVenduto(singoloRecord.bene,tempNumTot);
                }
            }
        }
        tempOrd.setTotalPrize(computeOrderPrize(tempOrd));
        return tempOrd;
    }

    /**
     * It calculates the amount the "still to be committed" order will cost
     *
     * @return
     */
    private double computeCurrentOrder() {
        double output = 0;
        for (recordListaBeni singoloRecord : tabellaBeni) {
            if (singoloRecord.pannello.getNumTot() != 0) {
                output += singoloRecord.pannello.getNumTot() *
                        singoloRecord.bene.getPrezzo();
            }
        }
        return output;
    }

    /**
     * Committs the calculated prize of the current partial order to the gui.
     */
    void updateCurrentOrder() {
        parent.updateCurrentOrder(computeCurrentOrder());
    }

    /**
     * Calculate prize o the given Order
     *
     * @param ordine the order to calculate
     *
     * @return Prize calculated.
     */
    private double computeOrderPrize(Ordine ordine) {
        List<recordSingoloBene> lista = ordine.getListaBeni();
        double output = 0;
        for (recordSingoloBene singoloBene : lista) {
            output += singoloBene.numTot * singoloBene.bene.getPrezzo();
        }
        return output;
    }

    /**
     * This cancells the last commited order. It's a function to treat carefully.
     */
    private void annullaUltimoOrdine() {
        final int result = javax.swing.JOptionPane.showConfirmDialog(this,
                "Vuoi veramente annullare l'ultimo ordine emesso?",
                "Annulla ultimo Ordine", javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);
        if (result == javax.swing.JOptionPane.YES_OPTION) {
            try {
                owner.annullaUltimoOrdine();
                javax.swing.JOptionPane.showMessageDialog(this,
                    "L'ultimo Ordine emesso è stato annullato",
                    "Operazione terminata",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException ex) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Il server non ha risposto alla richiesta di annullamento",
                    "Errore di comunicazione",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
        parent.cleanLastOrder();
    }
}
