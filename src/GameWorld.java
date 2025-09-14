package com.mycompany.a1;

import java.util.ArrayList;
import java.util.Random;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public class GameWorld {

    // Elapsed time (persists across world re-initialization when a life is lost)
    private int clockTime = 0;

    // Lives remaining
    private int lives = 3;

    // All world objects
    private ArrayList<GameObject> gameObjects;

    // ---------------------------------------------
    // Construction & Initialization
    // ---------------------------------------------
    public GameWorld() { }

    public void init() {
        gameObjects = new ArrayList<>();

        // Flags (you can add up to 9 total if you like)
        Point f1 = new Point(200, 200);
        gameObjects.add(new Flag(1, f1));
        gameObjects.add(new Flag(2, new Point(200, 800)));
        gameObjects.add(new Flag(3, new Point(700, 800)));
        gameObjects.add(new Flag(4, new Point(900, 400)));

        // Ant starts at Flag #1
        gameObjects.add(new Ant(f1));

        // Spiders (≥ 2)
        gameObjects.add(new Spider());
        gameObjects.add(new Spider());

        // FoodStations (≥ 2)
        gameObjects.add(new FoodStation());
        gameObjects.add(new FoodStation());

        System.out.println("World initialized.");
    }

    // ---------------------------------------------
    // Player commands (delegated from Game)
    // ---------------------------------------------
    public void accelerate() {
        Ant ant = findAnt();
        if (ant != null) ant.accelerate();
    }

    public void brake() {
        Ant ant = findAnt();
        if (ant != null) ant.brake();
    }

    public void left() {
        Ant ant = findAnt();
        if (ant == null) return;

        int newHeading = normalizeHeading(ant.getHeading() - 5);
        ant.setHeading(newHeading);
        System.out.println("Ant turns left to " + newHeading + "°.");
    }

    public void right() {
        Ant ant = findAnt();
        if (ant == null) return;

        int newHeading = normalizeHeading(ant.getHeading() + 5);
        ant.setHeading(newHeading);
        System.out.println("Ant turns right to " + newHeading + "°.");
    }

    // keep headings within [0, 359]
    private int normalizeHeading(int h) {
        h %= 360;
        if (h < 0) h += 360;
        return h;
    }


    public void changeFoodConsumptionRate() {
        Ant ant = findAnt();
        if (ant != null) {
            ant.setFoodConsumption();
            System.out.println("Ant food consumption rate is now " + ant.getFoodConsumptionRate());
        }
    }

    public void collisionFlag(int sequenceNumber) {
        Ant ant = findAnt();
        if (ant == null) return;

        ant.checkFlagCollision(sequenceNumber);

        if (ant.getLastFlagReached() == getMaxFlagNumber()) {
            System.out.println("Game over, you win! Total time: " + clockTime);
            exit();
        }
    }

    public void collisionFoodStation() {
        System.out.println("Ant collides with a food station.");

        // Collect non-empty stations
        ArrayList<FoodStation> nonEmpty = new ArrayList<>();
        for (GameObject obj : gameObjects) {
            if (obj instanceof FoodStation) {
                FoodStation fs = (FoodStation) obj;
                if (fs.getCapacity() > 0) nonEmpty.add(fs);
            }
        }

        if (nonEmpty.isEmpty()) {
            System.out.println("No non-empty food stations available.");
            return;
        }

        Ant ant = findAnt();
        if (ant == null) return;

        // Pick one at random
        FoodStation chosen = nonEmpty.get(new Random().nextInt(nonEmpty.size()));

        // Transfer food
        ant.setFoodLevel(ant.getFoodLevel() + chosen.getCapacity());

        // Empty & fade the station
        chosen.setCapacity(0);
        chosen.setColor(ColorUtil.rgb(0, 150, 0));

        // Spawn a new station
        gameObjects.add(new FoodStation());
    }

    public void collisionSpider() {
        Ant ant = findAnt();
        if (ant == null) return;

        ant.collisionSpider();

        if (ant.getHealthLevel() == 0) {
            loseLifeAndReset();
        }
    }

    public void gameClockTick() {
    	System.out.println("Clock tick #" + clockTime + " (use d/m to see updates)");
        clockTime++;

        // Reduce ant food & check loss condition
        Ant ant = findAnt();
        if (ant != null) {
            ant.modifyFoodLevel();
            if (ant.getFoodLevel() == 0) {
                loseLifeAndReset();
                return; // world reinitialized
            }
        }

        // Let spiders adjust heading / wander
        for (GameObject obj : gameObjects) {
            if (obj instanceof Spider) {
                ((Spider) obj).controlSpider();
            }
        }

  
    }

    public void displayStatus() {
        Ant ant = findAnt();
        if (ant == null) return;

        System.out.println("Lives left = " + lives);
        System.out.println("Elapsed time = " + clockTime);
        System.out.println("Last flag reached = " + ant.getLastFlagReached());
        System.out.println("Ant food level = " + ant.getFoodLevel());
        System.out.println("Ant health level = " + ant.getHealthLevel());
    }

    public void map() {
        for (GameObject obj : gameObjects) {
            System.out.println(obj.toString());
        }
    }

    public void exit() {
        System.exit(0);
    }

    // ---------------------------------------------
    // Helpers
    // ---------------------------------------------
    private Ant findAnt() {
        for (GameObject obj : gameObjects) {
            if (obj instanceof Ant) return (Ant) obj;
        }
        return null;
    }

    private int getMaxFlagNumber() {
        int max = 0;
        for (GameObject obj : gameObjects) {
            if (obj instanceof Flag) {
                int n = ((Flag) obj).getSequenceNumber();
                if (n > max) max = n;
            }
        }
        return max;
    }

    private void loseLifeAndReset() {
        System.out.println("The ant lost a life.");
        lives--;
        if (lives <= 0) {
            System.out.println("Game over, you failed!");
            exit();
            return;
        }
        // Re-init the world but keep clockTime (per spec)
        init();
    }

    // ---------------------------------------------
    // Getters/Setters (minimal)
    // ---------------------------------------------
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
    public int getClockTime() { return clockTime; }
    public void setClockTime(int t) { this.clockTime = t; }
}
