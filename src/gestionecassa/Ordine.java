/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ben
 */
public class Ordine {

    /**
     * indice cardinale dell'ordine effettuato
     */
    int nOrdine;

    /**
     * Data/ora in cui è stato effettuato l'ordine
     */
    Date data;

    /**
     * Lista dei beni per il singolo ordine.
     */
    List<BeneVenduto> listaBeni;
}
