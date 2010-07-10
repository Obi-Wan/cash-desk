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
import gestionecassa.ArticleOption;
import java.util.List;
import java.util.ArrayList;

/**
 * This class is the rapresentation of the <code>ArticleGroup</code> in an
 * <code>{@link Order}</code>. Stores the reference to the group, the sub total
 * price of the order for this group, and a list of sold articles.
 *
 * Not synchronized.
 *
 * @author ben
 */
public class EntryArticleGroup extends PairObjectInteger<ArticleGroup> {

    /**
     * List of sold articles.
     */
    public List<PairObjectInteger<Article>> articles;

    /**
     * Sub total price
     */
    public double partialPrice;

    /**
     * Default constructor
     *
     * @param gr Reference to the rapresented ArticleGroup
     */
    public EntryArticleGroup(ArticleGroup gr) {
        this(gr, 0, new ArrayList<PairObjectInteger<Article>>());
    }

    /**
     * Completely explicit constructor (it's not good to use it.)
     *
     * @param gr Reference to the rapresented ArticleGroup
     * @param numTot total number of articles chosen in this group
     * @param articles List of those sold articles entries.
     */
    EntryArticleGroup(ArticleGroup gr, int numTot, List<PairObjectInteger<Article>> articles) {
        super(gr, numTot);
        this.articles = articles;
        partialPrice = 0;
        for (PairObjectInteger<Article> entry : articles) {
            partialPrice += entry.numTot * entry.object.getPrice();
        }
    }

    /**
     * Adder helper
     *
     * @param article Article to add to this entry
     * @param numAddTot Quantity bought
     */
    public void addArticle(Article article, int numAddTot) {
        articles.add(new PairObjectInteger<Article>(article, numAddTot));
        this.numTot += numAddTot;
        partialPrice += numAddTot * article.getPrice();
    }

    /**
     * Adder helper
     *
     * @param article Article to add to this entry
     * @param numAddTot Quantity bought
     * @param progressive starting progressive number of the articles
     * @param partialsList List of partials for the options
     */
    public void addArticleWithOptions(Article article, int numAddTot,
            int progressive, List<PairObjectInteger<ArticleOption>> partialsList) {
        articles.add(new EntrySingleArticleWithOption(article, numAddTot, progressive,
                partialsList));
        this.numTot += numAddTot;
        partialPrice += numAddTot * article.getPrice();
    }
}