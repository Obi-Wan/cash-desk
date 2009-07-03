/*
 * XmlCassaHandler.java
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

package gestionecassa.clients.cassa;

import gestionecassa.XmlHandler;
import gestionecassa.clients.LuogoOptions;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;


/**
 *
 * @author ben
 */
public class XmlCassaHandler implements XmlHandler<LuogoOptions> {

    String optionsFile = new String("cassa.conf.xml");

    @Override
    public void saveOptions(LuogoOptions options) throws IOException {
        String output = "<?xml version='1.0' standalone='yes'?>\n";
        output += "<config version=\"1.0\" client=\"cassa\">\n";
        output += "  <username><![CDATA[" + options.defaultUsername + "]]></username>\n";
        output += "  <server><![CDATA[" + options.defaultServer + "]]></server>\n";
        output += "</config>\n";
        BufferedWriter outputStream =
            new BufferedWriter(new FileWriter(optionsFile));
        outputStream.write(output);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    public void loadOptions(LuogoOptions options) throws IOException,
            ParserConfigurationException, SAXException {
//        FileReader is = new FileReader(optionsFile);
//        BufferedReader inputStream =
//                new BufferedReader(is);
//        String input = "";
//        int temp;
//        while ((temp = inputStream.read()) != -1) {
//            input += (char)temp;
//        }
//        inputStream.close();
//
//        Log.GESTIONECASSA_CASSA.debug(input);

        Document document =
                DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(optionsFile);
        
        NamedNodeMap attributes = document.getAttributes();
        if (attributes != null) {
            for (int count = 0;count < attributes.getLength();count++) {
                if (attributes.item(count).getNodeName().equals("version")) {
                    if (!attributes.item(count).getNodeValue().equals("1.0")) {
                        throw new IOException("Wrong file version!");
                    }
                } else if (attributes.item(count).getNodeName().equals("client")) {
                    if (!attributes.item(count).getNodeValue().equals("cassa")) {
                        throw new IOException("This config file is for another client");
                    }
                }
            }
        }
        NodeList listaUsername = document.getElementsByTagName("username");
        for (int count = 0; count < listaUsername.getLength(); count++) {
            if (listaUsername.item(count).getNodeName().equals("username")) {
                options.defaultUsername = listaUsername.item(count).getTextContent();
            }
        }
        NodeList listaServer = document.getElementsByTagName("server");
        for (int count = 0; count < listaServer.getLength(); count++) {
            if (listaServer.item(count).getNodeName().equals("server")) {
                options.defaultServer = listaServer.item(count).getTextContent();
            }
        }
    }

}
