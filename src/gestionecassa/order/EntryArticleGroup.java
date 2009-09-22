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
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class EntryArticleGroup extends BaseEntry<ArticleGroup> implements Serializable {

    List<BaseEntry<Article>> articles;

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
    }

    /**
     * Adder helper
     *
     * @param article
     * @param numTot
     */
    public void addArticle(Article article, int numTot) {
        articles.add(new BaseEntry<Article>(article, numTot));
        this.numTot += numTot;
    }

    /**
     * Adder helper
     *
     * @param article
     * @param numTot
     * @param partialsList
     */
    public void addArticleWithOptions(ArticleWithOptions article, int numTot,
            int progressive, List<BaseEntry<String>> partialsList) {
        articles.add(new EntrySingleArticleWithOption(article, numTot, progressive,
                partialsList));
        this.numTot += numTot;
    }


}
