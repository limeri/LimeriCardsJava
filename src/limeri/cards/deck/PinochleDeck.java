package limeri.cards.deck;

/**
 * Pinochle deck implementation that uses PropertyReader-based DeckController
 * and log4j for logging.
 * 
 * @author limeri
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import limeri.cards.controller.DeckController;

public class PinochleDeck extends DeckController {
    
    private static final Logger logger = LogManager.getLogger(PinochleDeck.class);
    
    public PinochleDeck() {
        super();
        logger.info("Creating Pinochle deck with PropertyReader");
        initialize("Pinochle");
        logger.info("Pinochle deck created successfully with {} cards", getNumberOfCardsInDeck());
    }
}
