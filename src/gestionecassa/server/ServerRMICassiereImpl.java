/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public class ServerRMICassiereImpl extends ServiceThreadCommon
        implements ServerRMICassiere {

    ServerRMICassiereImpl(SessionRecord session, DataManager dataManager)
            throws RemoteException {
        super(session,dataManager);
    }
}
