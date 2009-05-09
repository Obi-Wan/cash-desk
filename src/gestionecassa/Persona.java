/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

/**
 *
 * @author ben
 */
public class Persona {

    /** id that identifies Cassiere.  */
    int id;

    String password;

    String username;

    public Persona() {
        this(0, "", "");
    }

    public Persona(int id, String password, String username) {
        this.id = id;
        this.password = new String(password);
        this.username = new String(username);
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}
