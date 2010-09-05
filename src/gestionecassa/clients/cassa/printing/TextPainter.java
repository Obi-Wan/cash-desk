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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Prints every article of the order, managing their order.
 * @author ben
 */
public class TextPainter {

    /**
     * The output file
     */
    BufferedWriter outputStream;

    List<RecordArticleWithPerparation> preps;

    final static List<String> sentences;

    static {
        sentences = new ArrayList<String>();
        sentences.add("Emesso da:");
        sentences.add("Per il cliente:");
        sentences.add("Per l'addetto:");
    }

    /**
     * Constructor tat initializes the file descriptor and the name of who
     * emitted the order
     * @param username
     * @throws IOException
     */
    public TextPainter(String username) throws IOException {
        outputStream = new BufferedWriter(new FileWriter("output.txt"));
        outputStream.write("  " + sentences.get(0) + "\n  -> " +
                username.toUpperCase() + " <-\n\n\n");

        preps = new LinkedList<RecordArticleWithPerparation>();
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
     * Adds the given <code>Article</code> with Options to the file to print
     * 
     * @param article the article to print
     * @param prog
     * @param option
     *
     * @throws IOException
     */
    public void addArticleWOptions(Article article, int prog,
            ArticleOption option) throws IOException {

        if (!article.hasOptions()) {
            throw new IOException("wrong article type: this should be with" +
                    " options");
        }
        
        preps.add(new RecordArticleWithPerparation(article, prog, option));
    }

    /**
     * Adds last pieces and closes the file descriptor.
     * @throws IOException
     */
    public void closePrint() throws IOException {
        if (!preps.isEmpty()) {
            for (int i = 0; i < 2; i++) {
                outputStream.write("  " + "-----------------\n  " +
                                    sentences.get(i+1) + "\n\n\n");
                for (RecordArticleWithPerparation record : preps) {
                    outputStream.write("  " +
                            String.format("N. %03d", record.prog) + "\n");

                    String centralString = "  " + record.article.getName() +
                                        ":\n" + "  " + record.option.getName();
                    centralString = centralString.toUpperCase();
                    outputStream.write(centralString + "\n\n\n");
                }
            }
        }
        outputStream.write("  " + "-----------------\n\n\n");
        outputStream.flush();
        outputStream.close();
    }

    /**
     * It actualy prints
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void doPrint() throws IOException, InterruptedException {
        String cmd[] = {"lpr", "-o", "PrintQuality=Text", "output.txt"};
        Runtime run = Runtime.getRuntime();
        run.exec(cmd).waitFor();
    }

    /**
     * Stores articles that need preparation
     */
    private class RecordArticleWithPerparation {
        Article article;
        int prog;
        ArticleOption option;

        public RecordArticleWithPerparation(Article article, int prog,
                ArticleOption option) {
            this.article = article;
            this.prog = prog;
            this.option = option;
        }
    }
}
