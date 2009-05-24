package gestionecassa;

/**
 *
 * @author ben
 */
public class Amministratore extends Persona {

    /**
     * Copy constructor
     *
     * @param tempPersona
     */
    public Amministratore(Persona tempPersona) {
        this(tempPersona.id, tempPersona.username, tempPersona.password);
    }

    /**
     * Creates an Amministratore from from specified fields
     * 
     * @param idAmministratore the id of Amministratore
     * @param username the username of the Amministratore
     * @param the password of the Amministratore
     */
    public Amministratore(int idAmministratore, String username, String password) {
        super(idAmministratore, username, password);
    }
}
