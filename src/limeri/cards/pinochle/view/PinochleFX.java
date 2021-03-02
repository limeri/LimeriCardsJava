package limeri.cards.pinochle.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import limeri.cards.model.CardModel;
import limeri.cards.view.CardFxModel;

public class PinochleFX extends Application {

    private static final int CARD_X_START_POSITION = 10;
    private static final int CARD_Y_START_POSITION = 50;
    private static final double VISIBLE_CARD_PORTION = .3;  // Portion of a covered card that is visible.

    ArrayList<ImageView> imageViews = new ArrayList<ImageView>();
    ArrayList<CardFxModel> imageModels = new ArrayList<CardFxModel>();
    Stage logStage;
    TextArea logText = new TextArea();

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeImageModels();

        int xpos = CARD_X_START_POSITION;
        int ypos = CARD_Y_START_POSITION;
        int coveredCardWidth = calculateCoveredCardWidth(imageViews.get(0).getImage());
        for(Iterator<CardFxModel> iterator = imageModels.iterator(); iterator.hasNext();) {
            CardFxModel model = (CardFxModel) iterator.next();
            ImageView image = model.getImage();
            image.relocate(xpos, ypos);
            xpos = xpos + coveredCardWidth;
            model.setVisible(true);
        }

        Button logButton = makeButton("LogButton", "Log Panel", "Make log panel dialog.", 30D, 170D);
        logButton.setOnAction((event) -> makeLogEventHandler(event, primaryStage));

        Button resetButton = makeButton("ResetCardsButton", "Reset Cards", "Redeal all the cards.", 120D, 170D);
        resetButton.setOnAction((event) -> resetEventHandlerForButton(event));

        Button doneButton = makeDoneButton("Exit", 220D, 170D);

        // File Menu
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setId("ExitMenuItem");
        exitMenuItem.setOnAction((event) -> doneMenuEventHandler(event));
        Menu fileMenu = new Menu("File");
        fileMenu.getItems().add(exitMenuItem);

        // Game Menu
        MenuItem logPanelMenuItem = new MenuItem("Log Panel");
        logPanelMenuItem.setId("LogMenuItem");
        logPanelMenuItem.setOnAction((event) -> makeLogEventHandler(event, primaryStage));
        MenuItem resetMenuItem = new MenuItem("Reset Cards");
        resetMenuItem.setId("ResetMenuItem");
        resetMenuItem.setOnAction((event) -> resetEventHandlerForMenu(event));
        Menu gameMenu = new Menu("Game");
        gameMenu.getItems().addAll(logPanelMenuItem, resetMenuItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, gameMenu);

        Pane controlPane = new Pane();
        controlPane.getChildren().addAll(imageViews);
        controlPane.getChildren().addAll(menuBar, logButton, resetButton, doneButton);
        controlPane.setPadding(new Insets(10, 10, 10, 10));
        Scene paneScene = new Scene(controlPane);

        // Calculate the width of the main window.
        int handWidth = calculateHandDisplayWidth();
        int stageWidth = handWidth + 100;

