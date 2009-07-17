/*
 * PostgreSQLDataBackend.java
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

package gestionecassa.server.datamanager.backends;

import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.ArticleWithOptions;
import gestionecassa.Cassiere;
import gestionecassa.Log;
import gestionecassa.ordine.EntrySingleArticle;
import gestionecassa.ordine.EntrySingleArticleWithOption;
import gestionecassa.ordine.EntrySingleOption;
import gestionecassa.ordine.Order;
import gestionecassa.server.datamanager.BackendAPI_2;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class PostgreSQLDataBackend implements BackendAPI_2 {

    /**
     * 
     */
    Logger logger;

    /**
     *
     */
    Connection db;

    /**
     *
     */
    Map<String,String> tables;

    /**
     *
     */
    public PostgreSQLDataBackend() {
        logger = Log.GESTIONECASSA_SERVER_DATAMANAGER_DB;

        tables = new ConcurrentSkipListMap<String, String>();
        tables.put("01_cassieres",
                "id_cassiere serial PRIMARY KEY, " +
                "username text UNIQUE, " +
                "password text, " +
                "enabled boolean ");
        tables.put("02_viewers",
                "id_viewer serial PRIMARY KEY, " +
                "username text UNIQUE, " +
                "password text, " +
                "enabled boolean ");
        tables.put("03_admins",
                "id_admin serial PRIMARY KEY, " +
                "username text UNIQUE, " +
                "password text, " +
                "enabled boolean ");
//        tables.put("04_events",
//                "id_event serial PRIMARY KEY, " +
//                "name text UNIQUE, " +
//                "start_date date UNIQUE, " +
//                "stop_date date UNIQUE ");
//        tables.put("05_date_event",
//                "id_date_event serial PRIMARY KEY, " +
//                "id_event integer REFERENCES events, " +
//                "title text, " +
//                "data date UNIQUE");
        tables.put("06_orders",
                "id_order serial PRIMARY KEY, " +
                "time_order timestamp, " +
                "hostname text, " +
                "id_cassiere integer REFERENCES cassieres ON DELETE RESTRICT, " +
                "price_tot numeric ");
        tables.put("07_articles",
                "id_article serial PRIMARY KEY, " +
                "name text UNIQUE, " +
                "enabled boolean, " +
                "opzioni boolean, " +
                "price numeric, " +
                "num_pos integer NOT NULL ");
        tables.put("08_options",
                "id_option serial PRIMARY KEY, " +
                "id_article integer REFERENCES articles ON DELETE CASCADE, " +
                "name text ");
        tables.put("09_articles_in_order",
                "id_art_in_ord serial PRIMARY KEY, " +
                "id_order integer REFERENCES orders ON DELETE CASCADE, " +
                "id_article integer REFERENCES articles ON DELETE RESTRICT, " +
                "num_tot integer ");
        tables.put("10_opts_of_article_in_order",
                "id_art_in_ord integer REFERENCES articles_in_order ON DELETE CASCADE, " +
                "id_option integer REFERENCES options ON DELETE RESTRICT, " +
                "num_parz integer ");
    }

    /**
     *
     * @throws IOException
     */
    public void init() throws IOException {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/GCDB";
            String username = "gestionecassa";
            String password = "GestioneCassa";
            db = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            logger.error("errore connessione db", ex);
            throw new IOException(ex);
        } catch (ClassNotFoundException ex) {
            logger.error("classe non trovata", ex);
            throw new IOException(ex);
        }
        
        String query =
                "SELECT table_name" +
                "   FROM information_schema.tables" +
                "   WHERE table_schema='public'" +
                "       AND table_type='BASE TABLE';";
        try {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            Set<String> tabelleDB = new ConcurrentSkipListSet<String>();
            while (rs.next()) {
                tabelleDB.add(rs.getString("table_name"));
            }
            rs.close();
            
            for (String table_ref : tables.keySet()) {
                String table_name = table_ref.substring(3);
                if (!tabelleDB.contains(table_name)) {
                    logger.warn(table_name + " is not in the table list. Creating " +
                            " blank one");
                    st.execute("CREATE TABLE " + table_name + " ( " +
                                    tables.get(table_ref) + " );");
                }
            }
            st.close();

        } catch (SQLException ex) {
            logger.error("Errore", ex);
            throw new IOException(ex);
        }
    }

    //-----------------//

    /**
     *
     * @param article
     * @param position
     * @throws IOException
     */
    public void addArticleToListAt(Article article, int position) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param article
     * @throws IOException
     */
    public void addArticleToList(Article article) throws IOException {
        String insQuery =
                "INSERT INTO articles (name, price, enabled, options)" +
                "VALUES ('" + article.getNome() + "', '" +
                    article.getPrezzo() + "', " + article.isEnabled() + ", '" +
                    article.hasOptions() + "')";
        genericAdder(insQuery);
        
        try {
            Statement stIns = db.createStatement();
            String currValQuery =
                    "SELECT currval( 'articles_id_article_seq' );";
            try {
                ResultSet keys = stIns.executeQuery(currValQuery);
                keys.next();
                int idArticle = keys.getInt("currval");
                if (article.hasOptions()) {
                    List<String> opts = ((ArticleWithOptions)article).getOpzioni();
                    String insOptsQuery =
                            "INSERT INTO options (id_article, name) " +
                            "VALUES ";
                    for (Iterator<String> it = opts.iterator(); it.hasNext();) {
                        String option = it.next();
                        insOptsQuery += "('" + idArticle +
                                "', '" + option + "')" +
                                (it.hasNext() ? "," : ";");
                    }

                    genericAdder(insOptsQuery);
                }
            } catch (SQLException ex) {
                logger.error("Errore con la query: " + currValQuery, ex);
                throw new IOException(ex);
            } finally {
                stIns.close();
            }
        } catch (SQLException ex) {
            logger.error("Errore nella counicazione col DB", ex);
            throw new IOException(ex);
        }
    }

    /**
     * 
     * @param article
     * @param enable
     * 
     * @throws IOException
     */
    public void enableArticleFromList(Article article, boolean enable) throws IOException {
        String query =
                "SELECT enabled" +
                "   FROM articles" +
                "   WHERE name = '" + article.getNome() + "';";
        genericEnabler(query, enable);
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    public List<Article> loadArticlesList() throws IOException {

        List<Article> outout = new Vector<Article>();
        String query =
                "SELECT *" +
                "   FROM articles" +
                "   ORDER BY num_pos;";
        try {
            Statement st = db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    int position = rs.getInt("num_pos");
                    int idArticle = rs.getInt("id_article");
                    
                    outout.add(position > outout.size()
                                    ? outout.size()
                                    : position,
                               rs.getBoolean("opzioni")
                                    ? new ArticleWithOptions(idArticle,
                                            rs.getString("name"),
                                            rs.getDouble("price"),
                                            getOptionsByArticleID(idArticle),
                                            rs.getBoolean("enabled"))
                                    : new Article(idArticle,
                                            rs.getString("name"),
                                            rs.getDouble("price"),
                                            rs.getBoolean("enabled"))
                               );
                }
            } catch (SQLException ex) {
                logger.error("Errore con la query: " + query, ex);
                throw new IOException(ex);
            } finally {
                st.close();
            }

        } catch (SQLException ex) {
            logger.error("Errore nella counicazione col DB", ex);
            throw new IOException(ex);
        }
        return outout;
    }

    private List<String> getOptionsByArticleID(int art_id) throws IOException {
        List<String> options = new LinkedList<String>();
        String queryOpts =
                "SELECT name" +
                "   FROM options" +
                "   WHERE id_article = '" + art_id + "';";
        try {
            Statement stOpts = db.createStatement();

            try {
                ResultSet rsOpts = stOpts.executeQuery(queryOpts);
                while (rsOpts.next()) {
                    options.add(rsOpts.getString("name"));
                }
            } catch (SQLException ex) {
                logger.error("Errore con la query: " + queryOpts, ex);
                throw new IOException(ex);
            } finally {
                stOpts.close();
            }
        } catch (SQLException ex) {
            logger.error("Errore nella counicazione col DB", ex);
            throw new IOException(ex);
        }
        return options;
    }

    //-----------------//

    /**
     * 
     * @param admin
     * @throws IOException
     */
    public void addAdmin(Admin admin) throws IOException {
        String insQuery =
                "INSERT INTO admins (username, password, enabled)" +
                "VALUES ('" + admin.getUsername() + "', '" +
                    admin.getPassword() + "', " +
                    admin.isEnabled() + " )";
        genericAdder(insQuery);
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public List<Admin> loadAdminsList() throws IOException {

        String query =
                "SELECT *" +
                "   FROM admins;";
        List<Admin> outout = new LinkedList<Admin>();
        try {
            Statement st = db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    outout.add(new Admin(rs.getInt("id_admin"),
                                         rs.getString("username"),
                                         rs.getString("password"),
                                         rs.getBoolean("enabled")));
                }
                rs.close();
            } catch (SQLException ex) {
                logger.error("Errore con la query: " + query, ex);
                throw new IOException(ex);
            } finally {
                st.close();
            }

        } catch (SQLException ex) {
            logger.error("Errore nella comunicazione col DB", ex);
            throw new IOException(ex);
        }
        return outout;
    }

    /**
     *
     * @param admin
     * @param enable
     * @throws IOException
     */
    public void enableAdmin(Admin admin, boolean enable) throws IOException {
        String query =
                "SELECT enabled" +
                "   FROM admins" +
                "   WHERE username = '" + admin.getUsername() + "';";
        genericEnabler(query, enable);
    }

    /**
     * 
     * @param cassiere
     * @throws IOException
     */
    public void addCassiere(Cassiere cassiere) throws IOException {
        String insQuery =
                "INSERT INTO admins (username, password, enabled)" +
                "VALUES ('" + cassiere.getUsername() + "', '" +
                    cassiere.getPassword() + "', " +
                    cassiere.isEnabled() + " )";
        genericAdder(insQuery);
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    public List<Cassiere> loadCassiereList() throws IOException {

        String query =
                "SELECT *" +
                "   FROM cassieres;";
        List<Cassiere> outout = new LinkedList<Cassiere>();
        try {
            Statement st = db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                while (rs.next()) {
                    outout.add(new Cassiere(rs.getInt("id_cassiere"),
                                         rs.getString("username"),
                                         rs.getString("password"),
                                         rs.getBoolean("enabled")));
                }
                rs.close();
            } catch (SQLException ex) {
                logger.error("Errore con la query: " + query, ex);
                throw new IOException(ex);
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            logger.error("Errore di comunicazione col DB", ex);
            throw new IOException(ex);
        }
        return outout;
    }

    /**
     *
     * @param cassiere
     * @param enable
     * @throws IOException
     */
    public void enableCassiere(Cassiere cassiere, boolean enable) throws IOException {
        String query =
                "SELECT enabled" +
                "   FROM cassieres" +
                "   WHERE username = '" + cassiere.getUsername() + "';";
        genericEnabler(query, enable);
    }
    
    //-----------------//

    public void addNewOrder(String id, Order order) throws IOException {
        String username = id.substring(0, id.lastIndexOf('@'));
        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").format(order.getData());

        String cassiereQuery =
                "SELECT id_cassiere, enabled" +
                "   FROM cassieres" +
                "   WHERE username = '" + username + "'";
        try {
            
            Statement stCass = db.createStatement();
            ResultSet rsCass = stCass.executeQuery(cassiereQuery);
            
            if (rsCass.next() && rsCass.getBoolean("enabled")) {

                String aggiuntaQuery =
                    "INSERT INTO orders (time_order, id_cassiere, " +
                            "price_tot, hostname)" +
                    "VALUES ('" + timestamp + "', '" +
                        rsCass.getInt("id_cassiere") + "', '" +
                        order.getTotalPrice() + "', '"+
                        order.getHostname() + "' )";

                Statement stOrder = db.createStatement();
                stOrder.execute(aggiuntaQuery);
                ResultSet key = stOrder.executeQuery("SELECT currval( 'orders_id_order_seq' );");
                key.next();

                Statement stAddArticleEntry = db.createStatement();
                for (EntrySingleArticle entry : order.getListaBeni()) {

                    String addEntry =
                        "INSERT INTO articles_in_order (id_article, id_order, " +
                            "num_tot )" +
                        "VALUES ('" + entry.bene.getId() + "', '" +
                            key.getInt("currval") + "', '" +
                            entry.numTot + "' )";
                    stAddArticleEntry.execute(addEntry);

                    if (entry.bene.hasOptions()) {
                        ResultSet artKey = 
                                stAddArticleEntry.executeQuery("SELECT " +
                                    "currval('articles_in_order_id_art_in_ord_seq')");
                        artKey.next();

                        EntrySingleArticleWithOption entryOpts =
                                (EntrySingleArticleWithOption)entry;
                        List<EntrySingleOption> opts = entryOpts.numParziale;
                        String addOpt =
                            "INSERT INTO opts_of_article_in_order (" +
                                "id_art_in_ord, id_option, num_parz )" +
                            "VALUES ";
                        Statement optionSt = db.createStatement();

                        for (Iterator<EntrySingleOption> iter = opts.iterator();
                                iter.hasNext();)
                        {
                            EntrySingleOption option = iter.next();
                            ResultSet optRs = optionSt.executeQuery(
                                    "SELECT id_option" +
                                    "   FROM options" +
                                    "   WHERE name = '"+option.nomeOpz+"';");
                            optRs.next();

                            addOpt += "('" + artKey.getInt("currval") +
                                    "', '" + optRs.getInt("id_option") +
                                    "', '" + option.numParz + "')" +
                                    (iter.hasNext() ? "," : ";");
                            optRs.close();
                        }

                        optionSt.executeUpdate(addOpt);
                        optionSt.close();
                        artKey.close();
                    }
                }
                key.close();
                stOrder.close();
            } else {
                rsCass.close();
                stCass.close();
                throw new IOException("The cassiere is not on the list, " +
                        "or is disabled");
            }

            rsCass.close();
            stCass.close();
        } catch (SQLException ex) {
            logger.error("Errore", ex);
            throw new IOException(ex);
        }
    }

    public void delLastOrder(String id) throws IOException {
        String username = id.substring(0, id.lastIndexOf('@'));
        String rawtst = id.substring(id.lastIndexOf('@')+1);
        String _date = rawtst.substring(0, rawtst.lastIndexOf('_'));
        String _time = rawtst.substring(rawtst.lastIndexOf('_')+1).replace('-', ':');
        String timestampSession = _date + " " + _time;

        System.out.println("delLastOrder, Username: " + username);
        System.out.println("delLastOrder, TimestampSession: " + timestampSession);

        String cassiereOrderQuery =
                "SELECT t1.id_cassiere, t2.id_order" + // , max(time_order)
                "   FROM cassieres AS t1, orders AS t2" +
                "   WHERE t1.id_cassiere = t2.id_cassiere" +
                "       AND t1.username = '" + username + "'" +
                "       AND t2.time_order > '" + timestampSession + "'" +
                "   ORDER BY t2.time_order DESC;";
        /* Molto scomodo prendere il primo di tanti, magari è meglio rpenderlo
         * direttamente con una query di questo tipo:
         *
         * SELECT t1.id_cassiere, t2.id_order
         *    FROM cassieres AS t1, orders AS t2
         *    WHERE t1.id_cassiere = t2.id_cassiere
         *        AND t1.username = 'bene'
         *        AND t2.time_order =
         *          (   SELECT max( time_order )
         *                  FROM orders
         *                  WHERE id_cassiere = t1.id_cassiere
         *                      AND time_order > '2009-07-17 20:42:00' )
         *    ORDER BY t2.time_order DESC;
         */
        try {
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery(cassiereOrderQuery);
            rs.next(); // not possible to query if no orders present

            String deleteQuery =
                    "DELETE FROM orders" +
                    "   WHERE id_order = '" + rs.getInt("id_order") + "';";

            try {
                st.executeUpdate(deleteQuery);

                st.close();
            } catch (SQLException ex1) {
                logger.error("Errore con la query: " + deleteQuery, ex1);
                throw new IOException(ex1);
            }
            
        } catch (SQLException ex) {
            logger.error("Errore con la query: " + cassiereOrderQuery, ex);
            throw new IOException(ex);
        }
    }
    
    //-------------------//
    // Utility functions
    //-------------------//

    private void genericEnabler(String query, boolean enable) throws IOException {
        try {
            Statement st = db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);
                rs.next();
                rs.updateBoolean("enabled", enable);
                rs.close();
            } catch (SQLException ex) {
                logger.error("Errore con query: " + query + " o con update", ex);
                throw new IOException(ex);
            } finally {
                st.close();
            }
            st.close();
        } catch (SQLException ex) {
            logger.error("Errore", ex);
            throw new IOException(ex);
        }
    }

    private void genericAdder(String query) throws IOException {
        try {
            Statement stIns = db.createStatement();
            try {
                stIns.executeUpdate(query);
            } catch (SQLException ex) {
                logger.error("Errore con la query: " + query, ex);
                throw new IOException(ex);
            } finally {
                stIns.close();
            }
        } catch (SQLException ex) {
            logger.error("Errore nella comunicazione col DB", ex);
            throw new IOException(ex);
        }
    }
}
