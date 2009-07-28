/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

import java.util.List;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class OrganizedEvent {

    /**
     * Titolo dell'evento.
     */
    public String name;

    /**
     * Lista delle date dell'evento.
     */
    public List<EventDate> datesList;

    public OrganizedEvent(String title) {
        this.name = new String(title);
        this.datesList = new Vector<EventDate>();
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof OrganizedEvent) &&
                (this.name.equals(((OrganizedEvent)obj).name)) &&
                (this.datesList.equals(((OrganizedEvent)obj).datesList))
                );
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + (this.datesList != null ? this.datesList.hashCode() : 0);
        return hash;
    }
}
