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
public class OrganizedEvent {

    /**
     * Titolo dell'evento.
     */
    String titolo;

    /**
     * Data in cui inizia.
     */
    Date dataInizio;

    /**
     * Data in cui finisce.
     */
    Date dataFine;

    /**
     * Lista delle date dell'evento.
     */
    List<EventDate> listaDate;
}
