/*
 * Log.java
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

package gestionecassa;

import org.apache.log4j.Logger;

/** Class used to log every important or intresting thing.
 *
 * @author BeN
 */
public class Log {

    /** Logger for package gestionecassa */
    static public final Logger GESTIONECASSA =
            Logger.getLogger("gestionecassa");

    /** Logger for package gestionecassa.amministrazione */
    static public final Logger GESTIONECASSA_AMMINISTRAZIONE =
            Logger.getLogger("gestionecassa.amministrazione");

    /** Logger for package gestionecassa.amministrazione.gui */
    static public final Logger GESTIONECASSA_AMMINISTRAZIONE_GUI =
            Logger.getLogger("gestionecassa.amministrazione.gui");

    /** Logger for package gestionecassa.cassa */
    static public final Logger GESTIONECASSA_CASSA =
            Logger.getLogger("gestionecassa.cassa");

    /** Logger for package gestionecassa.cassa.gui */
    static public final Logger GESTIONECASSA_CASSA_GUI =
            Logger.getLogger("gestionecassa.cassa.gui");

    /** Logger for package gestionecassa.cassa.gui */
    static public final Logger GESTIONECASSA_CASSA_PRINTING =
            Logger.getLogger("gestionecassa.cassa.printing");

    /** Logger for package gestionecassa.server */
    static public final Logger GESTIONECASSA_SERVER =
            Logger.getLogger("gestionecassa.server");

    /** Logger for package gestionecassa.server.datamanager */
    static public final Logger GESTIONECASSA_SERVER_DATAMANAGER =
            Logger.getLogger("gestionecassa.server.datamanager");

    /** Logger for package gestionecassa.server.datamanager.xml_be */
    static public final Logger GESTIONECASSA_SERVER_DATAMANAGER_XML =
            Logger.getLogger("gestionecassa.server.datamanager.xml");

    /** Logger for package gestionecassa.server.datamanager.db_be */
    static public final Logger GESTIONECASSA_SERVER_DATAMANAGER_DB =
            Logger.getLogger("gestionecassa.server.datamanager.db");

    static {
        // config da file
        org.apache.log4j.xml.DOMConfigurator.configure("logconfig.xml");

        // config manuale
        if (GESTIONECASSA.isDebugEnabled()) {
            String messaggio= "Debug is Enabled";
            // codice per comporre messaggio oneroso
            GESTIONECASSA.debug(messaggio);
        }
        //GESTIONECASSA.setLevel(Level.DEBUG);
    }

}













