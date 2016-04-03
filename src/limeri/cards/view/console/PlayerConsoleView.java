package limeri.cards.view.console;

/**
 * This class renders a hand on the console.
 */

import limeri.cards.model.HandModel;
import limeri.cards.view.PlayerView;
import limeri.cards.model.PlayerModel;
import limeri.cards.ViewFactory;
import limeri.cards.view.console.HandConsoleView;

public class PlayerConsoleView extends PlayerView {

    public void render() {
        PlayerModel player = getModel();
        System.out.println("\nPlayer" + player.getName());
        HandModel hand = player.getHand();
        HandConsoleView handView = ViewFactory.createHandView(hand);
        handView.render();
    }
}
