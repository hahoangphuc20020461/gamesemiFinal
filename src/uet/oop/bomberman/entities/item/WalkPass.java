package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.BombermanGame.player;

public class WalkPass extends Item {

    public WalkPass(int x, int y, Image img) {
        super(x, y, img);
    }

    public WalkPass(boolean take) {
        super(take);
    }

    public WalkPass() {}

    private void takeWalkPass() {
        for (Entity entity : stillObjects)
            if (entity instanceof WalkPass && !this.take)
                if (listKill[entity.getY() / 32][entity.getX() / 32] == '4')
                    entity.setImg(Sprite.powerup_wallpass.getFxImage());

        if (!this.take)
            if (player.getX() == this.x && player.getY() == this.y) {
                this.setImg(Sprite.grass.getFxImage());
                this.take = true;
            }
    }

    @Override
    public void update() {
        takeWalkPass();
    }
}
