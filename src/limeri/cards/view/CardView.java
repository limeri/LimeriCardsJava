package limeri.cards.view;

import limeri.cards.View;

public abstract class CardView extends View {

    /**
     * Override the default getModel and setModel to use the correct model.
     * @param model a {@link limeri.cards.model.CardModel} object
     */
    protected limeri.cards.model.CardModel getModel() {return (limeri.cards.model.CardModel)super.getModel();}
    protected void setModel(limeri.cards.model.CardModel model) {super.setModel(model);}

}