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
     * indice cardinale dell'ordine effettuato
     */
    int nOrdine;

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
     * Default constructor (well, at most :) )
     *
     * @param username
     * @param hostname
     */
    public Ordine(String username, String hostname) {
        this(0, new Date(), new String(username), new String(hostname),
                new ArrayList<recordSingoloBene>());
    }

    /**
     * Explicit constructor of the date
     *
     * @param data
     * @param username
     * @param hostname
     */
    public Ordine(Date data, String username, String hostname) {
        this(0, new Date(data.getTime()), new String(username), 
                new String(hostname), new ArrayList<recordSingoloBene>());
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
    private Ordine(int nOrdine, Date data, String username, String hostname,
            List<recordSingoloBene> listaBeni) {
        this.nOrdine = nOrdine;
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
    public void addBeneConOpzione(BeneConOpzione bene, int numTot, 
            List<recordSingolaOpzione> listaParziale) {
        listaBeni.add(new recordSingoloBeneConOpzione(bene, numTot,
                listaParziale));
    }
}
