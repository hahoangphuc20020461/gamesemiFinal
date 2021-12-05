package uet.oop.bomberman.entities.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.music.Sound;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.BombermanGame.player;

public class LifeUp extends Item {
    public LifeUp(int x, int y, Image img) {
        super(x, y, img);
    }

    private void takeItem() {
        for (Entity entity : stillObjects)
            if (entity instanceof LifeUp && !this.take)
                if (listKill[entity.getY() / 32][entity.getX() / 32] == '4')
                    entity.setImg(Sprite.powerup_detonator.getFxImage());

        if (!this.take)
            if (player.getX() == this.x && player.getY() == this.y) {
                this.setImg(Sprite.grass.getFxImage());
                this.take = true;
                denorator = 2;
                Sound take = new Sound(Sound.takeItem);
                take.play();
            }
    }

    @Override
    public void update() {
        takeItem();
    }
}
