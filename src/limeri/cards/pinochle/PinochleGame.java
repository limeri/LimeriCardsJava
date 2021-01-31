package limeri.cards.pinochle;

/**
 * This is the top of the hierarchy for Pinochle Game class.  The children will be dedicated to managing
 * the game based on the display (console, swing, mobile, whatever).
 */

import java.util.ArrayList;

import limeri.cards.CardRoot;
import limeri.cards.Constants;
import limeri.cards.deck.PinochleDeck;
import limeri.cards.model.PlayerModel;

public abstract class PinochleGame extends CardRoot {

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
     * Set up the players in the game.
     * @param numberOfPlayers
     * @return ArrayList of {@link PlayerModel} objects
     */
    public ArrayList<PlayerModel> initializePlayers(int numberOfPlayers, String humanName) {
        ArrayList<PlayerModel> players = new ArrayList<PlayerModel>();
        String[] positions = {Constants.TABLE_POS_WEST, Constants.TABLE_POS_NORTH, Constants.TABLE_POS_EAST, Constants.TABLE_POS_SOUTH};

        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerModel player = new PlayerModel();
            if (i == 3) {
                player.setName(humanName);
            }
            else {
                player.setName("Player " + (i+1));
            }
            player.setTablePosition(positions[i]);
            players.add(player);
        }
        return players;
    }

    /**
     * Set up the deck and the players.
     */
    public void initializeGame() {
        setDeck(new PinochleDeck());
        setNumberOfCardsInHand(this.deck.getNumberOfCardsInDeck()/getNumberOfPlayers());
        setPlayers(initializePlayers(getNumberOfPlayers(), "Henry"));
    }

    /**
     * Play a game.
     */
    public abstract void playGame();

    public static void main(String [] args) {
        PinochleGame pinochle = PinocleGameFactory.getPinochleGame();
        pinochle.initializeGame();
    }
}