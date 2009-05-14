/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ben
 */
public class ListaBeni implements Serializable {

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

    /**
     * Similar to toString but leaves it fully functional
     *
     * @return a written description of the list
     */
    public String getPrintableFormat() {
        String output = new String("Lista dei beni venduti:\n");
        for (BeneVenduto beneVenduto : lista) {
            output += new String(beneVenduto.getPrintableFormat() + "\n");
        }
        return output;
    }
}
