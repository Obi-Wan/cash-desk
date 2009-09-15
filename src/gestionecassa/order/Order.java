/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.order;

import gestionecassa.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ben
 */
public class Order implements Serializable, Comparable<Order> {

    /**
     * Data/ora in cui è stato effettuato l'ordine
     */
    Date date;

    /**
     * Username of the Cassiere that committed the order
     */
    String username;

    /**
     * Place where the order was committed
     */
    String hostname;

    /**
     * List of articles for this order
     */
    List<EntrySingleArticle> articlesList;

    /**
     *
     */
    double totalPrice;

    /**
     * Table at which the order has been committed
     */
    int table;

    /**
     * Default constructor (well, at most :) )
     *
     * @param username
     * @param hostname
     */
    public Order(String username, String hostname, int table) {
        this( new Date(), new String(username), new String(hostname), table,
                new ArrayList<EntrySingleArticle>());
        totalPrice = 0;
    }

    /**
     * Explicit constructor of the date
     *
     * @param date
     * @param username
     * @param hostname
     */
    public Order(Date data, String username, String hostname, int table) {
        this( new Date(data.getTime()), new String(username),
                new String(hostname), table,
                new ArrayList<EntrySingleArticle>());
        totalPrice = 0;
    }

    /**
     * Completely explicit constructor (it's not good to use it.)
     *
     * @param nOrdine
     * @param date
     * @param username
     * @param hostname
     * @param articlesList
     */
    private Order( Date date, String username, String hostname, int table,
            List<EntrySingleArticle> articles) {
        this.date = date;
        this.articlesList = articles;
        this.username = username;
        this.hostname = hostname;
        this.table = table;
    }

    /**
     * Copy constructor
     * 
     * @param order
     */
    public Order(Order order) {
        this.date = new Date(order.date.getTime());
        this.hostname = new String(order.hostname);
        this.username = new String(order.username);
        this.totalPrice = order.totalPrice;
        this.articlesList = new ArrayList<EntrySingleArticle>(order.articlesList);
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * 
     * @return
     */
    public List<EntrySingleArticle> getArticlesSold() {
        return articlesList;
    }

    /**
     * 
     * @return
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     *
     * @return
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * 
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @return
     */
    public int getTable() {
        return table;
    }

    /**
     * Adder helper
     *
     * @param article 
     * @param numTot
     */
    public void addArticle(Article article, int numTot) {
        articlesList.add(new EntrySingleArticle(article, numTot));
    }

    /**
     * Adder helper
     *
     * @param article
     * @param numTot
     * @param partialsList 
     */
    public void addArticleWithOptions(ArticleWithOptions article, int numTot,
            int progressive, List<EntrySingleOption> partialsList) {
        articlesList.add(new EntrySingleArticleWithOption(article, numTot, progressive,
                partialsList));
    }

    /**
     * 
     * @param totalPrice
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int compareTo(Order o) {
        return this.date.compareTo(o.date);
    }
}
