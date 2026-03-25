package limeri.cards.deck;

/**
 * Simplified version of PinochleDeck that uses SimpleDeckController
 * to avoid PropertyReader dependencies.
 */

import limeri.cards.controller.SimpleDeckController;

public class SimplePinochleDeck extends SimpleDeckController {
    public SimplePinochleDeck() {
        super();
        initialize("Pinochle");
    }
}