        String stageTitle = "Card Test App";
        primaryStage.setTitle(stageTitle);
        primaryStage.setOnCloseRequest((event) -> eventLogger(event, stageTitle));
        primaryStage.setScene(paneScene);
        primaryStage.setX(400);
        primaryStage.setY(100);
        primaryStage.setHeight(300);
        primaryStage.setWidth(stageWidth);
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.out.println("Starting CardTest main");
        Application.launch(args);
        System.out.println("Ending CardTest main");
    }

    /* ----- E V E N T   H A N D L E R S ----- */

    /**
     * This event handler logs when an event occurs.
     * 
     * @param event the Event object for the event
     * @param name the name of the object in the event
     */
    public void eventLogger(Event event, String name) {
        String msg = String.format("\"%s\": %s Event detected from source: %s\n", name, event.getEventType().toString(), event.getSource().toString());
        System.out.print(msg);
        if (logStage != null) {
            String logData = logText.getText() + msg;
            logText.setText(logData);
        }
    }

    /**
     * Remove a card from the board and forces the hand to redraw.
     *
     * @param event the Event object for the event
     */
    public void cardEventHandler(Event event) {
        ImageView image = (ImageView)event.getSource();
        String imageId = image.getId();
        eventLogger(event, imageId);

        // Find the card model for this image.
        CardFxModel card = findImageModel(imageId);
        card.setVisible(false);
        Pane parentPane = (Pane)image.getParent();
        redrawPane(parentPane);
    }

    /**
     * Reset the cards being displayed so the full hand is displayed when called from a button.
     *
     * @param event the Event object for the event
     */
    public void resetEventHandlerForButton(Event event) {
        Button btn = (Button)event.getSource();
        String btnId = btn.getId();
        eventLogger(event, btnId);
        Pane parentPane = (Pane)btn.getParent();
        resetCards(parentPane);
    }

    /**
     * Reset the cards being displayed so the full hand is displayed when called from a MenuItem.
     *
     * @param event the Event object for the event
     */
    public void resetEventHandlerForMenu(Event event) {
        MenuItem source = (MenuItem) event.getSource();
        String id = source.getId();
        eventLogger(event, id);

        Stage owner = (Stage)source.getParentPopup().getOwnerWindow();
        Scene scene = owner.getScene();
        Pane parentPane = (Pane)scene.getRoot();
        resetCards(parentPane);
    }

    /**
     * Finish the resetParentPane action because menus and buttons have different ways of getting
     * the parentPane.
     *
     * @param parentPane
     */
    private void resetCards(Pane parentPane) {
        for (Iterator<CardFxModel> iterator = imageModels.iterator(); iterator.hasNext();) {
            CardFxModel card = (CardFxModel) iterator.next();
            card.setVisible(true);
        }

        redrawPane(parentPane);
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
     * Dismiss a window event handler for buttons.
     *
     * @param event the Event object for the event
     */
    public void doneButtonEventHandler(Event event) {
        Button source = (Button) event.getSource();
        eventLogger(event, source.getText());
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Create or show the event log window.
     *
     * @param event the Event object for the event
     * @param owner the stage that owns the window
     */
    public void makeLogEventHandler(Event event, Stage owner) {
        eventLogger(event, "MakeLogDialog");
        if (logStage == null) {
            logStage = makeLogStage(owner);
        }
        else {
            logStage.show();
        }
    }

    /**
     * Hide the event log window.
     *
     * @param event the Event object for the event
     */
    public void logStageExitEventHandler(Event event) {
        logStage.hide();
        eventLogger(event, "DismissLogDialog");
    }

    /* ----- H E L P E R    M E T H O D S ----- */

    /**
     * Find a {@link CardModel} based on the image ID.
     *
     * @param imageId the ID of the image to find
     * @return the {@link CardModel} object for the image ID
     */
    private CardFxModel findImageModel(String imageId) {
        CardFxModel card = new CardFxModel();
        for(Iterator<CardFxModel> iterator = imageModels.iterator(); iterator.hasNext();) {
            card = (CardFxModel) iterator.next();
            if (card.getId().equals(imageId)) {
                break;
            }
        }
        return card;
    }

    /**
     * Redraw all the visible cards in the pane.  The steps are to clear all the cards from the pane and then add them back.
     *
     * @param pane the pane containing the cards.
     */
    private void redrawPane(Pane pane) {
        ObservableList<Node> nodes = pane.getChildren();

        // Get the ImageView nodes and remove them from the pane.
        ArrayList<ImageView> imagesToRemove = new ArrayList<ImageView>();
        for (Iterator<Node> iterator = nodes.iterator(); iterator.hasNext();) {
            Node node = (Node) iterator.next();
            if (node.getClass().equals(ImageView.class)) {
                imagesToRemove.add((ImageView)node);
            }
        }
        nodes.removeAll(imagesToRemove);

        int xpos = CARD_X_START_POSITION;
        int ypos = CARD_Y_START_POSITION;
        int coveredCardWidth = calculateCoveredCardWidth(imageViews.get(0).getImage());
        // Put all the images back into the pane.
        for (Iterator<CardFxModel> iterator = imageModels.iterator(); iterator.hasNext();) {
            CardFxModel card = (CardFxModel) iterator.next();
            if (card.getVisible()) {
                ImageView image = card.getImage();
                image.relocate(xpos, ypos);
                nodes.add(image);
                xpos = xpos + coveredCardWidth;
            }
        }

//            for (Iterator<CardModel> iterator = imageModels.iterator(); iterator.hasNext();) {
//                CardModel card = (CardModel) iterator.next();
//                if (card.getVisible()) {
//                    ImageView image = card.getImage();
//                    nodes.add(image);
//                }
//            }

    }

    /**
     * Initialize all the {@link CardModel} objects.
     */
    private void initializeImageModels() {
        String[] imagePaths = {
                "images/playing_cards/card_AS.png",
                "images/playing_cards/card_KS.png",
                "images/playing_cards/card_QS.png",
                "images/playing_cards/card_JS.png",
                "images/playing_cards/card_10S.png",
                "images/playing_cards/card_09S.png",
                "images/playing_cards/card_08S.png",
                "images/playing_cards/card_07S.png",
                "images/playing_cards/card_06S.png",
                "images/playing_cards/card_05S.png",
                "images/playing_cards/card_04S.png",
                "images/playing_cards/card_03S.png",
                "images/playing_cards/card_02S.png"
        };
        String[] imageIds = {
                "AceSpades", "KingSpades", "QueenSpades", "JackSpades", "TenSpades", "NineSpades", "EightSpades",
                "SevenSpades", "SixSpades", "FiveSpades", "FourSpades", "ThreeSpades", "TwoSpades"
        };
        int orderVal = 1;
        for(int i=0; i < imagePaths.length; i++) {
            ImageView imageView = makeCardImage(imagePaths[i], imageIds[i]);
            imageView.setViewOrder(orderVal);
            imageViews.add(imageView);
            CardFxModel model = new CardFxModel();
            model.setImagePath(imagePaths[i]);
            model.setId(imageIds[i]);
            model.setImage(imageView);
            model.setOrder(orderVal);
            imageModels.add(model);
            orderVal++;
        }
    }

    /**
     * Make a {@link CardModel} to add to the {@link CardModel}.  This way, the image is
     * only created once.
     *
     * @param imagePath the path of the image to create
     * @param id the ID to assign to the {@link ImageView}
     * @return an {@link CardModel} object
     */
    private ImageView makeCardImage(String imagePath, String id) {
        File cardFile = new File(imagePath);
        Image cardImage = new Image(cardFile.toURI().toString());
        ImageView cardImageView = new ImageView(cardImage);
        cardImageView.setId(id);
        cardImageView.setOnMouseClicked((event) -> cardEventHandler(event));
//            System.out.printf("Image width: %s, height: %s.\n", cardImage.getWidth(), cardImage.getHeight()); // 72x96
        return cardImageView;
    }

    /**
     * Calculate the width of a card that is covered.  This is used for figuring out the size of the hand.
     *
     * @return
     */
    private int calculateCoveredCardWidth(Image cardImage) {
        int cardWidth = (int)cardImage.getWidth();
        int coveredCardSize = (int)(cardWidth * VISIBLE_CARD_PORTION);
        return coveredCardSize;
    }

    /**
     * Calculate the width of the hand to be displayed.  This is used for figuring out the size of the overall table
     * and where to put things.
     *
     * @return
     */
    private int calculateHandDisplayWidth() {
        // Get a card image.
        CardFxModel cardModel = imageModels.get(0);
        ImageView cardImageView = cardModel.getImage();
        Image cardImage = cardImageView.getImage();
        // Calculate the width of all of the images.
        int cardWidth = (int)cardImage.getWidth();
        int coveredCardSize = calculateCoveredCardWidth(cardImage);
        int fullWidth = cardWidth + (12*coveredCardSize);
        return fullWidth;
    }

    /**
     * Make and show the log stage dialog.
     *
     * @param owner the owning {@link Stage} of the dialog
     * @return
     */
    private Stage makeLogStage(Stage owner) {
        logText.setId("TextLog");
        logText.setMinSize(950, 440);
        logText.setWrapText(true);

        ScrollPane pane = new ScrollPane(logText);
        pane.setPadding(new Insets(10, 10, 10, 10));
        Scene vboxScene = new Scene(pane);

        Stage logStage = new Stage();
        logStage.setTitle("Log");
        logStage.initOwner(owner);
        logStage.setScene(vboxScene);
        logStage.initStyle(StageStyle.DECORATED);
        logStage.setX(100);
        logStage.setY(400);
        logStage.setHeight(500);
        logStage.setWidth(1000);
        logStage.setOnCloseRequest((event) -> logStageExitEventHandler(event));
        logStage.show();
        return logStage;
    }

    /**
     * Make a {@link Button} object
     *
     * @param id the ID of the button
     * @param buttonLabel the label to add to the button
     * @param toolTip the tooltip of the button
     * @param xpos the X position of the button in the window
     * @param ypos the Y position of the button in the window
     * @return the {@link Button} object
     */
    private Button makeButton(String id, String buttonLabel, String toolTip, Double xpos, Double ypos) {
        Button button = new Button(buttonLabel);
        button.setId(id);
        button.setTooltip(new Tooltip(toolTip));
        button.relocate(xpos, ypos);
        return button;
    }

    /**
     * Make a "Done" or "Exit" button.  This is a button that will close a window.  The doneEventHandler
     * will be assigned as the setOnAction method.
     *
     * @param buttonLabel the label of the button
     * @param xpos the X position of the button in the window
     * @param ypos the Y position of the button in the window
     * @return a {@link Button} object
     */
    private Button makeDoneButton(String buttonLabel, Double xpos, Double ypos) {
        Button doneButton = makeButton("DoneButton", buttonLabel, "Close this window.", xpos, ypos);
        doneButton.setOnAction((event) -> doneButtonEventHandler(event));
        return doneButton;
    }

}
