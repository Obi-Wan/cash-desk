/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.server;

import gestionecassa.BeneVenduto;
import gestionecassa.BeneConOpzione;
import gestionecassa.Cassiere;
import gestionecassa.ListaBeni;
import gestionecassa.Persona;
import java.util.ArrayList;
import java.util.List;

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
        List<String> listaOpzioni = new ArrayList();
        listaOpzioni.add("cacca secca");
        listaOpzioni.add("cacca liquida");
        listaBeni.lista.add(new BeneVenduto("fagiolo", 25));
        listaBeni.lista.add(new BeneVenduto("ameba", 35));
        listaBeni.lista.add(new BeneVenduto("merda dello stige", 5.5));
        listaBeni.lista.add(new BeneConOpzione("panino alla", 10.25, listaOpzioni));
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
