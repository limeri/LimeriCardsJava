package limeri.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewFactory {

    private static final Logger logger = LogManager.getLogger(ViewFactory.class);

    public View createView(CardModel card) {
        if (card == null) {
            logger.error("Card model cannot be null");
            throw new IllegalArgumentException("Card model cannot be null");
        }

        // Implementation for creating views 
        // (with interface-based dependency injection)

        return new CardConsoleView(card);
    }
}
