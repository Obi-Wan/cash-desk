/*
 * TextPainterTest.java
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

package gestionecassa.clients.cassa.printing;

import gestionecassa.Article;
import gestionecassa.ArticleOption;
import gestionecassa.ArticleWithOptions;
import java.util.List;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ben
 */
public class TextPainterTest {

    List<Article> articles;

    public TextPainterTest() {
        articles = new ArrayList<Article>();
        List<ArticleOption> options = new ArrayList<ArticleOption>();
        options.add(new ArticleOption(0, "corta", true));
        options.add(new ArticleOption(1, "media", true));
        options.add(new ArticleOption(2, "lunga", true));
        articles.add(new Article(articles.size()+1, "gatto", 5.5));
        articles.add(new Article(articles.size()+1, "cane", 10));
        articles.add(new ArticleWithOptions(articles.size()+1, "falce", 4.25, options));
        articles.add(new Article(articles.size()+1, "vanga", 0.2));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addArticle method, of class TextPainter.
     * @throws Exception 
     */
    @Test
    public void testDoPrint() throws Exception {
        System.out.println("doPrint");
        TextPainter instance = new TextPainter("test");
        instance.addArticle(articles.get(0), 1);
        instance.doPrint();

        TextPainter instance2 = new TextPainter("test2");

        ArticleWithOptions articleChosen = (ArticleWithOptions)articles.get(2);
        ArticleOption option = articleChosen.getOptions().get(0);
        instance2.addArticleWOptions(articleChosen, 12, option);

        instance2.doPrint();
    }

}