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
import gestionecassa.Cassiere;
import gestionecassa.Person;
import gestionecassa.ordine.Order;
import java.io.IOException;
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

    static PostgreSQLDataBackend backend;

    Admin testAdmin;

    Cassiere testCassiere;

    public PostgreSQLDataBackendTest() {
        dbUrl = "jdbc:postgresql://localhost:5432/TestGCDB";
        testAdmin = new Admin(1, "admin", "password");
        testCassiere = new Cassiere(1, "bene", "male");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        backend = new PostgreSQLDataBackend();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        for (String table_ref : backend.tables.keySet()) {
            String table_name = table_ref.substring(3);
            String query = "DROP TABLE " + table_name + " CASCADE;";
            try {
                Statement st = backend.db.createStatement();
                try {
                    st.execute(query);
                } catch (SQLException ex) {
                    fail("Failed in Cleaning the DB: ");
                    ex.printStackTrace();
                } finally {
                    st.close();
                }
            } catch (SQLException ex) {
                fail("Failed in connecting to the DB");
            }
        }
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
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }
    }

    /**
     * Test of addArticleToListAt method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddArticleToListAt() throws Exception {
        System.out.println("addArticleToListAt");
    }

    /**
     * Test of addArticleToList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddArticleToList() throws Exception {
        System.out.println("addArticleToList");
    }

    /**
     * Test of enableArticleFromList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testEnableArticleFromList() throws Exception {
        System.out.println("enableArticleFromList");
    }

    /**
     * Test of loadArticlesList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testLoadArticlesList() throws Exception {
        System.out.println("loadArticlesList");
    }

    /**
     * Test of addAdmin method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddAdmin() throws Exception {
        System.out.println("addAdmin");

        try {
            backend.addAdmin(testAdmin);
        } catch (IOException ex) {
            fail("non sono riuscito ad aggiungere l'admin: " + ex.getMessage());
        }

        String query = "SELECT * FROM admins WHERE username = 'admin'";
        testPersonPresence(query,testAdmin);
    }

    /**
     * Test of loadAdminsList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testLoadAdminsList() throws Exception {
        System.out.println("loadAdminsList");

        List<Admin> adminList = backend.loadAdminsList();

        assertNotNull(adminList);
        assertEquals(adminList.size(), 1);
        assertEquals(adminList.get(0), testAdmin);
    }

    /**
     * Test of enableAdmin method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testEnableAdmin() throws Exception {
        System.out.println("enableAdmin");

        backend.enableAdmin(testAdmin, false);

        List<Admin> adminList = backend.loadAdminsList();

        assertTrue(!adminList.get(0).isEnabled());

        backend.enableAdmin(testAdmin, true);

        adminList = backend.loadAdminsList();

        assertTrue(adminList.get(0).isEnabled());
    }

    /**
     * Test of addCassiere method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddCassiere() {
        System.out.println("addCassiere");

        try {
            backend.addCassiere(testCassiere);
        } catch (IOException ex) {
            fail("non sono riuscito ad aggiungere il cassiere: " + ex.getMessage());
        }

        String query = "SELECT * FROM cassieres WHERE username = 'bene'";
        testPersonPresence(query,testCassiere);
    }

    /**
     * Test of loadCassiereList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testLoadCassiereList() throws Exception {
        System.out.println("loadCassiereList");

        List<Cassiere> cassiereList = backend.loadCassiereList();

        assertNotNull(cassiereList);
        assertEquals(cassiereList.size(), 1);
        assertEquals(cassiereList.get(0), testCassiere);
    }

    /**
     * Test of enableCassiere method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testEnableCassiere() throws Exception {
        System.out.println("enableCassiere");

        backend.enableCassiere(testCassiere, false);

        List<Cassiere> cassiereList = backend.loadCassiereList();

        assertTrue(!cassiereList.get(0).isEnabled());

        backend.enableCassiere(testCassiere, true);

        cassiereList = backend.loadCassiereList();

        assertTrue(cassiereList.get(0).isEnabled());
    }

    /**
     * Test of addNewOrder method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddNewOrder() throws Exception {
        System.out.println("addNewOrder");

        Order tempOrder = new Order("bene", "hell");
    }

    /**
     * Test of delLastOrder method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testDelLastOrder() throws Exception {
        System.out.println("delLastOrder");
    }

    private void testPersonPresence(String query, Person person) {
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);
                assertTrue("Non c'è un risultato!",rs.next());
                assertTrue("Non è l'ultimo!",rs.isLast());

                assertEquals("La password non corrisponde!",
                        rs.getString("password"), person.getPassword());
            } catch (SQLException ex) {
                fail("Failed in executing the query: " + query + "\n" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in connecting to the DB");
        }
    }
}