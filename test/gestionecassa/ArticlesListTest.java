/*
 * ArticlesListTest.java
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
public class ArticlesListTest {

    List<Article> articles;

    public ArticlesListTest() {

        articles = new Vector<Article>();
        List<String> options = new Vector<String>();
        options.add("corta");
        options.add("media");
        options.add("lunga");
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
     * Test of getPrintableFormat method, of class ArticlesList.
     */
    @Test
    public void testGetPrintableFormat() {
        System.out.println("getPrintableFormat");

        ArticlesList list = new ArticlesList(articles);
        System.out.println(list.getPrintableFormat());
    }

}