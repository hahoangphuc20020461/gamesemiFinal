package uet.oop.bomberman.entities.lifesingame;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.music.Sound;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.Animal;
import static uet.oop.bomberman.BombermanGame.listKill;
import static uet.oop.bomberman.control.Move.*;
import static uet.oop.bomberman.control.Move.Right;

public class Ballom extends LifesInGame{
    private static int swapKill = 1;
    private static int countKill = 0;

    public Ballom(int isMove, int swap, String direction, int count, int countToRun) {
        super(4, 1, "up", 0, 0);
    }

    public Ballom(int x, int y, Image img) {
        super(x, y, img);
    }

    private void killBallom(LifesInGame lifesInGame) {
        if (countKill % 32 == 0) {
            if (swapKill == 1) {
                lifesInGame.setImg(Sprite.mob_dead1.getFxImage());
                swapKill = 2;
            } else if (swapKill == 2) {
                lifesInGame.setImg(Sprite.mob_dead2.getFxImage());
                swapKill = 3;
            } else if (swapKill == 3) {
                lifesInGame.setImg(Sprite.mob_dead3.getFxImage());
                swapKill = 4;
            } else {
                lifesInGame.setLife(false);
                Animal.remove(lifesInGame);
                swapKill = 1;
                Sound kill = new Sound(Sound.enemyDead);
                kill.play();
            }
        }
    }

    private void killed() {
        for (LifesInGame animal : Animal) {
            if (animal instanceof Ballom && !animal.isLife()) {
                if (listKill[animal.getY() / 32][animal.getX() / 32] == '4') {
                    animal.setLife(false);
                    killBallom(animal);
                }
            }
        }
    }

    private void locOfBallom() {
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

    @Override
    public void update() {
        killed();
        countKill++;
        locOfBallom();

    }
}
