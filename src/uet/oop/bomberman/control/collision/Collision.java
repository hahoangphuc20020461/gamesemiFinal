package uet.oop.bomberman.control.collision;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame._map;
import static uet.oop.bomberman.BombermanGame._sprite;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;


public class Collision {

    public static boolean canMove(int nextX, int nextY) {
        int size = SCALED_SIZE;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 1) / size;

        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;

        return !((_map[nextY_1][nextX_1] == '#' || _map[nextY_1][nextX_1] == '*' || _map[nextY_1][nextX_1] == 'f') ||
                (_map[nextY_2][nextX_2] == '#' || _map[nextY_2][nextX_2] == '*' || _map[nextY_2][nextX_2] == 'f') ||
                (_map[nextY_3][nextX_3] == '#' || _map[nextY_3][nextX_3] == '*' || _map[nextY_3][nextX_3] == 'f') ||
                (_map[nextY_4][nextX_4] == '#' || _map[nextY_4][nextX_4] == '*' || _map[nextY_4][nextX_4] == 'f'));
    }

    public static boolean block_down_bomb(Entity entity, int power) {
        int size = SCALED_SIZE;
        int downX = entity.getX() / size;
        int downY = entity.getY() / size + 1 + power;
        return (_map[downY][downX] == 0 && _map[downY][downX] == '*') || _map[downY][downX] != '#';
    }

    public static boolean block_up_bomb(Entity entity, int power) {
        int size = SCALED_SIZE;
        int upX = entity.getX() / size;
        int upY = entity.getY() / size - 1 - power;
        return (_map[upY][upX] == 0 && _map[upY][upX] == '*') || _map[upY][upX] != '#';
    }

    public static boolean block_left_bomb(Entity entity, int power) {
        int size = SCALED_SIZE;
        int leftX = entity.getX() / size - 1 - power;
        int leftY = entity.getY() / size;
        return (_map[leftY][leftX] == 0 && _map[leftY][leftX] == '*') || _map[leftY][leftX] != '#';
    }

    public static boolean block_right_bomb(Entity entity, int power) {
        int size = SCALED_SIZE;
        int rightX = entity.getX() / size + 1 + power;
        int rightY = entity.getY() / size;
        return (_map[rightY][rightX] == 0 && _map[rightY][rightX] == '*') || _map[rightY][rightX] != '#';
    }

    public static boolean EnemyMove(int nextX, int nextY) {
        int size = SCALED_SIZE;

        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 1) / size;

        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;

        return !((_map[nextY_1][nextX_1] == '#' || _map[nextY_1][nextX_1] == '*' || _map[nextY_1][nextX_1] == 'f' || _map[nextY_1][nextX_1] == '5') ||
                (_map[nextY_2][nextX_2] == '#' || _map[nextY_2][nextX_2] == '*' || _map[nextY_2][nextX_2] == 'f' || _map[nextY_2][nextX_2] == '5') ||
                (_map[nextY_3][nextX_3] == '#' || _map[nextY_3][nextX_3] == '*' || _map[nextY_3][nextX_3] == 'f' || _map[nextY_3][nextX_3] == '5') ||
                (_map[nextY_4][nextX_4] == '#' || _map[nextY_4][nextX_4] == '*' || _map[nextY_4][nextX_4] == 'f' || _map[nextY_4][nextX_4] == '5'));
    }
}
