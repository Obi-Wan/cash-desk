/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.datamanager;

import gestionecassa.Admin;
import gestionecassa.ArticleWithPreparation;
import gestionecassa.Article;
import gestionecassa.Cassiere;
import gestionecassa.ArticlesList;
import gestionecassa.Log;
import gestionecassa.order.Order;
import gestionecassa.Person;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    ConcurrentHashMap<String, ConcurrentLinkedQueue<Order> > ordersTable;

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
     * Semaphore for the list of goods
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listArticlesSemaphore =
            new String("BeniSemaphore" + System.currentTimeMillis());

    /**
     * Semaphore for the list of goods
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listProgressiviSemaphore =
            new String("ProgressiviSemaphore" + System.currentTimeMillis());

    /**
     * Default constructor
     *
     * @param fallbackXML
     */
    public DataManager(BackendAPI_2 fallback, BackendAPI_1 dataBackend) {
        this.fallbackXML = dataBackend;
        this.dataBackendDB = fallback;
        this.logger = Log.GESTIONECASSA_SERVER_DATAMANAGER;

        ordersTable = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Order>>();

        try {
            dataBackendDB.init("jdbc:postgresql://localhost:5432/GCDB");
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
        synchronized (listAdminsSemaphore) {
            adminsList = new TreeMap<String, Admin>();
            try {
                List<Admin> listaAdmin;
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
        synchronized (listCassieriSemaphore) {
            cassieresList = new TreeMap<String, Cassiere>();
            try {
                List<Cassiere> listaCassiere;
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
        List<Article> lista;
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
                
                    for (Article article : articlesList.getList()) {
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
     *
     * @return
     */
    public ArticlesList getCurrentArticlesList() {
        synchronized (listArticlesSemaphore) {
            return articlesList;
        }
    }

    //----------------------------//
    // Cassa Client handle.
    //----------------------------//

    public void createNewCassaSession(String identifier) {
        ordersTable.put(identifier, new ConcurrentLinkedQueue<Order>());
    }

    public void closeCassaSession(String identifier) {
        // flush to disk
        ConcurrentLinkedQueue<Order> tempOrderList = ordersTable.get(identifier);
        try {
            // usefull to save even if we're using a DB
            fallbackXML.saveListOfOrders(identifier, tempOrderList);

            // once saved, delete it.
            ordersTable.remove(identifier);
        } catch (IOException ex) {
            logger.error("Order list could not be saved", ex);
        }
    }

    public void addNewOrder(String id, Order order) throws IOException {
        ordersTable.get(id).add(order);
        if (!useFallback) {
            dataBackendDB.addNewOrder(order);
        }
    }

    public void delLastOrder(String id) throws IOException {
        ConcurrentLinkedQueue<Order> tempOrderList = ordersTable.get(id);
        if (tempOrderList.size() >= 0) {
            Order tempOrder = (Order)tempOrderList.toArray()[tempOrderList.size() -1];
            if (!useFallback) {
                dataBackendDB.delLastOrder( tempOrder );
            }
            tempOrderList.remove( tempOrder );
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

    public void saveNewArticlesList(ArticlesList list) { // RIVEDI
        synchronized (listArticlesSemaphore) {
            articlesList = new ArticlesList(list.getList());
            
            synchronized (listProgressiviSemaphore) {
                progressivesList = new TreeMap<String, Integer>();

                for (Article article : list.getList()) {
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
    public void addArticle(Article article) {
        synchronized (listArticlesSemaphore) {
            articlesList.addArticle(article);

            if (!useFallback) {
                try {
                    dataBackendDB.addArticleToList(article);
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
     * Enables/disables an article at the index specified by position.
     *
     * @param position
     * @param enable
     */
    public void enableArticle(int position, boolean enable) {
        synchronized (listArticlesSemaphore) {
            Article temp = articlesList.enableArticle(position, enable);

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

    /**
     * Moves an article
     *
     * @param oldPos Old position
     * @param newPos New position
     */
    public void moveArticle(int oldPos, int newPos) {
        synchronized (listArticlesSemaphore) {
            Article temp = articlesList.moveArticleAt(oldPos, newPos);

            if (!useFallback) {
                try {
                    dataBackendDB.moveArticleAt(temp, newPos);
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
     * Moves the specified article
     *
     * @param article Article to move
     * @param newPos New position
     */
    public void moveArticle(Article article, int newPos) {
        synchronized (listArticlesSemaphore) {
            Article temp = articlesList.moveArticleAt(article, newPos);

            if (!useFallback) {
                try {
                    dataBackendDB.moveArticleAt(temp, newPos);
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

    public void terminate() {
    }
}
