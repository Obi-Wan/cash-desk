/*
 * PrinterHelperTest.java
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
import gestionecassa.ArticleGroup;
import gestionecassa.ArticleOption;
import gestionecassa.ArticleWithOptions;
import gestionecassa.ArticlesList;
import gestionecassa.order.Order;
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
public class PrinterHelperTest {

    static Order order;
    static List<Article> articles;
    static List<ArticleGroup> groups;
    static ArticlesList list;

    public PrinterHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        groups = new ArrayList<ArticleGroup>();

        int idArticle = 0;

        /* First group with id 0 */
        articles = new ArrayList<Article>();
        List<ArticleOption> options = new ArrayList<ArticleOption>();
        options.add(new ArticleOption(0, "corta", true));
        options.add(new ArticleOption(1, "media", true));
        options.add(new ArticleOption(2, "lunga", true));
        articles.add(new Article(++idArticle, "gatto", 5.5));
        articles.add(new Article(++idArticle, "cane", 10));
        articles.add(new ArticleWithOptions(++idArticle, "falce", 4.25, options));
        articles.add(new Article(++idArticle, "vanga", 0.2));

        groups.add(new ArticleGroup(1, "Group1", articles));

        /* Second group, empty, with id 1 */
        articles = new ArrayList<Article>();
        groups.add(new ArticleGroup(2, "Group2", articles));

        /* Articles not in group 1 to add later */
        articles = new ArrayList<Article>();options = new ArrayList<ArticleOption>();
        options.add(new ArticleOption(3, "corta1", true));
        options.add(new ArticleOption(4, "media1", true));
        options.add(new ArticleOption(5, "lunga1", true));
        articles.add(new Article(++idArticle, "gatto1", 5.5));
        articles.add(new Article(++idArticle, "cane1", 10));
        articles.add(new ArticleWithOptions(++idArticle, "falce1", 4.25, options));
        articles.add(new Article(++idArticle, "vanga1", 0.2));

        list = new ArticlesList(groups);
        order = new Order("bene", "hell", 0, list.getSignature());
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
     * Test of startPrintingOrder method, of class PrinterHelper.
     */
    @Test
    public void testStartPrintingOrder() {
        System.out.println("startPrintingOrder");
        PrinterHelper.startPrintingOrder(order);
        assertTrue(true);
    }

    /**
     * Test of run method, of class PrinterHelper.
     */
//    @Test
//    public void testRun() {
//        System.out.println("run");
//        PrinterHelper instance = null;
//        instance.run();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}