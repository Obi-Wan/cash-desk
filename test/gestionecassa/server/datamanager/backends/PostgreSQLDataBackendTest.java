/*
 * PostgreSQLDataBackendTest.java
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
import gestionecassa.EventDate;
import gestionecassa.OrganizedEvent;
import gestionecassa.Person;
import gestionecassa.order.EntrySingleArticleWithOption;
import gestionecassa.order.EntrySingleOption;
import gestionecassa.order.Order;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentSkipListSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ben
 */
public class PostgreSQLDataBackendTest {

    String dbUrl;

    static PostgreSQLDataBackend backend;

    Admin testAdmin;

    Cassiere testCassiere;

    List<Article> articles;

    OrganizedEvent event;

    List<EventDate> dates;

    public PostgreSQLDataBackendTest() {
        dbUrl = "jdbc:postgresql://localhost:5432/TestGCDB";
        testAdmin = new Admin(1, "admin", "password");
        testCassiere = new Cassiere(1, "bene", "male");

        articles = new Vector<Article>();
        List<String> options = new Vector<String>();
        options.add("corta");
        options.add("media");
        options.add("lunga");
        articles.add(new Article(articles.size()+1, "gatto", 5.5));
        articles.add(new Article(articles.size()+1, "cane", 10));
        articles.add(new ArticleWithOptions(articles.size()+1, "falce", 4.25, options));
        articles.add(new Article(articles.size()+1, "vanga", 0.2));

        dates = new LinkedList<EventDate>();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startDate, endDate;
        try {
            startDate = date.parse("2009-09-03 12:00:00");
            endDate = date.parse("2009-09-04 11:59:59");
        } catch (ParseException ex) {
            startDate = new Date();
            endDate = new Date();
            System.out.println("Mancata inizializzazione delle date: " +
                    ex.getLocalizedMessage());
        }
        dates.add(new EventDate("i marci", startDate.getTime(), endDate.getTime()));
        try {
            startDate = date.parse("2009-09-04 12:00:00");
            endDate = date.parse("2009-09-05 11:59:59");
        } catch (ParseException ex) {
            startDate = new Date();
            endDate = new Date();
            System.out.println("Mancata inizializzazione delle date: " +
                    ex.getLocalizedMessage());
        }
        dates.add(new EventDate("i brutti", startDate.getTime(), endDate.getTime()));
        try {
            startDate = date.parse("2009-09-05 12:00:00");
            endDate = date.parse("2009-09-06 11:59:59");
        } catch (ParseException ex) {
            startDate = new Date();
            endDate = new Date();
            System.out.println("Mancata inizializzazione delle date: " +
                    ex.getLocalizedMessage());
        }
        dates.add(new EventDate("gli orendi", startDate.getTime(), endDate.getTime()));

        event = new OrganizedEvent("Festa dell oscenita");
        event.datesList.addAll(dates);

        try {
            startDate = date.parse("2009-09-06 12:00:00");
            endDate = date.parse("2009-09-07 11:59:59");
        } catch (ParseException ex) {
            startDate = new Date();
            endDate = new Date();
            System.out.println("Mancata inizializzazione delle date: " +
                    ex.getLocalizedMessage());
        }
        dates.add(new EventDate("gli oribbili", startDate.getTime(), endDate.getTime()));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        backend = new PostgreSQLDataBackend();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        for (String table_ref : backend.tables.keySet()) {
            String table_name = table_ref.substring(3);
            String query = "DROP TABLE " + table_name + " CASCADE;";
            try {
                Statement st = backend.db.createStatement();
                try {
                    st.execute(query);
                } catch (SQLException ex) {
                    fail("Failed in Cleaning the DB: ");
                    ex.printStackTrace();
                } finally {
                    st.close();
                }
            } catch (SQLException ex) {
                fail("Failed in connecting to the DB");
            }
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testInit() throws Exception {
        System.out.println("init");
        backend.init(dbUrl);

        String query =  "SELECT table_name" +
                        "   FROM information_schema.tables" +
                        "   WHERE table_schema='public'" +
                        "       AND table_type='BASE TABLE';";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                Set<String> tabelleDB = new ConcurrentSkipListSet<String>();
                while (rs.next()) {
                    tabelleDB.add(rs.getString("table_name"));
                }
                rs.close();

                for (String table_ref : backend.tables.keySet()) {
                    String table_name = table_ref.substring(3);
                    if (!tabelleDB.contains(table_name)) {
                        fail("init did not create table: " + table_name);
                    }
                }
            } catch (SQLException ex) {
                fail("Failed in interrogating the DB");
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }
    }

    /**
     * Test of addArticleToList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddArticleToList() throws Exception {
        System.out.println("addArticleToList");
        
        Article temp = articles.get(0);

        backend.addArticleToList(temp);

        String query = "SELECT * FROM articles WHERE name = '"+
                temp.getName() + "'";
        testArticlePresence(query,temp);

        temp = articles.get(2);

        backend.addArticleToList(temp);

        query = "SELECT * FROM articles WHERE name = '" + temp.getName() + "'";
        testArticlePresence(query,temp);

        temp = articles.get(1);

        backend.addArticleToList(temp);

        query = "SELECT * FROM articles WHERE name = '" + temp.getName() + "'";
        testArticlePresence(query,temp);
    }

    /**
     * Test of addArticleToListAt method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddArticleToListAt() throws Exception {
        System.out.println("addArticleToListAt");

        Article temp = articles.get(3);

        backend.addArticleToListAt(temp,2);

        String query = "SELECT * FROM articles WHERE name = '"+
                temp.getName() + "'";
        testArticlePresence(query,temp);
    }

    /**
     * Test of moveArticleAt method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testMoveArticleAt() throws Exception {
        System.out.println("moveArticleAt");
    }

    /**
     * Test of loadArticlesList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testLoadArticlesList() throws Exception {
        System.out.println("loadArticlesList");

        List<Article> articleList = backend.loadArticlesList();

        assertNotNull(articleList);
        assertEquals(articleList.size(), 4);
        // gatto
        assertEquals(articleList.get(0), articles.get(0));
        // falce
        assertEquals(articleList.get(1), articles.get(2));
        assertEquals(articleList.get(1).getId(), 2);
        // vanga
        assertEquals(articleList.get(2), articles.get(3));
        assertEquals(articleList.get(2).getId(), 4);
        // cane
        assertEquals(articleList.get(3), articles.get(1));
        assertEquals(articleList.get(3).getId(), 3);
    }

    /**
     * Test of enableArticleFromList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testEnableArticleFromList() throws Exception {
        System.out.println("enableArticleFromList");
        
        Article temp = articles.get(0);

        backend.enableArticleFromList(temp, false);

        List<Article> articleList = backend.loadArticlesList();

        assertTrue(!articleList.get(0).isEnabled());

        backend.enableArticleFromList(temp, true);

        articleList = backend.loadArticlesList();

        assertTrue(articleList.get(0).isEnabled());
    }

    /**
     * Test of addAdmin method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddAdmin() throws Exception {
        System.out.println("addAdmin");

        try {
            backend.addAdmin(testAdmin);
        } catch (IOException ex) {
            fail("non sono riuscito ad aggiungere l'admin: " + ex.getMessage());
        }

        String query = "SELECT * FROM admins WHERE username = 'admin'";
        testPersonPresence(query,testAdmin);
    }

    /**
     * Test of loadAdminsList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testLoadAdminsList() throws Exception {
        System.out.println("loadAdminsList");

        List<Admin> adminList = backend.loadAdminsList();

        assertNotNull(adminList);
        assertEquals(adminList.size(), 1);
        assertEquals(adminList.get(0), testAdmin);
    }

    /**
     * Test of enableAdmin method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testEnableAdmin() throws Exception {
        System.out.println("enableAdmin");

        backend.enableAdmin(testAdmin, false);

        List<Admin> adminList = backend.loadAdminsList();

        assertTrue(!adminList.get(0).isEnabled());

        backend.enableAdmin(testAdmin, true);

        adminList = backend.loadAdminsList();

        assertTrue(adminList.get(0).isEnabled());
    }

    /**
     * Test of addCassiere method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddCassiere() {
        System.out.println("addCassiere");

        try {
            backend.addCassiere(testCassiere);
        } catch (IOException ex) {
            fail("non sono riuscito ad aggiungere il cassiere: " + ex.getMessage());
        }

        String query = "SELECT * FROM cassieres WHERE username = 'bene'";
        testPersonPresence(query,testCassiere);
    }

    /**
     * Test of loadCassiereList method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testLoadCassiereList() throws Exception {
        System.out.println("loadCassiereList");

        List<Cassiere> cassiereList = backend.loadCassiereList();

        assertNotNull(cassiereList);
        assertEquals(cassiereList.size(), 1);
        assertEquals(cassiereList.get(0), testCassiere);
    }

    /**
     * Test of enableCassiere method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testEnableCassiere() throws Exception {
        System.out.println("enableCassiere");

        backend.enableCassiere(testCassiere, false);

        List<Cassiere> cassiereList = backend.loadCassiereList();

        assertTrue(!cassiereList.get(0).isEnabled());

        backend.enableCassiere(testCassiere, true);

        cassiereList = backend.loadCassiereList();

        assertTrue(cassiereList.get(0).isEnabled());
    }

    /**
     * Test of addOrganizedEvent method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddOrganizedEvent() throws Exception {
        System.out.println("addOrganizedEvent");
        
        backend.addOrganizedEvent(event);
    }

    /**
     * Test of getOrganizedEvents method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testGetOrganizedEvents() throws Exception {
        System.out.println("getOrganizedEvents");

        List<OrganizedEvent> events = backend.getOrganizedEvents();
        assertNotNull(events);
        assertTrue(events.size() == 1);
        assertEquals(events.get(0), event);
        assertEquals(events.get(0).datesList, event.datesList);
    }

    /**
     * Test of addDateToOrgEvent method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddDateToOrgEvent() throws Exception {
        System.out.println("addDateToOrgEvent");

        backend.addDateToOrgEvent(dates.get(dates.size()-1), event.name);
    }

    /**
     * Test of getDatesOfOrgEvent method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testGetDatesOfOrgEvent() throws Exception {
        System.out.println("getDatesOfOrgEvent");

        List<EventDate> evDates = backend.getDatesOfOrgEvent(event.name);
        assertNotNull(evDates);
        assertTrue(evDates.size() == 4);
        assertEquals(evDates, dates);
    }

    /**
     * Test of addNewOrder method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddNewOrder() throws Exception {
        System.out.println("addNewOrder");

        articles = backend.loadArticlesList();

        Order tempOrder = new Order(testCassiere.getUsername(), "hell");
        tempOrder.addArticle(articles.get(0), 3);
        tempOrder.addArticle(articles.get(3), 2);
        
        backend.addNewOrder(tempOrder);

        // verify presence
        String query =  "SELECT c.username AS username, o.time_order AS time, " +
                            "o.price_tot AS price" +
                        "   FROM orders AS o, cassieres AS c" +
                        "   WHERE o.id_cassiere = c.id_cassiere;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);
                
                assertTrue(rs.next());
                assertTrue(rs.isLast());

                assertEquals(tempOrder.getUsername(), rs.getString("username"));
                assertEquals(tempOrder.getTotalPrice(), rs.getDouble("price"),0);
                assertEquals(new Timestamp(tempOrder.getData().getTime()),
                        rs.getTimestamp("time"));
                
            } catch (SQLException ex) {
                fail("Failed in interrogating the DB");
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }

        // verify presence
        query = "SELECT c.username AS u_name, a.name AS a_name, " +
                    "aio.num_tot AS num_tot, a.price AS price" +
                "   FROM cassieres AS c, orders AS o, articles AS a, " +
                    "articles_in_order AS aio" +
                "   WHERE o.id_order = aio.id_order" +
                "       AND aio.id_article = a.id_article" +
                "       AND c.id_cassiere = o.id_cassiere;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());
                assertTrue(!rs.isLast());

                assertEquals(tempOrder.getUsername(), rs.getString("u_name"));
                assertEquals(tempOrder.getArticlesSold().get(0).article.getPrice(),
                                rs.getDouble("price"),0);
                assertEquals(tempOrder.getArticlesSold().get(0).article.getName(),
                                rs.getString("a_name"));
                assertEquals(tempOrder.getArticlesSold().get(0).numTot,
                                rs.getInt("num_tot"));

                assertTrue(rs.next());
                assertTrue(rs.isLast());

                assertEquals(tempOrder.getUsername(), rs.getString("u_name"));
                assertEquals(tempOrder.getArticlesSold().get(1).article.getPrice(),
                                rs.getDouble("price"),0);
                assertEquals(tempOrder.getArticlesSold().get(1).article.getName(),
                                rs.getString("a_name"));
                assertEquals(tempOrder.getArticlesSold().get(1).numTot,
                                rs.getInt("num_tot"));

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }

        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
        tempOrder = new Order(date.parse("20090904213000"),testCassiere.getUsername(), "hell");
        tempOrder.addArticle(articles.get(0), 3);
        tempOrder.addArticle(articles.get(3), 2);

        backend.addNewOrder(tempOrder);

        // verify presence
        query = "SELECT e.name AS e_name, d.title AS d_title, " +
                    "c.username AS u_name, o.time_order AS o_time, " +
                    "o.price_tot AS o_price" +
                "   FROM cassieres AS c, orders AS o, events AS e, " +
                        "dates_event AS d" +
                "   WHERE o.id_cassiere = c.id_cassiere" +
                "       AND o.id_date_event = d.id_date_event" +
                "       AND e.id_event = d.id_event" +
                "       AND e.name <> 'other';";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());
                assertTrue(rs.isLast());

                assertEquals(tempOrder.getUsername(), rs.getString("u_name"));
                assertEquals(tempOrder.getTotalPrice(),
                                rs.getDouble("o_price"),0);
                assertEquals(event.name,rs.getString("e_name"));
                assertEquals(event.datesList.get(1).titleDate,rs.getString("d_title"));

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }
    }

    /**
     * Test of addNewOrder method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testAddNewOrderWithOptions() throws Exception {
        System.out.println("delLastOrder");

        articles = backend.loadArticlesList();

        List<EntrySingleOption> optionsList = new Vector<EntrySingleOption>();
        optionsList.add(new EntrySingleOption(
                ((ArticleWithOptions)articles.get(1)).getOptions().get(0), 2));
        optionsList.add(new EntrySingleOption(
                ((ArticleWithOptions)articles.get(1)).getOptions().get(1), 3));

        Order tempOrder = new Order(testCassiere.getUsername(), "hell");
        tempOrder.addArticleWithOptions(
                (ArticleWithOptions)articles.get(1), 5, 0, optionsList);

        backend.addNewOrder(tempOrder);

        // verify presence
        String query =  "SELECT c.username AS username, o.time_order AS time, " +
                            "o.price_tot AS price" +
                        "   FROM orders AS o, cassieres AS c" +
                        "   WHERE o.id_cassiere = c.id_cassiere;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.isLast());

                assertEquals(tempOrder.getUsername(), rs.getString("username"));
                assertEquals(tempOrder.getTotalPrice(), rs.getDouble("price"),0);
                assertEquals(new Timestamp(tempOrder.getData().getTime()),
                        rs.getTimestamp("time"));

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB");
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }

        // verify presence
        query = "SELECT c.username AS u_name, a.name AS a_name, " +
                    "aio.num_tot AS num_tot, a.price AS price" +
                "   FROM cassieres AS c, orders AS o, articles AS a, " +
                    "articles_in_order AS aio" +
                "   WHERE o.id_order = aio.id_order" +
                "       AND aio.id_article = a.id_article" +
                "       AND c.id_cassiere = o.id_cassiere;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.isLast());

                assertEquals(tempOrder.getUsername(), rs.getString("u_name"));
                assertEquals(tempOrder.getArticlesSold().get(0).article.getPrice(),
                                rs.getDouble("price"),0);
                assertEquals(tempOrder.getArticlesSold().get(0).article.getName(),
                                rs.getString("a_name"));
                assertEquals(tempOrder.getArticlesSold().get(0).numTot,
                                rs.getInt("num_tot"));

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }

        // verify presence
        query = "SELECT a.name AS a_name, op.name AS op_name, o.time_order AS time, " +
                    "oao.num_parz AS num_parz" +
                "   FROM orders AS o, articles AS a, articles_in_order AS aio, " +
                    "options AS op, opts_of_article_in_order AS oao" +
                "   WHERE o.id_order = aio.id_order" +
                "       AND aio.id_article = a.id_article" +
                "       AND oao.id_art_in_ord = aio.id_art_in_ord" +
                "       AND oao.id_option = op.id_option" +
                "       AND a.has_options = TRUE;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());
                assertTrue(!rs.isLast());

                assertEquals(tempOrder.getData().getTime(),
                                rs.getTimestamp("time").getTime());
                assertEquals(tempOrder.getArticlesSold().get(0).article.getName(),
                                rs.getString("a_name"));
                assertEquals(
                        ((EntrySingleArticleWithOption)tempOrder.getArticlesSold().get(0)).numPartial.get(0).numPartial,
                                rs.getInt("num_parz"));
                assertEquals(
                        ((EntrySingleArticleWithOption)tempOrder.getArticlesSold().get(0)).numPartial.get(0).optionName,
                                rs.getString("op_name"));

                assertTrue(rs.next());
                assertTrue(rs.isLast());

                assertEquals(tempOrder.getData().getTime(),
                                rs.getTimestamp("time").getTime());
                assertEquals(tempOrder.getArticlesSold().get(0).article.getName(),
                                rs.getString("a_name"));
                assertEquals(
                        ((EntrySingleArticleWithOption)tempOrder.getArticlesSold().get(0)).numPartial.get(1).numPartial,
                                rs.getInt("num_parz"));
                assertEquals(
                        ((EntrySingleArticleWithOption)tempOrder.getArticlesSold().get(0)).numPartial.get(1).optionName,
                                rs.getString("op_name"));

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }
    }



    /**
     * Test of delLastOrder method, of class PostgreSQLDataBackend.
     */
    @Test
    public void testDelLastOrder() throws Exception {
        System.out.println("delLastOrder");

        articles = backend.loadArticlesList();

        Order tempOrder = new Order(testCassiere.getUsername(), "hell");
        tempOrder.addArticle(articles.get(0), 3);
        tempOrder.addArticle(articles.get(3), 2);

        backend.addNewOrder(tempOrder);

        String query = "SELECT count( id_order ) FROM orders;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());

                assertEquals(rs.getInt("count"), 4);

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }

        query = "SELECT count( id_art_in_ord ) FROM articles_in_order;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());

                assertEquals(rs.getInt("count"), 7);

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }

