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
import gestionecassa.ordine.Order;
import gestionecassa.Person;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
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
    BackendAPI_1_5 dataBackend;

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
    ConcurrentHashMap<String, List<Order> > ordersTable;

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
     * @param dataBackend 
     */
    public DataManager(BackendAPI_1_5 dataBackend) {
        this.dataBackend = dataBackend;
        this.logger = Log.GESTIONECASSA_SERVER_DATAMANAGER;

        ordersTable = new ConcurrentHashMap<String, List<Order>>();

        loadUsersList();

        loadArticlesList();
    }

    /**
     * 
     */
    private void loadUsersList() {
        synchronized (listAdminsSemaphore) {
            adminsList = new TreeMap<String, Admin>();
            try {
                List<Admin> listaAdmin = dataBackend.loadAdminsList();
                for (Admin admin : listaAdmin) {
                    adminsList.put(admin.getUsername(), admin);
                }
            } catch (IOException ex) {
                logger.warn("reading Admins from file failed, creating a new " +
                        "blank/default list", ex);

                registerUser(new Admin(adminsList.size(), "admin", "password"));
            }
        }
        synchronized (listCassieriSemaphore) {
            cassieresList = new TreeMap<String, Cassiere>();
            try {
                List<Cassiere> listaCassiere = dataBackend.loadCassiereList();
                for (Cassiere cassiere : listaCassiere) {
                    cassieresList.put(cassiere.getUsername(), cassiere);
                }
            } catch (IOException ex) {
                logger.warn("reading Admins from file failed, creating a new " +
                        "blank list", ex);
            }
        }
    }

    /**
     * 
     */
    private void loadArticlesList() {
        synchronized (listArticlesSemaphore) {
            try {
                List<Article> lista = dataBackend.loadArticlesList();
                articlesList = new ArticlesList(lista);

                synchronized (listProgressiviSemaphore) {
                    progressivesList = new TreeMap<String, Integer>();

                    for (Article article : lista) {
                        if (article instanceof ArticleWithPreparation) {
                            progressivesList.put(article.getNome(), 0);
                        }
                    }
                }
            } catch (IOException ex) {
                logger.warn("ListaBeni could not be loaded, starting up a" +
                        " new clean list.", ex);
                
                articlesList = new ArticlesList();
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
                    dataBackend.saveCassiereList(cassieresList.values());
                } catch (IOException ex) {
                    logger.error("could not save the new Cassieri list", ex);
                }
            }
        } else if (user instanceof Admin) {
            synchronized (listAdminsSemaphore) {
                adminsList.put(user.getUsername(), (Admin)user);
                try {
                    dataBackend.saveAdminsList(adminsList.values());
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
            if (tempCassiere != null) {
                return new Cassiere(tempCassiere);
            }
        }
        synchronized (listAdminsSemaphore) {
            Admin tempAdmin = adminsList.get(username);
            if (tempAdmin != null) {
                return new Admin(tempAdmin);
            }
            return null;
        }
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
        ordersTable.put(identifier, new ArrayList<Order>());
    }

    public void closeCassaSession(String identifier) {
        // flush to disk
        List tempOrderList = ordersTable.get(identifier);
        try {
            dataBackend.saveListOfOrders(identifier, tempOrderList);
        } catch (IOException ex) {
            logger.error("Order list could not be saved", ex);
        }
    }

    public void addNewOrder(String id, Order order) {
        ordersTable.get(id).add(order);
    }

    public void delLastOrder(String id) {
        List tempOrderList = ordersTable.get(id);
        if (tempOrderList.size() >= 0) {
            tempOrderList.remove(tempOrderList.size() -1);
        }
    }

    public void terminate() {
    }

    //----------------------------//
    // Administration Client handle.
    //----------------------------//

    public void saveNewArticlesList(ArticlesList list) {
        synchronized (listArticlesSemaphore) {
            articlesList = new ArticlesList(list.list);
            
            synchronized (listProgressiviSemaphore) {
                progressivesList = new TreeMap<String, Integer>();

                for (Article article : list.list) {
                    if (article instanceof ArticleWithPreparation) {
                        progressivesList.put(article.getNome(), 0);
                    }
                }
            }
            try {
                dataBackend.saveArticlesList(articlesList);
            } catch (IOException ex) {
                logger.error("could not save articles list", ex);
            }
        }
    }

    /**
     * Used to ask "n" progressive numbers to associate to the BeniConPreparazione
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
}
