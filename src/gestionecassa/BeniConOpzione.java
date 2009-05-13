
package gestionecassa;

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
     * Tells us wether it has opsions or not.
     *
     * @return true
     */
    @Override
    public boolean hasOptions() {
        return true;
    }
}
