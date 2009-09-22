package gestionecassa.order;

import gestionecassa.Article;
import gestionecassa.ArticleWithOptions;
import java.util.ArrayList;
import java.util.List;

/**
 * Record per i beni con opzione
 *
 * @author ben
 */
public class EntrySingleArticleWithOption extends BaseEntry<Article> {

    /**
     * Parziali
     */
    public List<BaseEntry<String>> numPartial;

    /**
     * numbert at which start the progressive numbers (they are numTot in number)
     */
    public int startProgressive;

    public EntrySingleArticleWithOption(ArticleWithOptions article, int numTot,
            int startProg, List<BaseEntry<String>> numPartial) {
        super(article, numTot);
        this.startProgressive = startProg;
        this.numPartial = new ArrayList<BaseEntry<String>>(numPartial);
    }
}
