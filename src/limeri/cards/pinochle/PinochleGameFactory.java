package limeri.cards.pinochle;

/**
 * This factory will allow the user to choose the kind of display the game uses.
 */

import limeri.cards.CardRoot;
import limeri.cards.pinochle.Pinochle;
import limeri.cards.pinochle.game.*;

public class PinochleGameFactory extends CardRoot {

    /**
     * This will choose the display of the game.  For now, always use Swing.
     * @return a class representing the game being used
     */
    public static Pinochle getPinochleGame() {
        Pinochle game = new PinochleSwingGame();
        return game;
    }

}
