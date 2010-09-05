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

import gestionecassa.Article;
import gestionecassa.ArticleOption;
import gestionecassa.Log;
import gestionecassa.order.EntryArticleGroup;
import gestionecassa.order.PairObjectInteger;
import gestionecassa.order.EntrySingleArticleWithOption;
import gestionecassa.order.Order;
import java.io.IOException;
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
        printerHelper.start();
    }
    
    /**
     * Explicit constructor
     * 
     * @param order
     */
    private PrinterHelper( Order order ) {
        this.order = order;
    }

    @Override
    public void run() {
        try {
            TextPainter painter = new TextPainter(order.getUsername());
            
            for (EntryArticleGroup entryGroup : order.getGroups()) {
                for (PairObjectInteger<Article> entrySingleArticle : entryGroup.articles) {
                    if (entrySingleArticle.object.hasOptions()) {
                        EntrySingleArticleWithOption entry =
                                (EntrySingleArticleWithOption)entrySingleArticle;
                        int prog = entry.startProgressive;
                        for (PairObjectInteger<ArticleOption> entrySingleOption : entry.numPartial) {
                            for (int i = 0; i < entrySingleOption.numTot; i++) {
                                painter.addArticleWOptions(entrySingleArticle.object,
                                        prog++, entrySingleOption.object);
                            }
                        }
                    } else {
                        painter.addArticle(entrySingleArticle.object,
                                entrySingleArticle.numTot);
                    }
                }
            }
            painter.closePrint();
            painter.doPrint();

        } catch (InterruptedException ex) {
            logger.error("Errore nel tentativo di stampa",ex);
        } catch (IOException ex) {
            logger.error("Errore nel tentativo di stampa", ex);
        }
    }
}
