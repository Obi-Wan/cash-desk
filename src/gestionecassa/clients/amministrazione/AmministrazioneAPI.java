/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.amministrazione;

import gestionecassa.clients.ClientAPI;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface AmministrazioneAPI extends ClientAPI {

    /**
     * Closes the remote server to which we are connected to
     *
     * @throws java.rmi.RemoteException
     */
    void stopServer() throws RemoteException;
}
