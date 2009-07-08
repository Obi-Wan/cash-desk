/*
 * ListaBeniTest.java
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
public class ListaBeniTest {

    public ListaBeniTest() {
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

        ArticlesList instance = new ArticlesList();
        instance.list.add(new Article("culo", 80));
        instance.list.add(new Article("merda", 10));
        List<String> options = new ArrayList<String>();
        options.add("con mano");
        options.add("cor culo");
        instance.list.add(new ArticleWithOptions("rasponi", 20,options));

        String expResult = "Lista dei beni venduti:\n- culo\t € 80.0\n- " +
                "merda\t € 10.0\n- rasponi\t € 20.0\n  .\t con mano\n  .\t cor culo\n--\n";
        String result = instance.getPrintableFormat();

        System.out.println(result);
        assertEquals(expResult, result);
    }

}