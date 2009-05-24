/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.Amministratore;
import gestionecassa.BeneVenduto;
import gestionecassa.BeneConOpzione;
import gestionecassa.Cassiere;
import gestionecassa.ListaBeni;
import gestionecassa.Persona;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author ben
 */
public class DataManager {

    /**
     * List of registered users
     */
    TreeMap<String,Persona> listaUtenti;

    /**
     * Semaphore for the list of users
     *
     * NOTE: it's randozed to avoid the JVM to make optimizations, which could
     * lead the threads to share the same semaphore.
     */
    static final String listaUtentiSemaphore =
            new String("UsersSemaphore" + System.currentTimeMillis());

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
     */
    public DataManager() {
        synchronized (listaUtentiSemaphore) {
            listaUtenti = new TreeMap<String, Persona>();

            //FIXME solo x test
            listaUtenti.put("bene", new Cassiere(listaUtenti.size(), "bene", "male"));
        }
        synchronized (listaBeniSemaphore) {
            listaBeni = new ListaBeni();

            //FIXME solo per test
            List<String> listaOpzioni = new ArrayList();
            listaOpzioni.add("cacca secca");
            listaOpzioni.add("cacca liquida");
            listaBeni.lista.add(new BeneVenduto("fagiolo", 25));
            listaBeni.lista.add(new BeneVenduto("ameba", 35));
            listaBeni.lista.add(new BeneVenduto("merda dello stige", 5.5));
            listaBeni.lista.add(new BeneConOpzione("panino alla", 10.25, listaOpzioni));
        }
    }

    /**
     *
     * @param user
     * @return
     */
    int registraUtente(Persona user) {
        synchronized (listaUtentiSemaphore) {
            return 0;
        }
    }

    /**
     * Verifies a username exists, and if is the case, if the password is right
     * It creates a new read only copy of the user to prevent problems in syncronization
     *
     * @param username
     * @param password
     *
     * @return 
     */
    final Persona verificaUsername(String username, String password) {
        synchronized (listaUtentiSemaphore) {
            Persona tempPersona = listaUtenti.get(username);
            if (tempPersona instanceof Cassiere) {
                return new Cassiere(tempPersona);
            } else {
                return new Amministratore(tempPersona);
            }
        }
    }

    /**
     *
     * @return
     */
    final ListaBeni getCurrentListaBeni() {
        synchronized (listaBeniSemaphore) {
            return listaBeni;
        }
    }
}
