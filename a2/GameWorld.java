package com.mycompany.a2;

import java.util.ArrayList;
import java.util.Random;
import java.util.Observable;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

/**
 * GameWorld
 * - Holds game state and objects.
 * - Handles input actions, ticks, and observer updates.
 */
public class GameWorld extends Observable {

    private int timer = 0;
    private int lives = 3;
    private boolean sound = false;

    private GameObjectCollection gameObjects;
    private int width;
    private int height;

    public GameWorld() { }

    public void init() {
        gameObjects = new GameObjectCollection();

        Point start = new Point(200, 200);

        // Flags (fixed layout)
        gameObjects.add(new Flag(1, start));
        gameObjects.add(new Flag(2, new Point(200, 800)));
        gameObjects.add(new Flag(3, new Point(700, 800)));
        gameObjects.add(new Flag(4, new Point(900, 400)));

        // Ant (singleton)
        gameObjects.add(Ant.getInstance(start));

        // Spiders (≥ 2)
        gameObjects.add(new Spider());
        gameObjects.add(new Spider());

        // Food stations (≥ 2)
        gameObjects.add(new FoodStation());
        gameObjects.add(new FoodStation());

        update();
    }

    // --- Controls ---

    public void accelerate() {
        Ant a = getAnt();
        if (a != null) a.accelerate();
        update();
    }

    public void brake() {
        Ant a = getAnt();
        if (a != null) a.brake();
        update();
    }

    public void left() {
        Ant a = getAnt();
        if (a != null) a.steer(-5);
        update();
    }

    public void right() {
        Ant a = getAnt();
        if (a != null) a.steer(5);
        update();
    }

    public void collisionFlag(int n) {
        Ant a = getAnt();
        if (a == null) return;

        a.checkFlagCollision(n);

        // win condition: reached last flag (4)
        if (a.getLastFlagReached() == 4) {
            System.out.println("Game over, you win! Total time: " + timer);
            exit();
        }
        update();
    }

    public void collisionFoodStation() {
        System.out.println("Ant collides with a food station.");

        // collect non-empty stations
        ArrayList<FoodStation> stations = new ArrayList<>();
        IIterator it = gameObjects.getIterator();
        while (it.hasNext()) {
            GameObject obj = it.getNext();
            if (obj instanceof FoodStation) {
                FoodStation fs = (FoodStation) obj;
                if (fs.getCapacity() > 0) stations.add(fs);
            }
        }
        if (stations.isEmpty()) return;

        Ant a = getAnt();
        if (a == null) return;

        // pick one at random and transfer food
        FoodStation chosen = stations.get(new Random().nextInt(stations.size()));
        a.setFoodLevel(a.getFoodLevel() + chosen.getCapacity());
        chosen.setCapacity(0);
        chosen.setColor(ColorUtil.rgb(0, 150, 0));

        // spawn a new station
        gameObjects.add(new FoodStation());
        update();
    }

    public void collisionSpider() {
        Ant a = getAnt();
        if (a == null) return;

        a.collisionSpider();
        if (a.getHealthLevel() == 0) resetAfterDeath();
        update();
    }

    public void gameClockTick() {
        System.out.println("Clock tick #" + timer);
        timer++;

        IIterator it = gameObjects.getIterator();
        while (it.hasNext()) {
            GameObject obj = it.getNext();

            if (obj instanceof Ant) {
                Ant a = (Ant) obj;
                a.modifyFoodLevel(width, height);
                if (a.getFoodLevel() == 0) {
                    resetAfterDeath();
                    return; // world reinitialized
                }
            }
            if (obj instanceof Spider) {
                ((Spider) obj).controlSpider(width, height);
            }
        }
        update();
    }

    // --- Displays ---

    public void displayCurrentValues() {
        Ant a = getAnt();
        if (a == null) return;

        System.out.println("Number of lives left=" + lives);
        System.out.println("Current clock value=" + timer);
        System.out.println("Highest flag number reached=" + a.getLastFlagReached());
        System.out.println("Ant's current food level=" + a.getFoodLevel());
        System.out.println("Ant's health level=" + a.getHealthLevel());
    }

    // For MapView compatibility (prints each object's toString)
    public void displayCurrentMap() {
        IIterator it = gameObjects.getIterator();
        while (it.hasNext()) {
            System.out.println(it.getNext().toString());
        }
    }

    // Keep your original method name too if you prefer calling map() elsewhere
    public void map() { displayCurrentMap(); }

    // --- State control ---

    private void resetAfterDeath() {
        System.out.println("Ant lost a life.");
        lives--;
        if (lives <= 0) {
            System.out.println("Game over, you failed!");
            exit();
        }
        Ant.resetInstance();
        init();
    }

    public void exit() {
        System.exit(0);
    }

    private void update() {
        setChanged();
        notifyObservers(this);
    }

    // --- Helpers ---

    private Ant getAnt() {
        IIterator it = gameObjects.getIterator();
        while (it.hasNext()) {
            GameObject obj = it.getNext();
            if (obj instanceof Ant) return (Ant) obj;
        }
        return null;
    }

    // Expose Ant stats for ScoreView without leaking the Ant ref
    public int getLastFlagReached() {
        Ant a = getAnt();
        return (a != null) ? a.getLastFlagReached() : 1;
    }

    public int getFoodLevel() {
        Ant a = getAnt();
        return (a != null) ? a.getFoodLevel() : 0;
    }

    public int getHealthLevel() {
        Ant a = getAnt();
        return (a != null) ? a.getHealthLevel() : 0;
    }

    // --- Getters / Setters ---

    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }

    public int getTimer() { return timer; }
    public void setTimer(int t) { this.timer = t; }

    public boolean getSound() { return sound; }
    public void setSound(boolean s) { sound = s; update(); }

    public int getWidth() { return width; }
    public void setWidth(int w) { width = w; }

    public int getHeight() { return height; }
    public void setHeight(int h) { height = h; }
}
