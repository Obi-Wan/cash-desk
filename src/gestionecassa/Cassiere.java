/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

/**
 *
 * @author ben
 */
public class Cassiere extends Persona {

    /**
     * Creates a Cassiere from from specified fields
     *
     * @param idCassiere the id of cassiere
     * @param username the username of cassiere
     * @param the password of cassiere.
     */
    public Cassiere(int idCassiere,String username,String password) {
        super(idCassiere, password, username);
    }
}
