/*
 * DataManagerTest.java
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

package gestionecassa.server.datamanager;

import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.ArticleGroup;
import gestionecassa.ArticleOption;
import gestionecassa.ArticleWithOptions;
import gestionecassa.ArticlesList;
import gestionecassa.Cassiere;
import gestionecassa.Person;
import gestionecassa.exceptions.WrongArticlesListException;
import gestionecassa.order.BaseEntry;
import gestionecassa.order.EntryArticleGroup;
import gestionecassa.order.Order;
import gestionecassa.stubs.BackendStub_1;
import gestionecassa.stubs.BackendStub_2;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
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
public class DataManagerTest {

    DataManager dataManager;
    BackendStub_1 backend_1;
    BackendStub_2 backend_2;

    public DataManagerTest() {
        backend_1 = new BackendStub_1();
        backend_2 = new BackendStub_2();
        dataManager = new DataManager(backend_2, "", backend_1);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of registerUser method, of class DataManager.
     */
    @Test
    public void testRegisterCasssiere() {
        System.out.println("registerUser: Cassiere's part");
        Cassiere temp = new Cassiere(dataManager.getCassieresList().size(), "yeah", "bhoo");
        int numPrima = dataManager.getCassieresList().size();
        dataManager.registerUser(temp);
        assertEquals(numPrima+1, dataManager.getCassieresList().size());
        assertEquals(temp, dataManager.getCassieresList().get(temp.getUsername()));
    }

    /**
     * Test of registerUser method, of class DataManager.
     */
    @Test
    public void testRegisterAdmin() {
        System.out.println("registerUser: Admin's part");
        Admin temp = new Admin(dataManager.getAdminsList().size(), "yeah", "bhoo");
        int numPrima = dataManager.getAdminsList().size();
        dataManager.registerUser(temp);
        assertEquals(numPrima+1, dataManager.getAdminsList().size());
        assertEquals(temp, dataManager.getAdminsList().get(temp.getUsername()));
    }

    /**
     * Test of verifyUsername method, of class DataManager.
     */
    @Test
    public void testVerifyUsernamePresent() {
        System.out.println("verifyUsername: Test searching for a present one");
        Person temp = dataManager.verifyUsername("bene");
        assertNotNull(temp);
        assertEquals(temp, dataManager.getCassieresList().get("bene"));
    }

    /**
     * Test of verifyUsername method, of class DataManager.
     */
    @Test
    public void testVerifyUsernameNotPresent() {
        System.out.println("verifyUsername: Test searching for a not present one");
        assertNull(dataManager.verifyUsername("male"));
    }

    /**
     * Test of getAllArticlesList method, of class DataManager.
     * @throws IOException
     */
    @Test
    public void testGetCurrentArticlesList() throws IOException {
        System.out.println("getCurrentArticlesList");
        assertEquals(dataManager.getAllArticlesList().getGroupsList(),
                backend_1.loadArticlesList());
    }

    /**
     * Test of createNewCassaSession method, of class DataManager.
     */
    @Test
    public void testCreateNewCassaSession() {
        System.out.println("createNewCassaSession");

        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd_HH-mm-ss").format(new Date());

        assertTrue("no Cassieres in the list", dataManager.getCassieresList().size() > 0);
        String username = dataManager.getCassieresList().firstKey();
        String identifier = username + "@" + timestamp;

        dataManager.createNewCassaSession(identifier);

        assertNotNull(dataManager.getOrdersTable().get(username + "@" + timestamp));
    }

    /**
     * Test of closeCassaSession method, of class DataManager.
     */
    @Test
    public void testCloseCassaSession() {
        System.out.println("closeCassaSession");

        /* create session */
        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd_HH-mm-ss").format(new Date());
        String username = dataManager.getCassieresList().firstKey();
        String identifier = username + "@" + timestamp;
        dataManager.createNewCassaSession(identifier);

        /* test it */
        int numPrima = dataManager.getOrdersTable().size();
        dataManager.closeCassaSession(identifier);

        assertEquals("Not closed", numPrima-1, dataManager.getOrdersTable().size());
        assertNull(dataManager.getOrdersTable().get(identifier));

        assertEquals("not flushed", 0, backend_1.ordersList.size());
    }

    /**
     * Test of addNewOrder method, of class DataManager.
     * @throws IOException
     * @throws WrongArticlesListException 
     */
    @Test
    public void testAddNewOrder() throws IOException, WrongArticlesListException {
        System.out.println("addNewOrder");
        
        /* create session */
        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd_HH-mm-ss").format(new Date());
        String username = dataManager.getCassieresList().firstKey();
        String identifier = username + "@" + timestamp;
        dataManager.createNewCassaSession(identifier);

        /* add orders */
        Order order = new Order(username, "here", 0,
                            dataManager.getAllArticlesList().getSignature());
        ArticleGroup group = dataManager.getAllArticlesList().getEnabledList().getGroup(0);
        EntryArticleGroup groupsToAdd = new EntryArticleGroup(group);
        groupsToAdd.addArticle(group.getList().get(0), 2);

        order.addGroup(groupsToAdd);

        dataManager.addNewOrder(identifier, order);
        assertEquals("Order not added", 1,
                        dataManager.getOrdersTable().get(identifier).size());
        
        /* Verify orders were flushed */
        int numPrima = dataManager.getOrdersTable().size();
        dataManager.closeCassaSession(identifier);

        assertEquals("Not closed", numPrima-1, dataManager.getOrdersTable().size());
        assertNull(dataManager.getOrdersTable().get(identifier));

        assertEquals("not flushed", 1, backend_1.ordersList.size());
    }

    /**
     * Test of delLastOrder method, of class DataManager.
     * @throws IOException
     * @throws WrongArticlesListException
     */
    @Test

    public void testDelLastOrder() throws IOException, WrongArticlesListException {
        System.out.println("delLastOrder");

        /* create session */
        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd_HH-mm-ss").format(new Date());
        String username = dataManager.getCassieresList().firstKey();
        String identifier = username + "@" + timestamp;
        dataManager.createNewCassaSession(identifier);

        /* add orders */
        Order order = new Order(username, "here", 0,
                            dataManager.getAllArticlesList().getSignature());
        ArticleGroup group = dataManager.getAllArticlesList().getEnabledList().getGroup(0);
        EntryArticleGroup groupsToAdd = new EntryArticleGroup(group);
        groupsToAdd.addArticle(group.getList().get(0), 2);

        order.addGroup(groupsToAdd);

        dataManager.addNewOrder(identifier, order);

        dataManager.delLastOrder(identifier);
        assertEquals("Order not added", 0,
                        dataManager.getOrdersTable().get(identifier).size());

        /* Verify orders were flushed */
        int numPrima = dataManager.getOrdersTable().size();
        dataManager.closeCassaSession(identifier);

        assertEquals("Not closed", numPrima-1,
                                        dataManager.getOrdersTable().size());
        assertNull(dataManager.getOrdersTable().get(identifier));

        assertEquals("Flushed something it wasn't meant to be", 0,
                                                backend_1.ordersList.size());
    }

    /**
     * Test of getNProgressive method, of class DataManager.
     * @throws IOException
     * @throws WrongArticlesListException
     */
    @Test
    public void testGetNProgressivo() throws IOException, WrongArticlesListException {
        System.out.println("getNProgressivo");

        /* create session */
        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd_HH-mm-ss").format(new Date());
        String username = dataManager.getCassieresList().firstKey();
        String identifier = username + "@" + timestamp;
        dataManager.createNewCassaSession(identifier);

        /* add orders */
        Order order = new Order(username, "here", 0,
                            dataManager.getAllArticlesList().getSignature());
        ArticleGroup group = dataManager.getAllArticlesList().getEnabledList().getGroup(0);
        EntryArticleGroup groupsToAdd = new EntryArticleGroup(group);

        ArticleWithOptions articleWO = (ArticleWithOptions) group.getList().get(3);

        int progressive = dataManager.getNProgressive(articleWO.getName(), 2);
        groupsToAdd.addArticleWithOptions(
                articleWO, 2, progressive, new ArrayList<BaseEntry<String>>());

        order.addGroup(groupsToAdd);
        
        dataManager.addNewOrder(identifier, order);


        /* Verify orders were flushed */
        int numPrima = dataManager.getOrdersTable().size();
        dataManager.closeCassaSession(identifier);
    }

    /**
     * Test of saveNewArticlesList method, of class DataManager.
     * @throws IOException 
     */
    @Test
    public void testSaveNewArticlesList() throws IOException {
        System.out.println("saveNewArticlesList");

        Collection<Article> oldArticles = dataManager.getAllArticlesList().getArticlesList();
        
        List<Article> articles = new ArrayList<Article>();
        Collection<ArticleOption> listaOpzioni = new ArrayList<ArticleOption>();
        listaOpzioni.add(new ArticleOption(0, "cacca secca", true));
        listaOpzioni.add(new ArticleOption(1, "cacca liquida", true));
        articles.add(new Article(1,"fagiolo", 25));
        articles.add(new Article(2,"blabla", 35));
        articles.add(new Article(3,"merda dello stige", 5.5));
        articles.add(new ArticleWithOptions(4,"yeah", 10.25, listaOpzioni));
        
        List<ArticleGroup> groups = new ArrayList<ArticleGroup>();
        groups.add(new ArticleGroup(1, "group", articles));

        dataManager.saveNewArticlesList(new ArticlesList(groups));

        // FIXME non preservato l'ordine!
        assertEquals(dataManager.getAllArticlesList().getGroup(0).getList(), articles);

        assertEquals(
                ((List<ArticleGroup>)
                    backend_1.loadArticlesList()).get(0).getList(),
                articles);

        assertNotSame(
                ((List<ArticleGroup>)
                    backend_1.loadArticlesList()).get(0).getList(),
                oldArticles);
    }

}