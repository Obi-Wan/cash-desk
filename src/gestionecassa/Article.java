
package gestionecassa;

import java.io.Serializable;

/**
 *
 * @author ben
 */
public class Article implements Serializable {

    int id;

    /**
     *
     */
    String nome;

    /**
     *
     */
    double prezzo;

    /**
     * 
     */
    boolean enabled;

    /**
     * Explicit constructor
     *
     * @param nome Name of the good
     * @param prezzo Price
     */
    public Article(int id, String nome, double prezzo) {
        this(id, nome, prezzo, true);
    }

    /**
     * Most explicit constructor
     * 
     * @param nome
     * @param prezzo
     * @param enabled
     */
    public Article(int id, String nome, double prezzo, boolean enabled) {
        this.nome = new String(nome);
        this.prezzo = prezzo;
        this.enabled = enabled;
        this.id = id;
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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getId() {
        return id;
    }
}
