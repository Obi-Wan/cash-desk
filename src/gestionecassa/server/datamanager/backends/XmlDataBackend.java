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

import gestionecassa.BeneConOpzione;
import gestionecassa.BeneVenduto;
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
            List<recordSingoloBene> listaBeni = ordine.getListaBeni();

            for (recordSingoloBene singoloBene : listaBeni) {
                Element tempBene = temp.addElement("singolo_bene");
                
                tempBene.addElement("nome").addText(singoloBene.bene.getNome());
                tempBene.addElement("prezzo").addText(singoloBene.bene.getPrezzo()+"");
                tempBene.addElement("numero").addText(singoloBene.numTot+"");

                if (singoloBene.bene.hasOptions()) {
                    tempBene.addAttribute("opzioni", "true");
                    Element tempOpzioni = tempBene.addElement("opzioni");
                    List<recordSingolaOpzione> listaOpzioni =
                            ((recordSingoloBeneConOpzione)singoloBene).numParziale;

                    for (recordSingolaOpzione singolaOpzione : listaOpzioni) {
                        Element tempOpzione = tempOpzioni.addElement("opzione");
                        tempOpzione.addElement("nome").addText(singolaOpzione.nomeOpz);
                        tempOpzione.addElement("numero").addText(
                                ""+singolaOpzione.numParz);
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
}