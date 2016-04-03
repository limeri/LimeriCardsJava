package limeri.cards.model;

/**
 * TableModel contains the base information needed about a table.
 */

import java.util.ArrayList;
import java.util.Iterator;
import limeri.cards.exceptions.NoPlayersFoundException;
import limeri.cards.Model;
import limeri.cards.model.PlayerModel;

public class TableModel extends Model {
    private String color;
    private ArrayList<PlayerModel> players;

    protected String getColor() {return color;}
    protected void setColor(String color) {this.color = color;}

    public ArrayList<PlayerModel> getPlayers() {return players;}
    public void setPlayers(ArrayList<PlayerModel> players) {this.players = players;}

    /**
     * Get the number of players.
     * @return the number of players
     * @throws NoPlayersFoundException
     */
    public int getNumberOfPlayers() throws NoPlayersFoundException {
        if (this.players == null) {
            throw new NoPlayersFoundException();
        }
        return this.players.size();
    }

    /**
     * getPlayerIterator returns an iterator for the player ArrayList.
     * @return {@link Iterator} of {@link PlayerModel} objects
     */
    public Iterator<PlayerModel> getPlayerIterator() {
        return getPlayers().iterator();
    }
}
