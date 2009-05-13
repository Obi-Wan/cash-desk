
package gestionecassa;

/**
 *
 * @author ben
 */
public class BeneVenduto {

    /**
     *
     */
    String nome;

    /**
     *
     */
    float prezzo;

    /**
     * Default constructor
     */
    public BeneVenduto() {
        this("",0);
    }

    /**
     * Explicit constructor
     *
     * @param nome Name of the good
     * @param prezzo Price
     */
    public BeneVenduto(String nome, float prezzo) {
        this.nome = new String(nome);
        this.prezzo = prezzo;
    }

    /**
     * Tells us wheter it has options or not.
     *
     * @return false
     */
    public boolean hasOptions() {
        return false;
    }
}
