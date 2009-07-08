
package gestionecassa;

/**
 *
 * @author ben
 */
public class Person {

    /**
     * id that identifies Cassiere.
     */
    int id;

    /**
     *
     */
    String username;

    /**
     *
     */
    String password;

    /**
     * Default constructor
     */
    public Person() {
        this(0, "", "");
    }

    /**
     *
     * @param id
     * @param password
     * @param username
     */
    public Person(int id, String username, String password) {
        this.id = id;
        this.password = new String(password);
        this.username = new String(username);
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @return
     */
    public String getUsername() {
        return username;
    }

}
