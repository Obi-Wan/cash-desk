/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class ArticlesList implements Serializable {

    /**
     * Lista dei beni vendibili
     */
    List<Article> list;

    /**
     * Costruttore di default
     */
    public ArticlesList() {
        this(new Vector<Article>());
    }

    /**
     * Costruttore che riceve in input una list che si memorizza.
     *
     * @param list
     */
    public ArticlesList(List<Article> list) {
        this.list = new Vector<Article>(list);
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

    /**
     * Adder
     * 
     * @param article
     */
    public void addArticle(Article article) {
        list.add(article);
    }

    /**
     *
     * @param pos
     * @param enable
     */
    public Article enableArticle(int pos, boolean enable) {
        return list.get(pos).setEnabled(enable);
    }

    /**
     *
     * @param art
     * @param enable
     */
    public Article enableArticle(Article art, boolean enable) {
        for (Article article : list) {
            if (article.equals(art)) {
                article.setEnabled(enable);
                return article;
            }
        }
        return null;
    }

    /**
     *
     * @param oldPos
     * @param newPos
     */
    public Article moveArticleAt(int oldPos, int newPos) {
        Article temp = list.remove(oldPos);
        list.add(newPos,temp);
        return temp;
    }

    /**
     *
     * @param a
     * @param newPos
     */
    public Article moveArticleAt(Article a, int newPos) {
        for (Article article : list) {
            if (article.equals(a)) {
                list.remove(article);
                list.add(newPos, article);
                return article;
            }
        }
        return null;
    }

    /**
     * 
     * @return
     */
    final public List<Article> getList() {
        return list;
    }
}
