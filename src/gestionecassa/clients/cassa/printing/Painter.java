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
import gestionecassa.ArticleWithOptions;
import gestionecassa.order.EntrySingleArticleWithOption;
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

    Article article;

    final int progressive;

    final String option;

    /**
     * Constructor for <code>ArticleWithOption</code>
     * 
     * @param entry
     */
    public Painter(ArticleWithOptions article, int prog, String option) {
        this.article = article;
        this.progressive = prog;
        this.option = option;
    }

    /**
     * Constructor for <code>Article</code>
     *
     * @param article
     */
    public Painter(Article article) {
        this.article = article;
        this.progressive = 0;
        this.option = "";
    }

    /**
     * 
     * @param graphics
     * @param pageFormat
     * @param pageIndex
     * @return
     * @throws PrinterException
     */
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }


        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        /* Now we perform our rendering. This should take account of the type
         * of the article.
         */
        if (article.hasOptions()) {
            graphics.drawRect(0, 0, 100, 65);
            graphics.drawString(option, 5, 25);
            graphics.drawString(article.getName(), 5, 10);
        } else {
            graphics.drawString(article.getName(), 5, 10);
        }

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
}
