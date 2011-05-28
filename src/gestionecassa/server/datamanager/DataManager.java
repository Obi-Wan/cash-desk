/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.datamanager;

import gestionecassa.backends.BackendAPI_2;
import gestionecassa.backends.BackendAPI_1;
import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.ArticleGroup;
import gestionecassa.Cassiere;
import gestionecassa.ArticlesList;
import gestionecassa.Log;
import gestionecassa.exceptions.DuplicateArticleException;
import gestionecassa.order.Order;
import gestionecassa.Person;
import gestionecassa.exceptions.NotExistingGroupException;
import gestionecassa.exceptions.WrongArticlesListException;
import gestionecassa.server.ServerPrefs;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class DataManager implements DMCassaAPI, DMServerAPI,
        DMAdminAPI {

    /**
     * 
     */
    private Logger logger;
    
    /**
     * It takes care of password handling and verification
     */
    public PasswordManager passwordManager;

    /**
     * The data store backend
     */
    private BackendAPI_1 fallbackXML;

    /**
     * The data store backend
     */
    private BackendAPI_2 dataBackendDB;

    /**
     * 
     */
    private boolean useFallback;

    /**
     * List of registered users
     */
    private TreeMap<String,Cassiere> cassieresList;

    /**
     * List of registered users
     */
    private TreeMap<String,Admin> adminsList;

    /**
     *
     */
    private TreeMap<String, List<Order> > ordersTable;

    /**
     * list of handled good
     */
    private ArticlesList articlesList;

    /**
     * For the goods with time of preparation is nice to have a number of
     * corrispondence between our ticket and the one held by the cooker.
     *
     * NOTE: in this implementation it is resetted everytime the server
     * starts/stops
     */
    private TreeMap<String, Integer> progressivesList;

    /**
     * Semaphore for the list of users
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final private String listAdminsSemaphore =
            "AdminsSemaphore" + System.currentTimeMillis();

    /**
     * Semaphore for the list of users
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final private String listCassieriSemaphore =
            "CassieriSemaphore" + System.currentTimeMillis();

    /**
     * Semaphore for the list of Articles
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final private String listArticlesSemaphore =
            "ArticlesSemaphore" + System.currentTimeMillis();

    /**
     * Semaphore for the list of progressive numbers
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final private String listProgressiviSemaphore =
            "ProgressiviSemaphore" + System.currentTimeMillis();

    /**
     * Semaphore for the list of orders
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final private String listOrdersSemaphore =
            "OrdersSemaphore" + System.currentTimeMillis();

    /**
     * Creates a new DataManager class
     * @param fallback
     * @param prefs Server preferences
     * @param dataBackend
     */
    public DataManager(BackendAPI_2 fallback, ServerPrefs prefs,
            BackendAPI_1 dataBackend) {
        this.fallbackXML = dataBackend;
        this.dataBackendDB = fallback;
        this.logger = Log.GESTIONECASSA_SERVER_DATAMANAGER;
        
        passwordManager = new PasswordManager();
        try {
            passwordManager.initDigest(prefs.digestPasswords);
        } catch (NoSuchAlgorithmException ex) {
            prefs.digestPasswords = false;
            final String errorHash = " - Warn: MD5 algorithm not available "
                    + "for Hashing passwords. Disabling Hashing passwords.";
            logger.warn(errorHash, ex);
            System.out.println(errorHash);
        }
        logger.info("Charset used for bytes decoding: "
                + passwordManager.charset.displayName());

        ordersTable = new TreeMap<String, List<Order>>();

        try {
            dataBackendDB.init(prefs.dbUrl);
            useFallback =false;
            
        } catch (IOException ex) {
            useFallback = true;
            System.out.println("DB non accessibile o non conforme: using XML");
            Log.GESTIONECASSA_SERVER.error(
                    "errore nell'instanziare il db, uso XML", ex);
        }

        loadAdminsList();
        loadCassieresList();

        loadArticlesList();
    }

    /**
     * Initialization method for the list of Admins
     */
    private void loadAdminsList() {
        Collection<Admin> listaAdmin;
        synchronized (listAdminsSemaphore) {
            adminsList = new TreeMap<String, Admin>();
            try {
                if (useFallback) {
                    listaAdmin = fallbackXML.loadAdminsList();
                } else {
                    listaAdmin = dataBackendDB.loadAdminsList();
                }
                
                if (listaAdmin.isEmpty()) {
                    logger.warn("no admins, creating a new blank/default list");
                    registerUser(new Admin(adminsList.size()+1, "GCAdmin",
                                           "GCPassword"));
                } else {
                    for (Admin admin : listaAdmin) {
                        adminsList.put(admin.getUsername(), admin);
                    }
                }
            } catch (IOException ex) {
                logger.warn("reading Admins was not successful, creating a new " +
                        "blank/default list", ex);

                registerUser(new Admin(adminsList.size()+1, "GCAdmin",
                                       "GCPassword"));
            }
        }
    }

    /**
     * Initialization method for the list of Cassieres
     */
    private void loadCassieresList() {
        Collection<Cassiere> listaCassiere;
        synchronized (listCassieriSemaphore) {
            cassieresList = new TreeMap<String, Cassiere>();
            try {
                if (useFallback) {
                    listaCassiere = fallbackXML.loadCassiereList();
                } else {
                    listaCassiere = dataBackendDB.loadCassiereList();
                }
                
                for (Cassiere cassiere : listaCassiere) {
                    cassieresList.put(cassiere.getUsername(), cassiere);
                }
            } catch (IOException ex) {
                logger.warn("reading Admins from file failed, creating a new " +
                        "blank list", ex);

                registerUser(new Cassiere(cassieresList.size()+1, "bene", "male"));
            }
        }
    }

    /**
     * Initialization method for the list of Articles
     */
    private void loadArticlesList() {
        Collection<ArticleGroup> lista;
        synchronized (listArticlesSemaphore) {
            try {
                if (useFallback) {
                    lista = fallbackXML.loadArticlesList();
                } else {
                    lista = dataBackendDB.loadArticlesList();
                }
                articlesList = new ArticlesList(lista);
            } catch (IOException ex) {
                logger.warn("ListaBeni could not be loaded, starting up a" +
                        " new clean list.", ex);
                
                articlesList = new ArticlesList();
            } finally {
                synchronized (listProgressiviSemaphore) {
                    progressivesList = new TreeMap<String, Integer>();
                
                    for (Article article : articlesList.getArticlesList()) {
                        if (article.hasOptions()) {
                            progressivesList.put(article.getName(), 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * Registers the new {@code Person} to the list of new users
     * @param user New user to add
     */
    @Override
    public void registerUser(Person user) {
        if (user instanceof Cassiere) {
            synchronized (listCassieriSemaphore) {
                Cassiere tempRecord = new Cassiere((Cassiere)user,
                        passwordManager.digestPassword(user.getPassword()));
                cassieresList.put(user.getUsername(), tempRecord);
                try {
                    if (useFallback) {
                        fallbackXML.saveCassiereList(cassieresList.values());
                    } else {
                        dataBackendDB.addCassiere((Cassiere)user);
                    }
                } catch (IOException ex) {
                    logger.error("could not save the new Cassieri list", ex);
                }
            }
        } else if (user instanceof Admin) {
            synchronized (listAdminsSemaphore) {
                Admin tempRecord = new Admin((Admin)user,
                        passwordManager.digestPassword(user.getPassword()));
                adminsList.put(user.getUsername(), tempRecord);
                try {
                    if (useFallback) {
                        fallbackXML.saveAdminsList(adminsList.values());
                    } else {
                        dataBackendDB.addAdmin((Admin)user);
                    }
                } catch (IOException ex) {
                    logger.error("could not save the new Admins list", ex);
                }
            }
        }
    }

    /**
     * Verifies a username exists, and if is the case, it creates a new read
     * only copy of the user to prevent problems in syncronization
     *
     * @param username Username of the desired person
     *
     * @return <code>null</code> if not found, or the reference to the person
     */
    @Override
    public Person verifyUsername(String username) {
        synchronized (listCassieriSemaphore) {
            Cassiere tempCassiere = cassieresList.get(username);
            if (tempCassiere != null && tempCassiere.isEnabled()) {
                return new Cassiere(tempCassiere);
            }
        }
        synchronized (listAdminsSemaphore) {
            Admin tempAdmin = adminsList.get(username);
            if (tempAdmin != null && tempAdmin.isEnabled()) {
                return new Admin(tempAdmin);
            }
        }
        return null;
    }

    /**
     * Getter for the Articles List hold by the server
     *
     * @return a copy of the list of articles
     */
    @Override
    public ArticlesList getAllArticlesList() {
        synchronized (listArticlesSemaphore) {
            return new ArticlesList(articlesList);
        }
    }

    /**
     * Method to get the list of all the enabled Articles
     * Note that the method <method>getEnabledList()</method> already makes a
     * copy of the original list
     * @return the list of all enabled articles
     */
    @Override
    public ArticlesList getEnabledArticlesList() {
        synchronized (listArticlesSemaphore) {
            return articlesList.getEnabledList();
        }
    }

    private void checkListSignature(List<Integer> sign) throws WrongArticlesListException {
        if (!articlesList.getSignature().equals(sign)) {
            throw new WrongArticlesListException(
                    "Lists' signatures don't match\nmain: "
                    + articlesList.getSignature() + "\nclient:" + sign);
        }
    }

    //----------------------------//
    // Cassa Client handle.
    //----------------------------//

    @Override
    public void createNewCassaSession(String identifier) {
        synchronized (listOrdersSemaphore) {
            ordersTable.put(identifier, new ArrayList<Order>());
        }
    }

    @Override
    public void closeCassaSession(String identifier) {
        // flush to disk
        synchronized (listOrdersSemaphore) {
            List<Order> tempOrderList = ordersTable.get(identifier);
            try {
                // usefull to save even if we're using a DB
                fallbackXML.saveListOfOrders(identifier, tempOrderList);

                // once saved, delete it.
                ordersTable.remove(identifier);
            } catch (IOException ex) {
                logger.error("Order list could not be saved", ex);
            }
        }
    }

    @Override
    public void addNewOrder(String id, Order order)
            throws IOException, WrongArticlesListException {
        synchronized (listOrdersSemaphore) {
            checkListSignature(order.getListSignature());
            
            ordersTable.get(id).add(order);
            if (!useFallback) {
                dataBackendDB.addNewOrder(order);
            }
        }
    }

    @Override
    public void delLastOrder(String id) throws IOException {
        synchronized (listOrdersSemaphore) {
            List<Order> tempOrderList = ordersTable.get(id);
            if (tempOrderList!= null && tempOrderList.size() >= 0) {
                Order tempOrder = tempOrderList.get( tempOrderList.size()-1 );
                if (!useFallback) {
                    dataBackendDB.delLastOrder( tempOrder );
                }
                tempOrderList.remove( tempOrder );
            } else {
                throw new IOException("no Orders to delete! is the session alive?");
            }
        }
    }

    /**
     * Used to ask "n" progressive numbers to associate to the
     * <code>ArticleWithPreparation</code>
     *
     * @param artName Name of the article with multiple progressive numbers
     * @param n How many progressive numbers to associate to the article
     *
     * @return the first of the n progressive numbers
     */
    @Override
    public int getNProgressive(String artName, int n) {
        synchronized (listProgressiviSemaphore) {
            Integer progressiv = progressivesList.get(artName);
            if (progressiv == null) {
                throw new RuntimeException("ho trovato un nome che ci sarebbe " +
                        "dovuto essere nella lista dei progressivi ma che" +
                        " non c'era!!!");
            }
            progressivesList.put(artName, progressiv + n);
            return progressiv.intValue();
        }
    }

    //----------------------------//
    // Administration Client handle.
    //----------------------------//

    // FIXME potrebbe non esser corretto
    @Deprecated
    @Override
    public void saveNewArticlesList(ArticlesList list) { // RIVEDI
        synchronized (listArticlesSemaphore) {
            articlesList = new ArticlesList(list);
            
            synchronized (listProgressiviSemaphore) {
                progressivesList = new TreeMap<String, Integer>();

                for (Article article : list.getArticlesList()) {
                    if (article.hasOptions()) {
                        progressivesList.put(article.getName(), 0);
                    }
                }
            }
            try {
                fallbackXML.saveArticlesList(articlesList);
            } catch (IOException ex) {
                logger.error("could not save articles list", ex);
            }
        }
    }

    /**
     * Adds an article to the common list.
     * @param group Cardinal number refering to the group of this new article
     * @param article The article to add
     * @throws DuplicateArticleException
     * @throws NotExistingGroupException
     */
    @Override
    public void addArticle(int group, Article article)
            throws DuplicateArticleException, NotExistingGroupException {
        synchronized (listArticlesSemaphore) {
            articlesList.addArticleToGroup(group, article);

            if (!useFallback) {
                try {
                    dataBackendDB.addArticleToList(group, article);
                } catch (IOException ex) {
                    logger.error("could not save new article to DB", ex);
                }
            }

            if (article.hasOptions()) {
                synchronized (listProgressiviSemaphore) {
                    progressivesList.put(article.getName(), 0);
                }
            }
            try {
                fallbackXML.saveArticlesList(articlesList);
            } catch (IOException ex) {
                logger.error("could not save articles list to XML", ex);
            }
        }
    }

    /**
     * Adds an article to the common list.
     * @param group Cardinal number refering to the group of this new article
     * @param article The article to add
     * @throws DuplicateArticleException
     * @throws NotExistingGroupException
     */
    @Override
    public void addArticle(String group, Article article)
            throws DuplicateArticleException, NotExistingGroupException {
        synchronized (listArticlesSemaphore) {
            addArticle(articlesList.getGroupPos(group), article);
        }
    }

    /**
     * Enables/disables an article at the index specified by position.
     *
     * @param position
     * @param enable
     */
    @Override
    public void enableArticle(int group, int position, boolean enable) {
        synchronized (listArticlesSemaphore) {
            Article temp = articlesList.enableArticle(group, position, enable);

            if (!useFallback) {
                try {
                    dataBackendDB.enableArticleFromList(temp, enable);
                } catch (IOException ex) {
                    logger.error("could not save new article to DB", ex);
                }
            }
            
            try {
                fallbackXML.saveArticlesList(articlesList);
            } catch (IOException ex) {
                logger.error("could not save articles list to XML", ex);
            }
        }
    }

    /**
     * Enables/disables the article.
     *
     * @param article
     * @param enable
     */
    @Override
    public void enableArticle(Article article, boolean enable) {
        synchronized (listArticlesSemaphore) {
            Article temp = articlesList.enableArticle(article, enable);

            if (!useFallback) {
                try {
                    dataBackendDB.enableArticleFromList(temp, enable);
                } catch (IOException ex) {
                    logger.error("could not save new article to DB", ex);
                }
            }

            try {
                fallbackXML.saveArticlesList(articlesList);
            } catch (IOException ex) {
                logger.error("could not save articles list to XML", ex);
            }
        }
    }

//    /**
//     * Moves an article
//     *
//     * @param oldPos Old position
//     * @param newPos New position
//     */
//    @Override
//    public void moveArticle(int oldPos, int newPos) {
//        synchronized (listArticlesSemaphore) {
//            Article temp = articlesList.moveArticleAt(oldPos, newPos);
//
//            if (!useFallback) {
//                try {
//                    dataBackendDB.moveArticleAt(temp, newPos);
//                } catch (IOException ex) {
//                    logger.error("could not save new article to DB", ex);
//                }
//            }
//
//            try {
//                fallbackXML.saveArticlesList(articlesList);
//            } catch (IOException ex) {
//                logger.error("could not save articles list to XML", ex);
//            }
//        }
//    }
//
//    /**
//     * Moves the specified article
//     *
//     * @param article Article to move
//     * @param newPos New position
//     */
//    @Override
//    public void moveArticle(Article article, int newPos) {
//        synchronized (listArticlesSemaphore) {
//            Article temp = articlesList.moveArticleAt(article, newPos);
//
//            if (!useFallback) {
//                try {
//                    dataBackendDB.moveArticleAt(temp, newPos);
//                } catch (IOException ex) {
//                    logger.error("could not save new article to DB", ex);
//                }
//            }
//
//            try {
//                fallbackXML.saveArticlesList(articlesList);
//            } catch (IOException ex) {
//                logger.error("could not save articles list to XML", ex);
//            }
//        }
//    }

    @Override
    public void terminate() {
    }
    
    //--------- Debug Helpers API -----------//

    TreeMap<String, Admin> getAdminsList() {
        synchronized (listAdminsSemaphore) {
            return new TreeMap<String, Admin>(adminsList);
        }
    }

    TreeMap<String, Cassiere> getCassieresList() {
        synchronized (listCassieriSemaphore) {
            return new TreeMap<String, Cassiere>(cassieresList);
        }
    }

    TreeMap<String, List<Order>> getOrdersTable() {
        synchronized (listOrdersSemaphore) {
            return new TreeMap<String, List<Order>>(ordersTable);
        }
    }

    TreeMap<String, Integer> getProgressivesList() {
        synchronized (listProgressiviSemaphore) {
            return new TreeMap<String, Integer>(progressivesList);
        }
    }

    public boolean isUseFallback() {
        return useFallback;
    }
    
}
