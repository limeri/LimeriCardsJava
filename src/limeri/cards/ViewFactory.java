package limeri.cards;

/**
 * This creates a view.
 * 
 * @author limeri
 */

public class ViewFactory extends CardRoot {

    /**
     * Create a View to display cards on the console.
     * @param card a {@link limeri.cards.model.CardModel} object
     * @return a {@link limeri.cards.view.console.CardConsoleView} object
     */
    public static limeri.cards.view.console.CardConsoleView createCardView(limeri.cards.model.CardModel card) {
        limeri.cards.view.console.CardConsoleView cardView = new limeri.cards.view.console.CardConsoleView();
        cardView.setModel(card);
        return cardView;
    }

    /**
    * Create a View to display hands on the console.
     * @param hand a {@link limeri.cards.model.HandModel} object
     * @return a {@link limeri.cards.view.console.HandConsoleView} object
     */
//    public static limeri.cards.view.console.PlayerConsoleView createPlayerView(limeri.cards.model.PlayerModel player) {
//        limeri.cards.view.console.PlayerConsoleView playerView = new limeri.cards.view.console.PlayerConsoleView();
//        playerView.setModel(player);
//        return playerView;
//    }
}
