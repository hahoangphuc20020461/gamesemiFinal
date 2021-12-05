package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Item extends Entity {
    public boolean take = false;

    public Item(int x, int y, Image img) {
        super(x, y, img);
    }

    public Item() {}

    public Item(boolean take) {
        this.take = take;
    }

    public boolean isTake() {
        return take;
    }

    public void setTake(boolean take) {
        this.take = take;
    }

    @Override
    public void update() {

    }
}
