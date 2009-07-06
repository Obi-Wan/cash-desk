package gestionecassa;

/**
 *
 * @author ben
 */
public class Admin extends Persona {

    /**
     * Copy constructor
     *
     * @param tempPersona
     */
    public Admin(Persona tempPersona) {
        this(tempPersona.id, tempPersona.username, tempPersona.password);
    }

    /**
     * Creates an Admin from from specified fields
     * 
     * @param idAmministratore the id of Admin
     * @param username the username of the Admin
     * @param the password of the Admin
     */
    public Admin(int idAmministratore, String username, String password) {
        super(idAmministratore, username, password);
    }
}
