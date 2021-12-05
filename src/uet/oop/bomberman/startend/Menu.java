package uet.oop.bomberman.startend;


import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static uet.oop.bomberman.BombermanGame.*;

public class Menu {
    private static ImageView statusGame;

    public static void myMenu() {
        Image Start = new Image("backgr/play1.png");
        statusGame = new ImageView(Start);
        statusGame.setX(-75);
        statusGame.setY(-10);
        statusGame.setScaleX(0.5);
        statusGame.setScaleY(0.5);

        Pane pane = new Pane();
        pane.setMaxSize(992, 416);
        pane.getChildren().add(statusGame);

        Group root = new Group();
        root.getChildren().add(pane);
    }

    public static void updateMenu() {
        /*
        if (player.isLife()) {
            if (running) {
                Image pauseGame = new Image("images/pauseGame.png");
                statusGame.setImage(pauseGame);
            } else {
                Image playGame = new Image("images/playGame.png");
                statusGame.setImage(playGame);
            }
        }
        else {
            Image newGame = new Image("images/newGame.png");
            statusGame.setImage(newGame);
        }

         */
        if (!player.isLife()) {
            Image newGame = new Image("backgr/play1.png");
            statusGame.setImage(newGame);
        }
    }
}
