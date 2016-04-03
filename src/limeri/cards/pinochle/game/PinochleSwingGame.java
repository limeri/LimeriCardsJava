package limeri.cards.pinochle.game;

/**
 * This is the swing driver for the Pinochle game.
 * The initialization flow is:
 * 
 * <ul>
 * <li>Pinochle.initialize calls this.initializeGame</li>
 * <li>this.initializeGame calls super.initializeGame (inits the deck and players)</li>
 * <li>this.initializeGame calls PinochleGameSwingView.render</li>
 * <li>PinochleGameSwingView.render defers to GameView.render</li>
 * <li>GameView.render calls PinochleGameSwingView.initializeMenu</li>
 * <li>GameView.render calls PinochleGameSwingView.initializeGame</li>
 * <li>GameView.render calls GameView.showGame</li>
 * </ul>
 * 
 * The "New Game" flow is:
 * 
 * <ul>
 * <li>PinochleGameSwingView.miNewGame calls this.playGame</li>
 * <li>this.playGame calls this.playHand</li>
 * </ul>
 * 
 */

import java.util.ArrayList;

import limeri.cards.deck.PinochleDeck;
import limeri.cards.model.PlayerModel;
import limeri.cards.pinochle.PinochleGame;
import limeri.cards.pinochle.view.PinochleGameSwingView;

public class PinochleSwingGame extends PinochleGame {

    private PinochleGameSwingView gameView;

    public PinochleGameSwingView getGameView() {return gameView;}
    public void setGameView(PinochleGameSwingView gameView) {this.gameView = gameView;}

    /**
     * Create the initial game view.
     */
    public void initializeGame() {
        super.initializeGame();
        setGameView(new PinochleGameSwingView());
        gameView.setGame(this);
        gameView.drawInitialView();
    }

    /**
     * This method manages playing a hand from start to finish.
     */
    public void playHand() {
        PinochleGameSwingView gameView = getGameView();
        dealHand();
        gameView.showHands();
    }

    @Override
    public void playGame() {
        playHand();
    }

    /**
     * Deal the cards to the players.
     */
    private void dealHand() {
        PinochleDeck deck = getDeck();
        ArrayList<PlayerModel> players = getPlayers();
        deck.deal(players, getNumberOfCardsInHand());
    }
}