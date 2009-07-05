/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.ListaBeni;
import gestionecassa.ordine.Ordine;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import gestionecassa.server.SessionRecord;
import gestionecassa.server.datamanager.DMCassaAPI;

/**
 *
 * @author ben
 */
public class ServerRMICassiereImpl extends SharedServerService
        implements ServerRMICassiere {

    /**
     * Reference to the data manager
     */
    DMCassaAPI dataManager;

    /**
     * Identifier of this session, used with the data manager.
     */
    final String sessionIdentifier;

    /**
     *
     * @param session
     * @param dataManager
     *
     * @throws java.rmi.RemoteException
     */
    public ServerRMICassiereImpl(SessionRecord session, DMCassaAPI dataManager,
            Logger logger)
                throws  RemoteException {
        super(session,logger);
        this.dataManager = dataManager;

        sessionIdentifier = new String(session.getUsername());

        dataManager.createNewCassaSession(sessionIdentifier);
    }

    /**
     * 
     */
    @Override
    public void stopThread() {
        dataManager.closeCassaSession(sessionIdentifier);
        super.stopThread();
    }

    /**
     *
     * @param nuovoOrdine
     *
     * @throws java.rmi.RemoteException
     */
    public void sendOrdine(Ordine nuovoOrdine) throws RemoteException {
        dataManager.addNewOrder(sessionIdentifier, nuovoOrdine);
    }

    /**
     * 
     *
     * @throws java.rmi.RemoteException
     */
    public void annullaUltimoOrdine() throws RemoteException {
        dataManager.delLastOrder(sessionIdentifier);
    }

    /**
     * 
     * @return
     * @throws RemoteException
     */
    public ListaBeni requestListaBeni() throws RemoteException {
        return dataManager.getCurrentListaBeni();
    }
}
