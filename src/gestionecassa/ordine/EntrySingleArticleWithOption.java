package gestionecassa.ordine;

import gestionecassa.ArticleWithOptions;
import java.util.ArrayList;
import java.util.List;

/**
 * Record per i beni con opzione
 *
 * @author ben
 */
public class EntrySingleArticleWithOption extends EntrySingleArticle {

    /**
     * Parziali
     */
    public List<EntrySingleOption> numParziale;

    /**
     * numbert at which start the progressive numbers (they are numTot in number)
     */
    public int startProgressivo;

    public EntrySingleArticleWithOption(ArticleWithOptions bene, int numTot, int startProg, List<EntrySingleOption> numParziale) {
        super(bene, numTot);
        this.startProgressivo = startProg;
        this.numParziale = new ArrayList<EntrySingleOption>(numParziale);
    }
}
