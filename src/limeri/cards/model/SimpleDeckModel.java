package limeri.cards.model;

/**
 * A simplified version of DeckModel that hardcodes the Pinochle deck
 * without requiring the PropertyReader dependency.
 * 
 * @author limeri
 */

import java.util.*;
import limeri.cards.Model;

public class SimpleDeckModel extends Model {

    private ArrayList<CardModel> cards = new ArrayList<CardModel>();
    private int numberOfCardsInDeck;

    public ArrayList<CardModel> getCards() {return cards;}
    
    public int getNumberOfCardsInDeck() {return numberOfCardsInDeck;}
    public void setNumberOfCardsInDeck(int numberOfCardsInDeck) {this.numberOfCardsInDeck = numberOfCardsInDeck;}

    /**
     * Initialize the Pinochle deck with hardcoded card definitions.
     */
    public void initialize(String deckName) {
        if ("Pinochle".equals(deckName)) {
            initializePinochleDeck();
        } else {
            // Default to Pinochle for now
            initializePinochleDeck();
        }
        setNumberOfCardsInDeck(this.cards.size());
    }
    
    /**
     * Create the standard Pinochle deck:
     * 48 cards total - A, 10, K, Q, J, 9 in each suit, with 2 copies of each card
     */
    private void initializePinochleDeck() {
        // Define card properties: Name, Rank, Suit, SuitRank
        String[] names = {"A", "10", "K", "Q", "J", "9"};
        int[] ranks = {6, 5, 4, 3, 2, 1};
        String[] suits = {"S", "H", "C", "D"}; 
        int[] suitRanks = {4, 3, 2, 1}; // Spades > Hearts > Clubs > Diamonds
        
        // Create 2 copies of each card (Pinochle uses double deck)
        for (int copy = 0; copy < 2; copy++) {
            for (int suitIndex = 0; suitIndex < suits.length; suitIndex++) {
                String suit = suits[suitIndex];
                int suitRank = suitRanks[suitIndex];
                
                for (int nameIndex = 0; nameIndex < names.length; nameIndex++) {
                    String name = names[nameIndex];
                    int rank = ranks[nameIndex];
                    
                    // Generate image path
                    String imagePath = generateImagePath(name, suit);
                    
                    CardModel card = new CardModel(name, suit, rank, suitRank, imagePath);
                    this.cards.add(card);
                }
            }
        }
    }
    
    /**
     * Generate the image path for a card based on its name and suit.
     */
    private String generateImagePath(String name, String suit) {
        String cardName;
        switch (name) {
            case "A": cardName = "A"; break;
            case "10": cardName = "10"; break;
            case "K": cardName = "K"; break;
            case "Q": cardName = "Q"; break;
            case "J": cardName = "J"; break;
            case "9": cardName = "09"; break;
            default: cardName = name; break;
        }
        
        return "images/playing_cards_classic/card_" + cardName + suit + ".png";
    }
}
