package limeri.cards.pinochle;

import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Test version of Pinochle game that uses PropertyReader-based deck classes
 * and log4j for logging.
 */

import limeri.cards.controller.SimpleGameController;
import limeri.cards.controller.HandDisplayController;
import limeri.cards.Constants;
import limeri.cards.deck.SimplePinochleDeck;
import limeri.cards.model.PlayerModel;
import limeri.cards.view.ConsoleHandDisplay;

public class TestPinochle extends SimpleGameController {
    
    private static final Logger logger = LogManager.getLogger(TestPinochle.class);
    
    private int numberOfPlayers = Constants.PINOCHLE_INITIAL_PLAYERS;
    private int numberOfCardsInHand;
    private SimplePinochleDeck deck;
    private ArrayList<PlayerModel> players;

    public int getNumberOfPlayers() {return numberOfPlayers;}
    public void setNumberOfPlayers(int numberOfPlayers) {this.numberOfPlayers = numberOfPlayers;}

    public int getNumberOfCardsInHand() {return numberOfCardsInHand;}
    public void setNumberOfCardsInHand(int numberOfCardsInHand) {this.numberOfCardsInHand = numberOfCardsInHand;}

    public SimplePinochleDeck getDeck() {return deck;}
    public void setDeck(SimplePinochleDeck deck) {this.deck = deck;}

    public ArrayList<PlayerModel> getPlayers() {return players;}
    public void setPlayers(ArrayList<PlayerModel> players) {this.players = players;}

    /**
     * Set up the deck and the players.
     */
    public void initializeGame() {
        SimplePinochleDeck deck = new SimplePinochleDeck();
        setDeck(deck);
        setNumberOfCardsInHand(this.deck.getNumberOfCardsInDeck()/getNumberOfPlayers());
        setPlayers(initializePlayers(getNumberOfPlayers(), "Henry"));
    }

    /**
     * Deal all cards to the players (12 cards per hand for 4 players = 48 total cards).
     */
    public void dealCards() {
        if (deck != null && players != null && !players.isEmpty()) {
            logger.info("Dealing {} cards to each of {} players...", getNumberOfCardsInHand(), getNumberOfPlayers());
            deck.deal(players, getNumberOfCardsInHand());
            logger.info("All {} cards have been dealt.", deck.getNumberOfCardsInDeck());
        } else {
            logger.error("Cannot deal cards. Deck or players not properly initialized.");
        }
    }
    
    /**
     * Display all player hands according to the current game settings.
     * 
     * @param gameMode The hand access mode (OPEN_HAND or CLOSED_HAND)
     */
    public void displayHands(int gameMode) {
        if (players == null || players.isEmpty()) {
            logger.error("No players found to display hands.");
            return;
        }
        
        logger.info("Displaying hands in {} mode", gameMode == Constants.OPEN_HAND ? "OPEN" : "CLOSED");
        
        HandDisplayController displayController = new HandDisplayController();
        displayController.setGameHandAccess(gameMode);
        
        ConsoleHandDisplay consoleDisplay = new ConsoleHandDisplay();
        
        displayController.displayAllHands(players, consoleDisplay);
    }

    public void playGame() {
        // Deal cards and display hands
        dealCards();
        
        // Display hands in closed mode (default)
        displayHands(Constants.CLOSED_HAND);
    }

    /**
     * Start the game by initializing the players and board.
     * @param args
     */
    public static void main(String [] args) {
        logger.info("=== PINOCHLE GAME INITIALIZATION ===");
        
        TestPinochle pinochle = new TestPinochle();
        pinochle.initializeGame();
        
        // Play the game (deal cards and show in closed mode)
        pinochle.playGame();
        
        // Also demonstrate open hand mode
        logger.info("=== OPEN HAND MODE DEMONSTRATION ===");
        pinochle.displayHands(Constants.OPEN_HAND);
        
        logger.info("=== GAME COMPLETED ===");
    }
}
