package limeri.cards.controller;

/**
 * This is a generic class to control a deck of cards using PropertyReader.
 * Uses log4j for logging and improved error handling.
 * 
 * <ul>
 * <li>initialize: create the deck of cards using PropertyReader configuration
 * <li>deal: deal a specified number of cards to a list of hands
 * </ul>
 *
 * Todos:
 * <ul>
 *     <li>Allow all cards to be dealt even if the final count is uneven</li>
 *     <li>Allow for a kitty</li>
 * </ul>
 *
 * @author limeri
 */

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import limeri.cards.Controller;
import limeri.cards.model.CardModel;
import limeri.cards.model.DeckModel;
import limeri.cards.model.PlayerModel;

public class DeckController extends Controller {
    
    private static final Logger logger = LogManager.getLogger(DeckController.class);
    
    private DeckModel deck;

    protected DeckModel getDeck() {return deck;}
    protected void setDeck(DeckModel deck) {this.deck = deck;}

    /**
     * The deal method will shuffle and deal a specified number of cards to a specified number of hands.
     * 
     * @param players an ArrayList of {@link PlayerModel} objects to receive the dealt cards
     * @param cardsPerHand the number of cards to deal to each hand
     * @throws IllegalStateException if deck is not initialized or insufficient cards
     * @throws IllegalArgumentException if invalid parameters provided
     */
    public void deal(ArrayList<PlayerModel> players, int cardsPerHand) {
        if (deck == null) {
            logger.error("Cannot deal cards - deck not initialized");
            throw new IllegalStateException("Deck not initialized");
        }
        
        if (players == null || players.isEmpty()) {
            logger.error("Cannot deal cards - no players provided");
            throw new IllegalArgumentException("No players provided");
        }
        
        if (cardsPerHand <= 0 {
            logger.error("Cannot deal cards - invalid cards per hand: {}", cardsPerHand);
            throw new IllegalArgumentException("Cards per hand must be positive");
        }

        int totalCardsNeeded = players.size() * cardsPerHand;
        if (totalCardsNeeded > deck.getNumberOfCardsInDeck()) {
            logger.error("Cannot deal {} cards to {} players - only {} cards available", 
                        cardsPerHand, players.size(), deck.getNumberOfCardsInDeck());
            throw new IllegalArgumentException("Insufficient cards in deck");
        }
        
        logger.info("Dealing {} cards to {} players", cardsPerHand, players.size());
        
        Stack<CardModel> cards = new Stack<CardModel>();
        cards.addAll(this.deck.getCards());
        Collections.shuffle(cards);
        
        logger.debug("Shuffled {} cards for dealing", cards.size());

        // Now that the cards are shuffled, we can deal one hand at a time.
        for (PlayerModel player : players) {
            logger.debug("Dealing {} cards to player: {}", cardsPerHand, player.getName());
            
            // Deal all of the cards for one hand.
            for (int i = 0; i < cardsPerHand; i++) {
                try {
                    CardModel card = cards.pop();
                    player.addCardToHand(card);
                    logger.trace("Dealt card {}{} to {}", card.getName(), card.getSuit(), player.getName());
                } catch (EmptyStackException e) {
                    logger.error("Ran out of cards while dealing to player: {}", player.getName());
                    throw new IllegalStateException("Insufficient cards during dealing", e);
                }
            }
            player.sortHand();
            logger.debug("Completed dealing to player: {} (hand size: {})", player.getName(), player.getHand().size());
        }
        
        logger.info("Successfully dealt {} cards to {} players", totalCardsNeeded, players.size());
    }

    /**
     * Get the number of cards in the deck from the model.
     * @return the number of cards in the deck
     */
    public int getNumberOfCardsInDeck() {
        return this.deck.getNumberOfCardsInDeck();
    }

    /**
     * Initialize the deck controller by creating the model with the correct deck name.
     * @param deckName the name of the deck type to initialize
     * @throws RuntimeException if deck initialization fails
     */
    public void initialize(String deckName) {
        logger.info("Initializing deck controller with deck type: {}", deckName);
        try {
            initializeDeckModel(deckName);
            logger.info("Deck controller initialized successfully - {} cards available", getNumberOfCardsInDeck());
        } catch (Exception e) {
            logger.error("Failed to initialize deck controller: {}", e.getMessage(), e);
            throw new RuntimeException("Deck initialization failed", e);
        }
    }

    /**
     * Initialize the deck model using PropertyReader.
     * @param deckName the name of the deck type to initialize
     */
    private void initializeDeckModel(String deckName) {
        logger.debug("Creating new DeckModel for deck type: {}", deckName);
        this.deck = new DeckModel();
        this.deck.initialize(deckName);
        logger.debug("DeckModel created with {} cards", this.deck.getNumberOfCardsInDeck());
    }
    
    /**
     * Get a random card back image from the deck model.
     * @return a card back image path
     */
    public String getRandomCardBackImage() {
        if (deck == null) {
            logger.warn("Deck not initialized, cannot get card back image");
            return "images/playing_cards_classic/b1fh.png";
        }
        return deck.getRandomCardBackImage();
    }
    
    /**
     * Get a specific card back image from the deck model.
     * @param cardBackName the name of the card back
     * @return a card back image path
     */
    public String getCardBackImage(String cardBackName) {
        if (deck == null) {
            logger.warn("Deck not initialized, cannot get card back image");
            return "images/playing_cards_classic/b1fh.png";
        }
        return deck.getCardBackImage(cardBackName);
    }
}