/**
 * 
 */
package limeri.cards.view.console;

import limeri.cards.view.CardView;

/**
 * This class renders the console view for a card.
 * @author limeri
 */


public class CardConsoleView extends CardView {

    /**
     * Render a card on the console.
     * This method does not print any new lines.  That must be handled by the caller.
     */
    public void render()
    {
        limeri.cards.model.CardModel card = this.getModel();
        System.out.print(card.getName() + card.getSuit());
    }

}
