package gestionecassa.ordine;

import gestionecassa.BeneConOpzione;
import java.util.ArrayList;
import java.util.List;

/**
 * Record per i beni con opzione
 *
 * @author ben
 */
public class recordSingoloBeneConOpzione extends recordSingoloBene {

    /**
     * Parziali
     */
    public List<recordSingolaOpzione> numParziale;

    public recordSingoloBeneConOpzione(BeneConOpzione bene, int numTot, List<recordSingolaOpzione> numParziale) {
        super(bene, numTot);
        this.numParziale = new ArrayList<recordSingolaOpzione>(numParziale);
    }
}
