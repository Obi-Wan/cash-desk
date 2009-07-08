/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.ordine.Order;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface ServiceRMICassiere
        extends ServiceRMICommon, Remote, Serializable {

    public void sendOrdine(Order nuovoOrdine) throws RemoteException;

    void cancelLastOrder() throws RemoteException;

    public int getNProgressive(String nomeBene, int n) throws RemoteException;
}
