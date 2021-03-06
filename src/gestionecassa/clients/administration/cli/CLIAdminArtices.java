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
import gestionecassa.ArticleGroup;
import gestionecassa.ArticleOption;
import gestionecassa.clients.administration.AdministrationAPI;
import gestionecassa.exceptions.DuplicateArticleException;
import gestionecassa.exceptions.NotExistingGroupException;
import java.io.Console;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author ben
 */
public class CLIAdminArtices {

    AdministrationAPI baseClient;

    Console con;

    /**
     * 
     * @param baseClient
     * @param con
     */
    public CLIAdminArtices(AdministrationAPI baseClient, Console con) {
        this.baseClient = baseClient;
        this.con = con;
    }

    void printMenuGroups() throws RemoteException {
        String menu = "You can now choose between these alternatives:\n" +
                " - s - Show\n" +
                " - c - Chose a group\n" +
                " - a - Add\n" +
                " - e - Disable/Enable\n" +
                " - d - Move\n" +
                " - m - Modify\n" +
                " - q - Quit\n" +
                "Choice: ";
        char choice;
        con.writer().println("\n"+baseClient.getArticlesList().getPrintableFormat());
        do {
            choice = con.readLine(menu).charAt(0);
            switch (choice) {
                case 's': {
                    con.writer().println("\n" +
                            baseClient.getArticlesList().getPrintableFormat() +
                            "\nPress Enter to continue..");
                    con.readLine();
                    break;
                }
                case 'a': {
                    break;
                }
                case 'c': {
                    int groupPos = Integer.parseInt(con.readLine("Group number: "));
                    printMenuArticles(baseClient.getArticlesList().getGroup(groupPos));
                    break;
                }
                case 'e': {
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

    /**
     * 
     * @throws RemoteException
     */
    void printMenuArticles(ArticleGroup group) throws RemoteException {
        String menu = "You can now choose between these alternatives:\n" +
                " - s - Show\n" +
                " - a - Add\n" +
                " - e - Disable/Enable\n" +
                " - d - Move\n" +
                " - m - Modify\n" +
                " - q - Quit\n" +
                "Choice: ";
        char choice;
        con.writer().println("\n"+group.getPrintableFormat());
        do {
            choice = con.readLine(menu).charAt(0);
            switch (choice) {
                case 's': {
                    con.writer().println("\n" +
                            baseClient.getArticlesList().getPrintableFormat() +
                            "\nPress Enter to continue..");
                    con.readLine();
                    break;
                }
                case 'a': {
                    addArticle(group);
                    break;
                }
                case 'e': {
                    enableDisableArticle(group);
                    break;
                }
//                case 'd': {
//                    moveArticle();
//                    break;
//                }
                case 'm': {
                    modifyArticle(group);
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
    private void addArticle(ArticleGroup group) throws RemoteException {
        String name = con.readLine("Name: ");
        double price = Double.parseDouble(con.readLine("Price: "));
        boolean hasOptions = Boolean.parseBoolean(con.readLine("Has options (true to say yes): "));
        List<ArticleOption> options = new ArrayList<ArticleOption>();
        int idOpt = 0;
        if (hasOptions) {
            String opt;
            while ( !(opt = con.readLine()).isEmpty() ) {
                options.add(new ArticleOption(idOpt, opt, true));
            }
        }
        
        if (Boolean.parseBoolean(con.readLine("Do you want to proceed? (true to say yes): "))) {
            try {
                baseClient.addRMIArticle(group.getId(),
                        hasOptions ?
                            new Article(0, name, price, true, options) :
                            new Article(0, name, price));
            } catch (DuplicateArticleException ex) {

            } catch (NotExistingGroupException ex) {
                
            }
        }
    }

    /**
     *
     * @throws RemoteException
     */
    private void enableDisableArticle(ArticleGroup group) throws RemoteException {
        int num = Integer.parseInt(
                con.readLine("Position of the article to enable/disable: "));
        Article art = baseClient.getArticlesList().getGroupsList()
                .get(group.getId()).getList().get(num);
        boolean disen = Boolean.parseBoolean(
                con.readLine(art.isEnabled() ?
                    "Do you want to disable it? (true to say yes)" :
                    "Do you want to enable it? (true to say yes)" ));
        if (disen) {
            baseClient.enableRMIArticle(art, disen);
        }
    }

    /**
     *
     */
    private void modifyArticle(ArticleGroup group) {
        int num = Integer.parseInt(
                con.readLine("Position of the article to modify: "));
        Article art = baseClient.getArticlesList().getGroupsList()
                .get(group.getId()).getList().get(num);
        char choice = con.readLine(
                "Press:\n" +
                " - n - To modify the name\n" +
                " - c - For price\n").charAt(0);

        String name = art.getName();
        int id = art.getId();
        double price = art.getPrice();
        boolean enabled = art.isEnabled();
        List<ArticleOption> options = new ArrayList<ArticleOption>();
        if (art.hasOptions()) {
            options = art.getOptions();
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
            new Article(id, name, price, enabled, options) :
            new Article(id, name, price, enabled));

        if (Boolean.parseBoolean(
                con.readLine("Do you want to proceed? (true to say yes): "))) {
//            baseClient.modify???
        }
    }

//    /**
//     *
//     * @throws RemoteException
//     */
//    private void moveArticle(ArticleGroup group) throws RemoteException {
//        int num = Integer.parseInt(
//                con.readLine("Position of the article to move: "));
//        Article art = baseClient.getArticlesList().getGroupsList()
//                .get(group.getId()).getList().get(num);
//        int n_num = Integer.parseInt(
//                con.readLine("New position of the article " + art.getName() + ": "));
//
//        if (Boolean.parseBoolean(
//                con.readLine("Do you want to proceed? (true to say yes): "))) {
//            baseClient.moveRMIArticle(art, n_num);
//        }
//    }
}
