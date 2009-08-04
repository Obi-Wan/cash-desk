/*
 * AdministrationCLI.java
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

package gestionecassa.clients.amministrazione.cli;

import gestionecassa.clients.amministrazione.Administration;
import gestionecassa.clients.amministrazione.AdministrationAPI;

/**
 *
 * @author ben
 */
public class AdministrationCLI extends Administration {

    /**
     * Constructor
     * 
     * @param nomeLuogo
     */
    protected AdministrationCLI(String nomeLuogo) {
        super(nomeLuogo);
    }

    /**
     * Creator of the administration process
     *
     * @return reference to the main class
     */
    public static AdministrationAPI crea() {
        return new AdministrationCLI(System.getenv("HOSTNAME"));
    }

    /**
     * The main of the Amministratore Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {

        // cominciamo l'esecuzione del thread principale
        AdministrationCLI.crea().avvia();

    }


    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        // login / greeting stuff goes here.

        super.run();
    }
}
