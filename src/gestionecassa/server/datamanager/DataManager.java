/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.datamanager;

import gestionecassa.backends.BackendAPI_2;
import gestionecassa.backends.BackendAPI_1;
import gestionecassa.Admin;
import gestionecassa.ArticleWithPreparation;
import gestionecassa.Article;
import gestionecassa.ArticleGroup;
import gestionecassa.Cassiere;
import gestionecassa.ArticlesList;
import gestionecassa.Log;
import gestionecassa.exceptions.DuplicateArticleException;
import gestionecassa.order.Order;
import gestionecassa.Person;
import gestionecassa.exceptions.NotExistingGroupException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class DataManager implements DMCassaAPI, DMCommonAPI, DMServerAPI,
        DMAmministrazioneAPI {

    /**
     * 
     */
    Logger logger;

    /**
     * The data store backend
     */
    BackendAPI_1 fallbackXML;

    /**
     * The data store backend
     */
    BackendAPI_2 dataBackendDB;

    /**
     * 
     */
    boolean useFallback;

    /**
     * List of registered users
     */
    TreeMap<String,Cassiere> cassieresList;

    /**
     * List of registered users
     */
    TreeMap<String,Admin> adminsList;

    /**
     *
     */
    TreeMap<String, List<Order> > ordersTable;

    /**
     * list of handled good
     */
    ArticlesList articlesList;

    /**
     * For the goods with time of preparation is nice to have a number of
     * corrispondence between our ticket and the one held by the cooker.
     *
     * NOTE: in this implementation it is resetted everytime the server starts/stops
     */
    TreeMap<String, Integer> progressivesList;

    /**
     * Semaphore for the list of users
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listAdminsSemaphore =
            new String("AdminsSemaphore" + System.currentTimeMillis());

    /**
     * Semaphore for the list of users
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listCassieriSemaphore =
            new String("CassieriSemaphore" + System.currentTimeMillis());

    /**
     * Semaphore for the list of Articles
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listArticlesSemaphore =
            new String("ArticlesSemaphore" + System.currentTimeMillis());

    /**
     * Semaphore for the list of progressive numbers
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listProgressiviSemaphore =
            new String("ProgressiviSemaphore" + System.currentTimeMillis());

    /**
     * Semaphore for the list of orders
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listOrdersSemaphore =
            new String("OrdersSemaphore" + System.currentTimeMillis());

    /**
     * Default constructor
     *
     * @param fallbackXML
     */
    public DataManager(BackendAPI_2 fallback, String dbUrl, BackendAPI_1 dataBackend) {
        this.fallbackXML = dataBackend;
        this.dataBackendDB = fallback;
        this.logger = Log.GESTIONECASSA_SERVER_DATAMANAGER;

        ordersTable = new TreeMap<String, List<Order>>();

        try {
            dataBackendDB.init(dbUrl);
            useFallback =false;
            
        } catch (IOException ex) {
            useFallback = true;
            System.out.println("DB non accessibile o non conforme: using XML");
            Log.GESTIONECASSA_SERVER.error("errore nell'instanziare il db, uso XML", ex);
        }

        loadAdminsList();
        loadCassieresList();

        loadArticlesList();
    }

    /**
     * 
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
     * 
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
     * 
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
                        if (article instanceof ArticleWithPreparation) {
                            progressivesList.put(article.getName(), 0);
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public void registerUser(Person user) {
        if (user instanceof Cassiere) {
            synchronized (listCassieriSemaphore) {
                cassieresList.put(user.getUsername(), (Cassiere)user);
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
                adminsList.put(user.getUsername(), (Admin)user);
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
     * Verifies a username exists, and if is the case, 
     * it creates a new read only copy of the user to prevent problems in syncronization
     *
     * @param username
     *
     * @return 
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
     * @return
     */
    @Override
    public ArticlesList getArticlesList() {
        synchronized (listArticlesSemaphore) {
            return articlesList;
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
    public void addNewOrder(String id, Order order) throws IOException {
        synchronized (listOrdersSemaphore) {
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
            if (tempOrderList.size() >= 0) {
                Order tempOrder = tempOrderList.get( tempOrderList.size()-1 );
                if (!useFallback) {
                    dataBackendDB.delLastOrder( tempOrder );
                }
                tempOrderList.remove( tempOrder );
            }
        }
    }

    /**
     * Used to ask "n" progressive numbers to associate to the 
     * <code>ArticleWithPreparation</code>
     *
     * @param nomeBene
     *
     * @return the first of the n progressive numbers
     */
    @Override
    public int getNProgressivo(String articleName, int n) {
        synchronized (listProgressiviSemaphore) {
            Integer progressiv = progressivesList.get(articleName);
            if (progressiv == null) {
                throw new RuntimeException("ho trovato un nome che ci sarebbe " +
                        "dovuto essere nella lista dei progressivi ma che" +
                        " non c'era!!!");
            }
            progressivesList.put(articleName, progressiv+n);
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
                    if (article instanceof ArticleWithPreparation) {
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
     *
     * @param article
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

            if (article instanceof ArticleWithPreparation) {
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
     *
     * @param article
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
}
