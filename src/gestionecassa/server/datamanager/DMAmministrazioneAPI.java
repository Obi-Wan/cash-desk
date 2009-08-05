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
     * 
     * @param article
     */
    void addArticle(Article article);

    /**
     * 
     * @param position
     * @param enable
     */
    void enableArticle(int position, boolean enable);

    /**
     *
     * @param article
     * @param enable
     */
    void enableArticle(Article article, boolean enable);

    /**
     *
     * @param oldPos
     * @param newPos
     */
    void moveArticle(int oldPos, int newPos);

    /**
     *
     * @param article
     * @param newPos
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
