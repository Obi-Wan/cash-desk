/*
 * XmlDataBackend.java
 * 
 * Copyright (C) 2009 Nicola Roberto Viganò
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package gestionecassa.backends;

import gestionecassa.Admin;
import gestionecassa.Article;
import gestionecassa.ArticleGroup;
import gestionecassa.ArticleOption;
import gestionecassa.Cassiere;
import gestionecassa.ArticlesList;
import gestionecassa.Log;
import gestionecassa.order.PairObjectInteger;
import gestionecassa.order.Order;
import gestionecassa.order.EntrySingleArticleWithOption;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * XML based backend for saving/loading object.
 *
 * @author ben
 */
public class XmlDataBackend implements BackendAPI_1 {

    Logger logger;

    final String xmlDataPath = "XMLDB" + java.io.File.separator;

    final String ArtListFile = xmlDataPath + "listaBeni.xml";

    final String AdminListFile = xmlDataPath + "listaAdmin.xml";

    final String CassiereListFile = xmlDataPath + "listaCassa.xml";

    /**
     * Default constructor
     */
    public XmlDataBackend() {
        this.logger = Log.GESTIONECASSA_SERVER_DATAMANAGER_XML;
    }

    //------------------------//
    // Lista beni functions
    //------------------------//

    @Override
    public void saveArticlesList(ArticlesList lista) throws IOException {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "article_groups" );
        Collection<ArticleGroup> groups = lista.getGroupsList();
        for (ArticleGroup group : groups) {
            Element tempGroup = root.addElement("group");

            tempGroup.addElement("name").addText(group.getName());
            tempGroup.addElement("id").addText(group.getId()+"");

            for (Article art : group.getList()) {
                Element tempArt = tempGroup.addElement("article");

                tempArt.addElement("name").addText(art.getName());
                tempArt.addElement("price").addText(art.getPrice()+"");
                tempArt.addElement("id").addText(art.getId()+"");

                if (art.hasOptions()) {
                    tempArt.addAttribute("options", "true");
                    Element tempOpzioni = tempArt.addElement("options");

                    Collection<ArticleOption> options = art.getOptions();
                    for (ArticleOption option : options) {
                        tempOpzioni.addElement("option").addText(option.getName());
                    } //FIXME aggiungi altre parti di ArticleOption
                } else {
                    tempArt.addAttribute("options", "false");
                }
            }
        }

        // for debug purposes
        OutputFormat format = OutputFormat.createPrettyPrint();

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(ArtListFile),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    @Override
    public Collection<ArticleGroup> loadArticlesList() throws IOException {
        Collection<ArticleGroup> output = new LinkedList<ArticleGroup>();

        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(ArtListFile);
        } catch (DocumentException ex) {
            logger.warn("Error while reading/parsing the file", ex);
            throw new IOException("I was not able to read/parse the file", ex);
        }

        Element root = document.getRootElement();

        for (Object gr : root.elements("group")) {
            Element tempRefGroup = (Element)gr;

            String g_name = tempRefGroup.element("name").getText();
            int g_id = new Integer(tempRefGroup.element("id").getText()).intValue();

            Collection<Article> tempList = new LinkedList<Article>();
            for (Object art : tempRefGroup.elements("article")) {
                Element tempRefArt = (Element)art;

                String name = tempRefArt.element("name").getText();
                double price = new Double(tempRefArt.element("price").getText()).doubleValue();
                int id = new Integer(tempRefArt.element("id").getText()).intValue();

                Article article;
                if (tempRefArt.attribute("options").getValue().equals("true")) {

                    List<ArticleOption> opts = new LinkedList<ArticleOption>();
                    int idOpt = 0;
                    for (Object opt : tempRefArt.element("options").elements("option")) {
                        opts.add(new ArticleOption(idOpt++,((Element)opt).getText(),true));
                    }

                    article = new Article(id, name, price, true, opts);
                } else {
                    article = new Article(id, name, price);
                }
                tempList.add(article);
            }
            output.add(new ArticleGroup(g_id, g_name, true, tempList));
        }
        
