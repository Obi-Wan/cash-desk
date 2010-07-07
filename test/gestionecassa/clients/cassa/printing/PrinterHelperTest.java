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
import gestionecassa.order.EntryArticleGroup;
import gestionecassa.order.Order;
import gestionecassa.order.PairObjectInteger;
import gestionecassa.stubs.DebugDataProvider;
import java.util.ArrayList;
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
public class PrinterHelperTest {

    static Order order;
    static List<Article> articles;
    static List<ArticleGroup> groups;
    static ArticlesList list;

    public PrinterHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
        DebugDataProvider dataProvider = new DebugDataProvider();
        articles = dataProvider.getCopyArticles();
        groups = dataProvider.getCopyGroups();
        list = dataProvider.getCopyListOfArts();
        order = new Order("bene", "hell", 0, list.getSignature());
        
        EntryArticleGroup entryGroup = new EntryArticleGroup(groups.get(0));
        entryGroup.addArticle(groups.get(0).getList().get(0), 3);
        entryGroup.addArticle(groups.get(0).getList().get(3), 2);

        if (!groups.get(0).getList().get(2).hasOptions()) {
            fail("Bad data backend");
        }
        ArticleWithOptions art = (ArticleWithOptions) groups.get(0).getList().get(2);
        List<PairObjectInteger<ArticleOption>> options =
                new ArrayList<PairObjectInteger<ArticleOption>>();
        options.add(new PairObjectInteger<ArticleOption>(art.getOptions().get(0), 2));
        options.add(new PairObjectInteger<ArticleOption>(art.getOptions().get(1), 1));
        entryGroup.addArticleWithOptions(art,3, 2, options);

        order.addGroup(entryGroup);
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