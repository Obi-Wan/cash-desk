/*
 * XmlOptionsHandler.java
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

package gestionecassa;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author ben
 */
public class XmlOptionsHandler<DataType extends Options> {

    /**
     * reference to the logger used for error reporting
     */
    Logger logger;

    /**
     * Constructor
     * @param logger reference to the used logger
     */
    public XmlOptionsHandler(Logger logger) {
        this.logger = logger;
    }

    /**
     * Saves options to a file given by the option type
     * @param options
     * @throws IOException
     */
    public void saveOptions(DataType options) throws IOException {

        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "config" );
        root.addAttribute("version", options.getVersion());
        root.addAttribute("application", options.getApplication());

        for (Field field : options.getClass().getFields()) {
            try {
                root.addElement(field.getName()).addText(field.get(options) + "");
            } catch (IllegalArgumentException ex) {
                logger.warn("campo sbagliato", ex);
            } catch (IllegalAccessException ex) {
                logger.warn("campo sbagliato", ex);
            }
        }

        // lets write to a file
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer = new XMLWriter(new FileWriter(options.getFileName()), format);
        writer.write( document );
        writer.close();
    }

    /**
     * Loads options from a file
     * @param options
     * @throws IOException
     * @throws DocumentException
     */
    public void loadOptions(DataType options) throws IOException, DocumentException {

        SAXReader reader = new SAXReader();
        Document document = reader.read(options.getFileName());

        Element nodoRoot = document.getRootElement();
        if (!nodoRoot.attributeValue("version").equals(options.getVersion())) {
            throw new IOException("Wrong file version!");
        } else if (!nodoRoot.attributeValue("application").equals(options.getApplication())) {
            throw new IOException("This config file is for another client");
        }

        for (Field field : options.getClass().getFields()) {
            Element node = nodoRoot.element(field.getName());
            if (node != null) {
                try {
                    if (field.getType().equals(Boolean.TYPE)) {
                        field.setBoolean(options, new Boolean(node.getTextTrim()));
                    } else if (field.getType().equals(Integer.TYPE)) {
                        field.setInt(options, new Integer(node.getTextTrim()));
                    } else {
                        field.set(options, node.getTextTrim());
                    }
                } catch (IllegalArgumentException ex) {
                    logger.warn("campo sbagliato", ex);
                } catch (IllegalAccessException ex) {
                    logger.warn("campo sbagliato", ex);
                }
            } else {
                System.out.println("errore campo: " + field.getName() +
                        "\nThis config file does not contain right info");
                throw new IOException("This config file does not contain right info");
            }
        }
    }

}
