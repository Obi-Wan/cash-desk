/*
 * EntryArticleGroup.java
 * 
 * Copyright (C) 2009 Nicola Roberto Viganò
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package gestionecassa.order;

import gestionecassa.Article;
import gestionecassa.ArticleGroup;
import gestionecassa.ArticleWithOptions;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class EntryArticleGroup extends BaseEntry<ArticleGroup> {

    public List<BaseEntry<Article>> articles;

    public double partialPrice;

    /**
     * Default constructor
     */
    public EntryArticleGroup(ArticleGroup gr) {
        this(gr, 0, new Vector<BaseEntry<Article>>());
    }

    /**
     * Completely explicit constructor (it's not good to use it.)
     *
     * @param articles
     */
    EntryArticleGroup(ArticleGroup gr, int numTot, List<BaseEntry<Article>> articles) {
        super(gr, numTot);
        this.articles = articles;
        partialPrice = 0;
        for (BaseEntry<Article> entry : articles) {
            partialPrice += entry.numTot * entry.data.getPrice();
        }
    }

    /**
     * Adder helper
     *
     * @param article
     * @param numTot
     */
    public void addArticle(Article article, int numAddTot) {
        articles.add(new BaseEntry<Article>(article, numAddTot));
        this.numTot += numAddTot;
        partialPrice += numAddTot * article.getPrice();
    }

    /**
     * Adder helper
     *
     * @param article
     * @param numTot
     * @param partialsList
     */
    public void addArticleWithOptions(ArticleWithOptions article, int numAddTot,
            int progressive, List<BaseEntry<String>> partialsList) {
        articles.add(new EntrySingleArticleWithOption(article, numAddTot, progressive,
                partialsList));
        this.numTot += numAddTot;
        partialPrice += numAddTot * article.getPrice();
    }


}
