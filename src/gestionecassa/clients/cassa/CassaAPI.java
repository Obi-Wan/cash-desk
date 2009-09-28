/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.clients.ClientAPI;
import gestionecassa.order.Order;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface CassaAPI extends ClientAPI<CassaOptions> {

    /**
     *
     *
     * @throws java.rmi.RemoteException
     */
    void delRMILastOrder() throws RemoteException, IOException;

    /**
     *
     * @param nuovoOrdine
     *
     * @throws java.rmi.RemoteException
     */
    void sendRMINewOrder(Order nuovoOrdine) throws RemoteException, IOException;

    /**
     * Asks to the server for a new bunch of progressive numbers.
     *
     * @param nome Name of the goods.
     * @param n number of the progressive numbers.
     *
     * @return the first of the "n" progressive numbers.
     *
     * @throws RemoteException
     */
    int getNProgressivo(String nome, int n) throws RemoteException;
}
