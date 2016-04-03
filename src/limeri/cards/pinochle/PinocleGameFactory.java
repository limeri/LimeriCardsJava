package limeri.cards.pinochle;

/**
 * This factory will allow the user to choose the kind of display the game uses.
 */

import limeri.cards.CardRoot;
import limeri.cards.pinochle.PinochleGame;
import limeri.cards.pinochle.game.*;

public class PinocleGameFactory extends CardRoot {

    /**
     * This will choose the display of the game.  For now, always use Swing.
     * @return a class representing the game being used
     */
    public static PinochleGame getPinochleGame() {
        PinochleGame game = new PinochleSwingGame();
        return game;
    }

}
