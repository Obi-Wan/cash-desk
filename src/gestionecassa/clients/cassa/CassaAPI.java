/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.order.Order;
import gestionecassa.clients.ClientAPI;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface CassaAPI extends ClientAPI {

    /**
     *
     *
     * @throws java.rmi.RemoteException
     */
    void annullaUltimoOrdine() throws RemoteException, IOException;

    /**
     *
     * @param nuovoOrdine
     *
     * @throws java.rmi.RemoteException
     */
    void sendNuovoOrdine(Order nuovoOrdine) throws RemoteException, IOException;

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
