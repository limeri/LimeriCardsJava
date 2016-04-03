package limeri.cards.view.console;

/**
 * This class renders a hand on the console.
 */

import java.util.Iterator;

import limeri.cards.ViewFactory;
import limeri.cards.view.HandView;
import limeri.cards.view.console.CardConsoleView;

public class HandConsoleView extends HandView {

    public void render() {
        limeri.cards.model.HandModel hand = getModel();
        String separator = "";
        for (Iterator<limeri.cards.model.CardModel> iter = hand.getCurrentHandIterator(); iter.hasNext();) {
            limeri.cards.model.CardModel card = (limeri.cards.model.CardModel)iter.next();
            System.out.print(separator);
            CardConsoleView cardView = (CardConsoleView)ViewFactory.createCardView(card);
            cardView.render();
            separator = ", ";
        }
        System.out.println("");
    }
}
