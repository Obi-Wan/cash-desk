/*
 * MainServerStub.java
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
package gestionecassa.stubs;

import gestionecassa.ConnectionDetails;
import gestionecassa.exceptions.NotExistingSessionException;
import gestionecassa.exceptions.WrongLoginException;
import gestionecassa.server.ServerRMICommon;
import java.rmi.RemoteException;

/**
 *
 * @author ben
 */
public class MainServerStub implements ServerRMICommon {
    
    public enum KeepAliveState {
        Running,
        Closed,
        ConnectionError,
    }
    
    public KeepAliveState keepAliveState = KeepAliveState.Running;
    
    public int keepAliveCount = 0;

    @Override
    public ConnectionDetails doRMILogin(String username, String password)
            throws RemoteException, WrongLoginException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keepAlive(int sessionID)
            throws RemoteException, NotExistingSessionException
    {
        keepAliveCount++;
        
        switch (keepAliveState) {
            case Closed: {
                throw new NotExistingSessionException();
            }
            case ConnectionError: {
                throw new RemoteException();
            }
        }
    }

    @Override
    public void closeService(int sessionID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
