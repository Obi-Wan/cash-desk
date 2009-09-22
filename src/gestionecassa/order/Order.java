/*
 * Order.java
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

package gestionecassa.order;

import gestionecassa.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class Order implements Serializable, Comparable<Order> {

    /**
     * Data/ora in cui è stato effettuato l'ordine
     */
    Date date;

    /**
     * Username of the Cassiere that committed the order
     */
    String username;

    /**
     * Place where the order was committed
     */
    String hostname;

    /**
     * List of articles for this order
     */
    List<EntryArticleGroup> groupsList;

    /**
     *
     */
    double totalPrice;

    /**
     * Table at which the order has been committed
     */
    int table;

    /**
     * Default constructor (well, at most :) )
     *
     * @param username
     * @param hostname
     * @param table 
     */
    public Order(String username, String hostname, int table) {
        this( new Date(), new String(username), new String(hostname), table,
                new ArrayList<EntryArticleGroup>());
    }

    /**
     * Explicit constructor of the date
     *
     * @param date
     * @param username
     * @param hostname
     * @param table
     */
    public Order(Date data, String username, String hostname, int table) {
        this( new Date(data.getTime()), new String(username),
                new String(hostname), table,
                new ArrayList<EntryArticleGroup>());
    }

    /**
     * Completely explicit constructor (it's not good to use it.)
     *
     * @param table
     * @param date
     * @param username
     * @param hostname
     * @param groups
     */
    private Order( Date date, String username, String hostname, int table,
            List<EntryArticleGroup> groups) {
        this.date = date;
        this.groupsList = groups;
        this.username = username;
        this.hostname = hostname;
        this.table = table;
        this.totalPrice = 0;
    }

    /**
     * Copy constructor
     * 
     * @param order
     */
    public Order(Order order) {
        this.date = new Date(order.date.getTime());
        this.hostname = new String(order.hostname);
        this.username = new String(order.username);
        this.totalPrice = order.totalPrice;
        this.groupsList = new ArrayList<EntryArticleGroup>(order.groupsList);
    }

    /**
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * 
     * @return
     */
    public List<BaseEntry<Article>> getArticlesSold() {
        List<BaseEntry<Article>> list = new Vector<BaseEntry<Article>>();
        for (EntryArticleGroup group : groupsList) {
            list.addAll(group.articles);
        }
        return list;
    }

    /**
     * 
     * @return
     */
    public List<EntryArticleGroup> getGroups() {
        return groupsList;
    }

    /**
     * 
     * @return
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     *
     * @return
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * 
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @return
     */
    public int getTable() {
        return table;
    }

//    /**
//     * Adder helper
//     *
//     * @param article
//     * @param numTot
//     */
//    public void addArticle(Article article, int numTot) {
//        groupsList.add(new BaseEntry<Article>(article, numTot));
//    }

//    /**
//     * Adder helper
//     *
//     * @param article
//     * @param numTot
//     * @param partialsList
//     */
//    public void addArticleWithOptions(ArticleWithOptions article, int numTot,
//            int progressive, List<BaseEntry<String>> partialsList) {
//        groupsList.add(new EntrySingleArticleWithOption(article, numTot, progressive,
//                partialsList));
//    }

//    /**
//     *
//     * @param totalPrice
//     */
//    public void setTotalPrice(double totalPrice) {
//        this.totalPrice = totalPrice;
//    }

    /**
     *
     * @param group
     */
    public void addGroup(EntryArticleGroup group) {
        groupsList.add(group);
        totalPrice += group.partialPrice;
    }

    /**
     *
     */
    public void refreshTotalPrice() {
        totalPrice = 0;
        for (EntryArticleGroup group : groupsList) {
            totalPrice += group.partialPrice;
        }
    }

    /**
     * 
     * @param o
     * @return
     */
    @Override
    public int compareTo(Order o) {
        return this.date.compareTo(o.date);
    }
}
