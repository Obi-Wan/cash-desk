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

package gestionecassa.backends;

import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.ArticleGroup;
import gestionecassa.ArticleOption;
import gestionecassa.ArticleWithOptions;
import gestionecassa.ArticlesList;
import gestionecassa.Cassiere;
import gestionecassa.EventDate;
import gestionecassa.OrganizedEvent;
import gestionecassa.Person;
import gestionecassa.order.PairObjectInteger;
import gestionecassa.order.EntryArticleGroup;
import gestionecassa.order.EntrySingleArticleWithOption;
import gestionecassa.order.Order;
import gestionecassa.stubs.DebugDataProvider;
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
import java.util.ArrayList;
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
    List<ArticleGroup> groups;

    OrganizedEvent event;

    List<EventDate> dates;

    ArticlesList listOfArts;

    public PostgreSQLDataBackendTest() {
        dbUrl = "jdbc:postgresql://localhost:5432/TestGCDB";

        DebugDataProvider dataProvider = new DebugDataProvider();

        testAdmin = dataProvider.getCopyAdmins().get(0);
        testCassiere = dataProvider.getCopyCassieres().get(0);

        groups = dataProvider.getCopyGroups();
        articles = dataProvider.getCopyArticles();
        listOfArts = dataProvider.getCopyListOfArts();

        /*  */
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
        for (String table_ref : PostgreSQLDataBackend.tables.keySet()) {
            String table_name = table_ref.substring(3);
            String query = "DROP TABLE " + table_name + " CASCADE;";
            try {
                Statement st = backend.db.createStatement();
                try {
                    st.execute(query);
                } catch (SQLException ex) {
                    fail("Failed in Cleaning the DB: " + ex.getMessage());
                } finally {
                    st.close();
                }
            } catch (SQLException ex) {
                fail("Failed in connecting to the DB: " + ex.getMessage());
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
     * @throws Exception
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

                for (String table_ref : PostgreSQLDataBackend.tables.keySet()) {
                    String table_name = table_ref.substring(3);
                    if (!tabelleDB.contains(table_name)) {
                        fail("init did not create table: " + table_name);
                    }
                }
            } catch (SQLException ex) {
                fail("Failed in interrogating the DB: " + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
        }
    }

    /**
     * Test of addGroupToList method, of class PostgreSQLDataBackend.
     * @throws Exception
     */
    @Test
    public void testAddGroupToList() throws Exception {
        System.out.println("addGroupToList");

        backend.addGroupToList(groups.get(0));

        backend.addGroupToList(groups.get(1));
    }

    /**
     * Test of loadArticlesList method, of class PostgreSQLDataBackend.
     * @throws Exception
     */
    @Test
    public void testLoadArticlesOfGroup() throws Exception {
        System.out.println("loadArticlesOfGroup");

        List<Article> artsInGroup = backend.loadArticlesOfGroup(1);

        assertEquals(artsInGroup, groups.get(0).getList());
    }

    /**
     * Test of addArticleToList method, of class PostgreSQLDataBackend.
     * @throws Exception
     */
    @Test
    public void testAddArticleToList() throws Exception {
        System.out.println("addArticleToList");
        
        Article temp = articles.get(0);

        backend.addArticleToList(2, temp);

        String query = "SELECT * FROM articles WHERE name = '"+
                temp.getName() + "'";
        testArticlePresence(query, temp);

        query = "SELECT * FROM articles WHERE id_article = '"+
                temp.getId() + "'";
        testArticlePresence(query, temp);

        temp = articles.get(2);

        backend.addArticleToList(2, temp);

        query = "SELECT * FROM articles WHERE name = '" + temp.getName() + "'";
        testArticlePresence(query,temp);

        temp = articles.get(1);

        backend.addArticleToList(2, temp);

        query = "SELECT * FROM articles WHERE name = '" + temp.getName() + "'";
        testArticlePresence(query,temp);
    }

    /**
     * Test of moveArticleAt method, of class PostgreSQLDataBackend.
     * @throws Exception
     */
    @Test
    public void testMoveArticleAt() throws Exception {
        System.out.println("moveArticleAt");

        backend.moveArticleAt(articles.get(2), 0);

        String query = "SELECT * FROM articles WHERE num_pos = '0' AND id_group = '2'";
        testArticlePresence(query, articles.get(2));
    }

    /**
     * Test of addArticleToListAt method, of class PostgreSQLDataBackend.
     * @throws Exception
     */
    @Test
    public void testAddArticleToListAt() throws Exception {
        System.out.println("addArticleToListAt");

        Article temp = articles.get(3);

        backend.addArticleToListAt(2, temp, 2);

        String query = "SELECT * FROM articles WHERE name = '"+
                temp.getName() + "'";
        testArticlePresence(query,temp);
    }

    /**
     * Test of loadArticlesOfGroup method, of class PostgreSQLDataBackend.
     * @throws Exception
     */
    @Test
    public void testLoadArticlesList() throws Exception {
        System.out.println("loadArticlesList");

        List<ArticleGroup> grs = backend.loadArticlesList();
        assertEquals(grs.get(0).getName(), groups.get(0).getName());
        assertEquals(grs.get(0).getId(), groups.get(0).getId());
        assertEquals(grs.get(0).getList(), groups.get(0).getList());

        assertEquals(grs.get(1).getName(), groups.get(1).getName());
        assertEquals(grs.get(1).getId(), groups.get(1).getId());

        List<Article> artsInGroup = grs.get(1).getList();
        assertEquals(artsInGroup.size(), 4);
        // gatto
        assertEquals(artsInGroup.get(0), articles.get(2));
        assertEquals(artsInGroup.get(0).getId(), 6);
        // falce
        assertEquals(artsInGroup.get(1), articles.get(0));
        assertEquals(artsInGroup.get(1).getId(), 5);
        // vanga
        assertEquals(artsInGroup.get(2), articles.get(3));
        assertEquals(artsInGroup.get(2).getId(), 8);
        // cane
        assertEquals(artsInGroup.get(3), articles.get(1));
        assertEquals(artsInGroup.get(3).getId(), 7);
    }

    /**
     * Test of enableArticleFromList method, of class PostgreSQLDataBackend.
     * @throws Exception
     */
    @Test
    public void testEnableArticleFromList() throws Exception {
        System.out.println("enableArticleFromList");
        
        Article temp = groups.get(0).getList().get(0);

        backend.enableArticleFromList(temp, false);

        List<Article> articleList = backend.loadArticlesOfGroup(1);

        assertTrue(!articleList.get(0).isEnabled());

        backend.enableArticleFromList(temp, true);

        articleList = backend.loadArticlesOfGroup(1);

        assertTrue(articleList.get(0).isEnabled());
    }

    /**
     * Test of addAdmin method, of class PostgreSQLDataBackend.
     * @throws Exception
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
     * @throws Exception
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
     * @throws Exception
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
     * @throws Exception
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
     * @throws Exception
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
     * @throws Exception
     */
    @Test
    public void testAddOrganizedEvent() throws Exception {
        System.out.println("addOrganizedEvent");
        
        backend.addOrganizedEvent(event);
    }

    /**
     * Test of getOrganizedEvents method, of class PostgreSQLDataBackend.
     * @throws Exception
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
     * @throws Exception
     */
    @Test
    public void testAddDateToOrgEvent() throws Exception {
        System.out.println("addDateToOrgEvent");

        backend.addDateToOrgEvent(dates.get(dates.size()-1), event.name);
    }

    /**
     * Test of getDatesOfOrgEvent method, of class PostgreSQLDataBackend.
     * @throws Exception
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
     * @throws Exception
     */
    @Test
    public void testAddNewOrder() throws Exception {
        System.out.println("addNewOrder");

        articles = backend.loadArticlesOfGroup(1);

        Order tempOrder = new Order(testCassiere.getUsername(), "hell", 0,
                                    listOfArts.getSignature());
        EntryArticleGroup entry = new EntryArticleGroup(groups.get(0));
        entry.addArticle(groups.get(0).getList().get(0), 3);
        entry.addArticle(groups.get(0).getList().get(3), 2);
        tempOrder.addGroup(entry);
        
        backend.addNewOrder(tempOrder);

        // verify presence
        String query =  "SELECT c.username AS username, o.time_order AS time, " +
                            "o.price_tot AS price, o.table_num AS table" +
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
                assertEquals(new Timestamp(tempOrder.getDate().getTime()),
                        rs.getTimestamp("time"));
                assertEquals(tempOrder.getTable(), rs.getInt("table"));
                
            } catch (SQLException ex) {
                fail("Failed in interrogating the DB: " + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
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
                assertEquals(tempOrder.getGroups().get(0).articles.get(0).object.getPrice(),
                                rs.getDouble("price"),0);
                assertEquals(tempOrder.getGroups().get(0).articles.get(0).object.getName(),
                                rs.getString("a_name"));
                assertEquals(tempOrder.getGroups().get(0).articles.get(0).numTot,
                                rs.getInt("num_tot"));

                assertTrue(rs.next());
                assertTrue(rs.isLast());

                assertEquals(tempOrder.getUsername(), rs.getString("u_name"));
                assertEquals(tempOrder.getGroups().get(0).articles.get(1).object.getPrice(),
                                rs.getDouble("price"),0);
                assertEquals(tempOrder.getGroups().get(0).articles.get(1).object.getName(),
                                rs.getString("a_name"));
                assertEquals(tempOrder.getGroups().get(0).articles.get(1).numTot,
                                rs.getInt("num_tot"));

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB: " + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
        }

        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmmss");
        tempOrder = new Order(date.parse("20090904213000"),
                testCassiere.getUsername(), "hell", 0, listOfArts.getSignature());
        entry = new EntryArticleGroup(groups.get(0));
        entry.addArticle(groups.get(0).getList().get(0), 3);
        entry.addArticle(groups.get(0).getList().get(3), 2);
        tempOrder.addGroup(entry);

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
                fail("Failed in interrogating the DB: " + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
        }
    }

    /**
     * Test of addNewOrder method, of class PostgreSQLDataBackend.
     * @throws Exception
     */
    @Test
    public void testAddNewOrderWithOptions() throws Exception {
        System.out.println("delLastOrder");

        articles = backend.loadArticlesOfGroup(1);

        List<PairObjectInteger<ArticleOption>> optionsList =
                new ArrayList<PairObjectInteger<ArticleOption>>();
        optionsList.add(new PairObjectInteger<ArticleOption>(
                ((ArticleWithOptions)articles.get(2)).getOptions().get(0), 2));
        optionsList.add(new PairObjectInteger<ArticleOption>(
                ((ArticleWithOptions)articles.get(2)).getOptions().get(1), 3));

        Order tempOrder = new Order(testCassiere.getUsername(), "hell", 0,
                                    listOfArts.getSignature());
        EntryArticleGroup entry = new EntryArticleGroup(groups.get(0));
        entry.addArticleWithOptions(
                (ArticleWithOptions)articles.get(2), 5, 0, optionsList);
        tempOrder.addGroup(entry);

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
                assertEquals(new Timestamp(tempOrder.getDate().getTime()),
                        rs.getTimestamp("time"));

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB: " + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
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
                PairObjectInteger<Article> tempArtEntry =
                        tempOrder.getGroups().get(0).articles.get(0);
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.next());
                assertTrue(rs.isLast());

                assertEquals(tempOrder.getUsername(), rs.getString("u_name"));
                assertEquals(tempArtEntry.object.getPrice(),
                                rs.getDouble("price"),0);
                assertEquals(tempArtEntry.object.getName(),
                                rs.getString("a_name"));
                assertEquals(tempArtEntry.numTot,
                                rs.getInt("num_tot"));

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB: " + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
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
                EntrySingleArticleWithOption tempArtEntry =
                        (EntrySingleArticleWithOption)
                            tempOrder.getGroups().get(0).articles.get(0);
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());
                assertTrue(!rs.isLast());
                
                assertEquals(tempOrder.getDate().getTime(),
                                rs.getTimestamp("time").getTime());
                assertEquals(tempArtEntry.object.getName(),
                                rs.getString("a_name"));
                assertEquals(tempArtEntry.numPartial.get(0).numTot,
                                rs.getInt("num_parz"));
                assertEquals(tempArtEntry.numPartial.get(0).object.getName(),
                                rs.getString("op_name"));

                assertTrue(rs.next());
                assertTrue(rs.isLast());

                assertEquals(tempOrder.getDate().getTime(),
                                rs.getTimestamp("time").getTime());
                assertEquals(tempArtEntry.object.getName(),
                                rs.getString("a_name"));
                assertEquals(tempArtEntry.numPartial.get(1).numTot,
                                rs.getInt("num_parz"));
                assertEquals(tempArtEntry.numPartial.get(1).object.getName(),
                                rs.getString("op_name"));

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB: " + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
        }
    }



    /**
     * Test of delLastOrder method, of class PostgreSQLDataBackend.
     * @throws Exception
     */
    @Test
    public void testDelLastOrder() throws Exception {
        System.out.println("delLastOrder");

        articles = backend.loadArticlesOfGroup(1);

        Order tempOrder = new Order(testCassiere.getUsername(), "hell", 0,
                                    listOfArts.getSignature());
        EntryArticleGroup entry = new EntryArticleGroup(groups.get(0));
        entry.addArticle(groups.get(0).getList().get(0), 3);
        entry.addArticle(groups.get(0).getList().get(3), 2);
        tempOrder.addGroup(entry);

        backend.addNewOrder(tempOrder);

        String query = "SELECT count( id_order ) FROM orders;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());

                assertEquals(rs.getInt("count"), 4);

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB: " + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
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
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
        }

        query = "SELECT count( id_option ) FROM opts_of_article_in_order;";
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);

                assertTrue(rs.next());

                assertEquals(rs.getInt("count"), 2);

            } catch (SQLException ex) {
                fail("Failed in interrogating the DB: " + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
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
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
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
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
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
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in initializing the DB: " + ex.getMessage());
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
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in connecting to the DB: " + ex.getMessage());
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
                    for (ArticleOption option : ((ArticleWithOptions)testArticle).getOptions()) {
                        testOptionPresence(option.getName());
                    }
                }
            } catch (SQLException ex) {
                fail("Failed in executing the query: " + query + "\n" + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in connecting to the DB: " + ex.getMessage());
        }
    }

    /**
     *
     * @param query
     * @param testArticle
     */
    private void testGroupPresence(String query, ArticleGroup testGroup) {
        try {
            Statement st = backend.db.createStatement();
            try {
                ResultSet rs = st.executeQuery(query);
                assertTrue("Non c'è un risultato!",rs.next());
                assertTrue("Non è l'ultimo!",rs.isLast());

                assertTrue("Non è enabled", rs.getBoolean("enabled"));
                assertEquals("Non ha lo stesso id", testGroup.getId(),
                        rs.getInt("id_group"));
            } catch (SQLException ex) {
                fail("Failed in executing the query: " + query + "\n" + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in connecting to the DB: " + ex.getMessage());
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
            } catch (SQLException ex) {
                fail("Failed in executing the query: " + query + "\n" + ex.getMessage());
            } finally {
                st.close();
            }
        } catch (SQLException ex) {
            fail("Failed in connecting to the DB: " + ex.getMessage());
        }
    }
    
    /* -------------------------------------------- *
     *  Performance / Security / Concurrency Tests  *
     * -------------------------------------------- */

    @Test
    public void testPerformanceSubqueriesVSSeparateQueries() {
        
    }
}