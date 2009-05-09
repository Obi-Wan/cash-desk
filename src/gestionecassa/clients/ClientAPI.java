/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients;

import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public interface ClientAPI {

    /**
     * Function that starts the thread.
     */
    void avvia();

    /**
     * Returns the chosen logger for the application
     *
     * @return
     */
    Logger getLogger();

    /**
     * The stopping Method
     */
    void stopClient();
}
