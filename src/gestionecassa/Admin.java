package gestionecassa;

/**
 *
 * @author ben
 */
public class Admin extends Person {

    /**
     * Copy constructor
     *
     * @param tempPersona
     */
    public Admin(Person tempPerson) {
        this(tempPerson.id, tempPerson.username, tempPerson.password, tempPerson.enabled);
    }

    /**
     * Creates an Admin from from specified fields
     *
     * @param idAdmin the id of Admin
     * @param username the username of the Admin
     * @param the password of the Admin
     */
    public Admin(int idAdmin, String username, String password, boolean enabled) {
        super(idAdmin, username, password,enabled);
    }

    /**
     * Creates an Admin from from specified fields
     *
     * @param idAdmin the id of Admin
     * @param username the username of the Admin
     * @param the password of the Admin
     */
    public Admin(int idAdmin, String username, String password) {
        super(idAdmin, username, password,true);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Admin) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
