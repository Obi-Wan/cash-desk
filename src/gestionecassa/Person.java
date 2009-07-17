
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
     * 
     */
    boolean enabled;

    /**
     * Default constructor
     */
    public Person() {
        this(0, "", "",true);
    }

    /**
     *
     * @param id
     * @param password
     * @param username
     */
    public Person(int id, String username, String password, boolean b) {
        this.id = id;
        this.password = new String(password);
        this.username = new String(username);
        this.enabled = b;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
