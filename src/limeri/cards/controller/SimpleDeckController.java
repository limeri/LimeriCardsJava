package limeri.cards.controller;

/**
 * A simplified deck controller that uses SimpleDeckModel
 * to avoid dependency issues.
 * 
 * @author limeri.ai
 */

import java.util.*;
import limeri.cards.Controller;
import limeri.cards.model.CardModel;
import limeri.cards.model.SimpleDeckModel;
import limeri.cards.model.PlayerModel;

public class SimpleDeckController extends Controller {

    private SimpleDeckModel deck;

    protected SimpleDeckModel getDeck() {return deck;}
    protected void setDeck(SimpleDeckModel deck) {this.deck = deck;}

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
        this.deck = new SimpleDeckModel();
        this.deck.initialize(deckName);
    }
}
