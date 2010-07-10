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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
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

    static final Font centralFont;
    static final Font marginalFont;

    static {
        centralFont = new Font("Dialog", Font.PLAIN, 18);
        marginalFont = new Font("Dialog", Font.PLAIN, 12);
    }

    Article article;

    final int progressive;

    final String option;

    /**
     * Constructor for <code>Article</code> with Option
     * 
     * @param article
     * @param prog
     * @param option
     */
    public Painter(Article article, int prog, String option) {
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
     * Function that prints the paper
     * @param graphics
     * @param pageFormat
     * @param pageIndex
     * @return
     * @throws PrinterException
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Dimension dims = new Dimension((int)pageFormat.getImageableWidth(),
                                        (int)pageFormat.getImageableHeight());

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        /* Now we perform our rendering. This should take account of the type
         * of the article.
         */
        // debug
        graphics.drawRect(0, 0, dims.width, dims.height);
//DEBUGONLY        graphics.drawString(dims.width + ", " + dims.height, 0, dims.height);
        // end debug

        String centralString = article.getName();

        if (article.hasOptions()) {
            //debug only
//            if (option.equals("")) {
//                if (!article.getOptions().contains("")) {
//                    throw new RuntimeException(
//                            "Option was not passed but the article has some, " +
//                            "different from the empty");
//                }
//            }
            //end debug only

            centralString += ": " + option;

            String progStr = String.format("%03d", progressive);
            
            graphics.setFont(marginalFont);
            FontMetrics margMetr = graphics.getFontMetrics(marginalFont);
            Dimension margDims = new Dimension(margMetr.stringWidth(progStr)+2,
                                            margMetr.getAscent()+2);
            
            graphics.drawString(progStr, dims.width - margDims.width,
                                margDims.height);
        }
        centralString = centralString.toUpperCase();

        graphics.setFont(centralFont);
        FontMetrics metrics = graphics.getFontMetrics(centralFont);
        Dimension centralStrSize = new Dimension(metrics.stringWidth(centralString)+2,
                                        metrics.getAscent()+2);

        graphics.drawString(centralString, (dims.width / 2) - (centralStrSize.width / 2),
                (dims.height / 2) + (centralStrSize.height / 2));

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
}
