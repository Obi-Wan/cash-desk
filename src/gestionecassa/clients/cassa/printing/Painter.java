/*
 * Painter.java
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
import gestionecassa.order.EntrySingleArticle;
import gestionecassa.order.Order;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 *
 * @author ben
 */
public class Painter implements Printable {

    Order order;

    final int numPages;

    public Painter(Order order) {
        this.order = order;
        numPages = countPages(order);
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        if (pageIndex > numPages) {
            return NO_SUCH_PAGE;
        }
        /* Devo scoprire di che articolo si tratta */
        Article art = null;
        for(int posArticle = 0; posArticle < order.getListaBeni().size();
                    posArticle++) {
            pageIndex -= order.getListaBeni().get(posArticle).numTot;
            if (pageIndex < 0) {
                art = order.getListaBeni().get(posArticle).article;
                break;
            }
        }
        if (art == null) {
            throw new PrinterException("c'è stato un errore nel conteggio " +
                    "degli articoli da stampare");
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        /* Now we perform our rendering. This should take account of the type
         * of the article.
         */
        graphics.drawString(art.getPrintableFormat(), 100, 100);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    private int countPages(Order order) {
        int count = 0;
        for( EntrySingleArticle entry : order.getListaBeni()) {
            count += entry.numTot;
        }
        return count;
    }

}
