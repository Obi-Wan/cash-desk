/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ben
 */
public class ArticlesList implements Serializable {

    /**
     * Lista dei beni vendibili
     */
    public List<Article> list;

    /**
     * Costruttore di default
     */
    public ArticlesList() {
        this(new ArrayList<Article>());
    }

    /**
     * Costruttore che riceve in input una list che si memorizza.
     *
     * @param list
     */
    public ArticlesList(List<Article> lista) {
        this.list = new ArrayList<Article>(lista);
    }

    /**
     * Similar to toString but leaves it fully functional
     *
     * @return a written description of the list
     */
    public String getPrintableFormat() {
        String output = new String("Lista degli articoli venduti:\n");
        for (int i = 0; i < list.size(); i++) {
            Article article = list.get(i);
            output += String.format("%2d %s\n",i,article.getPrintableFormat());
        }
        return output;
    }
}
