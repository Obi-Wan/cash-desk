/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestionecassa;

import gestionecassa.exceptions.DuplicateArticleException;
import gestionecassa.exceptions.DuplicateGroupException;
import gestionecassa.exceptions.NotExistingGroupException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 *
 * @author ben
 */
public class ArticlesList implements Serializable {

    /**
     * List of the articles sold.
     */
    List<ArticleGroup> groups;

    Map<String, Integer> grNum;

    Map<String, Article> articles;

    /**
     * Default constructor
     */
    public ArticlesList() {
        this(new Vector<ArticleGroup>());
    }

    /**
     * Costruttore che riceve in input una list che si memorizza.
     *
     * @param list
     */
    public ArticlesList(Collection<ArticleGroup> list) {
        this.groups = new Vector<ArticleGroup>(list);
        generateArtMap();
        generateNumMap();
    }

    /**
     * Copy constructor
     * 
     * @param list
     */
    public ArticlesList(ArticlesList list) {
        this.groups = new Vector<ArticleGroup>(list.groups);
        this.grNum = new TreeMap<String, Integer>(list.grNum);
        this.articles = new TreeMap<String, Article>(list.articles);
    }

    private void generateNumMap() {
        grNum = new TreeMap<String, Integer>();
        for (int i = 0; i < groups.size(); i++) {
            grNum.put(groups.get(i).groupName, i);
        }
    }

    private void generateArtMap() {
        articles = new TreeMap<String, Article>();
        for (ArticleGroup artGroup : groups) {
            for (Article article : artGroup.getList()) {
                articles.put(article.name, article);
            }
        }
    }

    /**
     * Similar to toString but leaves it fully functional
     *
     * @return a written description of the list
     */
    public String getPrintableFormat() {
        String output = new String("Lista degli articoli venduti (per gruppi):\n");
        for (int i = 0; i < groups.size(); i++) {
            ArticleGroup artgr = groups.get(i);
            output += String.format("%2d %s\n",i,artgr.getPrintableFormat());
        }
        return output;
    }

    public void addGroup(ArticleGroup group) throws DuplicateGroupException,
            DuplicateArticleException {
        if (!grNum.containsKey(group.groupName)) {
            for (Article article : group.list) {
                if (articles.containsKey(article.name)) {
                    throw new DuplicateArticleException("Duplicated article: "
                            + article.name + " in group that's being added: " + 
                            group.groupName);
                }
            }
            groups.add(group);
            grNum.put(group.groupName, groups.size()-1);
            generateArtMap();
        } else {
            throw new DuplicateGroupException("Already existing group: "
                    + group.groupName);
        }
    }

    /**
     * Adds an article to the specified group
     *
     * @param article The article to add.
     * @param groupName Name of the group.
     */
    public void addArticleToGroup(String groupName, Article article)
            throws DuplicateArticleException, NotExistingGroupException {
        if (articles.containsKey(article.name)) {
            throw new DuplicateArticleException("Duplicate article with name:" +
                    article.name);
        } else if (!grNum.containsKey(groupName)) {
            throw new NotExistingGroupException("No group with name:" +
                    " " + groupName);
        } else {
            articles.put(article.name, article);
            groups.get(grNum.get(groupName)).addArticle(article);
        }
    }

    /**
     * Adds an article to the specified group
     *
     * @param article The article to add.
     * @param groupNum Cardinal number of the group
     */
    public void addArticleToGroup(int groupNum, Article article)
            throws DuplicateArticleException, NotExistingGroupException {
        if (articles.containsKey(article.name)) {
            throw new DuplicateArticleException("Duplicate article with name:" +
                    article.name);
        } else if (groupNum >= 0 && groupNum < groups.size()) {
            throw new NotExistingGroupException("No group with cardinal number:" +
                    " " + groupNum);
        } else {
            articles.put(article.name, article);
            groups.get(groupNum).addArticle(article);
        }
    }

    /**
     * Enables/disables a specified article
     *
     * @param gruop Cardinal number of the group
     * @param pos Position of the article
     * @param enable Enable/disable
     */
    public Article enableArticle(int group, int pos, boolean enable) {
        if ((group >= 0 && group < groups.size()) &&
                ( pos >= 0 && pos < groups.get(group).getList().size()) ) {
            return groups.get(group).enableArticle(pos, enable);
        } else {
            return null;
        }
    }

    /**
     * Enables/disables a specified article
     *
     * @param art The article to modify
     * @param enable Enable/disable
     */
    public Article enableArticle(Article art, boolean enable) {
        if (articles.containsKey(art.name)) {
            return articles.get(art.name).setEnabled(enable);
        } else {
            return null;
        }
    }

//    /**
//     * Moves an article to another group
//     *
//     * @param oldGroup
//     * @param newGroup
//     * @param art
//     *
//     * @return
//     */
//    public Article moveArticleToGroup(int oldGroup, int newGroup, int art) {
//
//        Article temp = list.remove(oldPos);
//        list.add(newPos,temp);
//        return temp;
//    }

    /**
     * Getter for the list
     *
     * @return the list in a <code>java.util.List</code> form.
     */
    final public List<ArticleGroup> getGroupsList() {
        return groups;
    }

    /**
     * Getter for the list
     *
     * @return the list in a <code>java.util.List</code> form.
     */
    final public Collection<Article> getArticlesList() {
        return articles.values();
    }

    public ArticleGroup getGroup(int pos) {
        if (pos >= 0 && pos < groups.size()) {
            return groups.get(pos);
        } else {
            return null;
        }
    }

    public ArticleGroup getGroup(String name) {
        if (grNum.containsKey(name)) {
            return groups.get(grNum.get(name));
        } else {
            return null;
        }
    }

    public int getGroupPos(String name) {
        if (grNum.containsKey(name)) {
            return grNum.get(name);
        } else {
            return -1;
        }
    }

    public ArticlesList getEnabledList() {
        List<ArticleGroup> tempList = new Vector<ArticleGroup>();
        for (ArticleGroup artGr : groups) {
            ArticleGroup tempGroup = new ArticleGroup(artGr.idGroup, artGr.groupName);
            if (tempGroup.isEnabled()) {
                for (Article art : artGr.getList()) {
                    if (art.enabled) {
                        tempGroup.addArticle(art);
                    }
                }
                tempList.add(tempGroup);
            }
        }
        return new ArticlesList(tempList);
    }
}
