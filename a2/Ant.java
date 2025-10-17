package com.mycompany.a2;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public class Ant extends Movable implements IFoodie {

    // core stats
    private int maximumSpeed = 20;
    private int foodLevel = 50;
    private int foodConsumptionRate = 2; // per tick
    private int healthLevel = 10;        // 0..10
    private int lastFlagReached = 1;

    // singleton
    private static Ant instance;

    private Ant(Point location) {
        // red, size=20, heading=0, speed=10
        super(ColorUtil.rgb(255, 0, 0), 20, location, 0, 10);
    }

    // create/get the single Ant; location only matters on first creation
    public static Ant getInstance(Point location) {
        if (instance == null) instance = new Ant(location);
        return instance;
    }

    // allow re-init after death/reset
    public static void resetInstance() { instance = null; }

    // --- presentation ---

    @Override
    public String toString() {
        return "Ant: loc=" + (Math.round(getX() * 10.0) / 10.0) + "," + (Math.round(getY() * 10.0) / 10.0) +
               " color=" + toStringColor() +
               " heading=" + getHeading() +
               " speed=" + getSpeed() +
               " size=" + getSize() +
               " maxSpeed=" + maximumSpeed +
               " foodConsumptionRate=" + foodConsumptionRate;
    }

    // --- controls ---

    // gradual accelerate, never above health-based cap
    public void accelerate() {
        int cap = allowedSpeedByHealth();
        int next = Math.min(getSpeed() + 1, cap);
        if (next > getSpeed()) {
            System.out.println("Ant is accelerating.");
            setSpeed(next);
        } else {
            System.out.println("Ant cannot accelerate further.");
        }
    }

    // reduce speed by 1 until 0
    public void brake() {
        if (getSpeed() > 0) {
            System.out.println("Ant is braking.");
            setSpeed(getSpeed() - 1);
        } else {
            System.out.println("Ant has reached min speed.");
        }
    }

    // turn by degrees; positive=right, negative=left (normalized to 0..359)
    public void steer(int amount) {
        int h = (getHeading() + amount) % 360;
        if (h < 0) h += 360;
        setHeading(h);
    }

    // --- events ---

    // strict flag order only
    public void checkFlagCollision(int sequenceNum) {
        if (lastFlagReached + 1 == sequenceNum) {
            System.out.println("Ant has collided with flag " + sequenceNum + ".");
            lastFlagReached = sequenceNum;
        } else {
            System.out.println("Please reach the flags in a sequential order.");
        }
    }

    // spider hit: lose health, fade red, enforce new speed cap
    public void collisionSpider() {
        System.out.println("Ant has collided with a spider.");

        if (healthLevel > 0) setHealthLevel(healthLevel - 1);

        int r = Math.max(0, ColorUtil.red(getColor()) - 15);
        setColor(ColorUtil.rgb(r, 0, 0));

        int cap = allowedSpeedByHealth();
        if (getSpeed() > cap) setSpeed(cap);
    }

    // tick hook: burn food, move if alive/fed, respect world bounds
    public void modifyFoodLevel(int worldWidth, int worldHeight) {
        if (foodLevel > 0 && healthLevel > 0) {
            foodLevel = Math.max(0, foodLevel - foodConsumptionRate);

            int cap = allowedSpeedByHealth();
            if (getSpeed() > cap) setSpeed(cap);

            if (foodLevel > 0 && healthLevel > 0) {
                move();
                checkBoundary(worldWidth, worldHeight);
            }
        }
    }

    // --- IFoodie support ---

    // A1-style IFoodie hook: adjust consumption rate a bit (kept simple)
    @Override
    public void setFoodConsumption() {
        // small tweak: +/-1 with clamp to at least 1
        int delta = (random.nextBoolean() ? 1 : -1);
        int next = Math.max(1, foodConsumptionRate + delta);
        foodConsumptionRate = next;
    }

    // extra convenience: explicit setter version
    public void setFoodConsumptionRate(int rate) {
        foodConsumptionRate = Math.max(1, rate);
    }

    // --- helpers ---

    // cap speed based on health (e.g., health 5 -> 50% of max)
    private int allowedSpeedByHealth() {
        int h = Math.max(0, Math.min(10, healthLevel));
        return (maximumSpeed * h) / 10;
    }

    // --- getters/setters with safety clamps ---

    public int getMaximumSpeed() { return maximumSpeed; }
    public void setMaximumSpeed(int maximumSpeed) {
        this.maximumSpeed = Math.max(1, maximumSpeed);
        int cap = allowedSpeedByHealth();
        if (getSpeed() > cap) setSpeed(cap);
    }

    public int getFoodLevel() { return foodLevel; }
    public void setFoodLevel(int foodLevel) { this.foodLevel = Math.max(0, foodLevel); }

    public int getFoodConsumptionRate() { return foodConsumptionRate; }

    public int getHealthLevel() { return healthLevel; }
    public void setHealthLevel(int healthLevel) {
        this.healthLevel = Math.max(0, Math.min(10, healthLevel));
        int cap = allowedSpeedByHealth();
        if (getSpeed() > cap) setSpeed(cap);
    }

    public int getLastFlagReached() { return lastFlagReached; }
    public void setLastFlagReached(int lastFlagReached) {
        this.lastFlagReached = Math.max(1, lastFlagReached);
    }
}
