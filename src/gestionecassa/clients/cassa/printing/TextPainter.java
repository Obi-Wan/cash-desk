/*
 * TextPainter.java
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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ben
 */
public class TextPainter {

    Article article;

    final int progressive;

    final String option;

    int copies = 1;

    /**
     * Constructor for <code>ArticleWithOption</code>
     *
     * @param entry
     */
    public TextPainter(ArticleWithOptions article, int prog, String option) {
        this.article = article;
        this.progressive = prog;
        this.option = option;
    }

    /**
     * Constructor for <code>Article</code>
     *
     * @param article
     */
    public TextPainter(Article article) {
        this.article = article;
        this.progressive = 0;
        this.option = "";
    }

    public void doPrint() throws IOException, InterruptedException {

        BufferedWriter outputStream =
            new BufferedWriter(new FileWriter("output.txt"));

        String centralString = article.getName();

        if (article.hasOptions()) {

            centralString += ":\n  " + option;

            String progStr = String.format("  N. %03d", progressive);

            outputStream.write(progStr);
            outputStream.newLine();
        }
        centralString = centralString.toUpperCase();

        outputStream.write("  " + centralString);
        outputStream.flush();
        outputStream.close();

        String cmd[] = {"lpr", "-o", "PrintQuality=Text", "output.txt"};
        Runtime run = Runtime.getRuntime();
        for (int i = 0; i < copies; i++) {
            run.exec(cmd).waitFor();
        }
    }

    void setCopies(int nm) {
        if (nm > 1) {
            copies = nm;
        }
    }
}
