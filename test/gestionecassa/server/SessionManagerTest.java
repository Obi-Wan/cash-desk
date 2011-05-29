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

import gestionecassa.Admin;
import gestionecassa.Cassiere;
import gestionecassa.Log;
import gestionecassa.Person;
import gestionecassa.server.clientservices.ServiceRMICassiereImpl;
import gestionecassa.server.datamanager.DataManager;
import gestionecassa.stubs.BackendStub_1;
import gestionecassa.stubs.BackendStub_2;
import java.rmi.RemoteException;
import java.util.Iterator;
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
    Person user1, user2, user3, user4;

    DataManager dataMngr;

    public SessionManagerTest() {
        sectMngr = new SessionManager(Log.GESTIONECASSA_SERVER);
        user1 = new Cassiere(0, "Cassere1", "Cassiere1");
        user2 = new Cassiere(2, "Cassere2", "Cassiere2");
        user3 = new Admin(3, "Admin1", "Admin1");
        user4 = new Cassiere((Cassiere)user1);

        dataMngr = new DataManager(new BackendStub_2(), new ServerPrefs(),
                new BackendStub_1());
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
     * Test of newSession method, of class SessionManager.
     */
    @Test
    public void testNewSession() {
        System.out.println("newSession");
        SessionRecord newRecord = new SessionRecord();
        newRecord.user = user1;
        int result = sectMngr.newSession(newRecord);

        assertEquals("Not first session!", 0, result);
        assertEquals("Not first session!", result, newRecord.sessionId);

        assertTrue("Session not inserted", sectMngr.getSessions().containsValue(newRecord));
        assertTrue("Session not right", sectMngr.getSessions().get(result).equals(newRecord));
    }

    /**
     * Test of isSessionAlreadyOpen method, of class SessionManager.
     */
    @Test
    public void testIsSessionAlreadyOpen() {
        System.out.println("isSessionAlreadyOpen");
        SessionRecord newRecord = new SessionRecord();
        newRecord.user = user1;
        int result = sectMngr.newSession(newRecord);

        assertTrue("Not first session!",result == 0);

        assertTrue("Session not inserted", sectMngr.isSessionAlreadyOpen(newRecord));
    }

    /**
     * Test of tick method, of class SessionManager.
     * @throws RemoteException 
     */
    @Test
    public void testTick() throws RemoteException {
        System.out.println("tick");
        SessionRecord newRecord = new SessionRecord();
        newRecord.user = user1;
        newRecord.serviceThread =
                new ServiceRMICassiereImpl(user1.getUsername(), true, dataMngr);
        int sessID = sectMngr.newSession(newRecord);

        for(int i = 0; i < SessionManager.toc+1; i++) {
            sectMngr.tick();
        }

        assertTrue("Session not inserted",
                sectMngr.isSessionAlreadyOpen(newRecord));

        assertEquals("Counter not increased", 1,
                sectMngr.getSessions().get(sessID).timeElapsed);

        for(int i = 0; i < 5 * (SessionManager.toc + 1); i++) {
            sectMngr.tick();
        }

        assertEquals("Counter not increased", 6,
                sectMngr.getSessions().get(sessID).timeElapsed);

        /* ora aggiungo un elemento che non verrà eliminato */
        SessionRecord newRecord2 = new SessionRecord();
        newRecord2.user = user2;
        newRecord2.serviceThread =
                new ServiceRMICassiereImpl(user1.getUsername(), true, dataMngr);
        int sessID2 = sectMngr.newSession(newRecord2);

        for(int i = 0; i < SessionManager.toc+1; i++) {
            sectMngr.tick();
        }

        System.out.println("Sessions opened");
        for (SessionRecord sessionRecord : sectMngr.getSessions().values()) {
            System.out.println(" - User: "+
                    ( sessionRecord.user == null ?
                        "null" :
                        sessionRecord.user.getUsername()));
            System.out.println("   Id: "+sessionRecord.sessionId);
            System.out.println("   Time: "+sessionRecord.timeElapsed);
        }

        System.out.println("Sessions recycled");
        for (Iterator<Integer> it = sectMngr.getRecycleIds().iterator(); it.hasNext();) {
            Integer id = it.next();
            System.out.println(" - Id: "+id);
        }

        assertNotNull("object is null", newRecord);
        assertFalse("Recycle is empty",
                sectMngr.getRecycleIds().isEmpty());
        assertFalse("Session is empty",
                sectMngr.getSessions().isEmpty());
        assertFalse("Session is still in",
                sectMngr.getSessions().containsKey(sessID));

        assertTrue("Session is in recycle",
                sectMngr.getRecycleIds().contains(sessID));
        assertTrue("New session is in sessions",
                sectMngr.getSessions().containsKey(sessID2));
    }

    /**
     * Test of keepAlive method, of class SessionManager.
     * @throws Exception 
     */
    @Test
    public void testKeepAlive() throws Exception {
        System.out.println("keepAlive");
        SessionRecord newRecord = new SessionRecord();
        newRecord.user = user1;
        newRecord.serviceThread =
                new ServiceRMICassiereImpl(user1.getUsername(), true, dataMngr);
        int sessID = sectMngr.newSession(newRecord);

        for(int i = 0; i < SessionManager.toc+1; i++) {
            sectMngr.tick();
        }

        sectMngr.keepAlive(sessID);

        assertEquals("Counter not resetted", 0,
                sectMngr.getSessions().get(sessID).timeElapsed);
    }

    /**
     * Test of closeService method, of class SessionManager.
     * @throws Exception 
     */
    @Test
    public void testCloseService() throws Exception {
        System.out.println("closeService");
        SessionRecord newRecord = new SessionRecord();
        newRecord.user = user1;
        newRecord.serviceThread =
                new ServiceRMICassiereImpl(user1.getUsername(), true, dataMngr);
        int sessID = sectMngr.newSession(newRecord);

        sectMngr.closeService(sessID);

        assertTrue("Session is not in recycle",
                sectMngr.getRecycleIds().contains(sessID));
        assertFalse("Session already in",
                sectMngr.getSessions().containsKey(sessID));
    }

    /**
     * Test of kickOff method, of class SessionManager.
     * @throws RemoteException 
     */
    @Test
    public void testKickOff() throws RemoteException {
        System.out.println("kickOff");
        SessionRecord newRecord = new SessionRecord();
        newRecord.user = user1;
        newRecord.serviceThread =
                new ServiceRMICassiereImpl(user1.getUsername(), true, dataMngr);
        int sessID = sectMngr.newSession(newRecord);

        sectMngr.kickOff(newRecord);

        assertTrue("Session is not in recycle",
                sectMngr.getRecycleIds().contains(sessID));
        assertFalse("Session already in",
                sectMngr.getSessions().containsKey(sessID));
    }

    /**
     * Test of temrinate method, of class SessionManager.
     * @throws RemoteException 
     */
    @Test
    public void testTemrinate() throws RemoteException {
        System.out.println("temrinate");
        SessionRecord newRecord = new SessionRecord();
        newRecord.user = user1;
        newRecord.serviceThread =
                new ServiceRMICassiereImpl(user1.getUsername(), true, dataMngr);
        int sessID = sectMngr.newSession(newRecord);

        sectMngr.temrinate();

        System.out.println("Sessions opened");
        for (SessionRecord sessionRecord : sectMngr.getSessions().values()) {
            System.out.println(" - User: "+
                    ( sessionRecord.user == null ?
                        "null" :
                        sessionRecord.user.getUsername()));
            System.out.println("   Id: "+sessionRecord.sessionId);
            System.out.println("   Time: "+sessionRecord.timeElapsed);
        }

        System.out.println("Sessions recycled");
        for (Iterator<Integer> it = sectMngr.getRecycleIds().iterator(); it.hasNext();) {
            Integer id = it.next();
            System.out.println(" - Id: "+id);
        }

        assertTrue("Recycle is not empty",
                sectMngr.getRecycleIds().isEmpty());
        assertTrue("Sessions is not empty",
                sectMngr.getSessions().isEmpty());
    }

}