package limeri.cards.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import limeri.cards.Model;

/**
 * This contains all of the information needed about a hand. 
 * @author limeri
 */
public class HandModel extends Model {

    private ArrayList<limeri.cards.model.CardModel> currentCards = new ArrayList<CardModel>();
    private ArrayList<limeri.cards.model.CardModel> originalCards;

    /**
     * Add a card to the hand.
     * @param card limeri.cards.model.CardModel object to include in current hand
     */
    public void addCard(limeri.cards.model.CardModel card) {
        this.currentCards.add(card);
    }

    /**
     * Remove a card to the hand.
     * @param card limeri.cards.model.CardModel object to remove from the current hand
     */
    public void Remove(limeri.cards.model.CardModel card) {
        this.currentCards.remove(card);
    }

    /**
     * Get the card iterator for the current hand.
     * @return iterator of the card ArrayList
     */
    public Iterator<limeri.cards.model.CardModel> getCurrentHandIterator() {
        // If the original cards are empty, set them to the current hand.
        if (this.originalCards == null) {
            this.originalCards = this.currentCards;
        }
        return this.currentCards.iterator();
    }

    /**
     * Get the card iterator for the original hand.
     * @return iterator of the card ArrayList
     */
    public Iterator<limeri.cards.model.CardModel> getOriginalHandIterator() {
        return this.originalCards.iterator();
    }

    public void sortCards() {
        Collections.sort(this.currentCards);
    }
}
