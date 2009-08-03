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
import gestionecassa.ArticleWithOptions;
import gestionecassa.order.EntrySingleOption;
import gestionecassa.order.Order;
import java.util.List;
import java.util.Vector;
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
    private static Vector<Article> articles;

    public PrinterHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        order = new Order("bene", "hell");

        articles = new Vector<Article>();
        List<String> options = new Vector<String>();
        options.add("corta");
        options.add("media");
        options.add("lunga");
        articles.add(new Article(articles.size()+1, "gatto", 5.5));
        articles.add(new Article(articles.size()+1, "cane", 10));
        articles.add(new ArticleWithOptions(articles.size()+1, "falce", 4.25, options));
        articles.add(new Article(articles.size()+1, "vanga", 0.2));

        List<EntrySingleOption> partialList = new Vector<EntrySingleOption>();
        partialList.add(new EntrySingleOption(options.get(0), 2));
        partialList.add(new EntrySingleOption(options.get(1), 3));

        order.addArticle(articles.get(0), 3);
        order.addArticle(articles.get(3), 2);
        order.addArticleWithOptions((ArticleWithOptions)articles.get(2), 5, 12, partialList);
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
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
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