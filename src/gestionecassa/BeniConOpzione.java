
package gestionecassa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ben
 */
public class BeniConOpzione extends BeneVenduto {

    /**
     * Lista delle opzioni disponibili per questo bene.
     */
    List<String> opzioni;

    /**
     * Explicit Constructor
     *
     * @param nome
     * @param prezzo
     * @param opzioni
     */
    public BeniConOpzione(String nome, float prezzo,List<String> opzioni) {
        super(nome, prezzo);
        this.opzioni = new ArrayList<String>(opzioni);
    }

    /**
     * Explicit base constructor
     *
     * @param nome
     * @param prezzo
     */
    public BeniConOpzione(String nome, float prezzo) {
        this(nome, prezzo, new ArrayList<String>());
    }

    /**
     * Tells us wether it has opsions or not.
     *
     * @return true
     */
    @Override
    public boolean hasOptions() {
        return true;
    }

    @Override
    public String getPrintableFormat() {
        String output = new String(super.getPrintableFormat() + "\n");
        for (String string : opzioni) {
            output += new String("  .\t "+ string + "\n");
        }
        output += "--";
        return output;
    }
}
