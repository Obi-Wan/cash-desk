/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.ordine;

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
    Date data;

    /**
     * 
     */
    String username;

    /**
     *
     */
    String hostname;

    /**
     * Lista dei beni per il singolo ordine.
     */
    List<EntrySingleArticle> listaBeni;

    /**
     *
     */
    double totalPrice;

    /**
     * Default constructor (well, at most :) )
     *
     * @param username
     * @param hostname
     */
    public Order(String username, String hostname) {
        this( new Date(), new String(username), new String(hostname),
                new ArrayList<EntrySingleArticle>());
        totalPrice = 0;
    }

    /**
     * Explicit constructor of the date
     *
     * @param data
     * @param username
     * @param hostname
     */
    public Order(Date data, String username, String hostname) {
        this( new Date(data.getTime()), new String(username), 
                new String(hostname), new ArrayList<EntrySingleArticle>());
        totalPrice = 0;
    }

    /**
     * Completely explicit constructor (it's not good to use it.)
     *
     * @param nOrdine
     * @param data
     * @param username
     * @param hostname
     * @param listaBeni
     */
    private Order( Date data, String username, String hostname,
            List<EntrySingleArticle> listaBeni) {
        this.data = data;
        this.listaBeni = listaBeni;
        this.username = username;
        this.hostname = hostname;
    }

    /**
     * Copy constructor
     * 
     * @param order
     */
    public Order(Order order) {
        this.data = new Date(order.data.getTime());
        this.hostname = new String(order.hostname);
        this.username = new String(order.username);
        this.totalPrice = order.totalPrice;
        this.listaBeni = new ArrayList<EntrySingleArticle>(order.listaBeni);
    }

    /**
     *
     * @return
     */
    public Date getData() {
        return data;
    }

    /**
     * 
     * @return
     */
    public List<EntrySingleArticle> getListaBeni() {
        return listaBeni;
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
     * Adder helper
     *
     * @param bene
     * @param numTot
     */
    public void addBeneVenduto(Article bene, int numTot) {
        listaBeni.add(new EntrySingleArticle(bene, numTot));
    }

    /**
     * Adder helper
     *
     * @param bene
     * @param numTot
     * @param listaParziale
     */
    public void addBeneConOpzione(ArticleWithOptions bene, int numTot, int progressive,
            List<EntrySingleOption> listaParziale) {
        listaBeni.add(new EntrySingleArticleWithOption(bene, numTot, progressive,
                listaParziale));
    }

    /**
     * 
     * @param totalPrice
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int compareTo(Order o) {
        return this.data.compareTo(o.data);
    }
}
