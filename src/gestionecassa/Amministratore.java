/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

/**
 *
 * @author ben
 */
public class Amministratore extends Persona {

    /**
     * Creates an Amministratore from from specified fields
     * 
     * @param idAmministratore the id of Amministratore
     * @param username the username of the Amministratore
     * @param the password of the Amministratore
     */
    public Amministratore(int idAmministratore,String username,String password) {
        super(idAmministratore, password, username);
    }
}
