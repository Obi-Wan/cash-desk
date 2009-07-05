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

import gestionecassa.ListaBeni;
import gestionecassa.ordine.Ordine;
import gestionecassa.ordine.recordSingolaOpzione;
import gestionecassa.ordine.recordSingoloBene;
import gestionecassa.ordine.recordSingoloBeneConOpzione;
import gestionecassa.server.datamanager.BackendAPI_1;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author ben
 */
public class XmlDataBackend implements BackendAPI_1 {

    final String xmlDataPath = "XMLDB" + java.io.File.separator;

    final String listaBeniFile = xmlDataPath + "listaBeni.xml";

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
                tempBene.addElement("nome").addText(singoloBene.bene.getNome());
                tempBene.addElement("prezzo").addText(singoloBene.bene.getPrezzo()+"");
                tempBene.addElement("numero").addText(singoloBene.numTot+"");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ListaBeni loadListaBeni() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
