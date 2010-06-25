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
import gestionecassa.ArticleOption;
import gestionecassa.ArticleWithOptions;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ben
 */
public class TextPainter {

    /**
     * The output file
     */
    BufferedWriter outputStream;

    /**
     * Constructor tat initializes the file descriptor and the name of who
     * emitted the order
     * @param username
     * @throws IOException
     */
    public TextPainter(String username) throws IOException {
        outputStream = new BufferedWriter(new FileWriter("output.txt"));
        outputStream.write("  " + "Emesso da:\n  -> " + username.toUpperCase() +
                " <-\n\n\n");
    }

    /**
     * Adds the given <code>Article</code> to the file to print
     *
     * @param article
     * @param copies
     * 
     * @throws IOException
     */
    public void addArticle(Article article, int copies) throws IOException {

        for (int i = 0; i < copies; i++) {
            outputStream.write(
                    "  " + article.getName().toUpperCase() + "\n\n\n");
        }
        outputStream.flush();

    }

    /**
     * Adds the given <code>ArticleWithOptions</code> to the file to print
     * 
     * @param article the article to print
     * @param prog
     * @param option
     *
     * @throws IOException
     */
    public void addArticleWOptions(ArticleWithOptions article, int prog,
            ArticleOption option) throws IOException {

        if (!article.hasOptions()) {
            throw new IOException("wrong article type: this should be with" +
                    " options");
        }
        
        for (int i = 0; i < 2; i++) {

            outputStream.write("  " + String.format("N. %03d", prog) + "\n");

            String centralString =
                    "  " + article.getName() + ":\n" + "  " + option.getName();
            centralString = centralString.toUpperCase();

            outputStream.write(centralString + "\n\n\n");
        }
        outputStream.flush();
    }

    /**
     * It actualy prints and closes the file descriptor.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void doPrint() throws IOException, InterruptedException {
        outputStream.write("  " + "-----------------\n\n\n");
        outputStream.flush();
        outputStream.close();

        String cmd[] = {"lpr", "-o", "PrintQuality=Text", "output.txt"};
        Runtime run = Runtime.getRuntime();
        run.exec(cmd).waitFor();
    }
}
