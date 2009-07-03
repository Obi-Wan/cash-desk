/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.Ordine;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class ServerRMICassiereImpl extends SharedServerService
        implements ServerRMICassiere {

    /**
     * 
     */
    List<Ordine> ordini;

    /**
     *
     * @param session
     * @param dataManager
     *
     * @throws java.rmi.RemoteException
     */
    ServerRMICassiereImpl(SessionRecord session, DataManager dataManager,
            Logger logger)
                throws  RemoteException {
        super(session,dataManager,logger);

        ordini = new ArrayList<Ordine>();
    }

    /**
     *
     * @param nuovoOrdine
     *
     * @throws java.rmi.RemoteException
     */
    public void sendOrdine(Ordine nuovoOrdine) throws RemoteException {
        ordini.add(nuovoOrdine);

        logger.debug("Ordini salvati\n" + ordini.toString());
    }

    /**
     * 
     *
     * @throws java.rmi.RemoteException
     */
    public void annullaUltimoOrdine() throws RemoteException {
        ordini.remove(ordini.size()-1);
    }
}
