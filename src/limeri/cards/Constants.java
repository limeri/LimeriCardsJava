package limeri.cards;

/**
 * These are the general constants used by the limeri card games.
 * 
 * @author limeri
 *
 */

public class Constants {
    // Property file information
    public final static String DECK_PROPERTIES_PATH = "properties/Decks.xml";

    // Card back image constants
    public final static String CARD_BACK_BLUE_FULL_HORIZONTAL   = "images/playing_cards/b1fh.png";
    public final static String CARD_BACK_BLUE_FULL_VERTICAL     = "images/playing_cards/b1fv.png";
    public final static String CARD_BACK_BLUE_PARTIAL_BOTTOM    = "images/playing_cards/b1pb.png";
    public final static String CARD_BACK_BLUE_PARTIAL_LEFT      = "images/playing_cards/b1pl.png";
    public final static String CARD_BACK_BLUE_PARTIAL_RIGHT     = "images/playing_cards/b1pr.png";
    public final static String CARD_BACK_BLUE_PARTIAL_TOP       = "images/playing_cards/b1pt.png";
    public final static String CARD_BACK_RED_FULL_HORIZONTAL    = "images/playing_cards/b2fh.png";
    public final static String CARD_BACK_RED_FULL_VERTICAL      = "images/playing_cards/b2fv.png";
    public final static String CARD_BACK_RED_PARTIAL_BOTTOM     = "images/playing_cards/b2pb.png";
    public final static String CARD_BACK_RED_PARTIAL_LEFT       = "images/playing_cards/b2pl.png";
    public final static String CARD_BACK_RED_PARTIAL_RIGHT      = "images/playing_cards/b2pr.png";
    public final static String CARD_BACK_RED_PARTIAL_TOP        = "images/playing_cards/b2pt.png";

    // Player types
    public final static int PLAYER_HUMAN = 1;
    public final static int PLAYER_COMPUTER = 2;

    // Table positions
    public final static String TABLE_POS_NORTH = "North";
    public final static String TABLE_POS_SOUTH = "South";
    public final static String TABLE_POS_EAST  = "East";
    public final static String TABLE_POS_WEST  = "West";

    // Card access values (are we playing the hand open or closed)
    public final static int CLOSED_HAND = 1;
    public final static int OPEN_HAND   = 2;
}
