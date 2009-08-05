
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
    String name;

    /**
     *
     */
    double price;

    /**
     * 
     */
    boolean enabled;

    /**
     * Explicit constructor
     *
     * @param name Name of the good
     * @param price Price
     */
    public Article(int id, String name, double price) {
        this(id, name, price, true);
    }

    /**
     * Most explicit constructor
     * 
     * @param name
     * @param price
     * @param enabled
     */
    public Article(int id, String name, double price, boolean enabled) {
        this.name = new String(name);
        this.price = price;
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
        return String.format("- %s - %10s -€ %05.2f",
                             (enabled ? "Enabled " : "Disabled"), name, price);
    }

    final public String getName() {
        return name;
    }

    final public double getPrice() {
        return price;
    }

    public Article setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Article) &&
                (this.name.equals(((Article)obj).name)) &&
                (this.price == (((Article)obj).price));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.price) ^
                                (Double.doubleToLongBits(this.price) >>> 32));
        return hash;
    }
}
