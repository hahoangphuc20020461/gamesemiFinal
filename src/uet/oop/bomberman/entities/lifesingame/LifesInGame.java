package uet.oop.bomberman.entities.lifesingame;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class LifesInGame extends Entity {
    protected int Move;
    protected int swap;
    protected String direct;
    protected int countStep;
    protected int countToRun;
    protected boolean life;

    public LifesInGame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public LifesInGame(int Move, int swap, String direct, int countStep, int countToRun) {
        this.Move = Move;
        this.swap = swap;
        this.direct = direct;
        this.countStep = countStep;
        this.countToRun = countToRun;
    }


    public LifesInGame() {}

    public int getMove() {
        return Move;
    }

    public void setMove(int move) {
        Move = move;
    }

    public int getSwap() {
        return swap;
    }

    public void setSwap(int swap) {
        this.swap = swap;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public int getCountStep() {
        return countStep;
    }

    public void setCountStep(int countStep) {
        this.countStep = countStep;
    }

    public int getCountToRun() {
        return countToRun;
    }

    public void setCountToRun(int countToRun) {
        this.countToRun = countToRun;
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }

    @Override
    public void update() {

    }
}
