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
     * List of the articles sold.
     */
    List<Article> list;

    /**
     * Default constructor
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
     * Adds an article
     * 
     * @param article The article to add.
     */
    public void addArticle(Article article) {
        list.add(article);
    }

    /**
     * Enables/disables a specified article
     *
     * @param pos Position of the article
     * @param enable Enable/disable
     */
    public Article enableArticle(int pos, boolean enable) {
        return list.get(pos).setEnabled(enable);
    }

    /**
     * Enables/disables a specified article
     *
     * @param art The article to modify
     * @param enable Enable/disable
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
     * Moves an article
     *
     * @param oldPos Old position
     * @param newPos New position
     */
    public Article moveArticleAt(int oldPos, int newPos) {
        Article temp = list.remove(oldPos);
        list.add(newPos,temp);
        return temp;
    }

    /**
     * Moves the specified article
     *
     * @param a Article to move
     * @param newPos New position
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
     * Getter for the list
     * 
     * @return the list in a <code>java.util.List</code> form.
     */
    final public List<Article> getList() {
        return list;
    }
}
