/*
 * DebugDataProvider.java
 * 
 * Copyright (C) 2010 Nicola Roberto Viganò
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package gestionecassa.stubs;

import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.ArticleGroup;
import gestionecassa.ArticleOption;
import gestionecassa.ArticlesList;
import gestionecassa.Cassiere;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ben
 */
public class DebugDataProvider {

    public ArticlesList listOfArts;
    public List<ArticleGroup> groups;
    public List<Article> articles;

    public List<Admin> admins;

    public List<Cassiere> cassieres;

    public DebugDataProvider() {
        groups = new ArrayList<ArticleGroup>();

        int idArticle = 0;
        int idOption = 0;
        int idGroup = 0;

        /* First group with id 0 */
        articles = new ArrayList<Article>();
        List<ArticleOption> options = new ArrayList<ArticleOption>();
        options.add(new ArticleOption(++idOption, "corta", true));
        options.add(new ArticleOption(++idOption, "media", true));
        options.add(new ArticleOption(++idOption, "lunga", true));
        articles.add(new Article(++idArticle, "gatto", 5.5));
        articles.add(new Article(++idArticle, "cane", 10));
        articles.add(new Article(++idArticle, "falce", 4.25, true, options));
        articles.add(new Article(++idArticle, "vanga", 0.2));

        groups.add(new ArticleGroup(++idGroup, "Group1", articles));

        /* Second group, empty, with id 1 */
        articles = new ArrayList<Article>();
        groups.add(new ArticleGroup(++idGroup, "Group2", articles));

        /* Articles not in group 1 to add later */
        articles = new ArrayList<Article>();
        options = new ArrayList<ArticleOption>();
        options.add(new ArticleOption(++idOption, "corta1", true));
        options.add(new ArticleOption(++idOption, "media1", true));
        options.add(new ArticleOption(++idOption, "lunga1", true));
        articles.add(new Article(++idArticle, "gatto1", 5.5));
        articles.add(new Article(++idArticle, "cane1", 10));
        articles.add(new Article(++idArticle, "falce1", 4.25, true, options));
        articles.add(new Article(++idArticle, "vanga1", 0.2));
        
        /* List of Articles creation */
        listOfArts = new ArticlesList(groups);

        /* Admins */
        admins = new ArrayList<Admin>();
        admins.add(new Admin(admins.size()+1, "admin", "password"));
        admins.add(new Admin(admins.size()+1, "admin1", "password1"));

        /* Cassieres */
        cassieres = new ArrayList<Cassiere>();
        cassieres.add(new Cassiere(cassieres.size()+1, "bene", "male"));
        cassieres.add(new Cassiere(cassieres.size()+1, "bene1", "male1"));
    }

    public List<Admin> getCopyAdmins() {
        return new ArrayList<Admin>(admins);
    }

    public List<Cassiere> getCopyCassieres() {
        return new ArrayList<Cassiere>(cassieres);
    }

    public List<ArticleGroup> getCopyGroups() {
        return new ArrayList<ArticleGroup>(groups);
    }

    public ArticlesList getCopyListOfArts() {
        return new ArticlesList(listOfArts);
    }

    public List<Article> getCopyArticles() {
        return new ArrayList<Article>(articles);
    }


}
