/*
 * PainterTest.java
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
import gestionecassa.stubs.DebugDataProvider;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.List;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ben
 */
public class PainterTest {

    List<Article> articles;

    public PainterTest() {
        DebugDataProvider dataProvider = new DebugDataProvider();

        /* Ensures 4 articles:
         * 0 - Article
         * 1 - Article
         * 2 - Article with Options
         * 3 - Article
         */
        articles = dataProvider.getCopyGroups().get(0).getList();
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of print method, of class Painter.
     * @throws Exception
     */
    @Test
    public void testPrint() throws Exception {
        System.out.println("print");


        PrinterJob job = PrinterJob.getPrinterJob();

        assertNotNull("No print services: get a printer!",job.getPrintService());

        PrintRequestAttributeSet attribs = new HashPrintRequestAttributeSet();

        // Debug output (during develop phase)
        Destination dest = new Destination(new File("out.ps").toURI());
        attribs.add(dest);

        Media media = MediaSizeName.ISO_A7;
        attribs.add(media);

        OrientationRequested orient = OrientationRequested.LANDSCAPE;
        attribs.add(orient);

        System.out.println("printing: " + articles.get(0).getName());

        job.setPrintable(new Painter(articles.get(0)));

        job.print(attribs);

        attribs.remove(dest); //
        dest = new Destination(new File("out1.ps").toURI());
        attribs.add(dest); //

        System.out.println("printing: " + articles.get(2).getName());

        if (!articles.get(2).hasOptions()) {
            fail("Bad data backend");
        }
        Article articleChosen = articles.get(2);
        String nameOfTheOption = articleChosen.getOptions().get(0).getName();
        job.setPrintable(new Painter(articleChosen, 12, nameOfTheOption));

        job.print(attribs);
    }

}