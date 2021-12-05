package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uet.oop.bomberman.control.Move;
import uet.oop.bomberman.control.collision.Collision;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.item.FlamePass;
import uet.oop.bomberman.entities.item.FlameUp;
import uet.oop.bomberman.entities.item.LifeUp;
import uet.oop.bomberman.entities.item.SpeedUp;
import uet.oop.bomberman.entities.lifesingame.Ballom;
import uet.oop.bomberman.entities.lifesingame.Doll;
import uet.oop.bomberman.entities.lifesingame.LifesInGame;
import uet.oop.bomberman.entities.lifesingame.Oneal;
import uet.oop.bomberman.graphics.Sprite;

import javafx.fxml.FXMLLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static uet.oop.bomberman.entities.Portal.isPortal;
//import static uet.oop.bomberman.entities.Portal.isnotPortal;


public class BombermanGame extends Application {

    /*
        W:992px
        H:416px
     */

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static int _level = 1;
    public static int _width = 0;
    public static int _height = 0;
    public static char[][] _map;
    public static char[][] listKill;
    public static boolean running = true;

    public static ImageView authorView;
    public static ImageView winView;

    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static  List<Entity> stillObjects = new ArrayList<>();
    public static  List<Entity> obs = new ArrayList<>();
    public static List<LifesInGame> Animal = new ArrayList<>();
    public static LifesInGame player;
    public static Sprite _sprite;

    public static int speed = 1;
    public static int denorator = 1;



    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }


    @Override
    public void start(Stage stage) throws IOException {

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Image deadplayer = new Image("backgr/gameover.jpg");
        Image win = new Image("backgr/backgr.png");
        authorView = new ImageView(deadplayer);
        winView = new ImageView(win);

        // Tao root container
        Group root = new Group();
        Group group = new Group();
        Group group1 = new Group();
        root.getChildren().add(canvas);
        group.getChildren().add(authorView);
        group1.getChildren().add(winView);

        // Tao scene
        Scene scene = new Scene(root);

        Scene scene1 = new Scene(group);

        Scene scene2 = new Scene(group1);

            player = new Bomber(1, 1, Sprite.player_right_2.getFxImage());
            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.LEFT ) {
                    //bomberman.setX((bomberman.getX() - 1 * speed));
                    Move.Left(player);
                }
                if (event.getCode() == KeyCode.RIGHT ) {
                    //bomberman.setX((bomberman.getX() + 1 * speed));
                    Move.Right(player);
                }
                if (event.getCode() == KeyCode.DOWN ) {
                    //bomberman.setY((bomberman.getY() + 1 * speed));
                    Move.Down(player);
                }
                if (event.getCode() == KeyCode.UP ) {
                    //bomberman.setY((bomberman.getY() - 1 * speed));
                    Move.Up(player);
                }
                if (event.getCode() == KeyCode.SPACE) {
                    Bomb.putBomb(player);
                }
            });
            //entities.add(bomberman);
            entities.add(player);
            player.setLife(false);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman");
        stage.show();
        //mainStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (running && isPortal == false) {
                    render();
                    update();
                }
                else if (running == false){
                    authorView.setImage(deadplayer);
                    stage.setScene(scene1);
                    stage.show();
                    stop();
                }
                 else if (isPortal == true){
                    authorView.setImage(win);
                    stage.setScene(scene1);
                    stage.show();
                    stop();
                }
            }
        };
        timer.start();

        createMap();

    }
    /*
        public void createMap() {
            for (int i = 0; i < WIDTH; i++) {
                for (int j = 0; j < HEIGHT; j++) {
                    Entity object;

                    if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                    }
                    else {
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                    }

                    stillObjects.add(object);
                }
            }
        }
    */
    public void update() {
        entities.forEach(Entity::update);
        stillObjects.forEach(Entity::update);
        //obs.forEach(Entity::update);
        Animal.forEach(LifesInGame::update);
        player.update();
        player.setCountToRun(player.getCountToRun() + 1);

        if (player.getCountToRun() == 8) {
            Move.checkMove(player);
            player.setCountToRun(0);
        }
        for (LifesInGame a : Animal) {
            a.setCountToRun(a.getCountToRun() + 1);
            if (a.getCountToRun() == 32) {
                Move.checkMove(a);
                a.setCountToRun(0);
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        //obs.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        Animal.forEach(g -> g.render(gc));
        player.render(gc);
    }

    public void createMap() throws IOException {
        String path = "res/levels/Level" + _level + ".txt";
        try {
            File mapFile = new File(path);
            Scanner sc = new Scanner(mapFile);
            String[] info = sc.nextLine().split(" ");
            _level = Integer.parseInt(info[0]);
            _height = Integer.parseInt(info[1]);
            _width = Integer.parseInt(info[2]);

            _map = new char[_height][_width];
            listKill = new char[_height][_width];
/*
            LifesInGame Ballom1 = new Ballom(13, 1, Sprite.balloom_right1.getFxImage());
            LifesInGame Ballom2 = new Ballom(18, 5, Sprite.balloom_right1.getFxImage());
            LifesInGame Ballom3 = new Ballom(24, 5, Sprite.balloom_right1.getFxImage());
            Animal.add(Ballom1);
            Animal.add(Ballom2);
            Animal.add(Ballom3);

            LifesInGame Doll1 = new Doll(5, 5,Sprite.doll_left1.getFxImage());
            LifesInGame Doll2 = new Doll(25, 10,Sprite.doll_left1.getFxImage());
            LifesInGame Doll3 = new Doll(7, 11,Sprite.doll_left1.getFxImage());
            Animal.add(Doll1);
            Animal.add(Doll3);
            Animal.add(Doll2);

            LifesInGame Oneal1 = new Oneal(9, 11, Sprite.oneal_left1.getFxImage());
            LifesInGame Oneal2 = new Oneal(25, 8, Sprite.oneal_left1.getFxImage());
            LifesInGame Oneal3 = new Oneal(27, 10, Sprite.oneal_left1.getFxImage());
            LifesInGame Oneal4 = new Oneal(9, 5, Sprite.oneal_left1.getFxImage());
            Animal.add(Oneal1);
            Animal.add(Oneal2);
            Animal.add(Oneal3);
            Animal.add(Oneal4);

 */

            for (int i = 0; i < _height; i++) {
                String mapRow = sc.nextLine();
                for (int j = 0; j < _width; j++) {
                    _map[i][j] = mapRow.charAt(j);
                    switch (_map[i][j]) {
                        case '#':
                            stillObjects.add(j + i * _width, new Wall(j, i, Sprite.wall.getFxImage()));
                            break;
                        case '*':
                            stillObjects.add(j + i * _width, new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        case 'f' :
                            stillObjects.add(j + i * _width, new FlameUp(j, i, Sprite.brick.getFxImage()));
                            break;
                        case 's' :
                            stillObjects.add(j + i * _width, new SpeedUp(j, i, Sprite.brick.getFxImage()));
                            break;
                        case 'x' :
                            stillObjects.add(j + i * _width, new Portal(j, i, Sprite.brick.getFxImage()));
                            break;
                        case 'd' :
                            stillObjects.add(j + i * _width, new LifeUp(j, i, Sprite.brick.getFxImage()));
                            break;
                        default:
                            stillObjects.add(j + i * _width, new Grass(j, i, Sprite.grass.getFxImage()));
                    }
                }
            }
        } catch (NullPointerException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

