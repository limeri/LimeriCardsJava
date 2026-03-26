package limeri.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewFactory extends CardRoot {

    private static final Logger logger = LogManager.getLogger(ViewFactory.class);

    /**
     * Create a View to display cards on the console.
     * @param card a {@link limeri.cards.model.CardModel} object
     * @return a {@link limeri.cards.view.console.CardConsoleView} object
     */
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
