/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa.clients.amministrazione;

import gestionecassa.Log;
import gestionecassa.clients.Luogo;

/**
 *
 * @author ben
 */
public class Amministrazione extends Luogo implements AmministrazioneAPI {

    /**
     * Local store of himself, with restrictions
     */
    static AmministrazioneAPI businessLogicLocale;

    /**
     * Creator of the singleton
     *
     * @return reference to the singleton
     */
    public static synchronized AmministrazioneAPI crea() {
        // Fase di set-up
        if (businessLogicLocale == null) {
            Amministrazione tempClient = new Amministrazione(System.getenv("HOSTNAME"));
            businessLogicLocale = tempClient;
        }
        return businessLogicLocale;
    }

    /**
     * Creates a new instance of Amministrazione.
     */
    private Amministrazione(String nomeLuogo) {
        super(nomeLuogo, Log.GESTIONECASSA_AMMINISTRAZIONE);
    }

    /**
     * The main of the Amministratore Client side application.
     *
     * @param args Useless. Not considered yet.
     */
    public static void main(String[] args) {

        // cominciamo l'esecuzione del thread principale
        Amministrazione.crea().avvia();

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
