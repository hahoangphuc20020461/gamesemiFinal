package uet.oop.bomberman.entities.lifesingame;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.BombermanGame.player;
import static uet.oop.bomberman.control.Move.*;
import static uet.oop.bomberman.control.Move.Right;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Oneal extends LifesInGame {
    private static int swapKill = 1;
    private static int countKill = 0;

    public Oneal(int isMove, int swap, String direction, int count, int countToRun) {
        super(4, 1, "up", 0, 0);
    }

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
    }

    private void killEnemy(LifesInGame animal) {
        if (countKill % 16 == 0) {
            if (swapKill == 1) {
                animal.setImg(Sprite.mob_dead1.getFxImage());

                swapKill = 2;
            } else if (swapKill == 2) {
                animal.setImg(Sprite.mob_dead2.getFxImage());
                swapKill = 3;
            } else if (swapKill == 3) {
                animal.setImg(Sprite.mob_dead3.getFxImage());
                swapKill = 4;
            } else {
                animal.setLife(false);
                Animal.remove(animal);
                swapKill = 1;
            }
        }
    }

    private void kill() {
        for (LifesInGame animal : Animal) {
            if (listKill[animal.getY() / 32][animal.getX() / 32] == '4') {
                animal.setLife(false);
                killEnemy(animal);
            }
        }
    }

    private void onealMove() {
        if(this.x == player.getX()
                && this.y/SCALED_SIZE - player.getY()/SCALED_SIZE <= 2
                && this.y/SCALED_SIZE - player.getY()/SCALED_SIZE >= 0
                && getAt(this.x, player.getY() + 32)) {

            Up(this);


        } else if(this.x == player.getX()
                && player.getY()/SCALED_SIZE - this.y/SCALED_SIZE <= 2
                && player.getY()/SCALED_SIZE - this.y/SCALED_SIZE >= 0
                && getAt(this.x, this.y + 32)) {

            Down(this);

        } else if(this.y == player.getY()
                && player.getX()/SCALED_SIZE - this.x/SCALED_SIZE <= 2
                && player.getX()/SCALED_SIZE - this.x/SCALED_SIZE >=0
                && getAt(this.x + 32, this.y)) {


            Right(this);
        } else if(this.y == player.getY()
                && this.x/SCALED_SIZE - player.getX()/SCALED_SIZE <= 2
                && this.x/SCALED_SIZE - player.getX()/SCALED_SIZE >= 0
                && getAt(player.getX() + 32, this.y)) {

            Left(this);

        } else {
            Random random = new Random();
            int dir = random.nextInt(4);

            switch (dir) {
                case 0:
                    Down(this);
                    break;
                case 1:
                    Up(this);
                    break;
                case 2:
                    Left(this);
                    break;
                case 3:
                    Right(this);
                    break;
            }
        }
    }


    @Override
    public void update() {
        kill();
        countKill++;
        onealMove();
    }
}
