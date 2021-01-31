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

    // Initial number of players
    public final static int PINOCHLE_INITIAL_PLAYESRS = 4;
}
