/*
 * DaemonReestablishConnectionTest.java
 * 
 * Copyright (C) 2011 Nicola Roberto Viganò
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gestionecassa.clients;

import gestionecassa.ConnectionDetails;
import gestionecassa.clients.DaemonReestablishConnection.DaemonRuntimeState;
import gestionecassa.exceptions.NotExistingSessionException;
import gestionecassa.stubs.MainServerStub;
import java.rmi.RemoteException;
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
public class DaemonReestablishConnectionTest {
    
    public DaemonReestablishConnectionTest() {
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
     * Test of getDaemonState method, of class DaemonReestablishConnection.
     * @throws InterruptedException 
     */
    @Test
    public void testNormalKeepAliveAndClosure() throws InterruptedException {
        System.out.println("NormalKeepAliveAndClosure");
        
        ConnectionDetails connDetails = new ConnectionDetails();
        connDetails.timeout = 10000;
        connDetails.sessionID = 0;
        MainServerStub stubServer = new MainServerStub();
        
        DaemonReestablishConnection instance =
                new DaemonReestablishConnection(stubServer, connDetails);
        
        instance.start();
        
        Thread.sleep(50);
        
        System.out.println(" - First - Normal Keep Alive");
        assertEquals("Wrong thread state: should be running",
                DaemonRuntimeState.Running, instance.getDaemonState());
        assertEquals("Wrong number of requests", 1, stubServer.keepAliveCount);
        
        instance.interrupt();
        
        Thread.sleep(50);
        
        System.out.println(" - Second - Normal Closed Service");
        assertEquals("Wrong thread state: should be Stopped",
                DaemonRuntimeState.Stopped, instance.getDaemonState());
    }

    /**
     * Test of getDaemonState method, of class DaemonReestablishConnection.
     * @throws InterruptedException 
     */
    @Test
    public void testNotExistingService() throws InterruptedException {
        System.out.println("NotExistingService");
        
        ConnectionDetails connDetails = new ConnectionDetails();
        connDetails.timeout = 200;
        connDetails.sessionID = 0;
        MainServerStub stubServer = new MainServerStub();
        
        DaemonReestablishConnection instance =
                new DaemonReestablishConnection(stubServer, connDetails);
        
        instance.start();
        
        Thread.sleep(50);
        
        stubServer.keepAliveState = MainServerStub.KeepAliveState.Closed;
        
        Thread.sleep(100);
        
        assertEquals("Wrong thread state: should be in NotExistingSessionError",
                DaemonRuntimeState.NotExistingSessionError, instance.getDaemonState());
        assertEquals("Wrong number of requests", 2, stubServer.keepAliveCount);
        assertTrue("Wrong Instance of exception",
                instance.getErrorException() instanceof NotExistingSessionException);
    }

    /**
     * Test of getDaemonState method, of class DaemonReestablishConnection.
     * @throws InterruptedException 
     */
    @Test
    public void testRemoteError() throws InterruptedException {
        System.out.println("NotExistingService");
        
        ConnectionDetails connDetails = new ConnectionDetails();
        connDetails.timeout = 200;
        connDetails.sessionID = 0;
        MainServerStub stubServer = new MainServerStub();
        
        DaemonReestablishConnection instance =
                new DaemonReestablishConnection(stubServer, connDetails);
        
        instance.start();
        
        Thread.sleep(50);
        
        stubServer.keepAliveState = MainServerStub.KeepAliveState.ConnectionError;
        
        Thread.sleep(100);
        
        assertEquals("Wrong thread state: should be in RemoteError",
                DaemonRuntimeState.RemoteError, instance.getDaemonState());
        assertEquals("Wrong number of requests", 2, stubServer.keepAliveCount);
        assertTrue("Wrong Instance of exception",
                instance.getErrorException() instanceof RemoteException);
    }
}
