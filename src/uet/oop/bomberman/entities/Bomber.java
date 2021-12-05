package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.entities.lifesingame.LifesInGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.music.Sound;

import static uet.oop.bomberman.BombermanGame.*;

public class Bomber extends LifesInGame {
    private int countKill = 0;
    private int swapKill = 1;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public Bomber() {
    }

    public Bomber(int isMove, int swap, String direction, int countStep, int countToRun) {
        super(4, 1, "down", 0, 0);
    }

    private void bomberKilled(LifesInGame pl) {
        if (countKill % 16 == 0) {
            if (swapKill == 1) {
                pl.setImg(Sprite.player_dead1.getFxImage());
                swapKill = 2;
            } else if (swapKill == 2) {
                pl.setImg(Sprite.player_dead2.getFxImage());
                swapKill = 3;
            } else if (swapKill == 3) {
                pl.setImg(Sprite.player_dead3.getFxImage());
                swapKill = 4;
            } else {
                pl.setImg(Sprite.transparent.getFxImage());
                running = false;
                //Image gameOver = new Image(getClass().getResourceAsStream("res/backgr/gameover.png"));
                //authorView.setImage(gameOver);
                Sound die = new Sound(Sound.playerDead);
                die.play();
            }
        }
    }

    private void check() {
            if (player instanceof Bomber && !player.isLife()) {
                if (listKill[player.getY() / 32][player.getX() / 32] == '4') {
                    player.setLife(false);
                    bomberKilled(player);
                }
            }
        }

    private void checkEnemy3() {
        int ax = player.getX();
        int ay = player.getY();
        for (LifesInGame animal : Animal) {
            int bx = animal.getX();
            int by = animal.getY();
            if (ax == bx && by - 32 <= ay && by + 32 >= ay
                    || ay == by && bx - 32 <= ax && bx + 32 >= ax) {
                player.setLife(false);
                bomberKilled(player);
                break;
            }
        }
    }


    @Override
    public void update() {
        check();
        checkEnemy3();
        countKill++;
    }
}
