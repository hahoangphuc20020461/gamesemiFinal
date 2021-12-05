package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.music.Sound;

import static uet.oop.bomberman.BombermanGame.*;

public class FlameUp extends Item {
    public FlameUp(int x, int y, Image img) {
        super(x, y, img);
    }

    public FlameUp(boolean take) {
        super(take);
    }

    public FlameUp() {}

    private void takeTheFlame() {
        for (Entity entity : stillObjects)
            if (entity instanceof FlameUp && !this.take)
                if (listKill[entity.getY() / 32][entity.getX() / 32] == '4')
                    entity.setImg(Sprite.powerup_flames.getFxImage());

        if (!this.take)
            if (player.getX() == this.x && player.getY() == this.y) {
                this.setImg(Sprite.grass.getFxImage());
                this.take = true;
                Bomb.powerBomb += 2;
                Sound take = new Sound(Sound.takeItem);
                take.play();
            }
    }

    @Override
    public void update() {
    takeTheFlame();
    }
}
