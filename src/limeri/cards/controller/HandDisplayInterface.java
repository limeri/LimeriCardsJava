package limeri.cards.controller;

/**
 * Interface for different hand display implementations.
 * This allows for different types of displays (console, GUI, etc.)
 * while maintaining consistent display logic.
 * 
 * @author limeri.ai
 */

import limeri.cards.model.PlayerModel;

public interface HandDisplayInterface {
    
    /**
     * Display a player's hand according to visibility rules.
     * 
     * @param player The player whose hand to display
     * @param showCards If true, show the actual cards; if false, show card backs
     * @param cardBackImage Path to the card back image to use when not showing cards
     */
    void displayHand(PlayerModel player, boolean showCards, String cardBackImage);
}
