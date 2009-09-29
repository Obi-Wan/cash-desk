/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.ArticlesList;
import gestionecassa.order.Order;
import java.io.IOException;
import java.rmi.RemoteException;
import org.apache.log4j.Logger;
import gestionecassa.server.datamanager.DMCassaAPI;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public ServiceRMICassiereImpl(String username, DMCassaAPI dataManager,
            Logger logger)
                throws  RemoteException {
        super(logger);
        this.dataManager = dataManager;

        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd_HH-mm-ss").format(new Date());
        sessionIdentifier = new String(username + "@" + timestamp);

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
    @Override
    public void sendOrder(Order newOrder) throws RemoteException, IOException {
        dataManager.addNewOrder(sessionIdentifier, newOrder);
    }

    /**
     * 
     *
     * @throws java.rmi.RemoteException
     */
    @Override
    public void cancelLastOrder() throws RemoteException, IOException {
        dataManager.delLastOrder(sessionIdentifier);
    }

    /**
     * 
     * @return
     * @throws RemoteException
     */
    @Override
    public ArticlesList requestArticlesList() throws RemoteException {
        return dataManager.getArticlesList().getEnabledList();
    }

    /**
     * 
     * @param articleName
     * @param n 
     * @return
     * @throws RemoteException
     */
    @Override
    public int getNProgressive(String articleName, int n) throws RemoteException {
        return dataManager.getNProgressive(articleName,n);
    }
}
