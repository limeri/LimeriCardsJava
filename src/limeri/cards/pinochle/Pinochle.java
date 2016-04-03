package limeri.cards.pinochle;

/**
 * This is the driver for the Pinochle game.  This class will get the game initialized.  It is up
 * to the initializer to enable the player to start a game.
 */

import limeri.cards.CardRoot;
import limeri.cards.pinochle.PinocleGameFactory;
import limeri.cards.pinochle.PinochleGame;

public abstract class Pinochle extends CardRoot {

    public static void main(String [] args) {
        PinochleGame pinochle = PinocleGameFactory.getPinochleGame();
        pinochle.initializeGame();
    }
}