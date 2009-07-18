/*
 * PostgreSQLDataBackendTest.java
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

package gestionecassa.server.datamanager.backends;

import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.Cassiere;
import gestionecassa.ordine.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
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
public class PostgreSQLDataBackendTest {

    String dbUrl;

    PostgreSQLDataBackend backend;

    public PostgreSQLDataBackendTest() {
        dbUrl = "jdbc:postgresql://localhost:5432/TestGCDB";
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
     * Test of init method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testInit() throws Exception {
        System.out.println("init");
        backend = new PostgreSQLDataBackend();
        backend.init(dbUrl);

        String query =  "SELECT table_name" +
                        "   FROM information_schema.tables" +
                        "   WHERE table_schema='public'" +
                        "       AND table_type='BASE TABLE';";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                Set<String> tabelleDB = new ConcurrentSkipListSet<String>();
                while (rs.next()) {
                    tabelleDB.add(rs.getString("table_name"));
                }
                rs.close();

                for (String table_ref : backend.tables.keySet()) {
                    String table_name = table_ref.substring(3);
                    if (!tabelleDB.contains(table_name)) {
                        fail("init did not create table: " + table_name);
                    }
                }
            } catch (SQLException ex) {
                fail("Failed in interrogating the DB");
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB");
        }
    }

    /**
     * Test of addArticleToListAt method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddArticleToListAt() throws Exception {
        System.out.println("addArticleToListAt");
        Article article = null;
        int position = 0;
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addArticleToList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddArticleToList() throws Exception {
        System.out.println("addArticleToList");
        Article article = null;
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        instance.addArticleToList(article);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableArticleFromList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testEnableArticleFromList() throws Exception {
        System.out.println("enableArticleFromList");
        Article article = null;
        boolean enable = false;
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        instance.enableArticleFromList(article, enable);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadArticlesList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testLoadArticlesList() throws Exception {
        System.out.println("loadArticlesList");
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        List expResult = null;
        List result = instance.loadArticlesList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAdmin method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddAdmin() throws Exception {
        System.out.println("addAdmin");
        Admin admin = null;
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        instance.addAdmin(admin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadAdminsList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testLoadAdminsList() throws Exception {
        System.out.println("loadAdminsList");
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        List expResult = null;
        List result = instance.loadAdminsList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableAdmin method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testEnableAdmin() throws Exception {
        System.out.println("enableAdmin");
        Admin admin = null;
        boolean enable = false;
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        instance.enableAdmin(admin, enable);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCassiere method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddCassiere() throws Exception {
        System.out.println("addCassiere");
        Cassiere cassiere = null;
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        instance.addCassiere(cassiere);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadCassiereList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testLoadCassiereList() throws Exception {
        System.out.println("loadCassiereList");
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        List expResult = null;
        List result = instance.loadCassiereList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enableCassiere method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testEnableCassiere() throws Exception {
        System.out.println("enableCassiere");
        Cassiere cassiere = null;
        boolean enable = false;
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        instance.enableCassiere(cassiere, enable);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNewOrder method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddNewOrder() throws Exception {
        System.out.println("addNewOrder");
        String sessionId = "";
        Order order = null;
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        instance.addNewOrder(sessionId, order);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delLastOrder method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testDelLastOrder() throws Exception {
        System.out.println("delLastOrder");
        String sessionId = "";
        PostgreSQLDataBackend instance = new PostgreSQLDataBackend();
        instance.delLastOrder(sessionId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}