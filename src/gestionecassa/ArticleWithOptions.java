
package gestionecassa;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ben
 */
public class ArticleWithOptions extends Article implements ArticleWithPreparation {

    /**
     * Lista delle options disponibili per questo bene.
     */
    List<String> options;

    /**
     * Explicit Constructor
     *
     * @param nome
     * @param prezzo
     * @param options
     */
    public ArticleWithOptions(int id, String name, double price, List<String> options) {
        super(id,name, price);
        this.options = new ArrayList<String>(options);
    }

    /**
     * Explicit Constructor
     *
     * @param nome
     * @param prezzo
     * @param options
     */
    public ArticleWithOptions(int id, String nome, double prezzo, List<String> options, boolean b) {
        super(id, nome, prezzo, b);
        this.options = new ArrayList<String>(options);
    }

    /**
     * Explicit base constructor
     *
     * @param nome
     * @param prezzo
     */
    public ArticleWithOptions(int id, String nome, double prezzo) {
        this(id, nome, prezzo, new ArrayList<String>());
    }

    /**
     * Tells us wether it has opsions or not.
     *
     * @return true
     */
    @Override
    public boolean hasOptions() {
        return true;
    }

    @Override
    public String getPrintableFormat() {
        String output = new String(super.getPrintableFormat() + "\n");
        for (String string : options) {
            output += new String("  .\t "+ string + "\n");
        }
        output += "--";
        return output;
    }

    /**
     * 
     * @return
     */
    public List<String> getOptions() {
        return options;
    }

    @Override
    public boolean equals(Object obj) {
        return ((obj instanceof ArticleWithOptions) &&
                (options.equals(((ArticleWithOptions)obj).options)) &&
                super.equals(obj));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + (this.options != null ? this.options.hashCode() : 0);
        return hash + super.hashCode();
    }
}
