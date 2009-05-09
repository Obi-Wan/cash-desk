/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.cassa;

import gestionecassa.Log;
import gestionecassa.clients.Luogo;

/**
 *
 * @author ben
 */
public class Cassa extends Luogo implements CassaAPI {

    /** Local store of himself, with restrictions */
    static CassaAPI businessLogicLocale;

    public static synchronized CassaAPI crea() {
        // Fase di set-up
        if (businessLogicLocale == null) {
            Cassa tempClient = new Cassa(System.getenv("HOSTNAME"));
            businessLogicLocale = tempClient;
        }
        return businessLogicLocale;
    }

    /** Creates a new instance of Cassa. */
    private Cassa(String nomeLuogo) {
        super(nomeLuogo, Log.GESTIONECASSA_CASSA);
    }

    /** The main of the Cassiere Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {

        // cominciamo l'esecuzione del thread principale
        Cassa.crea().avvia();

    }
    
    /**
     * The Real Main of he application.
     */
    @Override
    public void run() {
        // avvia la fase di login
        //this.showFormLogin();
        // Comincia l'esecuzione normale
        super.run();
    }
}
