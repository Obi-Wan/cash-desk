/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.exceptions;

/**
 *
 * @author ben
 */
public class ActorAlreadyExistingException extends Exception {

    /**
     * Creates a new instance of <code>ActorAlreadyExistingException</code> without detail message.
     */
    public ActorAlreadyExistingException() {
    }


    /**
     * Constructs an instance of <code>ActorAlreadyExistingException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ActorAlreadyExistingException(String msg) {
        super(msg);
    }
}
