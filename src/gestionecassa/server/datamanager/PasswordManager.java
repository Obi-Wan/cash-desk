/*
 * PasswordManager.java
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
package gestionecassa.server.datamanager;

import gestionecassa.Person;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * @author ben
 */
public class PasswordManager {
    
    /**
     * MD5 algorithm to hash the passwords and check them.
     */
    MessageDigest digestAlgo;
    
    /**
     * The used charset for decoding strings and bytes
     */
    public final Charset charset;

    /**
     * Semaphore for the digesting algortihm
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final private String algoSemaphore =
            "AlgoSemaphore" + System.currentTimeMillis();

    public PasswordManager() {
        if (Charset.isSupported("UTF8")) {
            charset = Charset.forName("UTF8");
        } else {
            charset = Charset.defaultCharset();
        }
        digestAlgo = null;
    }
    
    /**
     * It initializes the object, and in case of an error with the algorithm, it
     * throws an exception
     * @param digest
     * @throws NoSuchAlgorithmException 
     */
    public void initDigest(boolean digest) throws NoSuchAlgorithmException {
        synchronized (algoSemaphore) {
            if (digest) {
                try {
                    digestAlgo = MessageDigest.getInstance("MD5");
                    digestAlgo.reset();
                } catch (NoSuchAlgorithmException ex) {
                    digestAlgo = null;
                    throw ex;
                }
            } else {
                digestAlgo = null;
            }
        }
    }
    
    public boolean checkUserAndPassword(Person user, String password) {
        if (user != null) {
            if (digestAlgo != null) {
                byte[] inputPassword = null;
                byte[] savedPassword = user.getPassword().getBytes(charset);
                
                synchronized (algoSemaphore) {
                  inputPassword = digestAlgo.digest(password.getBytes(charset));
                }
                
                return Arrays.equals(savedPassword, inputPassword);
            } else {
                return user.getPassword().equals(password);
            }
        }
        return false;
    }
    
    public String digestPassword(String password) {
        byte[] inputPassword = null;
        synchronized (algoSemaphore) {
          inputPassword = digestAlgo.digest(password.getBytes(charset));
        }
        return new String(inputPassword, charset);
    }
}
