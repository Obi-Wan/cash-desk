/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.Ordine;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface ServerRMICassiere 
        extends ServerRMIShared, Remote, Serializable {

    public void sendOrdine(Ordine nuovoOrdine) throws RemoteException;
}
