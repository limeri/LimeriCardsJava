package limeri.cards.view;

import limeri.cards.View;
import limeri.cards.model.TableModel;

public abstract class TableView extends View {
    /**
     * Override the default getModel and setModel to use TableModel.
     * @param model a {@link TableModel} object
     */
    public TableModel getModel() {return (TableModel)super.getModel();}
    public void setModel(TableModel model) {super.setModel(model);}
}
