/*
 * GuiNewOrderPanel.java
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
 * GuiNewOrderPanel.java
 *
 * Created on 12-mag-2009, 22.16.39
 */

package gestionecassa.clients.cassa.gui;

import gestionecassa.clients.gui.VisualListsMngr;
import gestionecassa.clients.cassa.*;
import gestionecassa.ArticleWithOptions;
import gestionecassa.Article;
import gestionecassa.ArticleGroup;
import gestionecassa.ArticlesList;
import gestionecassa.clients.gui.VariableVisualList;
import gestionecassa.exceptions.WrongArticlesListException;
import gestionecassa.order.EntryArticleGroup;
import gestionecassa.order.Order;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

/**
 *
 * @author ben
 */
public final class GuiNewOrderPanel extends javax.swing.JPanel implements VariableVisualList {

    /**
     * Reference alla classe della business logic
     */
    CassaAPI owner;

    /**
     * 
     */
    GuiAppFrameCassa frame;

    /**
     * Local Reference to the articles list.
     */
    ArticlesList articlesList;

    /**
     * 
     */
    VisualListsMngr<GuiGroupPanel, ArticleGroup> varListMng;

    /** 
     * Creates new form GuiNewOrderPanel
     *
     * @param owner Reference to the client app
     * @param frame Reference to the frame containing this panel
     */
    public GuiNewOrderPanel(CassaAPI owner, GuiAppFrameCassa frame) {
        initComponents();
        this.owner = owner;
        this.frame = frame;
        
        fetchArticlesList();

        varListMng = new VisualListsMngr<GuiGroupPanel, ArticleGroup>(this);
        varListMng.setHasInitialGap(true);
        buildContentsList();
        rebuildVisualList();

        this.setPreferredSize(new Dimension(800, 450));

        this.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        this.getActionMap().put("ENTER", new AbstractAction() {
            @Override
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

    setMinimumSize(new java.awt.Dimension(150, 150));

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 559, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 443, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables


    /**
     * Populates the list of the panels related to each article sold.
     */
    void buildContentsList() {
        this.varListMng.resetList();
        /* Visual Id of articles for shortcuts from keyboard */
        int i = 0;
        /* Every group will have its panel */
        for (ArticleGroup articleGroup : articlesList.getGroupsList()) {

            /* create and add to the manager the panel for this group */
            GuiGroupPanel grPanel = new GuiGroupPanel(this, articleGroup);
            this.varListMng.addRecord(grPanel, articleGroup);

            /* for every article in that group */
            for (Article article : articleGroup.getList()) {
                GuiAbstrSingleEntryPanel tempPanel;
                if (article instanceof ArticleWithOptions) {
                    tempPanel =
                            new GuiOrderSingleArticleWOptionsPanel(this,
                                                (ArticleWithOptions)article,i);
                } else {
                    tempPanel = new GuiOrderSingleArticlePanel(this, article, i);
                }
                grPanel.varListMngr.addRecord(tempPanel, article);
                i++;
            }

            /* let's create the internal list for this group panel */
            grPanel.varListMngr.buildVisualList();
        }
    }

    void cleanDataFields() {
        varListMng.cleanDataFields();
        frame.getStatusPanel().setPartialOrder(0);
    }

    @Override
    public void rebuildVisualList() {
        varListMng.buildVisualList();
        frame.refreshContentPanel();
    }

    void updateList() {
        forceRMIRequestArticlesList();
        fetchArticlesList();
        buildContentsList();
        rebuildVisualList();
    }

    /**
     * Gets the ArticleList and stores it locally.
     */
    private void fetchArticlesList() {
        articlesList = owner.getArticlesList();
    }

    /**
     * it fetches the ArticleList from the server (through the CassaAPI)
     * and makes the client store it locally
     */
    private void forceRMIRequestArticlesList() {
        try {
            owner.fetchRMIArticlesList();
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Il server non ha risposto alla richiesta della lista",
                "Errore di comunicazione",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Creates a new order from the chosen Articles
     * @return the created order
     * @throws RemoteException
     */
    private Order createNewOrder() throws RemoteException {
        // TODO One day will be needed here to handle the table properly
        Order tempOrd = new Order(owner.getUsername(), owner.getHostname(), 0,
                                    articlesList.getSignature());

        for (GuiGroupPanel group : varListMng.getPanels()) {
            EntryArticleGroup tempEntry = group.collectOrderEntries();
            if (tempEntry.numTot > 0) {
                tempOrd.addGroup(tempEntry);
            }
        }
        return tempOrd;
    }

    /**
     * It calculates the amount the "still to be committed" order will cost
     * @return count of this partial order
     */
    private double computeCurrentOrder() {
        double output = 0;
        for (GuiGroupPanel group : varListMng.getPanels()) {
            output += group.getPartialOrderPrice();
        }
        return output;
    }

    /**
     * Committs the calculated price of the current partial order to the gui.
     */
    void updateCurrentOrder() {
        frame.getStatusPanel().setPartialOrder(computeCurrentOrder());
    }

    /**
     * If the new order is not empty, it sends it to the server and cleans the
     * gui, ready for compiling a new order.
     */
    void confirmAndSendNewOrder() {
        try {
            Order nuovoOrdine = createNewOrder();

            if (nuovoOrdine.getTotalPrice() != 0) {
                owner.sendRMINewOrder(nuovoOrdine);
                frame.getStatusPanel().setEmittedOrder(nuovoOrdine.getTotalPrice());
                this.cleanDataFields();
            }
        } catch (WrongArticlesListException ex) {
            owner.getLogger().warn("The list of articles is outdated", ex);
            javax.swing.JOptionPane.showMessageDialog(this,
                "La lista degli articoli non coincide con quella del server\n" +
                "Per favore aggiornala",
                "Errore nella lista degli articoli",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (RemoteException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Il server non ha risposto alla richiesta dell'invio del " +
                "nuovo ordine",
                "Errore di comunicazione",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Il server ha avuto problemi col DB",
                "Errore del Backend sul server",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This cancells the last commited order. It's a function to treat carefully
     */
    void undoLastOrder() {
        final int result = javax.swing.JOptionPane.showConfirmDialog(this,
                "Vuoi veramente annullare l'ultimo ordine emesso?",
                "Annulla ultimo Ordine", javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);
        if (result == javax.swing.JOptionPane.YES_OPTION) {
            try {
                owner.delRMILastOrder();
                frame.getStatusPanel().cleanLastOrder();
                
                javax.swing.JOptionPane.showMessageDialog(this,
                    "L'ultimo Ordine emesso è stato annullato",
                    "Operazione terminata",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException ex) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Il server non ha risposto alla richiesta di annullamento",
                    "Errore di comunicazione",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Il server ha avuto problemi col DB",
                    "Errore del Backend sul server",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
