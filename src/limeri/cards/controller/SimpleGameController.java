package limeri.cards.controller;

import java.util.ArrayList;

import limeri.cards.Constants;
import limeri.cards.Controller;
import limeri.cards.model.PlayerModel;

public class SimpleGameController extends Controller {
    private int numberOfPlayers = Constants.PINOCHLE_INITIAL_PLAYERS;
    private int numberOfCardsInHand;
    private ArrayList<PlayerModel> players;

    public int getNumberOfPlayers() {return numberOfPlayers;}
    public void setNumberOfPlayers(int numberOfPlayers) {this.numberOfPlayers = numberOfPlayers;}

    public int getNumberOfCardsInHand() {return numberOfCardsInHand;}
    public void setNumberOfCardsInHand(int numberOfCardsInHand) {this.numberOfCardsInHand = numberOfCardsInHand;}

    public ArrayList<PlayerModel> getPlayers() {return players;}
    public void setPlayers(ArrayList<PlayerModel> players) {this.players = players;}

    /**
     * Set up the players in the game.
     * @param numberOfPlayers
     * @return ArrayList of {@link PlayerModel} objects
     */
    public ArrayList<PlayerModel> initializePlayers(int numberOfPlayers, String humanName) {
        ArrayList<PlayerModel> players = new ArrayList<PlayerModel>();
        String[] positions = {Constants.TABLE_POS_WEST, Constants.TABLE_POS_NORTH, Constants.TABLE_POS_EAST, Constants.TABLE_POS_SOUTH};

        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerModel player = new PlayerModel();
            if (i == 3) {
                player.setName(humanName);
            }
            else {
                player.setName("Player " + (i+1));
            }
            player.setTablePosition(positions[i]);
            players.add(player);
        }
        return players;
    }
}
