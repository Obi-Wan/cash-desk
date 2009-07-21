package gestionecassa.order;

import java.io.Serializable;

public class EntrySingleOption implements Serializable {

    public String optionName;
    public int numPartial;

    public EntrySingleOption(String nomeOpz, int card) {
        super();
        this.optionName = nomeOpz;
        this.numPartial = card;
    }
}
