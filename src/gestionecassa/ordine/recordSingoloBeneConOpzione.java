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

    /**
     * numbert at which start the progressive numbers (they are numTot in number)
     */
    public int startProgressivo;

    public recordSingoloBeneConOpzione(BeneConOpzione bene, int numTot, int startProg, List<recordSingolaOpzione> numParziale) {
        super(bene, numTot);
        this.startProgressivo = startProg;
        this.numParziale = new ArrayList<recordSingolaOpzione>(numParziale);
    }
}
