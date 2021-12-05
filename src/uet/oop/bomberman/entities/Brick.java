package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.control.collision.Collision;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;

public class Brick extends Entity{
    private int swapBrick = 1;
    private int countBreak = 0;
    private long _timeAct;
    private long timeTemp;

    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    private void hideOnBrick() {
        for (Entity entity : stillObjects) {
            if (entity instanceof Brick && !entity.isBreakdown()) {
                if (listKill[entity.getY() / 32][entity.getX() / 32] == '4') {
                        entity.setImg(Sprite.grass.getFxImage());
                    //entity.setBreakdown(false);
                    //BrickExplosion(entity);
                }
            }
        }
    }

    private void BrickExplosion(Entity e) {
        if (countBreak % 32 == 0) {
            if (swapBrick == 1) {
                e.setImg(Sprite.brick_exploded.getFxImage());
                swapBrick = 2;
            } else if (swapBrick == 2) {
                e.setImg(Sprite.brick_exploded1.getFxImage());
                swapBrick = 3;
            } else if (swapBrick == 3) {
                e.setImg(Sprite.brick_exploded2.getFxImage());
                swapBrick = 4;
            } else {
                e.setBreakdown(false);
                stillObjects.remove(e);
                //e.setImg(Sprite.grass.getFxImage());
                swapBrick = 1;
            }
        }
    }

    @Override
    public void update() {
        //countBreak++;
        hideOnBrick();
    }

}
