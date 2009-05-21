/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.BeneVenduto;
import gestionecassa.Cassiere;
import gestionecassa.ListaBeni;
import gestionecassa.Persona;

/**
 *
 * @author ben
 */
public class DataManager {

    /**
     * list of handled good
     */
    ListaBeni listaBeni;

    /**
     * Default constructor
     */
    public DataManager() {
        listaBeni = new ListaBeni();

        //FIXME solo per test
        listaBeni.lista.add(new BeneVenduto("fagiolo", 25));
        listaBeni.lista.add(new BeneVenduto("ameba", 35));
    }

    /**
     *
     * @param user
     * @return
     */
    int registraUtente(Persona user) {
        return 0;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    Persona verificaUsername(String username, String password) {
        return new Cassiere(0,username,password);
    }

    /**
     *
     * @return
     */
    final ListaBeni getCurrentListaBeni() {
        return listaBeni;
    }
}
