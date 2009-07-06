/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server.datamanager;

import gestionecassa.Admin;
import gestionecassa.Cassiere;
import gestionecassa.ListaBeni;
import gestionecassa.Log;
import gestionecassa.ordine.Ordine;
import gestionecassa.Persona;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class DataManager implements DMCassaAPI, DMCommonAPI, DMServerAPI,
        DMAmministrazioneAPI {

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
    ConcurrentHashMap<String, List<Ordine> > tabellaOrdini;

    /**
     * The data store backend
     */
    BackendAPI_1 dataBackend;

    /**
     * 
     */
    Logger logger;

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
     * list of handled good
     */
    ListaBeni listaBeni;

    /**
     * Semaphore for the list of goods
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listaBeniSemaphore =
            new String("BeniSemaphore" + System.currentTimeMillis());

    /**
     * Default constructor
     *
     * @param dataBackend 
     */
    public DataManager(BackendAPI_1 dataBackend) {
        this.dataBackend = dataBackend;
        this.logger = Log.GESTIONECASSA_SERVER_DATAMANAGER;

        tabellaOrdini = new ConcurrentHashMap<String, List<Ordine>>();

        loadListaUtenti();

        synchronized (listaBeniSemaphore) {
            try {
                listaBeni = new ListaBeni(dataBackend.loadListaBeni());
            } catch (IOException ex) {
                logger.warn("ListaBeni could not be loaded, starting up a" +
                        " new clean list.", ex);
                
                listaBeni = new ListaBeni();
            }
        }
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
     * @param user
     * @return
     */
    public void registraUtente(Persona user) {
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
    public Persona verificaUsername(String username) {
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
    public ListaBeni getCurrentListaBeni() {
        synchronized (listaBeniSemaphore) {
            return listaBeni;
        }
    }

    //----------------------------//
    // Cassa Client handle.
    //----------------------------//

    public void createNewCassaSession(String identifier) {
        tabellaOrdini.put(identifier, new ArrayList<Ordine>());
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

    public void addNewOrder(String id, Ordine order) {
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

    public void saveNewListaBeni(ListaBeni lista) {
        synchronized (listaBeniSemaphore) {
            listaBeni = new ListaBeni(lista.lista);
            try {
                dataBackend.saveListaBeni(listaBeni);
            } catch (IOException ex) {
                logger.error("could not save lista beni", ex);
            }
        }
    }
}
