package limeri.cards.pinochle.game;

/**
 * This is the driver for the Pinochle game.
 */

import java.util.ArrayList;
import java.util.Iterator;

import limeri.cards.deck.PinochleDeck;
import limeri.cards.model.PlayerModel;
import limeri.cards.pinochle.PinochleGame;
import limeri.cards.ViewFactory;
import limeri.cards.view.console.PlayerConsoleView;

public class PinochleConsoleGame extends PinochleGame {

    /**
     * Play a hand on the console.
     */
    public void playHand() {
        PinochleDeck deck = getDeck();
        ArrayList<PlayerModel> players = getPlayers();
        deck.deal(players, getNumberOfCardsInHand());

        for (Iterator<PlayerModel> iter = players.iterator(); iter.hasNext();) {
            PlayerModel player = (PlayerModel)iter.next();
            PlayerConsoleView playerView = (PlayerConsoleView)ViewFactory.createPlayerView(player);
            playerView.render();
        }
    }

    /**
     * Play a game.
     */
    public void playGame() {
        playHand();
    }
}