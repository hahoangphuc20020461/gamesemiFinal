package uet.oop.bomberman.entities.item;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.music.Sound;

import static uet.oop.bomberman.BombermanGame.*;

public class SpeedUp extends Item {
    public SpeedUp(int x, int y, Image img) {
        super(x, y, img);
    }

    public SpeedUp() {}

    public SpeedUp(boolean take) {
        super(take);
    }

    private void takeTheSpeed() {
        for (Entity entity : stillObjects)
            if (entity instanceof SpeedUp && !this.take) {
                if (listKill[entity.getY() / 32][entity.getX() / 32] == '4')
                    entity.setImg(Sprite.powerup_speed.getFxImage());
            }
        if (!this.take) {
            if (player.getX() == this.x && player.getY() == this.y) {
                this.setImg(Sprite.grass.getFxImage());
                this.take = true;
                speed = 2;
                Sound take = new Sound(Sound.takeItem);
                take.play();
            }
        }
    }


    @Override
    public void update() {
        //super.update();
        takeTheSpeed();
    }
}
