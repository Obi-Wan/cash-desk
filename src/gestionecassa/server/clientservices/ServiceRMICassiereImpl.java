/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.ArticlesList;
import gestionecassa.ordine.Order;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import gestionecassa.server.SessionRecord;
import gestionecassa.server.datamanager.DMCassaAPI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author ben
 */
public class ServiceRMICassiereImpl extends SharedServerService
        implements ServiceRMICassiere {

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
    public ServiceRMICassiereImpl(SessionRecord session, DMCassaAPI dataManager,
            Logger logger)
                throws  RemoteException {
        super(session,logger);
        this.dataManager = dataManager;

        // FIXME italian locale to be avoided in future.
        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd_HH-mm-ss", Locale.ITALIAN).format(new Date());
        sessionIdentifier = new String(session.getUsername() + "@" + timestamp);

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
    public void sendOrdine(Order newOrder) throws RemoteException {
        dataManager.addNewOrder(sessionIdentifier, newOrder);
    }

    /**
     * 
     *
     * @throws java.rmi.RemoteException
     */
    public void cancelLastOrder() throws RemoteException {
        dataManager.delLastOrder(sessionIdentifier);
    }

    /**
     * 
     * @return
     * @throws RemoteException
     */
    public ArticlesList requestArticlesList() throws RemoteException {
        return dataManager.getCurrentArticlesList();
    }

    /**
     * 
     * @param nomeBene
     * @return
     * @throws RemoteException
     */
    public int getNProgressive(String articleName, int n) throws RemoteException {
        return dataManager.getNProgressivo(articleName,n);
    }
}