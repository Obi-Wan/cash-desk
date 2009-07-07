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
public class Ordine implements Serializable {

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
    List<recordSingoloBene> listaBeni;

    /**
     *
     */
    double totalPrize;

    /**
     * Default constructor (well, at most :) )
     *
     * @param username
     * @param hostname
     */
    public Ordine(String username, String hostname) {
        this( new Date(), new String(username), new String(hostname),
                new ArrayList<recordSingoloBene>());
        totalPrize = 0;
    }

    /**
     * Explicit constructor of the date
     *
     * @param data
     * @param username
     * @param hostname
     */
    public Ordine(Date data, String username, String hostname) {
        this( new Date(data.getTime()), new String(username), 
                new String(hostname), new ArrayList<recordSingoloBene>());
        totalPrize = 0;
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
    private Ordine( Date data, String username, String hostname,
            List<recordSingoloBene> listaBeni) {
        this.data = data;
        this.listaBeni = listaBeni;
        this.username = username;
        this.hostname = hostname;
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
    public List<recordSingoloBene> getListaBeni() {
        return listaBeni;
    }

    /**
     * 
     * @return
     */
    public double getTotalPrize() {
        return totalPrize;
    }

    /**
     * Adder helper
     *
     * @param bene
     * @param numTot
     */
    public void addBeneVenduto(BeneVenduto bene, int numTot) {
        listaBeni.add(new recordSingoloBene(bene, numTot));
    }

    /**
     * Adder helper
     *
     * @param bene
     * @param numTot
     * @param listaParziale
     */
    public void addBeneConOpzione(BeneConOpzione bene, int numTot, int progressive,
            List<recordSingolaOpzione> listaParziale) {
        listaBeni.add(new recordSingoloBeneConOpzione(bene, numTot, progressive,
                listaParziale));
    }

    /**
     * 
     * @param totalPrize
     */
    public void setTotalPrize(double totalPrize) {
        this.totalPrize = totalPrize;
    }
}
