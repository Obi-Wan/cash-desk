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
    BackendAPI_1 dataBackend;

    /**
     * List of registered users
     */
    TreeMap<String,Cassiere> listaCassieri;

    /**
     * List of registered users
     */
    TreeMap<String,Admin> listaAdmins;

    /**
     *
     */
    ConcurrentHashMap<String, List<Order> > tabellaOrdini;

    /**
     * list of handled good
     */
    ArticlesList listaBeni;

    /**
     * For the goods with time of preparation is nice to have a number of
     * corrispondence between our ticket and the one held by the cooker.
     *
     * NOTE: in this implementation it is resetted everytime the server starts/stops
     */
    TreeMap<String, Integer> listaProgressivi;

    /**
     * Semaphore for the list of users
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listaAdminsSemaphore =
            new String("AdminsSemaphore" + System.currentTimeMillis());

    /**
     * Semaphore for the list of users
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listaCassieriSemaphore =
            new String("CassieriSemaphore" + System.currentTimeMillis());

    /**
     * Semaphore for the list of goods
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listaBeniSemaphore =
            new String("BeniSemaphore" + System.currentTimeMillis());

    /**
     * Semaphore for the list of goods
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listaProgressiviSemaphore =
            new String("ProgressiviSemaphore" + System.currentTimeMillis());

    /**
     * Default constructor
     *
     * @param dataBackend 
     */
    public DataManager(BackendAPI_1 dataBackend) {
        this.dataBackend = dataBackend;
        this.logger = Log.GESTIONECASSA_SERVER_DATAMANAGER;

        tabellaOrdini = new ConcurrentHashMap<String, List<Order>>();

        loadListaUtenti();

        loadListaBeni();
    }

    /**
     * 
     */
    private void loadListaUtenti() {
        synchronized (listaAdminsSemaphore) {
            listaAdmins = new TreeMap<String, Admin>();
            try {
                List<Admin> listaAdmin = dataBackend.loadListaAdmin();
                for (Admin amministratore : listaAdmin) {
                    listaAdmins.put(amministratore.getUsername(), amministratore);
                }
            } catch (IOException ex) {
                logger.warn("reading Admins from file failed, creating a new " +
                        "blank/default list", ex);

                registraUtente(new Admin(listaAdmins.size(), "admin", "password"));
            }
        }
        synchronized (listaCassieriSemaphore) {
            listaCassieri = new TreeMap<String, Cassiere>();
            try {
                List<Cassiere> listaCassiere = dataBackend.loadListaCassiere();
                for (Cassiere cassiere : listaCassiere) {
                    listaCassieri.put(cassiere.getUsername(), cassiere);
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
    private void loadListaBeni() {
        synchronized (listaBeniSemaphore) {
            try {
                List<Article> lista = dataBackend.loadListaBeni();
                listaBeni = new ArticlesList(lista);

                synchronized (listaProgressiviSemaphore) {
                    listaProgressivi = new TreeMap<String, Integer>();

                    for (Article beneVenduto : lista) {
                        if (beneVenduto instanceof ArticleWithPreparation) {
                            listaProgressivi.put(beneVenduto.getNome(), 0);
                        }
                    }
                }
            } catch (IOException ex) {
                logger.warn("ListaBeni could not be loaded, starting up a" +
                        " new clean list.", ex);
                
                listaBeni = new ArticlesList();
            }
        }
    }

    /**
     *
     * @param user
     * @return
     */
    public void registraUtente(Person user) {
        if (user instanceof Cassiere) {
            synchronized (listaCassieriSemaphore) {
                listaCassieri.put(user.getUsername(), (Cassiere)user);
                try {
                    dataBackend.saveListaCassiere(listaCassieri.values());
                } catch (IOException ex) {
                    logger.error("could not save the new Cassieri list", ex);
                }
            }
        } else if (user instanceof Admin) {
            synchronized (listaAdminsSemaphore) {
                listaAdmins.put(user.getUsername(), (Admin)user);
                try {
                    dataBackend.saveListaAdmin(listaAdmins.values());
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
    public Person verificaUsername(String username) {
        synchronized (listaCassieriSemaphore) {
            Cassiere tempCassiere = listaCassieri.get(username);
            if (tempCassiere != null) {
                return new Cassiere(tempCassiere);
            }
        }
        synchronized (listaAdminsSemaphore) {
            Admin tempAdmin = listaAdmins.get(username);
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
    public ArticlesList getCurrentListaBeni() {
        synchronized (listaBeniSemaphore) {
            return listaBeni;
        }
    }

    //----------------------------//
    // Cassa Client handle.
    //----------------------------//

    public void createNewCassaSession(String identifier) {
        tabellaOrdini.put(identifier, new ArrayList<Order>());
    }

    public void closeCassaSession(String identifier) {
        // flush to disk
        List tempOrderList = tabellaOrdini.get(identifier);
        try {
            dataBackend.saveListaOrdini(identifier, tempOrderList);
        } catch (IOException ex) {
            logger.error("Order list could not be saved", ex);
        }
    }

    public void addNewOrder(String id, Order order) {
        tabellaOrdini.get(id).add(order);
    }

    public void delLastOrder(String id) {
        List tempOrderList = tabellaOrdini.get(id);
        if (tempOrderList.size() >= 0) {
            tempOrderList.remove(tempOrderList.size() -1);
        }
    }

    public void terminate() {
    }

    //----------------------------//
    // Amministrazione Client handle.
    //----------------------------//

    public void saveNewListaBeni(ArticlesList lista) {
        synchronized (listaBeniSemaphore) {
            listaBeni = new ArticlesList(lista.lista);
            
            synchronized (listaProgressiviSemaphore) {
                listaProgressivi = new TreeMap<String, Integer>();

                for (Article beneVenduto : lista.lista) {
                    if (beneVenduto instanceof ArticleWithPreparation) {
                        listaProgressivi.put(beneVenduto.getNome(), 0);
                    }
                }
            }
            try {
                dataBackend.saveListaBeni(listaBeni);
            } catch (IOException ex) {
                logger.error("could not save lista beni", ex);
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
    public int getNProgressivo(String nomeBene, int n) {
        synchronized (listaProgressiviSemaphore) {
            Integer progressivo = listaProgressivi.get(nomeBene);
            if (progressivo == null) {
                throw new RuntimeException("ho trovato un nome che ci sarebbe " +
                        "dovuto essere nella lista dei progressivi ma che" +
                        " non c'era!!!");
            }
            listaProgressivi.put(nomeBene, progressivo+n);
            return progressivo.intValue();
        }
    }
}
