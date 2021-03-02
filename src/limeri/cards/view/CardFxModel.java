package limeri.cards.view;

import java.util.Comparator;

import javafx.scene.image.ImageView;
import limeri.cards.model.CardModel;

public class CardFxModel extends CardView {
    private String imagePath;
    private String id;
    private Boolean visible = true;
    private ImageView image = new ImageView();
    private int order;

    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Boolean getVisible() {
        return visible;
    }
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    public ImageView getImage() {
        return image;
    }
    public void setImage(ImageView image) {
        this.image = image;
    }

}

class CardSorter implements Comparator<CardFxModel> {

    @Override
    public int compare(CardFxModel card1, CardFxModel card2) {
        return card1.getOrder() - card2.getOrder();
    }
}
