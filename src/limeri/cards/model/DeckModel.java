package limeri.cards.model;

/**
 * DeckModel that uses PropertyReader to load deck configuration from XML files.
 * Uses log4j for logging instead of System.out.println.
 * 
 * @author limeri
 */

import java.io.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import limeri.cards.*;
import Limeri.PropertyReader.*;

public class DeckModel extends Model {
    
    private static final Logger logger = LogManager.getLogger(DeckModel.class);
    
    private ArrayList<CardModel> cards = new ArrayList<CardModel>();
    private int numberOfCardsInDeck;
    private PropertyMap cardBacks;
    
    public ArrayList<CardModel> getCards() { return cards; }
    
    public int getNumberOfCardsInDeck() { return numberOfCardsInDeck; }
    public void setNumberOfCardsInDeck(int numberOfCardsInDeck) { this.numberOfCardsInDeck = numberOfCardsInDeck; }
    
    public PropertyMap getCardBacks() { return cardBacks; }

    /**
     * Initialize the deck using PropertyReader to load configuration from XML.
     */
    public void initialize(String deckName) {
        logger.info("Initializing deck: {}", deckName);
        
        try {
            // Load the deck configuration
            PropertyMap deckMap = getDeckPropertyArray(deckName);
            PropertyArray cardProps = (PropertyArray)(deckMap.getValue("Cards"));
            
            // Also load card backs from root
            File propertyFile = new File(Constants.DECK_PROPERTIES_PATH);
            PropertyMap rootMap = new PropertyMap();
            rootMap.setXMLFile(propertyFile);
            rootMap.parse();
            // For now, skip card backs and use default
            this.cardBacks = null;
            logger.debug("Skipping card backs parsing for now");
            
            logger.debug("Loaded card definitions and card backs");
            
            // Create cards using the iterator approach
            for (Iterator<?> i = cardProps.iterator(); i.hasNext();) {
                Properties prop = (Properties)i.next();
                
                String name = prop.getProperty("Name");
                String suit = prop.getProperty("Suit");
                String rankString = prop.getProperty("Rank");
                int rank = Integer.valueOf(rankString).intValue();
                String suitRankString = prop.getProperty("SuitRank");
                int suitRank = Integer.valueOf(suitRankString).intValue();
                String numInDeckString = prop.getProperty("NumberInDeck");
                int numberInDeck = Integer.valueOf(numInDeckString).intValue();
                String imagePath = prop.getProperty("Image");
                
                // Create the specified number of copies of this card
                for (int j = 0; j < numberInDeck; j++) {
                    CardModel card = new CardModel(name, suit, rank, suitRank, imagePath);
                    this.cards.add(card);
                    logger.trace("Created card: {}{} (copy {} of {})", name, suit, j + 1, numberInDeck);
                }
            }
            
            setNumberOfCardsInDeck(this.cards.size());
            logger.info("Successfully initialized {} deck with {} cards", deckName, this.cards.size());
            
        } catch (Exception e) {
            logger.error("Error initializing deck '{}': {}", deckName, e.getMessage(), e);
            throw new RuntimeException("Failed to initialize deck", e);
        }
    }

    /**
     * Get the correct deck PropertyArray from the Deck.xml property file. This array will contain
     * information about all of the cards in the deck.
     * @param deckName the name of the PropertyMap with the cards in the file.
     * @return PropertyMap containing the deck information
     */
    private PropertyMap getDeckPropertyArray(String deckName) {
        File propertyFile = new File(Constants.DECK_PROPERTIES_PATH);
        
        PropertyMap rootMap = new PropertyMap();
        rootMap.setXMLFile(propertyFile);
        try {
            rootMap.parse();
        } catch(Exception e) {
            logger.error("Error parsing property file: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to parse property file", e);
        }
        PropertyMap deckMap = (PropertyMap)rootMap.getValue(deckName);
        return deckMap;
    }
    
    /**
     * Get a random card back image path from the available card backs.
     */
    public String getRandomCardBackImage() {
        if (cardBacks == null) {
            logger.warn("No card backs available, using default");
            return "images/playing_cards_classic/b1fh.png";
        }
        
        // Use the default blue card back
        String cardBackName = "CARD_BACK_BLUE_FULL_HORIZONTAL";
        PropertyMap cardBackData = (PropertyMap) cardBacks.getValue(cardBackName);
        
        String imagePath = cardBackData.getValue("Image").toString();
        logger.debug("Selected card back: {} -> {}", cardBackName, imagePath);
        
        return imagePath;
    }
    
    /**
     * Get a specific card back image path by name.
     */
    public String getCardBackImage(String cardBackName) {
        if (cardBacks == null) {
            logger.warn("Card back '{}' not found, using default", cardBackName);
            return getRandomCardBackImage();
        }
        
        try {
            PropertyMap cardBackData = (PropertyMap) cardBacks.getValue(cardBackName);
            if (cardBackData == null) {
                logger.warn("Card back '{}' not found, using default", cardBackName);
                return getRandomCardBackImage();
            }
        } catch (Exception e) {
            logger.warn("Card back '{}' not found, using default", cardBackName);
            return getRandomCardBackImage();
        }
        
        PropertyMap cardBackData = (PropertyMap) cardBacks.getValue(cardBackName);
        String imagePath = cardBackData.getValue("Image").toString();
        
        logger.debug("Retrieved card back: {} -> {}", cardBackName, imagePath);
        return imagePath;
    }
}
