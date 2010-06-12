/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.clientservices;

import gestionecassa.ArticlesList;
import gestionecassa.exceptions.WrongArticlesListException;
import gestionecassa.order.Order;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public interface ServiceRMICassiereAPI extends Remote, Serializable {

    /**
     * Method to get the list of all the enabled Articles
     * @return the list of all enabled articles
     * @throws RemoteException
     */
    public ArticlesList getEnabledArticlesList() throws RemoteException;

    /**
     *
     * @param newOrder
     * @throws RemoteException
     * @throws IOException
     */
    public void sendOrder(Order newOrder)
            throws RemoteException, IOException, WrongArticlesListException;

    /**
     * Deletes the last submitted order
     *
     * @throws RemoteException
     * @throws IOException
     */
    void delLastOrder() throws RemoteException, IOException;

    /**
     * 
     * @param artName
     * @param n
     * @return
     * @throws RemoteException
     */
    public int getNProgressive(String artName, int n) throws RemoteException;
}
