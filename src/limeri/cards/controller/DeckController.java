package limeri.cards.controller;

/**
 * This is a generic class to control a deck of cards.  It has the base methods:
 * 
 * <ul>
 * <li>initialize: create the dec of cards. It is probably called in constructor of the game specific subclass of Deck.
 * <li>deal: deal a specified number of cards to a list of hands
 * </ul>
 * 
 * @author limeri
 * 
 */

import java.util.*;

import limeri.cards.Controller;
import limeri.cards.model.CardModel;
import limeri.cards.model.DeckModel;
import limeri.cards.model.PlayerModel;

public class DeckController extends Controller {

    private DeckModel deck;

    protected DeckModel getDeck() {return deck;}
    protected void setDeck(DeckModel deck) {this.deck = deck;}

    /**
     * The deal method will shuffle and deal a specified number of cards to a specified number of hands.
     * 
     * @param players an ArrayList of {@link PlayerModel} objects to receive the dealt cards
     * @param cardsPerHand the number of cards to deal to each hand
     */
    public void deal(ArrayList<PlayerModel> players, int cardsPerHand) {
        Stack<CardModel> cards = new Stack<CardModel>();
        cards.addAll(this.deck.getCards());
        Collections.shuffle(cards);

        // Now that the cards are shuffled, we can deal one hand at a time.
        for (Iterator<?> playerIterator = players.iterator(); playerIterator.hasNext();) {
            PlayerModel player = (PlayerModel)playerIterator.next();

            // Deal all of the cards for one hand.
            for (int i = 0; i < cardsPerHand; i++) {
                CardModel card = null;
                try {
                    card = cards.pop();
                }
                catch (EmptyStackException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                player.addCardToHand(card);
            }
            player.sortHand();
        }
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
     * @param deckName
     */
    public void initialize(String deckName) {
        initializeDeckModel(deckName);
    }

    /**
     * Initialize the deck model.
     * @param deckName
     */
    private void initializeDeckModel(String deckName) {
        this.deck = new DeckModel();
        this.deck.initialize(deckName);
    }
}