        query = "SELECT count( id_option ) FROM opts_of_article_in_order;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());

                assertEquals(rs.getInt("count"), 2);

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }

        backend.delLastOrder(tempOrder);

        query = "SELECT count( id_order ) FROM orders;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());

                assertEquals(rs.getInt("count"), 3);

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }

        query = "SELECT count( id_art_in_ord ) FROM articles_in_order;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());

                assertEquals(rs.getInt("count"), 5);

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }

        query = "SELECT count( id_option ) FROM opts_of_article_in_order;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());

                assertEquals(rs.getInt("count"), 2);

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: ");
            ex.printStackTrace();
        }
    }
    
    /* -------------------------------------------- *
     *  Utility Functions                           *
     * -------------------------------------------- */

    /**
     * 
     * @param query
     * @param person
     */
    private void testPersonPresence(String query, Person person) {
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);
                assertTrue("Non c'è un risultato!",rs.next());
                assertTrue("Non è l'ultimo!",rs.isLast());

                assertEquals("La password non corrisponde!",
                        rs.getString("password"), person.getPassword());
            } catch (SQLException ex) {
                fail("Failed in executing the query: " + query + "\n" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in connecting to the DB");
        }
    }

    /**
     *
     * @param query
     * @param testArticle
     */
    private void testArticlePresence(String query, Article testArticle) {
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);
                assertTrue("Non c'è un risultato!",rs.next());
                assertTrue("Non è l'ultimo!",rs.isLast());

                assertEquals("Il prezzo non corrisponde!",
                        testArticle.getPrice(),rs.getDouble("price"),0);
                assertEquals(testArticle.hasOptions()
                                ? "Dovrebbe aver opzioni ma non ne ha!"
                                : "Non dovrebbe aver opzioni ma ne ha!",
                             testArticle.hasOptions(),rs.getBoolean("has_options"));
                if (testArticle.hasOptions()) {
                    for (String option : ((ArticleWithOptions)testArticle).getOptions()) {
                        testOptionPresence(option);
                    }
                }
            } catch (SQLException ex) {
                fail("Failed in executing the query: " + query + "\n" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in connecting to the DB");
        }
    }

    /**
     * 
     * @param option
     */
    private void testOptionPresence(String option) {
        String query = "SELECT * FROM options WHERE name = '" + option + "'";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);
                assertTrue("Non c'è un risultato!",rs.next());
                assertTrue("Non è l'ultimo!",rs.isLast());

                assertEquals("l'opzione non corrisponde!",
                             option,rs.getString("name"));
            } catch (SQLException ex) {
                fail("Failed in executing the query: " + query + "\n" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in connecting to the DB");
        }
    }
    
    /* -------------------------------------------- *
     *  Performance / Security / Concurrency Tests  *
     * -------------------------------------------- */

    @Test
    public void testPerformanceSubqueriesVSSeparateQueries() {
        
    }
}