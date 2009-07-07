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
import gestionecassa.BeneConOpzione;
import gestionecassa.BeneVenduto;
import gestionecassa.Cassiere;
import gestionecassa.ListaBeni;
import gestionecassa.Log;
import gestionecassa.ordine.Ordine;
import gestionecassa.ordine.recordSingolaOpzione;
import gestionecassa.ordine.recordSingoloBene;
import gestionecassa.ordine.recordSingoloBeneConOpzione;
import gestionecassa.server.datamanager.BackendAPI_1;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    public void saveListaOrdini(String id, List<Ordine> lista) throws IOException {

        final String timestamp = new SimpleDateFormat(
                "yyyy-MM-dd_HH-mm-ss", Locale.ITALIAN).format(new Date());
        String fileName = xmlDataPath + id + "_" + timestamp + ".xml";

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "orders" );
        root.addAttribute("timestamp", timestamp);

        for (Ordine ordine : lista) {
            Element temp = root.addElement("ordine");
            temp.addElement("data").addText(ordine.getData().toString());
            temp.addElement("prezzo_totale").addText(ordine.getTotalPrize()+"");
            List<recordSingoloBene> listaBeni = ordine.getListaBeni();

            for (recordSingoloBene singoloBene : listaBeni) {
                Element tempBene = temp.addElement("singolo_bene");
                
                tempBene.addElement("nome").addText(singoloBene.bene.getNome());
                tempBene.addElement("prezzo").addText(singoloBene.bene.getPrezzo()+"");
                tempBene.addElement("numero").addText(singoloBene.numTot+"");

                if (singoloBene.bene.hasOptions()) {
                    tempBene.addAttribute("opzioni", "true");
                    Element tempOpzioni = tempBene.addElement("opzioni");
                    int progressivo =
                            ((recordSingoloBeneConOpzione)singoloBene).startProgressivo;
                    List<recordSingolaOpzione> listaOpzioni =
                            ((recordSingoloBeneConOpzione)singoloBene).numParziale;

                    for (recordSingolaOpzione singolaOpzione : listaOpzioni) {

                        String stringaProgressivi = new String((progressivo++) + "");
                        for (int i = 1; i < singolaOpzione.numParz; i++) {
                            stringaProgressivi += ", " + progressivo++;
                        }
                        Element tempOpzione = tempOpzioni.addElement("opzione");
                        tempOpzione.addElement("nome").addText(singolaOpzione.nomeOpz);
                        tempOpzione.addElement("numero").addText(
                                ""+singolaOpzione.numParz);
                        tempOpzione.addElement("n_progressivi").addText(stringaProgressivi);
                    }
                } else {
                    tempBene.addAttribute("opzioni", "false");
                }
            }
        }

        // for debug purposes
        OutputFormat format = OutputFormat.createPrettyPrint();

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(fileName),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    //------------------------//
    // Lista beni functions
    //------------------------//

    public void saveListaBeni(ListaBeni lista) throws IOException {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "beni" );
        List<BeneVenduto> listaVera = lista.lista;
        for (BeneVenduto beneVenduto : listaVera) {
            Element tempBene = root.addElement("bene");
            tempBene.addElement("nome").addText(beneVenduto.getNome());
            tempBene.addElement("prezzo").addText(beneVenduto.getPrezzo()+"");
            
            if (beneVenduto.hasOptions()) {
                tempBene.addAttribute("opzioni", "true");
                Element tempOpzioni = tempBene.addElement("opzioni");

                List<String> listOpzioni = ((BeneConOpzione)beneVenduto).getOpzioni();
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

    public List<BeneVenduto> loadListaBeni() throws IOException {
        List<BeneVenduto> output = new ArrayList<BeneVenduto>();

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
            BeneVenduto tempBene;

            String nome = tempRefBene.element("nome").getText();
            double prezzo = new Double(tempRefBene.element("prezzo").getText()).doubleValue();

            if (tempRefBene.attribute("opzioni").getValue().equals("true")) {

                List<String> opzioni = new ArrayList<String>();
                for (Object opzione : tempRefBene.element("opzioni").elements("opzione")) {
                    opzioni.add(((Element)opzione).getText());
                }

                tempBene = new BeneConOpzione(nome, prezzo, opzioni);
            } else {
                tempBene = new BeneVenduto(nome, prezzo);
            }
            output.add(tempBene);
        }
        
        return output;
    }

    //------------------------//
    // Lista Utenti functions
    //------------------------//

    public void saveListaAdmin(Collection<Admin> lista) throws IOException {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "admins" );
        
        for (Admin amministratore : lista) {
            Element tempAdmin = root.addElement("admin");
            tempAdmin.addElement("username").addText(amministratore.getUsername());
            tempAdmin.addElement("password").addText(amministratore.getPassword());
        }

        // for debug purposes
        OutputFormat format = OutputFormat.createPrettyPrint();

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(listaAdminFile),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    public List<Admin> loadListaAdmin() throws IOException {
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
            output.add(new Admin(output.size(), username, password));
        }

        return output;
    }

    public void saveListaCassiere(Collection<Cassiere> lista) throws IOException {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "cassieri" );
        
        for (Cassiere cassiere : lista) {
            Element tempAdmin = root.addElement("cassiere");
            tempAdmin.addElement("username").addText(cassiere.getUsername());
            tempAdmin.addElement("password").addText(cassiere.getPassword());
        }

        // for debug purposes
        OutputFormat format = OutputFormat.createPrettyPrint();

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(listaCassiereFile),format);
        //XMLWriter writer = new XMLWriter(new FileWriter(fileName));
        writer.write( document );
        writer.close();
    }

    public List<Cassiere> loadListaCassiere() throws IOException {
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

        for (Object admin : nodoRoot.elements("cassiere")) {
            Element tempRefAdmin = (Element)admin;

            String username = tempRefAdmin.element("username").getText();
            String password = tempRefAdmin.element("password").getText();
            output.add(new Cassiere(output.size(), username, password));
        }

        return output;
    }
}
