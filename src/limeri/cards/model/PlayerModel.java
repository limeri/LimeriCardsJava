package limeri.cards.model;

import limeri.cards.Model;

/**
 * This class contains all of the player information for the game.
 * @author limeri
 */
import limeri.cards.model.HandModel;

public class PlayerModel extends Model {

    private HandModel hand;
    private String name;
    private int playerType;
    private String tablePosition;

    public HandModel getHand() {return hand;}
    public void setHand(HandModel hand) {this.hand = hand;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getPlayerType() {return playerType;}
    public void setPlayerType(int playerType) {this.playerType = playerType;}

    public String getTablePosition() {return tablePosition;}
    public void setTablePosition(String tablePosition) {this.tablePosition = tablePosition;}

}
