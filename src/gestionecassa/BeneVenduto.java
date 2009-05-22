
package gestionecassa;

import java.io.Serializable;

/**
 *
 * @author ben
 */
public class BeneVenduto implements Serializable {

    /**
     *
     */
    String nome;

    /**
     *
     */
    double prezzo;

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
    public BeneVenduto(String nome, double prezzo) {
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

    /**
     * Sort of toString, but leaving that fully functional
     *
     * @return The string describing the good.
     */
    public String getPrintableFormat() {
        return new String("- " + nome + "\t € " + prezzo);
    }

    final public String getNome() {
        return nome;
    }

    final public double getPrezzo() {
        return prezzo;
    }
}
