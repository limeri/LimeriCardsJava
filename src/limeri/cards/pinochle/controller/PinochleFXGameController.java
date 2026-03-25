package limeri.cards.pinochle.controller;

import java.util.HashMap;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import limeri.cards.Constants;
import limeri.cards.Controller;

public class PinochleFXGameController extends Controller {

    HashMap<String, Stage> debugConsole = new HashMap<String, Stage>();
    Stage parentStage;

    public Stage getParentStage() {
        return this.parentStage;
    }

    public void setParentStage(Stage parent) {
        this.parentStage = parent;
    }

    /**
     * This event handler logs when an event occurs.
     * 
     * @param event the Event object for the event
     * @param name the name of the object in the event
     */
    public void eventLogger(Event event, String name) {
        String msg = String.format("\"%s\": %s Event detected from source: %s\n", name, event.getEventType().toString(), event.getSource().toString());
        System.out.print(msg);
    }

    /**
     * This debug console logger logs messages for a specific player (North, South, East, or West).
     * 
     * @param msg the message to be logged
     * @param stage the debugConsole stage to get the message
     */
    public void debugConsoleLogger(String msg, Stage stage) {
        ScrollPane pane = (ScrollPane) stage.getScene().getRoot();
        TextArea textArea = (TextArea) pane.getContent();
        String newLine = "";
        if (textArea.getText().length() > 0) {
            newLine = "\n";
        }
        textArea.setText(textArea.getText() + newLine + msg);
    }

    /**
     * This debug console logger logs messages for a specific player (North, South, East, or West).
     * 
     * @param msg the message to be logged
     * @param name the name of the debugConsole stage to get the message (North, South, East, or West)
     */
    public void debugConsoleLogger(String msg, String name) {
        Stage stage = debugConsole.get(name);
        if (stage != null) {
            debugConsoleLogger(msg, stage);
        }
    }

    /**
     * Create or show the event log window.
     *
     * @param event the Event object for the event
     * @param owner the stage that owns the window
     */
    public Stage makeDebugConsoleEventHandler(Event event, String name) {
        eventLogger(event, "MakeDebugConsole");
        Stage debugConsoleStage = debugConsole.get(name);
        if (debugConsoleStage  == null) {
            debugConsoleStage = makeDebugConsole(getParentStage(), name);
            debugConsole.put(name, debugConsoleStage);
            final Stage finalStage = debugConsoleStage;
            debugConsoleStage.setOnCloseRequest((closeEvent) -> debugConsoleExitEventHandler(closeEvent, finalStage));
        }
        else {
            debugConsoleStage.show();
        }
        debugConsoleLogger("MakeDebugConsole for " + name, name);
        return debugConsoleStage;
    }

    @FXML
    public void makeDebugConsoleEventHandlerWest(Event event) {
        makeDebugConsoleEventHandler(event, Constants.TABLE_POS_WEST);
    }

    @FXML
    public void makeDebugConsoleEventHandlerNorth(Event event) {
        makeDebugConsoleEventHandler(event, Constants.TABLE_POS_NORTH);
    }

    @FXML
    public void makeDebugConsoleEventHandlerEast(Event event) {
        makeDebugConsoleEventHandler(event, Constants.TABLE_POS_EAST);
    }

    @FXML
    public void makeDebugConsoleEventHandlerSouth(Event event) {
        makeDebugConsoleEventHandler(event, Constants.TABLE_POS_SOUTH);
    }

    /**
     * Hide the event log window.
     *
     * @param event the Event object for the event
     */
    public void debugConsoleExitEventHandler(Event event, Stage debugConsoleStage) {
        debugConsoleStage.hide();
        String stageTitle = debugConsoleStage.getTitle();
        eventLogger(event, "DismissLogDialog");
        debugConsoleLogger("DismissLogDialog for " + stageTitle, debugConsoleStage);
    }

    /**
     * Dismiss a window event handler for MenuItems.
     *
     * @param event the Event object for the event
     * @param stage the stage to dismiss
     */
    public void doneMenuEventHandler(Event event) {
        MenuItem source = (MenuItem) event.getSource();
        Stage owner = (Stage)source.getParentPopup().getOwnerWindow();
        eventLogger(event, source.getId());
        owner.close();
    }

    /**
     * Make and show the log stage dialog.
     *
     * @param owner the owning {@link Stage} of the dialog
     * @return
     */
    private Stage makeDebugConsole(Stage owner, String name) {
        TextArea debugConsole = new TextArea();
        debugConsole.setId("TextLog");
        debugConsole.setMinSize(950, 440);
        debugConsole.setWrapText(true);

        ScrollPane pane = new ScrollPane(debugConsole);
        pane.setPadding(new Insets(10, 10, 10, 10));
        Scene vboxScene = new Scene(pane);

        Stage logStage = new Stage();
        logStage.setTitle(name + " Log");
        logStage.initOwner(owner);
        logStage.setScene(vboxScene);
        logStage.initStyle(StageStyle.DECORATED);
        logStage.setX(100);
        logStage.setY(400);
        logStage.setHeight(500);
        logStage.setWidth(1000);
        logStage.setAlwaysOnTop(false);
        logStage.show();
        return logStage;
    }

}
