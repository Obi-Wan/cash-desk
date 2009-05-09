/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

/**
 *
 * @author ben
 */
public class BeneVenduto {

    String nome;
    float prezzo;

    public BeneVenduto() {
        this("",0);
    }

    public BeneVenduto(String nome, float prezzo) {
        this.nome = new String(nome);
        this.prezzo = prezzo;
    }
}
