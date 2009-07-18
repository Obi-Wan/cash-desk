/*
 * DataManagerTest.java
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

package gestionecassa.server.datamanager;

import gestionecassa.ArticlesList;
import gestionecassa.Person;
import gestionecassa.stubs.BackendStub_1;
import gestionecassa.stubs.BackendStub_2;
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
public class DataManagerTest {

    DataManager dataManager;

    public DataManagerTest() {
        BackendAPI_1 backend_1 = new BackendStub_1();
        BackendAPI_2 backend_2 = new BackendStub_2();
        dataManager = new DataManager(backend_2, backend_1);
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
     * Test of registerUser method, of class DataManager.
     */
    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        Person user = null;
        DataManager instance = null;
        instance.registerUser(user);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of verifyUsername method, of class DataManager.
     */
    @Test
    public void testVerifyUsername() {
        System.out.println("verifyUsername");
        String username = "";
        DataManager instance = null;
        Person expResult = null;
        Person result = instance.verifyUsername(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentArticlesList method, of class DataManager.
     */
    @Test
    public void testGetCurrentArticlesList() {
        System.out.println("getCurrentArticlesList");
        DataManager instance = null;
        ArticlesList expResult = null;
        ArticlesList result = instance.getCurrentArticlesList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createNewCassaSession method, of class DataManager.
     */
    @Test
    public void testCreateNewCassaSession() {
        System.out.println("createNewCassaSession");
        String identifier = "";
        DataManager instance = null;
        instance.createNewCassaSession(identifier);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeCassaSession method, of class DataManager.
     */
    @Test
    public void testCloseCassaSession() {
        System.out.println("closeCassaSession");
        String identifier = "";
        DataManager instance = null;
        instance.closeCassaSession(identifier);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addNewOrder method, of class DataManager.
     */
    @Test
    public void testAddNewOrder() {
        System.out.println("addNewOrder");
    }

    /**
     * Test of delLastOrder method, of class DataManager.
     */
    @Test
    public void testDelLastOrder() {
        System.out.println("delLastOrder");
    }

    /**
     * Test of getNProgressivo method, of class DataManager.
     */
    @Test
    public void testGetNProgressivo() {
        System.out.println("getNProgressivo");
        String articleName = "";
        int n = 0;
        DataManager instance = null;
        int expResult = 0;
        int result = instance.getNProgressivo(articleName, n);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveNewArticlesList method, of class DataManager.
     */
    @Test
    public void testSaveNewArticlesList() {
        System.out.println("saveNewArticlesList");
        ArticlesList list = null;
        DataManager instance = null;
        instance.saveNewArticlesList(list);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of terminate method, of class DataManager.
     */
    @Test
    public void testTerminate() {
        System.out.println("terminate");
        DataManager instance = null;
        instance.terminate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}