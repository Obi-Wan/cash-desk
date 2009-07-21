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

import gestionecassa.order.Order;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.lang.reflect.Method;
import javax.print.attribute.Attribute;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;

/**
 *
 * @author ben
 */
public class PrinterHelper extends Thread {
    
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
        job.setPrintable(new Painter());

        // Debug output (during develop phase)
        Destination dest = new Destination(new File("out.ps").toURI());
        PrintRequestAttributeSet attribs = new HashPrintRequestAttributeSet();
        attribs.add(dest);

        // needed only in the develop phase
        boolean ok = job.printDialog(attribs);

        // Develop output to individuate the attributes to apply
        Attribute[] attribsArray = attribs.toArray();
        for (Attribute attribute : attribsArray) {
            System.out.println(attribute.getName() + ": " + attribute.getCategory().getSimpleName());
            Method[] methods = attribute.getCategory().getMethods();
            System.out.println(" Method:");
            for (Method method : methods) {
                System.out.println("   - "+ method.toString());
            }
        }

        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
            }
        }
    }

}
