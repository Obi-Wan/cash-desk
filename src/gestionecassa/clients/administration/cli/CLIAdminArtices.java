/*
 * CLIAdminArtices.java
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

package gestionecassa.clients.administration.cli;

import gestionecassa.clients.administration.AdministrationAPI;
import java.io.Console;

/**
 *
 * @author ben
 */
public class CLIAdminArtices {

    AdministrationAPI owner;

    Console con;

    public CLIAdminArtices(AdministrationAPI owner, Console con) {
        this.owner = owner;
        this.con = con;
    }

    /**
     *
     */
    void printMenuArticles() {
        String menu = "You can now choose between these alternatives:\n" +
                " - s - Show\n" +
                " - a - Add" +
                " - d - Disable/Enable" +
                " - m - Modify\n" +
                " - q - Quit\n" +
                "Choice: ";
        char choice;
        con.writer().println("\n"+owner.getArticlesList().getPrintableFormat());
        do {
            choice = con.readLine(menu).charAt(0);
            switch (choice) {
                case 's': {
                    con.writer().println("\n" +
                            owner.getArticlesList().getPrintableFormat() +
                            "\nPress Enter to continue..");
                    con.readLine();
                    break;
                }
                case 'a': {
                    break;
                }
                case 'd': {
                    break;
                }
                case 'm': {
                    break;
                }
                default: {

                }
            }
        } while(choice != 'q');
    }

}
