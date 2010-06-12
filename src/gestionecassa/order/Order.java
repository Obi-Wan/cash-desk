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
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Stores all the information about an order emitted.
 *
 * @author ben
 */
public class Order implements Serializable, Comparable<Order> {

    /**
     * Time at which the order was committed
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
     * Total price of the order, that will be payed.
     */
    double totalPrice;

    /**
     * Table at which the order has been committed
     */
    int table;

    /**
     *
     */
    final private int[] listSignature;

    /**
     * Default constructor (well, at most :) )
     *
     * @param username Username of the <code>Cassiere</code> that emitted this
     * @param hostname <code>Cassa</code> at which the order was emitted
     * @param table Table where to serve the order
     * @param listSign Signature of the list from which the groups are taken
     */
    public Order(String username, String hostname, int table, int[] listSign) {
        this( new Date(), new String(username), new String(hostname), table,
                new Vector<EntryArticleGroup>(), listSign);
    }

    /**
     * Explicit constructor of the date
     *
     * @param date Time of creation of the order
     * @param username Username of the <code>Cassiere</code> that emitted this
     * @param hostname <code>Cassa</code> at which the order was emitted
     * @param table Table where to serve the order
     * @param listSign Signature of the list from which the groups are taken
     */
    public Order(Date date, String username, String hostname, int table,
                int[] listSign) {
        this( new Date(date.getTime()), new String(username),
                new String(hostname), table,
                new Vector<EntryArticleGroup>(), listSign);
    }

    /**
     * Completely explicit constructor
     *
     * Note that it's not good to use this constructor, indeed it was marked
     * private. Groups are usualy added later to the order.
     *
     * @param date Time of creation of the order
     * @param username Username of the <code>Cassiere</code> that emitted this
     * @param hostname <code>Cassa</code> at which the order was emitted
     * @param table Table where to serve the order
     * @param groups List of groups that contain <code>Article</code>s sold
     * @param listSign Signature of the list from which the groups are taken
     */
    private Order( Date date, String username, String hostname, int table,
            List<EntryArticleGroup> groups, int[] listSign) {
        this.date = date;
        this.groupsList = groups;
        this.username = username;
        this.hostname = hostname;
        this.table = table;
        this.totalPrice = 0;
        this.listSignature = listSign;
    }

    /**
     * Copy constructor
     * 
     * @param order The order to copy from
     */
    public Order(Order order) {
        this.date = new Date(order.date.getTime());
        this.hostname = new String(order.hostname);
        this.username = new String(order.username);
        this.totalPrice = order.totalPrice;
        this.groupsList = new Vector<EntryArticleGroup>(order.groupsList);
        this.listSignature = order.listSignature;
    }

    /**
     * Getter for the date
     *
     * @return Time at which the order was emitted
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for just a list of all the <code>Article</code>s sold, without
     * groups structure. Note that this is not good since it completely
     * discards the structure of groups.
     *
     * It's reasonably used just in the Database implementation, since groups
     * consistency is preserved by the DB structure.
     * Otherwise it's deprecated.
     * 
     * @return List of all the <code>Article</code>s sold
     */
    @Deprecated
    public List<BaseEntry<Article>> getArticlesSold() {
        List<BaseEntry<Article>> list = new Vector<BaseEntry<Article>>();
        for (EntryArticleGroup group : groupsList) {
            list.addAll(group.articles);
        }
        return list;
    }

    /**
     * Getter for the list of groups
     * 
     * @return List of <code>ArticleGroup</code>s that contain <code>Article</code>s sold
     */
    public List<EntryArticleGroup> getGroups() {
        return groupsList;
    }

    /**
     * Getter for the price of the order
     * 
     * @return The price that needs to be payed for this order.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Getter for the hostname of <code>Cassa</code> of creation of this order.
     *
     * @return A string containing the Hostname.
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Getter for the username of the <code>Cassiere</code> who emitted this order.
     *
     * @return A string containing the Username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the number of the table at which the order needs to be served
     * 
     * @return Numerical value of the table
     */
    public int getTable() {
        return table;
    }

    /**
     * Adds a new <code>EntryArticleGroup</code> to the <code>Order</code>.
     *
     * @param group New <code>ArticleGroup</code> entry to be added.
     */
    public void addGroup(EntryArticleGroup group) {
        groupsList.add(group);
        totalPrice += group.partialPrice;
    }

    /**
     * Recalculates the total price, just in case it was modified inconsistently
     */
    public void refreshTotalPrice() {
        totalPrice = 0;
        for (EntryArticleGroup group : groupsList) {
            totalPrice += group.partialPrice;
        }
    }

    /**
     * Compares two <code>Order</code>s. I don't mind about the result: it's
     * just used to order them in a data structure.
     * 
     * @param o Order to compare to
     *
     * @return -1, 0 or 1 just to distinguish them.
     */
    @Override
    public int compareTo(Order o) {
        final int dateComp = this.date.compareTo(o.date);
        return (dateComp == 0) ? this.username.compareTo(o.username) : dateComp;
    }

    /**
     * Getts the signature of the list used to create the order
     * @return an array of integers containing the signature
     */
    public int[] getListSignature() {
        return listSignature;
    }
}
