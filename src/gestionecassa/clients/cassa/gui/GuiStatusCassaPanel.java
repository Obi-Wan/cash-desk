/*
 * GuiStatusCassaPanel.java
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
 * GuiStatusCassaPanel.java
 *
 * Created on 6-lug-2009, 16.16.23
 */

package gestionecassa.clients.cassa.gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author ben
 */
public class GuiStatusCassaPanel extends javax.swing.JPanel {

    GuiOrderPanel orderPanel;

    /** Creates new form GuiStatusCassaPanel
     * @param hostname Hostname of the machine onto which we are running
     */
    public GuiStatusCassaPanel(String hostname) {
        initComponents();
        jTextFieldHostname.setText(hostname);

        enableButtons(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPanelMachineStatus = new javax.swing.JPanel();
    jLabelHostname = new javax.swing.JLabel();
    jTextFieldHostname = new javax.swing.JTextField();
    jLabelUser = new javax.swing.JLabel();
    jTextFieldUser = new javax.swing.JTextField();
    jLabelLogged = new javax.swing.JLabel();
    jTextFieldLogged = new javax.swing.JTextField();
    jPanelLastOrder = new javax.swing.JPanel();
    jLabelTime = new javax.swing.JLabel();
    jTextFieldTime = new javax.swing.JTextField();
    jLabelPrize = new javax.swing.JLabel();
    jTextFieldPrice = new javax.swing.JTextField();
    jLabelStatus = new javax.swing.JLabel();
    jTextFieldStatus = new javax.swing.JTextField();
    jPanelCurrentOrder = new javax.swing.JPanel();
    jLabelPrizeCurrent = new javax.swing.JLabel();
    jTextFieldPriceCurrent = new javax.swing.JTextField();
    jPanelButtons = new javax.swing.JPanel();
    jButtonConferma = new javax.swing.JButton();
    jButtonPulisci = new javax.swing.JButton();
    jButtonAggiorna = new javax.swing.JButton();
    jButtonAnnulla = new javax.swing.JButton();

    jPanelMachineStatus.setBorder(javax.swing.BorderFactory.createTitledBorder("Client Status"));

    jLabelHostname.setText("Hostname");

    jTextFieldHostname.setEditable(false);
    jTextFieldHostname.setOpaque(false);

    jLabelUser.setText("Username");

    jTextFieldUser.setEditable(false);
    jTextFieldUser.setOpaque(false);

    jLabelLogged.setText("Logged");

    jTextFieldLogged.setEditable(false);
    jTextFieldLogged.setOpaque(false);

    javax.swing.GroupLayout jPanelMachineStatusLayout = new javax.swing.GroupLayout(jPanelMachineStatus);
    jPanelMachineStatus.setLayout(jPanelMachineStatusLayout);
    jPanelMachineStatusLayout.setHorizontalGroup(
      jPanelMachineStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelMachineStatusLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelMachineStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanelMachineStatusLayout.createSequentialGroup()
            .addComponent(jLabelHostname)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextFieldHostname, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
          .addGroup(jPanelMachineStatusLayout.createSequentialGroup()
            .addComponent(jLabelUser)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextFieldUser, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
          .addGroup(jPanelMachineStatusLayout.createSequentialGroup()
            .addComponent(jLabelLogged)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jTextFieldLogged, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)))
        .addContainerGap())
    );
    jPanelMachineStatusLayout.setVerticalGroup(
      jPanelMachineStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelMachineStatusLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelMachineStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelHostname)
          .addComponent(jTextFieldHostname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelMachineStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelUser)
          .addComponent(jTextFieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelMachineStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelLogged)
          .addComponent(jTextFieldLogged, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(14, Short.MAX_VALUE))
    );

    jPanelLastOrder.setBorder(javax.swing.BorderFactory.createTitledBorder("Last Order"));

    jLabelTime.setText("Time");

    jTextFieldTime.setEditable(false);

    jLabelPrize.setText("Price");

    jTextFieldPrice.setEditable(false);
    jTextFieldPrice.setFont(new java.awt.Font("Dialog", 1, 36));

    jLabelStatus.setText("Status");

    jTextFieldStatus.setEditable(false);

    javax.swing.GroupLayout jPanelLastOrderLayout = new javax.swing.GroupLayout(jPanelLastOrder);
    jPanelLastOrder.setLayout(jPanelLastOrderLayout);
    jPanelLastOrderLayout.setHorizontalGroup(
      jPanelLastOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelLastOrderLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelLastOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabelTime)
          .addComponent(jLabelPrize)
          .addComponent(jLabelStatus))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelLastOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jTextFieldPrice, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
          .addComponent(jTextFieldTime, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
          .addComponent(jTextFieldStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
        .addContainerGap())
    );
    jPanelLastOrderLayout.setVerticalGroup(
      jPanelLastOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelLastOrderLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelLastOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelTime)
          .addComponent(jTextFieldTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelLastOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelPrize)
          .addComponent(jTextFieldPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanelLastOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelStatus)
          .addComponent(jTextFieldStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(13, Short.MAX_VALUE))
    );

    jPanelCurrentOrder.setBorder(javax.swing.BorderFactory.createTitledBorder("Partial Order"));

    jLabelPrizeCurrent.setText("Price");

    jTextFieldPriceCurrent.setEditable(false);

    javax.swing.GroupLayout jPanelCurrentOrderLayout = new javax.swing.GroupLayout(jPanelCurrentOrder);
    jPanelCurrentOrder.setLayout(jPanelCurrentOrderLayout);
    jPanelCurrentOrderLayout.setHorizontalGroup(
      jPanelCurrentOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelCurrentOrderLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jLabelPrizeCurrent)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jTextFieldPriceCurrent, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanelCurrentOrderLayout.setVerticalGroup(
      jPanelCurrentOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelCurrentOrderLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelCurrentOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabelPrizeCurrent)
          .addComponent(jTextFieldPriceCurrent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    jButtonAggiorna.setText("Aggiorna Lista");
    jButtonAggiorna.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonAggiornaActionPerformed(evt);
      }
    });

    jButtonAnnulla.setText("Annulla Ultimo Ordine");
    jButtonAnnulla.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButtonAnnullaActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanelButtonsLayout = new javax.swing.GroupLayout(jPanelButtons);
    jPanelButtons.setLayout(jPanelButtonsLayout);
    jPanelButtonsLayout.setHorizontalGroup(
      jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelButtonsLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jButtonConferma)
          .addComponent(jButtonAnnulla)
          .addComponent(jButtonAggiorna)
          .addComponent(jButtonPulisci))
        .addContainerGap(20, Short.MAX_VALUE))
    );

    jPanelButtonsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonAggiorna, jButtonAnnulla, jButtonConferma, jButtonPulisci});

    jPanelButtonsLayout.setVerticalGroup(
      jPanelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanelButtonsLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jButtonConferma)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
        .addComponent(jButtonPulisci)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButtonAggiorna)
        .addGap(18, 18, 18)
        .addComponent(jButtonAnnulla)
        .addContainerGap())
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jPanelLastOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jPanelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jPanelMachineStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(jPanelCurrentOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPanelMachineStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jPanelLastOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanelCurrentOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jPanelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

    private void jButtonConfermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfermaActionPerformed
        orderPanel.confirmAndSendNewOrder();
}//GEN-LAST:event_jButtonConfermaActionPerformed

    private void jButtonPulisciActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPulisciActionPerformed
        orderPanel.cleanDataFields();
}//GEN-LAST:event_jButtonPulisciActionPerformed

    private void jButtonAggiornaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAggiornaActionPerformed
        orderPanel.refreshList();
}//GEN-LAST:event_jButtonAggiornaActionPerformed

    private void jButtonAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAnnullaActionPerformed
        orderPanel.undoLastOrder();
}//GEN-LAST:event_jButtonAnnullaActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButtonAggiorna;
  private javax.swing.JButton jButtonAnnulla;
  private javax.swing.JButton jButtonConferma;
  private javax.swing.JButton jButtonPulisci;
  private javax.swing.JLabel jLabelHostname;
  private javax.swing.JLabel jLabelLogged;
  private javax.swing.JLabel jLabelPrize;
  private javax.swing.JLabel jLabelPrizeCurrent;
  private javax.swing.JLabel jLabelStatus;
  private javax.swing.JLabel jLabelTime;
  private javax.swing.JLabel jLabelUser;
  private javax.swing.JPanel jPanelButtons;
  private javax.swing.JPanel jPanelCurrentOrder;
  private javax.swing.JPanel jPanelLastOrder;
  private javax.swing.JPanel jPanelMachineStatus;
  private javax.swing.JTextField jTextFieldHostname;
  private javax.swing.JTextField jTextFieldLogged;
  private javax.swing.JTextField jTextFieldPrice;
  private javax.swing.JTextField jTextFieldPriceCurrent;
  private javax.swing.JTextField jTextFieldStatus;
  private javax.swing.JTextField jTextFieldTime;
  private javax.swing.JTextField jTextFieldUser;
  // End of variables declaration//GEN-END:variables

    /**
     *
     * @param name
     */
    void setLogin( String name ) {
        final String timestamp = new SimpleDateFormat(
                "HH:mm:ss dd/MM/yy", Locale.ITALIAN).format(new Date());
        jTextFieldUser.setText(name);
        jTextFieldLogged.setText(timestamp);
    }

    /**
     * 
     */
    void reset() {
        jTextFieldLogged.setText("");
        jTextFieldUser.setText("");
        jTextFieldPriceCurrent.setText("");
        jTextFieldTime.setText("");
        jTextFieldPrice.setText("");
    }

    void cleanLastOrder() {
        jTextFieldStatus.setText("Annullato");
        jButtonAnnulla.setEnabled(false);
    }

    /**
     *
     * @param price
     */
    void setPartialOrder(double price) {
        jTextFieldPriceCurrent.setText(new Double(price).toString());
    }

    /**
     * 
     * @param time
     * @param price
     */
    void setEmittedOrder(double price) {
        jTextFieldPrice.setText(String.format(Locale.ITALIAN,"%3.2f", price));
        final String timestamp = new SimpleDateFormat(
                "HH:mm:ss", Locale.ITALIAN).format(new Date());
        jTextFieldTime.setText(timestamp);
        jTextFieldStatus.setText("Emesso");
        jButtonAnnulla.setEnabled(true);
    }

    /**
     * Enables/Disables all the buttons related to order committing at once
     *
     * @param b true is enalbe, false disable
     */
    private void enableButtons(boolean b) {
        jButtonAggiorna.setEnabled(b);
        jButtonAnnulla.setEnabled(b);
        jButtonConferma.setEnabled(b);
        jButtonPulisci.setEnabled(b);
    }

    /**
     * Sets the panel that manages the orders.
     * @param orderPanel
     */
    public void setOrderPanel(GuiOrderPanel orderPanel) {
        this.orderPanel = orderPanel;
        if (orderPanel == null) {
            enableButtons(false);
        } else {
            enableButtons(true);
        }
        jButtonAnnulla.setEnabled(false);
    }
}
