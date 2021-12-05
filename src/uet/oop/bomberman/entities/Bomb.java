package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.control.collision.Collision;
import uet.oop.bomberman.entities.lifesingame.LifesInGame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.BombermanGame.*;
import uet.oop.bomberman.music.Sound;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Bomb extends Entity {

    public static int haveBomb = 0;
    protected static long _timeToExplode;
    protected static Bomber bomber;
    private static Entity bomb;
    private static long timeTemp;
    private static int swapActive = 1;
    private static int swapExplosion = 1;

    private static Entity fl_down;
    private static Entity fl_up;
    private static Entity fl_left;
    private static Entity fl_right;

    public static int powerBomb = 0;
    private static int powerBombDown = 0;
    private static int powerBombUp = 0;
    private static int powerBombLeft = 0;
    private static int powerBombRight = 0;

    private static boolean isEdge = false;
    private static boolean isMiddle = false;


    private static final List<Entity> listBombMiddleW = new ArrayList<>();
    private static final List<Entity> listBombMiddleH = new ArrayList<>();


    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public Bomb() {}

    @Override
    public void update() {
        checkBomb();
        checkExplosion();
    }

    public static void putBomb(LifesInGame lifes) {
        if (haveBomb == 0) {
            haveBomb++;
            _timeToExplode = System.currentTimeMillis();
            timeTemp = _timeToExplode;
            int x = lifes.getX() / SCALED_SIZE;
            int y = lifes.getY() / SCALED_SIZE;
            bomb = new Bomb(x, y, Sprite.bomb.getFxImage());
            stillObjects.add(bomb);
            _map[player.getY() / SCALED_SIZE][player.getX() / SCALED_SIZE] = '5';
            Sound putbomb = new Sound(Sound.putBomb);
            putbomb.play();
        }
    }

    public static void activeBomb() {
        if (swapActive == 1) {
            bomb.setImg(Sprite.bomb.getFxImage());
            swapActive = 2;
        } else if (swapActive == 2) {
            bomb.setImg(Sprite.bomb_1.getFxImage());
            swapActive = 3;
        } else if (swapActive == 3) {
            bomb.setImg(Sprite.bomb_2.getFxImage());
            swapActive = 4;
        } else {
            bomb.setImg(Sprite.bomb_1.getFxImage());
            swapActive = 1;
        }
    }

    public static void createMiddle() {
        Entity middle;
        for (int i = 1; i <= powerBombDown; i++) {
            middle = new Bomb(bomb.getX() / 32, bomb.getY() / 32 + i,
                    Sprite.bomb_exploded.getFxImage());
            listBombMiddleH.add(middle);
        }
        for (int i = 1; i <= powerBombUp; i++) {
            middle = new Bomb(bomb.getX() / 32, bomb.getY() / 32 - i,
                    Sprite.bomb_exploded.getFxImage());
            listBombMiddleH.add(middle);
        }
        for (int i = 1; i <= powerBombLeft; i++) {
            middle = new Bomb(bomb.getX() / 32 - i, bomb.getY() / 32,
                    Sprite.bomb_exploded.getFxImage());
            listBombMiddleW.add(middle);
        }
        for (int i = 1; i <= powerBombRight; i++) {
            middle = new Bomb(bomb.getX() / 32 + i, bomb.getY() / 32,
                    Sprite.bomb_exploded.getFxImage());
            listBombMiddleW.add(middle);
        }
        stillObjects.addAll(listBombMiddleW);
        stillObjects.addAll(listBombMiddleH);
    }

    private static void checkBomb () {
            if (haveBomb == 1) {
                if (System.currentTimeMillis() - _timeToExplode < 3000) {
                    if (System.currentTimeMillis() - timeTemp > 100) {
                        activeBomb();
                        timeTemp += 100;
                    }
                } else {
                    haveBomb = 2;
                    //obs.remove(bomb);
                    _timeToExplode = System.currentTimeMillis();
                    timeTemp = _timeToExplode;
                }
            }
        }

    public static void createEdge() {
        if (Collision.block_down_bomb(bomb, 0)) {
            fl_down = new Bomb(bomb.getX() / 32, bomb.getY() / 32 + 1,
                    Sprite.bomb_exploded.getFxImage());
            if (powerBomb > 0)
                for (int i = 1; i <= powerBomb; i++)
                    if (Collision.block_down_bomb(bomb, i)) {
                        fl_down.setY(bomb.getY() + 32 + i * 32);
                        powerBombDown++;
                    } else break;
            stillObjects.add(fl_down);
        }
        if (Collision.block_up_bomb(bomb, 0)) {
            fl_up = new Bomb(bomb.getX() / 32, bomb.getY() / 32 - 1,
                    Sprite.bomb_exploded.getFxImage());
            if (powerBomb > 0)
                for (int i = 1; i <= powerBomb; i++)
                    if (Collision.block_up_bomb(bomb, i)) {
                        fl_up.setY(bomb.getY() - 32 - i * 32);
                        powerBombUp++;
                    } else break;
            stillObjects.add(fl_up);
        }
        if (Collision.block_left_bomb(bomb, 0)) {
            fl_left = new Bomb(bomb.getX() / 32 - 1, bomb.getY() / 32,
                    Sprite.bomb_exploded.getFxImage());
            if (powerBomb > 0)
                for (int i = 1; i <= powerBomb; i++)
                    if (Collision.block_left_bomb(bomb, i)) {
                        fl_left.setX(bomb.getX() - 32 - i * 32);
                        powerBombLeft++;
                    } else break;
            stillObjects.add(fl_left);
        }
        if (Collision.block_right_bomb(bomb, 0)) {
            fl_right = new Bomb(bomb.getX() / 32 + 1, bomb.getY() / 32,
                    Sprite.bomb_exploded.getFxImage());
            if (powerBomb > 0)
                for (int i = 1; i <= powerBomb; i++)
                    if (Collision.block_right_bomb(bomb, i)) {
                        fl_right.setX(bomb.getX() + 32 + i * 32);
                        powerBombRight++;
                    } else break;
            stillObjects.add(fl_right);
        }
    }

        private static void checkExplosion () {
            if (haveBomb == 2)
                if (System.currentTimeMillis() - _timeToExplode < 1000) {
                    if (System.currentTimeMillis() - timeTemp > 100) {
                        if (!isEdge) {
                            createEdge();
                            isEdge = true;
                        }
                        if (powerBomb > 0) {
                            if (!isMiddle) {
                                createMiddle();
                                isMiddle = true;
                            }
                        }
                        //new SoundManager("sound/bomb_explosion.wav", "explosion");
                        Sound explosion = new Sound(Sound.Explosion);
                        explosion.play();
                        explosionCenter();
                        timeTemp += 100;
                    }
                } else {
                    haveBomb = 0;
                    _map[bomb.getY() / 32][bomb.getX() / 32] = 0;
                    listKill[bomb.getY() / 32][bomb.getX() / 32] = 0;
                    bomb.setImg(Sprite.transparent.getFxImage());
                    if (Collision.block_down_bomb(bomb, powerBombDown)) {
                        fl_down.setImg(Sprite.transparent.getFxImage());
                        _map[fl_down.getY() / 32][fl_down.getX() / 32] = 0;
                        listKill[fl_down.getY() / 32][fl_down.getX() / 32] = 0;
                    }
                    if (Collision.block_up_bomb(bomb, powerBombUp)) {
                        fl_up.setImg(Sprite.transparent.getFxImage());
                        _map[fl_up.getY() / 32][fl_up.getX() / 32] = 0;
                        listKill[fl_up.getY() / 32][fl_up.getX() / 32] = 0;
                    }
                    if (Collision.block_left_bomb(bomb, powerBombLeft)) {
                        fl_left.setImg(Sprite.transparent.getFxImage());
                        _map[fl_left.getY() / 32][fl_left.getX() / 32] = 0;
                        listKill[fl_left.getY() / 32][fl_left.getX() / 32] = 0;
                    }
                    if (Collision.block_right_bomb(bomb, powerBombRight)) {
                        fl_right.setImg(Sprite.transparent.getFxImage());
                        _map[fl_right.getY() / 32][fl_right.getX() / 32] = 0;
                        listKill[fl_right.getY() / 32][fl_right.getX() / 32] = 0;
                    }
                    if (isMiddle) {
                        for (Entity e : listBombMiddleW) {
                            listKill[e.getY() / 32][e.getX() / 32] = 0;
                            _map[e.getY() / 32][e.getX() / 32] = 0;
                        }
                        for (Entity e : listBombMiddleH) {
                            listKill[e.getY() / 32][e.getX() / 32] = 0;
                            _map[e.getY() / 32][e.getX() / 32] = 0;
                        }
                    }
                    stillObjects.removeAll(listBombMiddleH);
                    stillObjects.removeAll(listBombMiddleW);
                    listBombMiddleH.clear();
                    listBombMiddleW.clear();

                    isEdge = false;
                    isMiddle = false;
                    powerBombDown = 0;
                    powerBombUp = 0;
                    powerBombLeft = 0;
                    powerBombRight = 0;
                }
        }
    public static void explosionCenter() {
        if (swapExplosion == 1) {
            bomb.setImg(Sprite.bomb_exploded.getFxImage());
               listKill[bomb.getY() / 32][bomb.getX() / 32] = '4';
            if (Collision.block_down_bomb(bomb, powerBombDown)) {
                fl_down.setImg(Sprite.explosion_vertical_down_last.getFxImage());
                listKill[fl_down.getY() / 32][fl_down.getX() / 32] = '4';
            }
            if (Collision.block_up_bomb(bomb, powerBombUp)) {
                fl_up.setImg(Sprite.explosion_vertical_top_last.getFxImage());
                listKill[fl_up.getY() / 32][fl_up.getX() / 32] = '4';
            }
            if (Collision.block_left_bomb(bomb, powerBombLeft)) {
                fl_left.setImg(Sprite.explosion_horizontal_left_last.getFxImage());
                listKill[fl_left.getY() / 32][fl_left.getX() / 32] = '4';
            }
            if (Collision.block_right_bomb(bomb, powerBombRight)) {
                fl_right.setImg(Sprite.explosion_horizontal_right_last.getFxImage());
                listKill[fl_right.getY() / 32][fl_right.getX() / 32] = '4';
            }
            if (listBombMiddleH.size() > 0)
                for (Entity e : listBombMiddleH) {
                    e.setImg(Sprite.explosion_vertical.getFxImage());
                    listKill[e.getY() / 32][e.getX() / 32] = '4';
                }
            if (listBombMiddleW.size() > 0)
                for (Entity e : listBombMiddleW) {
                    e.setImg(Sprite.explosion_horizontal.getFxImage());
                    listKill[e.getY() / 32][e.getX() / 32] = '4';
                }
            swapExplosion = 2;

        } else if (swapExplosion == 2) {
            bomb.setImg(Sprite.bomb_exploded1.getFxImage());
            if (Collision.block_down_bomb(bomb, powerBombDown))
                fl_down.setImg(Sprite.explosion_vertical_down_last1.getFxImage());
            if (Collision.block_up_bomb(bomb, powerBombUp))
                fl_up.setImg(Sprite.explosion_vertical_top_last1.getFxImage());
            if (Collision.block_left_bomb(bomb, powerBombLeft))
                fl_left.setImg(Sprite.explosion_horizontal_left_last1.getFxImage());
            if (Collision.block_right_bomb(bomb, powerBombRight))
                fl_right.setImg(Sprite.explosion_horizontal_right_last1.getFxImage());
            if (isMiddle) {
                for (Entity e : listBombMiddleH) {
                    e.setImg(Sprite.explosion_vertical1.getFxImage());
                }
                for (Entity e : listBombMiddleW) {
                    e.setImg(Sprite.explosion_horizontal1.getFxImage());
                }
            }
            swapExplosion = 3;

        } else if (swapExplosion == 3) {
            bomb.setImg(Sprite.bomb_exploded2.getFxImage());
            if (Collision.block_down_bomb(bomb, powerBombDown))
                fl_down.setImg(Sprite.explosion_vertical_down_last2.getFxImage());
            if (Collision.block_up_bomb(bomb, powerBombUp))
                fl_up.setImg(Sprite.explosion_vertical_top_last2.getFxImage());
            if (Collision.block_left_bomb(bomb, powerBombLeft))
                fl_left.setImg(Sprite.explosion_horizontal_left_last2.getFxImage());
            if (Collision.block_right_bomb(bomb, powerBombRight))
                fl_right.setImg(Sprite.explosion_horizontal_right_last2.getFxImage());
            if (isMiddle) {
                for (Entity e : listBombMiddleH) {
                    e.setImg(Sprite.explosion_vertical2.getFxImage());
                }
                for (Entity e : listBombMiddleW) {
                    e.setImg(Sprite.explosion_horizontal2.getFxImage());
                }
            }
            swapExplosion = 1;
        }
    }

        public Bomber getBomber () {
            return bomber;
        }

        public void setBomber (Bomber bomber){
            this.bomber = bomber;
        }
    }

