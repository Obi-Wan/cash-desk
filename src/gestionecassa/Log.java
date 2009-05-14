package gestionecassa;

import org.apache.log4j.Logger;

/** Class used to log every important or intresting thing.
 *
 * @author 
 */
public class Log {

    /** Logger for package gestionecassa */
    static public final Logger GESTIONECASSA =
            Logger.getLogger("gestionecassa");

    /** Logger for package gestionecassa.amministrazione */
    static public final Logger GESTIONECASSA_AMMINISTRAZIONE =
            Logger.getLogger("gestionecassa.amministrazione");

    /** Logger for package gestionecassa.amministrazione.gui */
    static public final Logger GESTIONECASSA_AMMINISTRAZIONE_GUI =
            Logger.getLogger("gestionecassa.amministrazione.gui");

    /** Logger for package gestionecassa.cassa */
    static public final Logger GESTIONECASSA_CASSA =
            Logger.getLogger("gestionecassa.cassa");

    /** Logger for package gestionecassa.cassa.gui */
    static public final Logger GESTIONECASSA_CASSA_GUI =
            Logger.getLogger("gestionecassa.cassa.gui");

    /** Logger for package gestionecassa.server */
    static public final Logger GESTIONECASSA_SERVER =
            Logger.getLogger("gestionecassa.server");
    
    /** Logger for package gestionecassa.server.dbm */
    static public final Logger GESTIONECASSA_SERVER_DBM =
            Logger.getLogger("gestionecassa.server.dbm");

    static {
        // config da file
        org.apache.log4j.xml.DOMConfigurator.configure("src/gestionecassa/logconfig.xml");

        // config manuale
        if (GESTIONECASSA.isDebugEnabled()) {
            String messaggio= "";
            // codice per comporre messaggio oneroso
            GESTIONECASSA.debug(messaggio);
        }
        //GESTIONECASSA.setLevel(Level.DEBUG);
    }

}













