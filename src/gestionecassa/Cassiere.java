
package gestionecassa;

/**
 *
 * @author ben
 */
public class Cassiere extends Person {

    /**
     * Copy constructor
     *
     * @param tempPersona
     */
    public Cassiere(Person tempPersona) {
        this(tempPersona.id,tempPersona.username,tempPersona.password,tempPersona.enabled);
    }

    /**
     * Creates a Cassiere from from specified fields
     *
     * @param idCassiere the id of cassiere
     * @param username the username of cassiere
     * @param the password of cassiere.
     */
    public Cassiere(int idCassiere, String username, String password, boolean enalbed) {
        super(idCassiere, username, password,enalbed);
    }

    /**
     * Creates a Cassiere from from specified fields
     *
     * @param idCassiere the id of cassiere
     * @param username the username of cassiere
     * @param the password of cassiere.
     */
    public Cassiere(int idCassiere, String username, String password) {
        super(idCassiere, username, password,true);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Cassiere) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
