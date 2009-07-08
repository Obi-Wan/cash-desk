package gestionecassa.ordine;

import gestionecassa.Article;
import java.io.Serializable;

/**
 * Record per ogni bene dell'ordine
 *
 * @author ben
 */
public class EntrySingleArticle implements Serializable {

    /**
     * Reference al bene.
     */
    public Article bene;
    /**
     * Numero totale per questo articolo
     */
    public int numTot;

    public EntrySingleArticle(Article bene, int numTot) {
        super();
        this.bene = bene;
        this.numTot = numTot;
    }
}
