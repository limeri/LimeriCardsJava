package limeri.cards.controller;

/**
 * This controller manages the display of player hands based on game settings
 * and player positions. It handles the logic for showing cards vs. card backs.
 * 
 * @author limeri.ai
 */

import java.util.ArrayList;
import limeri.cards.Controller;
import limeri.cards.Constants;
import limeri.cards.model.PlayerModel;

public class HandDisplayController extends Controller {
    private int gameHandAccess = Constants.CLOSED_HAND;
    
    public int getGameHandAccess() {return gameHandAccess;}
    public void setGameHandAccess(int gameHandAccess) {this.gameHandAccess = gameHandAccess;}
    
    /**
     * Determines whether a player's hand should be shown (cards visible) or 
     * hidden (card backs visible) based on game settings and player position.
     * 
     * Rules:
     * - If game is OPEN_HAND, all hands are visible
     * - If game is CLOSED_HAND:
     *   - South player (human) hand is always visible
     *   - Other players (West, North, East) show card backs only
     * 
     * @param player The player whose hand visibility is being determined
     * @return true if cards should be shown, false if card backs should be shown
     */
    public boolean shouldShowCards(PlayerModel player) {
        // If the game is set to open hands, show all cards
        if (gameHandAccess == Constants.OPEN_HAND) {
            return true;
        }
        
        // For closed hands, only show the South player's cards (human player)
        return Constants.TABLE_POS_SOUTH.equals(player.getTablePosition());
    }
    
    /**
     * Gets the appropriate card back image path based on the player's table position.
     * Different positions use different card back orientations.
     * 
     * @param player The player whose card back image is needed
     * @return String path to the appropriate card back image
     */
    public String getCardBackImageForPosition(PlayerModel player) {
        String position = player.getTablePosition();
        
        // Use different card back orientations based on table position
        switch (position) {
            case Constants.TABLE_POS_NORTH:
                return "images/playing_cards_classic/b1fh.png"; // Blue full horizontal
            case Constants.TABLE_POS_SOUTH:
                return "images/playing_cards_classic/b1fv.png"; // Blue full vertical
            case Constants.TABLE_POS_EAST:
                return "images/playing_cards_classic/b1pr.png"; // Blue partial right
            case Constants.TABLE_POS_WEST:
                return "images/playing_cards_classic/b1pl.png"; // Blue partial left
            default:
                return "images/playing_cards_classic/b1fv.png"; // Default to vertical
        }
    }
    
    /**
     * Display all player hands according to the visibility rules.
     * 
     * @param players ArrayList of PlayerModel objects to display
     * @param displayImpl The display implementation to use for output
     */
    public void displayAllHands(ArrayList<PlayerModel> players, HandDisplayInterface displayImpl) {
        System.out.println("\n=== HANDS DEALT ===");
        System.out.println("Game Mode: " + (gameHandAccess == Constants.OPEN_HAND ? "OPEN" : "CLOSED"));
        
        // Display hands in table order: North, East, South, West
        String[] positions = {Constants.TABLE_POS_NORTH, Constants.TABLE_POS_EAST, 
                             Constants.TABLE_POS_SOUTH, Constants.TABLE_POS_WEST};
        
        for (String position : positions) {
            PlayerModel player = findPlayerByPosition(players, position);
            if (player != null) {
                boolean showCards = shouldShowCards(player);
                displayImpl.displayHand(player, showCards, getCardBackImageForPosition(player));
            }
        }
    }
    
    /**
     * Helper method to find a player by their table position.
     * 
     * @param players ArrayList of players to search
     * @param position Table position to find
     * @return PlayerModel at the specified position, or null if not found
     */
    private PlayerModel findPlayerByPosition(ArrayList<PlayerModel> players, String position) {
        for (PlayerModel player : players) {
            if (position.equals(player.getTablePosition())) {
                return player;
            }
        }
        return null;
    }
}
