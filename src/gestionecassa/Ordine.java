/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ben
 */
public class Ordine {

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
     * Record per ogni bene dell'ordine
     *
     * @author ben
     */
    public class recordSingoloBene {

        /**
         * Reference al bene.
         */
        public BeneVenduto bene;

        /**
         * Numero totale per questo articolo
         */
        public int numTot;

        /**
         * Explicit constructor
         *
         * @param bene
         * @param numTot
         */
        public recordSingoloBene(BeneVenduto bene, int numTot) {
            this.bene = bene;
            this.numTot = numTot;
        }
    }

    /**
     * Record per i beni con opzione
     *
     * @author ben
     */
    public class recordSingoloBeneConOpzione extends recordSingoloBene {

        /**
         * Parziali
         */
        public List<int[]> numParziale;

        public recordSingoloBeneConOpzione(BeneConOpzione bene, int numTot,
                List<int[]> numParziale) {
            super(bene, numTot);
            this.numParziale = new ArrayList<int[]>(numParziale);
        }
    }

    /**
     * Default constructor
     */
    public Ordine() {
        this(0, new Date(), new ArrayList<recordSingoloBene>());
    }

    /**
     * Explicit constructor of the date
     *
     * @param data
     */
    public Ordine(Date data) {
        this(0, new Date(data.getTime()), new ArrayList<recordSingoloBene>());
    }

    /**
     * Completely explicit constructor (it's not good to use it.)
     *
     * @param nOrdine
     * @param data
     * @param listaBeni
     */
    private Ordine(int nOrdine, Date data, List<recordSingoloBene> listaBeni) {
        this.nOrdine = nOrdine;
        this.data = data;
        this.listaBeni = listaBeni;
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
            List<int[]> listaParziale) {
        listaBeni.add(new recordSingoloBeneConOpzione(bene, numTot,
                listaParziale));
    }
}
