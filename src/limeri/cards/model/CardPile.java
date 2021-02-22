package limeri.cards.model;

/**
 * A CardPile contains any cards that are not in a hand.  This can include cards that were not dealt to hands
 * (such as in Rummy), a discard pile, or meld.  These cards should be kept in order.
 */

import java.util.ArrayList;

import limeri.cards.Model;

public class CardPile extends Model {
    private ArrayList <CardModel> cards;

    public ArrayList<CardModel> getCards() {return cards;}
    public void setCards(ArrayList<CardModel> cards) {this.cards = cards;}
}
