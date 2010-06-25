/*
 * ArticleGroup.java
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

package gestionecassa;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * Group of Articles, not synchronized.
 * When working on this class you need to externally synchornize it if accessing
 * concurrently.
 *
 * @author ben
 */
public class ArticleGroup extends ManageableObject implements Serializable {

    /**
     * List of the articles sold in this group
     */
    private List<Article> list;

    /**
     * Default constructor for the list of articles (but explicit for the name)
     * 
     * @param id
     * @param grn Name of this group
     */
    public ArticleGroup(int id, String grn) {
        this(id, grn, new ArrayList<Article>());
    }

    /**
     * Explicit constructor
     *
     * @param id
     * @param grn Name of this group
     * @param list List of articles of this group
     */
    public ArticleGroup(int id, String grn, Collection<Article> list) {
        this(id, grn, true, list);
    }

    /**
     * Explicit constructor
     *
     * @param id
     * @param grn Name of this group
     * @param en If group is enabled/disabled
     * @param list List of articles of this group
     */
    public ArticleGroup(int id, String grn, boolean en, Collection<Article> list) {
        super(id, grn, en);
        this.list = new ArrayList<Article>(list);
    }

    /**
     * Gets the list of articles in this group
     *
     * @return A list containing the articles of this group
     */
    public List<Article> getList() {
        return list;
    }


    /**
     * Adds an article
     *
     * @param article The article to add.
     */
    void addArticle(Article article) {
        list.add(article);
    }

    /**
     * Enables/disables a specified article
     *
     * @param pos Position of the article
     * @param enable Enable/disable
     */
    Article enableArticle(int pos, boolean enable) {
        Article temp = list.get(pos);
        temp.setEnabled(enable);
        return temp;
    }

    /**
     * Enables/disables a specified article
     *
     * @param art The article to modify
     * @param enable Enable/disable
     */
    Article enableArticle(Article art, boolean enable) {
        for (Article article : list) {
            if (article.equals(art)) {
                article.setEnabled(enable);
                return article;
            }
        }
        return null;
    }

    /**
     * Moves an article
     *
     * @param oldPos Old position
     * @param newPos New position
     * @return reference to the article moved
     */
    public Article moveArticleAt(int oldPos, int newPos) {
        Article temp = list.remove(oldPos);
        list.add(newPos,temp);
        return temp;
    }

    /**
     * Moves the specified article
     *
     * @param a Article to move
     * @param newPos New position
     * @return reference to the article moved
     */
    public Article moveArticleAt(Article a, int newPos) {
        for (Article article : list) {
            if (article.equals(a)) {
                list.remove(article);
                list.add(newPos, article);
                return article;
            }
        }
        return null;
    }


    /**
     * Similar to toString but leaves it fully functional
     *
     * @return A String containing the description of the content of this group
     */
    @Override
    public String getPrintableFormat() {
        String output = "Articoli del gruppo " + getName() + ":\n";
        for (int i = 0; i < list.size(); i++) {
            Article article = list.get(i);
            output += String.format("%2d %s\n",i,article.getPrintableFormat());
        }
        return output;
    }

}
