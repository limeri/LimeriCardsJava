package limeri.cards.view;

/**
 * Console-based implementation of hand display.
 * Displays player hands as text output to the console.
 * 
 * @author limeri
 */

import java.util.Iterator;
import limeri.cards.controller.HandDisplayInterface;
import limeri.cards.model.CardModel;
import limeri.cards.model.PlayerModel;

public class ConsoleHandDisplay implements HandDisplayInterface {
    
    @Override
    public void displayHand(PlayerModel player, boolean showCards, String cardBackImage) {
        System.out.println("\n" + player.getTablePosition() + " (" + player.getName() + "):");
        System.out.println("Hand Size: " + player.getHand().size());
        
        if (showCards) {
            displayActualCards(player);
        } else {
            displayCardBacks(player, cardBackImage);
        }
    }
    
    /**
     * Display the actual cards in the player's hand.
     * 
     * @param player The player whose cards to display
     */
    private void displayActualCards(PlayerModel player) {
        System.out.println("Cards:");
        
        // Display cards in a formatted grid
        Iterator<CardModel> cardIterator = player.getHandIterator();
        int cardCount = 0;
        StringBuilder cardLine = new StringBuilder();
        
        while (cardIterator.hasNext()) {
            CardModel card = cardIterator.next();
            
            // Format card as "Name of Suit" (e.g., "A of S", "10 of H")
            String cardDisplay = String.format("%-5s", card.getName() + card.getSuit());
            cardLine.append(cardDisplay).append(" ");
            
            cardCount++;
            
            // Print 6 cards per line for better readability
            if (cardCount % 6 == 0) {
                System.out.println("  " + cardLine.toString());
                cardLine = new StringBuilder();
            }
        }
        
        // Print any remaining cards
        if (cardLine.length() > 0) {
            System.out.println("  " + cardLine.toString());
        }
        
        // Show hand summary
        displayHandSummary(player);
    }
    
    /**
     * Display card backs instead of actual cards.
     * 
     * @param player The player whose card backs to display
     * @param cardBackImage The card back image path (displayed as text reference)
     */
    private void displayCardBacks(PlayerModel player, String cardBackImage) {
        System.out.println("Cards: [HIDDEN - " + player.getHand().size() + " cards face down]");
        
        // Show a visual representation of card backs
        StringBuilder cardBacks = new StringBuilder();
        for (int i = 0; i < player.getHand().size(); i++) {
            cardBacks.append("[###] ");
            
            // Line break every 6 cards
            if ((i + 1) % 6 == 0) {
                cardBacks.append("\n  ");
            }
        }
        System.out.println("  " + cardBacks.toString());
        System.out.println("Card Back: " + extractCardBackName(cardBackImage));
    }
    
    /**
     * Display a summary of the hand showing suit distribution.
     * 
     * @param player The player whose hand to summarize
     */
    private void displayHandSummary(PlayerModel player) {
        // Count cards by suit
        int spades = 0, hearts = 0, clubs = 0, diamonds = 0;
        
        Iterator<CardModel> cardIterator = player.getHandIterator();
        while (cardIterator.hasNext()) {
            CardModel card = cardIterator.next();
            switch (card.getSuit()) {
                case "S": spades++; break;
                case "H": hearts++; break;
                case "C": clubs++; break;
                case "D": diamonds++; break;
            }
        }
        
        System.out.println("Summary: Spades=" + spades + ", Hearts=" + hearts + 
                          ", Clubs=" + clubs + ", Diamonds=" + diamonds);
    }
    
    /**
     * Extract a readable name from the card back image path.
     * 
     * @param imagePath The full image path
     * @return A readable name for the card back
     */
    private String extractCardBackName(String imagePath) {
        if (imagePath.contains("b1fh")) return "Blue Full Horizontal";
        if (imagePath.contains("b1fv")) return "Blue Full Vertical";
        if (imagePath.contains("b1pr")) return "Blue Partial Right";
        if (imagePath.contains("b1pl")) return "Blue Partial Left";
        if (imagePath.contains("b1pb")) return "Blue Partial Bottom";
        if (imagePath.contains("b1pt")) return "Blue Partial Top";
        return "Blue Card Back";
    }
}
