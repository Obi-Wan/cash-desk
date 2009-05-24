
package gestionecassa;

/**
 *
 * @author ben
 */
public class Cassiere extends Persona {

    /**
     * Copy constructor
     *
     * @param tempPersona
     */
    public Cassiere(Persona tempPersona) {
        this(tempPersona.id,tempPersona.username,tempPersona.password);
    }

    /**
     * Creates a Cassiere from from specified fields
     *
     * @param idCassiere the id of cassiere
     * @param username the username of cassiere
     * @param the password of cassiere.
     */
    public Cassiere(int idCassiere, String username, String password) {
        super(idCassiere, username, password);
    }
}
