package limeri.cards.pinochle.controller;

import javafx.event.Event;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import limeri.cards.Controller;

public class PinochleFXMainController extends Controller {

    /**
     * Dismiss a window event handler for MenuItems.
     *
     * @param event the Event object for the event
     * @param stage the stage to dismiss
     */
    public void exitButtonEventHandler(Event event) {
        MenuItem source = (MenuItem) event.getSource();
        Stage owner = (Stage)source.getParentPopup().getOwnerWindow();
        owner.close();
    }


}
