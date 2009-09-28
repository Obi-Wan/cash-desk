/*
 * SessionManagerTest.java
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

package gestionecassa.server;

import gestionecassa.Log;
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
public class SessionManagerTest {

    SessionManager sectMngr;

    public SessionManagerTest() {
        sectMngr = new SessionManager(Log.GESTIONECASSA_SERVER);
        sectMngr.start();
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
        sectMngr.stopServer();
    }

    /**
     * Test of newSession method, of class SessionManager.
     */
    @Test
    public void testNewSession() {
        System.out.println("newSession");
        SessionRecord newRecord = null;
        SessionManager instance = null;
        int expResult = 0;
        int result = instance.newSession(newRecord);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of verifySession method, of class SessionManager.
     */
    @Test
    public void testVerifySession() {
        System.out.println("verifySession");
        SessionRecord record = null;
        SessionManager instance = null;
        boolean expResult = false;
        boolean result = instance.verifySession(record);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of keepAlive method, of class SessionManager.
     */
    @Test
    public void testKeepAlive() throws Exception {
        System.out.println("keepAlive");
        int sessionID = 0;
        SessionManager instance = null;
        instance.keepAlive(sessionID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeService method, of class SessionManager.
     */
    @Test
    public void testCloseService() throws Exception {
        System.out.println("closeService");
        int sessionID = 0;
        SessionManager instance = null;
        instance.closeService(sessionID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}