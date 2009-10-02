/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.Article;
import gestionecassa.ArticlesList;
import gestionecassa.exceptions.MalformedOrderEXception;
import gestionecassa.order.BaseEntry;
import gestionecassa.order.EntryArticleGroup;
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
     */
    final boolean trustOrders;

    /**
     *
     * @param session
     * @param dataManager
     *
     * @throws java.rmi.RemoteException
     */
    public ServiceRMICassiereImpl(String username, boolean trust,
            DMCassaAPI dataManager, Logger logger)
                throws  RemoteException {
        super(logger);
        this.dataManager = dataManager;
        this.trustOrders = trust;

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
        if (!trustOrders) {
            try {
                checkOrder(newOrder);
            } catch (MalformedOrderEXception ex) {
                logger.warn("Client with session: " + sessionIdentifier +
                        " sent a Malformed Order!", ex);
                throw new RemoteException("Malformed order detected", ex);
            }
        }
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

    /**
     * Checks for errors in orders compilation, preventing the DB from
     * corrupting or the server from crashing.
     *
     * Creates a lot of overhead on server side, but it's useful in not
     * completely trusted environments.
     *
     * @param order
     * @throws MalformedOrderEXception
     */
    private void checkOrder(Order order) throws MalformedOrderEXception {
        for (EntryArticleGroup groupEntry : order.getGroups()) {
            if (groupEntry.numTot == 0) {
                throw new MalformedOrderEXception("Empty group found");
            }

            double groupPrice = 0;
            for (BaseEntry<Article> artEntry : groupEntry.articles) {
                if (artEntry.numTot == 0) {
                    throw new MalformedOrderEXception("Empty article found");
                }
                groupPrice += artEntry.numTot * artEntry.data.getPrice();
            }
            if (groupEntry.partialPrice != groupPrice) {
                logger.warn("wrong partial price in session: " +
                        sessionIdentifier + ". Recalculating.");
                groupEntry.partialPrice = groupPrice;
            }
        }
        order.refreshTotalPrice();
    }
}
