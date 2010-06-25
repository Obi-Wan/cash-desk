package gestionecassa.order;

import gestionecassa.Article;
import gestionecassa.ArticleOption;
import gestionecassa.ArticleWithOptions;
import java.util.ArrayList;
import java.util.List;

/**
 * Specialized version of <code>{@link PairObjectInteger}</code> that holds information
 * about an entry rapresenting an <code>{@link ArticleWithOptions}</code>.
 *
 * @author ben
 */
public class EntrySingleArticleWithOption extends PairObjectInteger<Article> {

    /**
     * Partials
     */
    public List<PairObjectInteger<ArticleOption>> numPartial;

    /**
     * Numbert at which start the progressive numbers (they are numTot in number)
     */
    public int startProgressive;

    /**
     * Constructor
     *
     * @param article The article rapresented
     * @param numTot Qauntity sold of this article
     * @param startProg Starting progressive number
     * @param numPartial List of partials for the options
     */
    public EntrySingleArticleWithOption(ArticleWithOptions article, int numTot,
            int startProg, List<PairObjectInteger<ArticleOption>> numPartial) {
        super(article, numTot);
        this.startProgressive = startProg;
        this.numPartial = new ArrayList<PairObjectInteger<ArticleOption>>(numPartial);
    }
}
