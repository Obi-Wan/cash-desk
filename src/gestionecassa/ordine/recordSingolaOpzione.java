package gestionecassa.ordine;

import java.io.Serializable;

public class recordSingolaOpzione implements Serializable {

    public String nomeOpz;
    public int numParz;

    public recordSingolaOpzione(String nomeOpz, int card) {
        super();
        this.nomeOpz = nomeOpz;
        this.numParz = card;
    }
}
