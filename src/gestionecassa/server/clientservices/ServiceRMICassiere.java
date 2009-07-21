/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.order.Order;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface ServiceRMICassiere
        extends ServiceRMICommon, Remote, Serializable {

    public void sendOrdine(Order nuovoOrdine) throws RemoteException, IOException;

    void cancelLastOrder() throws RemoteException, IOException;

    public int getNProgressive(String nomeBene, int n) throws RemoteException;
}
