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
public class Evento {

    /**
     * Titolo dell'evento.
     */
    String titolo;

    /**
     * Data in cui si svolge.
     */
    Date data;

    /**
     * Lista degli ordini avuti all'evento
     */
    List<Ordine> listaOrdini;
}
