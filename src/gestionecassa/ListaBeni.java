/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ben
 */
public class ListaBeni {

    /**
     * Lista dei beni vendibili
     */
    public List<BeneVenduto> lista;

    /**
     * Costruttore di default
     */
    public ListaBeni() {
        this(new ArrayList<BeneVenduto>());
    }

    /**
     * Costruttore che riceve in input una lista che si memorizza.
     *
     * @param lista
     */
    public ListaBeni(List<BeneVenduto> lista) {
        this.lista = new ArrayList<BeneVenduto>(lista);
    }
}
