package gestionecassa.ordine;

import java.io.Serializable;

public class EntrySingleOption implements Serializable {

    public String nomeOpz;
    public int numParz;

    public EntrySingleOption(String nomeOpz, int card) {
        super();
        this.nomeOpz = nomeOpz;
        this.numParz = card;
    }
}
