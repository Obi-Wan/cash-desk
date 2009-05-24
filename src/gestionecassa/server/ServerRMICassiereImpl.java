/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.Ordine;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public class ServerRMICassiereImpl extends SharedServerService
        implements ServerRMICassiere {

    ServerRMICassiereImpl(SessionRecord session, DataManager dataManager)
            throws  RemoteException{
        super(session,dataManager);
    }

    public void sendOrdine(Ordine nuovoOrdine) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
