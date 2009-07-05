/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.ordine.Ordine;
import gestionecassa.clients.ClientAPI;
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
    void annullaUltimoOrdine() throws RemoteException;

    /**
     *
     * @param nuovoOrdine
     *
     * @throws java.rmi.RemoteException
     */
    void sendNuovoOrdine(Ordine nuovoOrdine) throws RemoteException;
}
