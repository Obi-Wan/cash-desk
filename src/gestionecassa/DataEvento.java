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
public class DataEvento {

    /**
     * Title of this specific date.
     */
    String titoloData;

    /**
     * Data
     */
    Date data;

    /**
     * Lista degli ordini avuti all'evento
     */
    List<Ordine> listaOrdini;
}
