package limeri.cards.model;

/**
 * This is a generic class to control a deck of cards.  It has the base methods:
 * 
 * <ul>
 * <li>initialize: create the dec of cards. It is probably called in constructor of the game specific subclass of Deck.
 * </ul>
 * 
 * @author limeri
 * 
 */

import java.io.*;
import java.util.*;

import limeri.cards.*;
import Limeri.PropertyReader.*;

public class DeckModel extends Model {

    private ArrayList<limeri.cards.model.CardModel> cards = new ArrayList<CardModel>();
    private int numberOfCardsInDeck;

    public ArrayList<limeri.cards.model.CardModel> getCards() {return cards;}
//    public void setCards(ArrayList<limeri.cards.model.CardModel> cards) {this.cards = cards;}

    public int getNumberOfCardsInDeck() {return numberOfCardsInDeck;}
    public void setNumberOfCardsInDeck(int numberOfCardsInDeck) {this.numberOfCardsInDeck = numberOfCardsInDeck;}

    /**
     * Read the cards in a deck from the decks.xml input file.
     * @param deckName
     */
    public void initialize(String deckName) {
        // Add the cards to the deck.
        PropertyMap deckMap = getDeckPropertyArray(deckName);
        PropertyArray cardProps = (PropertyArray)(deckMap.getValue("Cards"));
        for (Iterator<?> i = cardProps.iterator(); i.hasNext();) {

            Properties     prop = (Properties)i.next();

            String name             = prop.getProperty("Name");
            String suit             = prop.getProperty("Suit");
            String rankString       = prop.getProperty("Rank");
            int    rank             = Integer.valueOf(rankString).intValue();
            String suitRankString   = prop.getProperty("SuitRank");
            int    suitRank         = Integer.valueOf(suitRankString).intValue();
            String numInDeckString  = prop.getProperty("NumberInDeck");
            int    numberInDeck     = Integer.valueOf(numInDeckString).intValue();
            String imagePath        = prop.getProperty("Image");

            limeri.cards.model.CardModel card = new limeri.cards.model.CardModel(name, suit, rank, suitRank, imagePath);
            for (int j = 0; j < numberInDeck; j++) {
                this.cards.add(card);
            }
        }
        setNumberOfCardsInDeck(this.cards.size());
    }

    /**
     * Get the correct deck PropertyArray from the Deck.xml property file.  This array will contain
     * information about all of the cards in the deck.
     * @param deckName the name of the PropertyMap with the cards in the file.
     * @return limeri.PropertyReader.PropertyMap containing the deck information
     */
    private PropertyMap getDeckPropertyArray(String deckName) {
        File propertyFile = new File(Constants.DECK_PROPERTIES_PATH);

        PropertyMap rootMap = new PropertyMap();
        rootMap.setXMLFile(propertyFile);
        try {
            rootMap.parse();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        PropertyMap deckMap = (PropertyMap)rootMap.getValue(deckName);
        return deckMap;
    }
}
