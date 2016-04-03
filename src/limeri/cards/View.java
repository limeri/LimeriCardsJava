package limeri.cards;

/**
 * This is the top of the View hierarchy for all games.
 * 
 * @author limeri
 */

public abstract class View extends CardRoot {
    private limeri.cards.Model model;

    protected limeri.cards.Model getModel() {return model;}
    protected void setModel(limeri.cards.Model model) {this.model = model;}

}
