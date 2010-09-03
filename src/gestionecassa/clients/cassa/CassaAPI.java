/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.clients.ClientAPI;
import gestionecassa.exceptions.WrongArticlesListException;
import gestionecassa.order.Order;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface CassaAPI extends ClientAPI<CassaPrefs> {

    /**
     *
     *
     * @throws java.rmi.RemoteException
     * @throws IOException
     */
    void delRMILastOrder() throws RemoteException, IOException;

    /**
     *
     * @param nuovoOrdine
     *
     * @throws java.rmi.RemoteException
     * @throws IOException
     * @throws WrongArticlesListException
     */
    void sendRMINewOrder(Order nuovoOrdine)
            throws RemoteException, IOException, WrongArticlesListException;

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
