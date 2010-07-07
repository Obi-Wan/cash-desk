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
import gestionecassa.stubs.DebugDataProvider;
import java.util.List;
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
        DebugDataProvider dataProvider = new DebugDataProvider();

        articles = dataProvider.getCopyGroups().get(0).getList();
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

        if (!articles.get(2).hasOptions()) {
            fail("Bad data backend");
        }
        ArticleWithOptions articleChosen = (ArticleWithOptions)articles.get(2);
        ArticleOption option = articleChosen.getOptions().get(0);
        instance2.addArticleWOptions(articleChosen, 12, option);

        instance2.doPrint();
    }

}