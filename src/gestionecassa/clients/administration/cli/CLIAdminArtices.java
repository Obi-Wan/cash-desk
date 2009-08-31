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

import gestionecassa.Article;
import gestionecassa.ArticleWithOptions;
import gestionecassa.clients.administration.AdministrationAPI;
import java.io.Console;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class CLIAdminArtices {

    AdministrationAPI owner;

    Console con;

    /**
     * 
     * @param owner
     * @param con
     */
    public CLIAdminArtices(AdministrationAPI owner, Console con) {
        this.owner = owner;
        this.con = con;
    }

    /**
     * 
     * @throws RemoteException
     */
    void printMenuArticles() throws RemoteException {
        String menu = "You can now choose between these alternatives:\n" +
                " - s - Show\n" +
                " - a - Add" +
                " - e - Disable/Enable" +
                " - d - Move" +
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
                    addArticle();
                    break;
                }
                case 'e': {
                    enableDisableArticle();
                    break;
                }
                case 'd': {
                    moveArticle();
                    break;
                }
                case 'm': {
                    modifyArticle();
                    break;
                }
                default: {

                }
            }
        } while(choice != 'q');
    }

    /**
     * 
     * @throws RemoteException
     */
    private void addArticle() throws RemoteException {
        String name = con.readLine("Name: ");
        double price = Double.parseDouble(con.readLine("Price: "));
        boolean hasOptions = Boolean.parseBoolean(con.readLine("Has options (true to say yes): "));
        List<String> options = new Vector<String>();
        if (hasOptions) {
            String opt;
            while ( !(opt = con.readLine()).isEmpty() ) {
                options.add(opt);
            }
        }
        
        if (Boolean.parseBoolean(con.readLine("Do you want to proceed? (true to say yes): "))) {
            owner.addRMIArticle(
                    (hasOptions ?
                        new ArticleWithOptions(0, name, price, options) :
                        new Article(0, name, price)));
        }
    }

    /**
     *
     * @throws RemoteException
     */
    private void enableDisableArticle() throws RemoteException {
        int num = Integer.parseInt(
                con.readLine("Position of the article to enable/disable: "));
        Article art = owner.getArticlesList().getList().get(num);
        boolean disen = Boolean.parseBoolean(
                con.readLine(art.isEnabled() ?
                    "Do you want to disable it? (true to say yes)" :
                    "Do you want to enable it? (true to say yes)" ));
        if (disen) {
            owner.enableRMIArticle(art, disen);
        }
    }

    /**
     *
     */
    private void modifyArticle() {
        int num = Integer.parseInt(
                con.readLine("Position of the article to modify: "));
        Article art = owner.getArticlesList().getList().get(num);
        char choice = con.readLine(
                "Press:\n" +
                " - n - To modify the name\n" +
                " - c - For price\n").charAt(0);

        String name = art.getName();
        int id = art.getId();
        double price = art.getPrice();
        boolean enabled = art.isEnabled();
        List<String> options = new Vector<String>();
        if (art.hasOptions()) {
            options = ((ArticleWithOptions)art).getOptions();
        }

        switch (choice) {
            case 'n':
                name = con.readLine("Name [" + art.getName() + "]: ");
                break;
            case 'c':
                price = Double.parseDouble(
                        con.readLine("Price [" + art.getPrice() + "]: "));
                break;
        }
        Article newArt = (art.hasOptions() ?
            new ArticleWithOptions(id, name, price, options, enabled) :
            new Article(id, name, price, enabled));

        if (Boolean.parseBoolean(
                con.readLine("Do you want to proceed? (true to say yes): "))) {
//            owner.modify???
        }
    }

    /**
     * 
     * @throws RemoteException
     */
    private void moveArticle() throws RemoteException {
        int num = Integer.parseInt(
                con.readLine("Position of the article to move: "));
        Article art = owner.getArticlesList().getList().get(num);
        int n_num = Integer.parseInt(
                con.readLine("New position of the article " + art.getName() + ": "));

        if (Boolean.parseBoolean(
                con.readLine("Do you want to proceed? (true to say yes): "))) {
            owner.moveRMIArticle(art, n_num);
        }
    }
}
