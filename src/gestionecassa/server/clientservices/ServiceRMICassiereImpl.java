/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.Article;
import gestionecassa.ArticlesList;
import gestionecassa.exceptions.MalformedOrderEXception;
import gestionecassa.exceptions.WrongArticlesListException;
import gestionecassa.order.PairObjectInteger;
import gestionecassa.order.EntryArticleGroup;
import gestionecassa.order.Order;
import java.io.IOException;
import java.rmi.RemoteException;
import gestionecassa.server.datamanager.DMCassaAPI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ben
 */
public class ServiceRMICassiereImpl extends SharedServerService
        implements ServiceRMICassiereAPI {

    /**
     * Reference to the object manager
     */
    DMCassaAPI dataManager;

    /**
     * Identifier of this session, used with the object manager.
     */
    final String sessionIdentifier;

    /**
     * 
     */
    final boolean trustOrders;

    /**
     * 
     * @param username
     * @param trust
     * @param dataManager
     * @throws RemoteException
     */
    public ServiceRMICassiereImpl(String username, boolean trust,
            DMCassaAPI dataManager)
                throws  RemoteException
    {
        this.dataManager = dataManager;
        this.trustOrders = trust;

        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd_HH-mm-ss").format(new Date());
        sessionIdentifier = username + "@" + timestamp;

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
     * Method to send new orders from clients
     * 
     * @param newOrder the new order to verify and append
     * @throws RemoteException
     * @throws IOException
     * @throws WrongArticlesListException
     */
    @Override
    public void sendOrder(Order newOrder)
            throws RemoteException, IOException, WrongArticlesListException {
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
     * Deletes the last submitted order
     *
     * @throws RemoteException
     * @throws IOException
     */
    @Override
    public void delLastOrder() throws RemoteException, IOException {
        dataManager.delLastOrder(sessionIdentifier);
    }

    /**
     * Method to get the list of all the enabled Articles
     * @return the list of all enabled articles
     * @throws RemoteException
     */
    @Override
    public ArticlesList getEnabledArticlesList() throws RemoteException {
        return dataManager.getEnabledArticlesList();
    }

    /**
     * 
     * @param artName
     * @param n 
     * @return
     * @throws RemoteException
     */
    @Override
    public int getNProgressive(String artName, int n) throws RemoteException {
        return dataManager.getNProgressive(artName,n);
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
            for (PairObjectInteger<Article> artEntry : groupEntry.articles) {
                if (artEntry.numTot == 0) {
                    throw new MalformedOrderEXception("Empty article found");
                }
                groupPrice += artEntry.numTot * artEntry.object.getPrice();
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
