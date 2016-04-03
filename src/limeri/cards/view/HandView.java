package limeri.cards.view;

import limeri.cards.View;

public abstract class HandView extends View {

    public HandView() {
        super();
    }
    /**
     * Override the default getModel and setModel to use the a Hand model instead of a generic model.
     * @param model a {@link limeri.cards.model.HandModel} object
     */
    protected limeri.cards.model.HandModel getModel() {return (limeri.cards.model.HandModel)super.getModel();}
    protected void setModel(limeri.cards.model.HandModel model) {super.setModel(model);}

}