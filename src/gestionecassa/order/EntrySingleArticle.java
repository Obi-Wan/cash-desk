package gestionecassa.order;

import gestionecassa.Article;
import java.io.Serializable;

/**
 * Record per ogni article dell'ordine
 *
 * @author ben
 */
public class EntrySingleArticle implements Serializable {

    /**
     * Reference al article.
     */
    public Article article;
    /**
     * Numero totale per questo articolo
     */
    public int numTot;

    public EntrySingleArticle(Article article, int numTot) {
        super();
        this.article = article;
        this.numTot = numTot;
    }
}
