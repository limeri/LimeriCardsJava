package limeri.cards.pinochle;

import java.util.ArrayList;

/**
 * This is the driver for the Pinochle game.  This class will get the game initialized.  It is up
 * to the initializer to enable the player to start a game.
 */

import limeri.cards.controller.GameController;
import limeri.cards.Constants;
import limeri.cards.deck.PinochleDeck;
import limeri.cards.model.PlayerModel;

public class Pinochle extends GameController {
    private int numberOfPlayers = Constants.PINOCHLE_INITIAL_PLAYESRS;
    private int numberOfCardsInHand;
    private PinochleDeck deck;
    private ArrayList<PlayerModel> players;

    public int getNumberOfPlayers() {return numberOfPlayers;}
    public void setNumberOfPlayers(int numberOfPlayers) {this.numberOfPlayers = numberOfPlayers;}

    public int getNumberOfCardsInHand() {return numberOfCardsInHand;}
    public void setNumberOfCardsInHand(int numberOfCardsInHand) {this.numberOfCardsInHand = numberOfCardsInHand;}

    public PinochleDeck getDeck() {return deck;}
    public void setDeck(PinochleDeck deck) {this.deck = deck;}

    public ArrayList<PlayerModel> getPlayers() {return players;}
    public void setPlayers(ArrayList<PlayerModel> players) {this.players = players;}

    /**
     * Set up the deck and the players.
     */
    public void initializeGame() {
        PinochleDeck deck = new PinochleDeck();
        setDeck(deck);
        setNumberOfCardsInHand(this.deck.getNumberOfCardsInDeck()/getNumberOfPlayers());
        setPlayers(initializePlayers(getNumberOfPlayers(), "Henry"));
//      PinochleGame pinochle = PinochleGameFactory.getPinochleGame();
    }

    /**
     * Start the game by initializing the players and board.
     * @param args
     */
    public static void main(String [] args) {
        Pinochle pinochle = new Pinochle();
        pinochle.initializeGame();
    }
}