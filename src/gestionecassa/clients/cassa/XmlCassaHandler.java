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
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author ben
 */
public class XmlCassaHandler implements XmlHandler<LuogoOptions> {

    String optionsFile = new String("cassa.conf.xml");

    @Override
    public void saveOptions(LuogoOptions options) throws IOException {
        
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "config" );
        root.addAttribute("version", "1.0");
        root.addAttribute("client", "cassa");

        root.addElement("username").addText(options.defaultUsername);

        root.addElement("server").addText(options.defaultServer);

        // lets write to a file
        XMLWriter writer = new XMLWriter(new FileWriter(optionsFile));
        writer.write( document );
        writer.close();
    }

    @Override
    public void loadOptions(LuogoOptions options) throws IOException, 
            DocumentException {

        SAXReader reader = new SAXReader();
        Document document = reader.read(optionsFile);
        
        Element nodoRoot = document.getRootElement();
        if (!nodoRoot.attributeValue("version").equals("1.0")) {
            throw new IOException("Wrong file version!");
        } else if (!nodoRoot.attributeValue("client").equals("cassa")) {
            throw new IOException("This config file is for another client");
        }

        Element nodoUser = nodoRoot.element("username");
        if (nodoUser != null) {
            options.defaultUsername = nodoUser.getStringValue();
        } else {
            throw new IOException("This config file does not contain right info");
        }

        Element nodoServer = nodoRoot.element("server");
        if (nodoUser != null) {
            options.defaultServer = nodoServer.getStringValue();
        } else {
            throw new IOException("This config file does not contain right info");
        }
    }
}
