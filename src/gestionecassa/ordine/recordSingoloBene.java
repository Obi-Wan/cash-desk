package gestionecassa.ordine;

import gestionecassa.BeneVenduto;
import java.io.Serializable;

/**
 * Record per ogni bene dell'ordine
 *
 * @author ben
 */
public class recordSingoloBene implements Serializable {

    /**
     * Reference al bene.
     */
    public BeneVenduto bene;
    /**
     * Numero totale per questo articolo
     */
    public int numTot;

    public recordSingoloBene(BeneVenduto bene, int numTot) {
        super();
        this.bene = bene;
        this.numTot = numTot;
    }
}
