package gestionecassa.clients.gui;

import gestionecassa.clients.cassa.gui.GuiAbstrSingleEntryPanel;

/**
 * Class that defines a record of panels and other useful info.
 *
 * @author ben
 */
public class RecordPanels<PanelType extends GuiAbstrSingleEntryPanel, DataType> {

    /**
     *
     */
    public final DataType data;

    /**
     * Reference to the panel that will show up in the gui
     */
    public final PanelType displayedPanel;

    /**
     * Explicit constructor
     * 
     * @param pan
     * @param data
     */
    public RecordPanels(PanelType pan, DataType data) {
        this.displayedPanel = pan;
        this.data = data;
    }
}