        return output;
    }

    //------------------------//
    // Lista Utenti functions
    //------------------------//

    @Override
    public void saveAdminsList(Collection<Admin> lista) throws IOException {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "admins" );
        
        for (Admin admin : lista) {
            Element tempAdmin = root.addElement("admin");
            tempAdmin.addElement("id").addText(admin.getId()+"");
            tempAdmin.addElement("username").addText(admin.getUsername());
            tempAdmin.addElement("password").addText(admin.getPassword());
        }

        // for debug purposes
        OutputFormat format = OutputFormat.createPrettyPrint();

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(AdminListFile),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    @Override
    public Collection<Admin> loadAdminsList() throws IOException {
        Collection<Admin> output = new LinkedList<Admin>();

        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(AdminListFile);
        } catch (DocumentException ex) {
            logger.warn("Error while reading/parsing the Admin file", ex);
            throw new IOException("I was not able to read/parse the Admin file", ex);
        }

        Element nodoRoot = document.getRootElement();

        for (Object admin : nodoRoot.elements("admin")) {
            Element tempRefAdmin = (Element)admin;

            String username = tempRefAdmin.element("username").getText();
            String password = tempRefAdmin.element("password").getText();
            int id = new Integer(tempRefAdmin.element("id").getText()).intValue();
            output.add(new Admin(id, username, password));
        }

        return output;
    }

    @Override
    public void saveCassiereList(Collection<Cassiere> lista) throws IOException {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "cassieri" );
        
        for (Cassiere cassiere : lista) {
            Element tempCassiere = root.addElement("cassiere");
            tempCassiere.addElement("id").addText(cassiere.getId()+"");
            tempCassiere.addElement("username").addText(cassiere.getUsername());
            tempCassiere.addElement("password").addText(cassiere.getPassword());
        }

        // for debug purposes
        OutputFormat format = OutputFormat.createPrettyPrint();

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(CassiereListFile),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    @Override
    public Collection<Cassiere> loadCassiereList() throws IOException {
        Collection<Cassiere> output = new LinkedList<Cassiere>();

        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(CassiereListFile);
        } catch (DocumentException ex) {
            logger.warn("Error while reading/parsing the Cassieri file", ex);
            throw new IOException("I was not able to read/parse the Cassieri file", ex);
        }

        Element nodoRoot = document.getRootElement();

        for (Object cassiere : nodoRoot.elements("cassiere")) {
            Element tempRefCassiere = (Element)cassiere;

            String username = tempRefCassiere.element("username").getText();
            String password = tempRefCassiere.element("password").getText();
            int id = new Integer(tempRefCassiere.element("id").getText()).intValue();
            output.add(new Cassiere(id, username, password));
        }

        return output;
    }

    //--------------------------//
    // Orders handle functions.
    //--------------------------//

    @Override
    public void saveListOfOrders(String id, Collection<Order> list)
            throws IOException {

        String fileName = xmlDataPath + id + ".xml";

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "orders" );

        for (Order order : list) {
            addOrderToElement(root, order);
        }

        // for debug purposes
        OutputFormat format = OutputFormat.createPrettyPrint();

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(fileName),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    private void addOrderToElement(Element root, Order order) {
        Element xmlOrder = root.addElement("ordine");
        xmlOrder.addElement("data").addText(order.getDate().toString());
        xmlOrder.addElement("prezzo_totale").addText(order.getTotalPrice()+"");
        Collection<PairObjectInteger<Article>> listaBeni = order.getArticlesSold();

        for (PairObjectInteger<Article> singleArticle : listaBeni) {
            Element xmlArticle = xmlOrder.addElement("singolo_bene");

            xmlArticle.addElement("nome").addText(singleArticle.object.getName());
            xmlArticle.addElement("prezzo").addText(singleArticle.object.getPrice()+"");
            xmlArticle.addElement("numero").addText(singleArticle.numTot+"");

            if (singleArticle.object.hasOptions()) {
                xmlArticle.addAttribute("opzioni", "true");
                Element xmlOptions = xmlArticle.addElement("opzioni");
                int progressivo =
                        ((EntrySingleArticleWithOption)singleArticle).startProgressive;
                Collection<PairObjectInteger<ArticleOption>> options =
                        ((EntrySingleArticleWithOption)singleArticle).numPartial;

                for (PairObjectInteger<ArticleOption> option : options) {

                    String stringaProgressivi = (progressivo++) + "";
                    for (int i = 1; i < option.numTot; i++) {
                        stringaProgressivi += ", " + progressivo++;
                    }
                    Element xmlOption = xmlOptions.addElement("opzione");
                    xmlOption.addElement("nome").addText(option.object.getName());
                    xmlOption.addElement("numero").addText(
                            ""+option.numTot);
                    xmlOption.addElement("n_progressivi").addText(stringaProgressivi);
                }
            } else {
                xmlArticle.addAttribute("opzioni", "false");
            }
        }
    }
//
//    //--------------------------//
//    // API Version 1.5
//    //--------------------------//
//
//    public void saveNewOrder(String id, Order order) throws IOException {
//
//        SAXReader reader = new SAXReader();
//        Document document;
//        String fileName = xmlDataPath + id + ".xml";
//        try {
//            document = reader.read(fileName);
//        } catch (DocumentException ex) {
//            logger.warn("Error while reading/parsing the orders file this " +
//                    "session: "+id, ex);
//            throw new IOException("I was not able to read/parse the orders " +
//                    "file of this session: "+id, ex);
//        }
//
//        Element root = document.getRootElement();
//
//        addOrderToElement(root, order);
//
//        // for debug purposes
//        OutputFormat format = OutputFormat.createPrettyPrint();
//
//        // lets write to a file
//        XMLWriter writer = new XMLWriter(new FileWriter(fileName),format);
//        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
//        writer.write( document );
//        writer.close();
//    }
//
//    public void delLastOrder(String id) throws IOException {
//
//        SAXReader reader = new SAXReader();
//        Document document;
//        String fileName = xmlDataPath + id + ".xml";
//        try {
//            document = reader.read(fileName);
//        } catch (DocumentException ex) {
//            logger.warn("Error while reading/parsing the orders file this " +
//                    "session: "+id, ex);
//            throw new IOException("I was not able to read/parse the orders " +
//                    "file of this session: "+id, ex);
//        }
//
//        Element root = document.getRootElement();
//
//        if (root.nodeCount() != 0) {
//            root.remove(root.node(root.nodeCount()-1));
//        }
//
//        // for debug purposes
//        OutputFormat format = OutputFormat.createPrettyPrint();
//
//        // lets write to a file
//        XMLWriter writer = new XMLWriter(new FileWriter(fileName),format);
//        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
//        writer.write( document );
//        writer.close();
//    }
}
