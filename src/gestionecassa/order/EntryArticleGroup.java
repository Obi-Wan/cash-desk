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
import gestionecassa.ArticleWithOptions;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class EntryArticleGroup implements Serializable {

    String groupName;

    int groupId;

    List<EntrySingleArticle> articles;

    String username;

    /**
     * Default constructor
     */
    public EntryArticleGroup(String name, int id) {
        this(new String(name), id, "", new Vector<EntrySingleArticle>());
    }

    /**
     * Completely explicit constructor (it's not good to use it.)
     *
     * @param articles
     */
    EntryArticleGroup(String gname, int gid, String uname,
            List<EntrySingleArticle> articles) {
        this.articles = articles;
        this.username = uname;
        this.groupId = gid;
        this.groupName = gname;
    }

    /**
     * Adder helper
     *
     * @param article
     * @param numTot
     */
    public void addArticle(Article article, int numTot) {
        articles.add(new EntrySingleArticle(article, numTot));
    }

    /**
     * Adder helper
     *
     * @param article
     * @param numTot
     * @param partialsList
     */
    public void addArticleWithOptions(ArticleWithOptions article, int numTot,
            int progressive, List<EntrySingleOption> partialsList) {
        articles.add(new EntrySingleArticleWithOption(article, numTot, progressive,
                partialsList));
    }


}
