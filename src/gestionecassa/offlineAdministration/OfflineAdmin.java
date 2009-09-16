/*
 * OfflineAdmin.java
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

package gestionecassa.offlineAdministration;

/**
 *
 * @author ben
 */
public class OfflineAdmin extends Thread {

    /** Variable that tells to the main thread he has to
     * stop working.
     */
    protected volatile boolean stopApp;

    private OfflineAdminForm form;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        avvia();
    }

    static private OfflineAdmin blogic;

    static {
        blogic = null;
    }


    /**
     * Starts the thread
     */
    synchronized static public OfflineAdmin avvia() {
        if (blogic == null) {
            blogic = new OfflineAdmin();
            blogic.start();
        }
        return blogic;
    }

    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {

        form = new OfflineAdminForm(this);
        form.setVisible(true);

        // Comincia l'esecuzione normale
        try {
            while (stopApp == false) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.dumpStack();
        }
        //exit
        System.out.println("sto uscendo dal client");
    }

    /**
     * The stopping Method
     */
    public void stopClient() {
        stopApp = true;
    }


}
