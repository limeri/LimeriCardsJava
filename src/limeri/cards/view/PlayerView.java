package limeri.cards.view;

import limeri.cards.View;

public abstract class PlayerView extends View {

    /**
     * Override the default getModel and setModel to use PlayerModel.
     * @param model a {@link limeri.cards.model.PlayerModel} object
     */
    protected limeri.cards.model.PlayerModel getModel() {return (limeri.cards.model.PlayerModel)super.getModel();}
    protected void setModel(limeri.cards.model.PlayerModel model) {super.setModel(model);}

}