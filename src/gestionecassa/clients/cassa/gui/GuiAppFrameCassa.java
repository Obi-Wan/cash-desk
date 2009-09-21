/*
 * GuiAppFrameCassa.java
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
 * GuiAppFrameCassa.java
 *
 * Created on 21-mag-2009, 19.54.50
 */

package gestionecassa.clients.cassa.gui;

import gestionecassa.clients.gui.GuiToolbarPanel;
import gestionecassa.clients.gui.GuiAppFrame;
import gestionecassa.clients.gui.GuiHelper;
import gestionecassa.clients.gui.GuiOkCancelDialog;
import gestionecassa.clients.gui.GuiOptionsPanel;
import gestionecassa.clients.cassa.CassaAPI;
import java.awt.BorderLayout;

/**
 *
 * @author ben
 */
public class GuiAppFrameCassa extends GuiAppFrame<CassaAPI> {

    /**
     *
     */
    GuiToolbarPanel toolbar;

    /**
     * 
     */
    GuiStatusCassaPanel statusPanel;

    /**
     * Creates new form GuiAppFrameCassa
     *
     * @param owner
     */
    public GuiAppFrameCassa(CassaAPI owner) {
        super(owner);
        initComponents();

        toolbar = new GuiToolbarPanel(this);

        statusPanel = new GuiStatusCassaPanel(owner.getHostname());

        if (!(getContentPane().getLayout() instanceof BorderLayout)) {
            getContentPane().setLayout(new BorderLayout());
        }
        
        getContentPane().add(toolbar, java.awt.BorderLayout.PAGE_START);
        getContentPane().add(jScrollPanelMain, java.awt.BorderLayout.CENTER);
        getContentPane().add(statusPanel, java.awt.BorderLayout.LINE_END);

        enableLogout(false);

        GuiHelper.packAndCenter(this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables


    /**
     * Enables or disables logout button.
     *
     * @param value
     */
    public void enableLogout(boolean value) {
        toolbar.enableLogout(value);
    }

    /**
     * 
     */
    @Override
    public void selectedDialogOptions() {
        new GuiOkCancelDialog(this, "Client Options",
                                    new GuiOptionsPanel(owner)).setVisible(true);
    }

    /**
     *
     * @param price
     */
    void updateCurrentOrder(double price) {
        statusPanel.setPartialOrder(price);
    }

    /**
     *
     * @param price
     */
    void updateNewOrder(double price) {
        statusPanel.setEmittedOrder(price);
    }

    /**
     * 
     */
    void cleanLastOrder() {
        statusPanel.cleanLastOrder();
    }

    /**
     *
     * @param cassaAPI
     * @param username
     */
    public void setupAfterLogin(CassaAPI cassaAPI, String username) {
        this.enableLogout(true);
        GuiNewOrderPanel orderPanel = new GuiNewOrderPanel(cassaAPI,this);
        this.setContentPanel(orderPanel);
        statusPanel.setOrderPanel(orderPanel);
        statusPanel.setLogin(username);
    }

    /**
     *
     */
    public void setdownAfterLogout() {
        statusPanel.setOrderPanel(null);
        this.enableLogout(false);
        statusPanel.reset();
    }
}
