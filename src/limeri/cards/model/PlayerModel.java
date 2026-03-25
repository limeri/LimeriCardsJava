package limeri.cards.model;

/**
 * This class contains all of the player information for the game.
 * @author limeri
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import limeri.cards.Model;

public class PlayerModel extends Model {

    private ArrayList<CardModel> hand;
    private ArrayList<CardModel> originalHand;
    private String name;
    private int playerType;
    private String tablePosition;
    private int handAccess = limeri.cards.Constants.CLOSED_HAND;
    
    public PlayerModel() {
        hand = new ArrayList<CardModel>();
        originalHand = new ArrayList<CardModel>();
    }

    public ArrayList<CardModel> getHand() {return hand;}
    public void setHand(ArrayList<CardModel> hand) {this.hand = hand;}

    public ArrayList<CardModel> getOriginalHand() {return originalHand;}
    public void setOriginalHand(ArrayList<CardModel> hand) {this.originalHand = hand;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getPlayerType() {return playerType;}
    public void setPlayerType(int playerType) {this.playerType = playerType;}

    public String getTablePosition() {return tablePosition;}
    public void setTablePosition(String tablePosition) {this.tablePosition = tablePosition;}

    public int getHandAccess() {return handAccess;}
    public void setHandAccess(int handAccess) {this.handAccess = handAccess;}

    /**
     * Add a card to the player's hand.
     * @param card
     */
    public void addCardToHand(CardModel card) {
        hand.add(card);
    }

    /**
     * Remove a card from the hand.
     * @param card limeri.cards.model.CardModel object to remove from the current hand
     */
    public void Remove(limeri.cards.model.CardModel card) {
        this.hand.remove(card);
    }

    /**
     * Sort the cards in the hand based on the compareTo in CardModel.
     */
    public void sortHand() {
        Collections.sort(this.hand);
    }

    /**
     * Get the card iterator for the current hand.
     * @return iterator of the card ArrayList
     */
    public Iterator<limeri.cards.model.CardModel> getHandIterator() {
        return this.hand.iterator();
    }

    /**
     * Get the card iterator for the original hand.
     * @return iterator of the card ArrayList
     */
    public Iterator<limeri.cards.model.CardModel> getOriginalHandIterator() {
        return this.originalHand.iterator();
    }
}
