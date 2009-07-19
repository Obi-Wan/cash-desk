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

package gestionecassa.server.datamanager.backends;

import gestionecassa.Admin;
import gestionecassa.ArticleWithOptions;
import gestionecassa.Article;
import gestionecassa.Cassiere;
import gestionecassa.ArticlesList;
import gestionecassa.Log;
import gestionecassa.ordine.Order;
import gestionecassa.ordine.EntrySingleOption;
import gestionecassa.ordine.EntrySingleArticle;
import gestionecassa.ordine.EntrySingleArticleWithOption;
import gestionecassa.server.datamanager.BackendAPI_1;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * XML based backend for saving/loading data.
 *
 * @author ben
 */
public class XmlDataBackend implements BackendAPI_1 {

    Logger logger;

    final String xmlDataPath = "XMLDB" + java.io.File.separator;

    final String listaBeniFile = xmlDataPath + "listaBeni.xml";

    final String listaAdminFile = xmlDataPath + "listaAdmin.xml";

    final String listaCassiereFile = xmlDataPath + "listaCassa.xml";

    /**
     * Default constructor
     */
    public XmlDataBackend() {
        this.logger = Log.GESTIONECASSA_SERVER_DATAMANAGER_XML;
    }

    //------------------------//
    // Lista beni functions
    //------------------------//

    public void saveArticlesList(ArticlesList lista) throws IOException {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "beni" );
        List<Article> listaVera = lista.list;
        for (Article beneVenduto : listaVera) {
            Element tempBene = root.addElement("bene");
            tempBene.addElement("nome").addText(beneVenduto.getNome());
            tempBene.addElement("prezzo").addText(beneVenduto.getPrezzo()+"");
            tempBene.addElement("id").addText(beneVenduto.getId()+"");
            
            if (beneVenduto.hasOptions()) {
                tempBene.addAttribute("opzioni", "true");
                Element tempOpzioni = tempBene.addElement("opzioni");

                List<String> listOpzioni = ((ArticleWithOptions)beneVenduto).getOptions();
                for (String nomeOpzione : listOpzioni) {
                    tempOpzioni.addElement("opzione").addText(nomeOpzione);
                }
            } else {
                tempBene.addAttribute("opzioni", "false");
            }
        }

        // for debug purposes
        OutputFormat format = OutputFormat.createPrettyPrint();

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(listaBeniFile),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    public List<Article> loadArticlesList() throws IOException {
        List<Article> output = new ArrayList<Article>();

        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(listaBeniFile);
        } catch (DocumentException ex) {
            logger.warn("Error while reading/parsing the file", ex);
            throw new IOException("I was not able to read/parse the file", ex);
        }

        Element nodoRoot = document.getRootElement();
        
        for (Object bene : nodoRoot.elements("bene")) {
            Element tempRefBene = (Element)bene;
            Article tempBene;

            String nome = tempRefBene.element("nome").getText();
            double prezzo = new Double(tempRefBene.element("prezzo").getText()).doubleValue();
            int id = new Integer(tempRefBene.element("id").getText()).intValue();

            if (tempRefBene.attribute("opzioni").getValue().equals("true")) {

                List<String> opzioni = new ArrayList<String>();
                for (Object opzione : tempRefBene.element("opzioni").elements("opzione")) {
                    opzioni.add(((Element)opzione).getText());
                }

                tempBene = new ArticleWithOptions(id,nome, prezzo, opzioni);
            } else {
                tempBene = new Article(id,nome, prezzo);
            }
            output.add(tempBene);
        }
        
        return output;
    }

    //------------------------//
    // Lista Utenti functions
    //------------------------//

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
        XMLWriter writer = new XMLWriter(new FileWriter(listaAdminFile),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    public List<Admin> loadAdminsList() throws IOException {
        List<Admin> output = new ArrayList<Admin>();

        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(listaAdminFile);
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
        XMLWriter writer = new XMLWriter(new FileWriter(listaCassiereFile),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    public List<Cassiere> loadCassiereList() throws IOException {
        List<Cassiere> output = new ArrayList<Cassiere>();

        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(listaCassiereFile);
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

    public void saveListOfOrders(String id, ConcurrentLinkedQueue<Order> list) throws IOException {

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
        xmlOrder.addElement("data").addText(order.getData().toString());
        xmlOrder.addElement("prezzo_totale").addText(order.getTotalPrice()+"");
        List<EntrySingleArticle> listaBeni = order.getListaBeni();

        for (EntrySingleArticle singleArticle : listaBeni) {
            Element xmlArticle = xmlOrder.addElement("singolo_bene");

            xmlArticle.addElement("nome").addText(singleArticle.bene.getNome());
            xmlArticle.addElement("prezzo").addText(singleArticle.bene.getPrezzo()+"");
            xmlArticle.addElement("numero").addText(singleArticle.numTot+"");

            if (singleArticle.bene.hasOptions()) {
                xmlArticle.addAttribute("opzioni", "true");
                Element xmlOptions = xmlArticle.addElement("opzioni");
                int progressivo =
                        ((EntrySingleArticleWithOption)singleArticle).startProgressivo;
                List<EntrySingleOption> options =
                        ((EntrySingleArticleWithOption)singleArticle).numParziale;

                for (EntrySingleOption option : options) {

                    String stringaProgressivi = new String((progressivo++) + "");
                    for (int i = 1; i < option.numParz; i++) {
                        stringaProgressivi += ", " + progressivo++;
                    }
                    Element xmlOption = xmlOptions.addElement("opzione");
                    xmlOption.addElement("nome").addText(option.nomeOpz);
                    xmlOption.addElement("numero").addText(
                            ""+option.numParz);
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
