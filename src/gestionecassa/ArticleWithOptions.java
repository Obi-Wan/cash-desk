
package gestionecassa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ben
 */
public class ArticleWithOptions extends Article implements ArticleWithPreparation {

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
    public ArticleWithOptions(int id, String nome, double prezzo, List<String> opzioni) {
        super(id,nome, prezzo);
        this.opzioni = new ArrayList<String>(opzioni);
    }

    /**
     * Explicit Constructor
     *
     * @param nome
     * @param prezzo
     * @param opzioni
     */
    public ArticleWithOptions(int id, String nome, double prezzo, List<String> opzioni, boolean b) {
        super(id, nome, prezzo, b);
        this.opzioni = new ArrayList<String>(opzioni);
    }

    /**
     * Explicit base constructor
     *
     * @param nome
     * @param prezzo
     */
    public ArticleWithOptions(int id, String nome, double prezzo) {
        this(id, nome, prezzo, new ArrayList<String>());
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

    /**
     * 
     * @return
     */
    public List<String> getOpzioni() {
        return opzioni;
    }
}
