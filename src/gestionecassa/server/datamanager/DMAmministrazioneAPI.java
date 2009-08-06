/*
 * DMAmministrazioneAPI.java
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

package gestionecassa.server.datamanager;

import gestionecassa.Article;
import gestionecassa.ArticlesList;
import gestionecassa.Person;

/**
 *
 * @author ben
 */
public interface DMAmministrazioneAPI extends DMCommonAPI, DMServerAPI {

    //--------- Articles ------------//

    /**
     * Saves the new list of goods sold.
     * 
     * @param lista
     */
    void saveNewArticlesList(ArticlesList lista);

    /**
     * Adds an article to the common list.
     *
     * @param article
     */
    void addArticle(Article article);

    /**
     * Enables/disables an article at the index specified by position.
     *
     * @param position
     * @param enable
     */
    void enableArticle(int position, boolean enable);

    /**
     * Enables/disables the article.
     *
     * @param article
     * @param enable
     */
    void enableArticle(Article article, boolean enable);

    /**
     * Moves an article
     *
     * @param oldPos Old position
     * @param newPos New position
     */
    void moveArticle(int oldPos, int newPos);

    /**
     * Moves the specified article
     *
     * @param article Article to move
     * @param newPos New position
     */
    void moveArticle(Article article, int newPos);

    //--------- Users ---------------//
    
    /**
     *
     * @param user
     * @return
     */
    void registerUser(Person user);

}
