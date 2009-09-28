
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
     * Copy construcotr
     * 
     * @param old The person to copy from
     */
    public Person(Person old) {
        this(old.id, old.username, old.password, old.enabled);
    }

    /**
     * Explicit construcotr
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

    /**
     * Checks if this <code>Person</code> is enabled.
     * @return <code>true</code> if enabled, <code>false</code> otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof Person) && 
                (this.id == ((Person)obj).id) &&
                (this.username.equals(((Person)obj).username)) &&
                (this.password.equals(((Person)obj).password)));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }
}
