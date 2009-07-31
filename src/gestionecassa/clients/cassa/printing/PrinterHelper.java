/*
 * PrinterHelper.java
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

package gestionecassa.clients.cassa.printing;

import gestionecassa.ArticleWithOptions;
import gestionecassa.Log;
import gestionecassa.order.EntrySingleArticle;
import gestionecassa.order.EntrySingleArticleWithOption;
import gestionecassa.order.EntrySingleOption;
import gestionecassa.order.Order;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.lang.reflect.Method;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaSizeName;
import org.apache.log4j.Logger;

/**
 *
 * @author ben
 */
public class PrinterHelper extends Thread {

    static Logger logger = Log.GESTIONECASSA_CASSA_PRINTING;
    
    Order order;

    public static void startPrintingOrder(Order order) {
        PrinterHelper printerHelper = new PrinterHelper(new Order(order));
        printerHelper.run();
    }
    
    /**
     * Explicit constructor
     * 
     * @param order
     */
    private PrinterHelper( Order order ) {
        this.order = order;
    }

    /**
     * 
     */
    @Override
    public void run() {
        super.run();
        PrinterJob job = PrinterJob.getPrinterJob();

        PrintRequestAttributeSet attribs = new HashPrintRequestAttributeSet();
        
        // Debug output (during develop phase)
        Destination dest = new Destination(new File("out.ps").toURI());
        attribs.add(dest);

        Media media = MediaSizeName.ISO_A9;
        attribs.add(media);

        for (EntrySingleArticle entrySingleArticle : order.getListaBeni()) {
            if (entrySingleArticle.article.hasOptions()) {
                EntrySingleArticleWithOption entry =
                        (EntrySingleArticleWithOption)entrySingleArticle;
                int prog = entry.startProgressive;
                for (EntrySingleOption entrySingleOption : entry.numPartial) {
                    for (int i = 0; i < entrySingleOption.numPartial; i++) {
                        job.setPrintable(new Painter(
                                (ArticleWithOptions)entrySingleArticle.article,
                                prog++, entrySingleOption.optionName));
                    }
                }
            } else {
                job.setPrintable(new Painter(entrySingleArticle.article));
                job.setCopies(entrySingleArticle.numTot);
            }
            // needed only in the develop phase
            //boolean ok = job.printDialog(attribs);

            //if (ok) {
            try {
                //job.print();
                job.print(attribs);
            } catch (PrinterException ex) {
                logger.error("Errore nel tentativo di stampa", ex);
            }
            //}
        }

        // Develop output to individuate the attributes to apply
//        Attribute[] attribsArray = attribs.toArray();
//        for (Attribute attribute : attribsArray) {
//            System.out.println(attribute.getName() + ": " + attribute.getCategory().getSimpleName());
//            Method[] methods = attribute.getCategory().getMethods();
//            System.out.println(" Method:");
//            for (Method method : methods) {
//                System.out.println("   - "+ method.toString());
//            }
//        }
    }

}
