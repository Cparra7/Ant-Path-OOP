package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Ant extends Movable {

    // core state (same behavior)
    private int maximumSpeed = 100;
    private int foodLevel = 5000;
    private int foodConsumptionRate = 1;
    private int healthLevel = 3;
    private int lastFlagReached = 1;

    // game link
    private GameWorld gw;

    // Singleton (keep)
    private static Ant ant;

    // Only created through createAnt()
    private Ant(Point location, GameWorld gw) {
        // required defaults for Movable (keep these values the same)
        super(ColorUtil.rgb(255, 0, 0), 50, location, 0, 100, gw);
        this.gw = gw;
    }

    // speed up by 1 until hitting max
    public void accelerate() {
        if (getSpeed() < getMaximumSpeed()) {
            System.out.println("Ant accelerated.");
            setSpeed(getSpeed() + 1);
        } else {
            System.out.println("Ant reached max speed.");
        }
    }

    // slow down by 1 until 0
    public void brake() {
        if (getSpeed() > 0) {
            System.out.println("Ant is braking.");
            setSpeed(getSpeed() - 1);
        } else {
            System.out.println("Ant reached min speed.");
        }
    }

    // turn left/right by amount (positive or negative)
    public void turn(int amount) {
        setHeading(getHeading() + amount);
    }

    // only counts the next sequential flag; win at 4
    public boolean checkFlagCollision(int sequenceNum) {
        if (getLastFlagReached() + 1 == sequenceNum) {
            System.out.println("Ant hit a flag " + sequenceNum + ".");
            setLastFlagReached(sequenceNum);

            if (getLastFlagReached() == 4) {
                System.out.println("Game over, you win!  Total time: " + gw.getTimer());
                System.exit(0);
            }
            return true;
        } else {
            System.out.println("Must get flag " + (getLastFlagReached() + 1) + " first");
            return false;
        }
    }

    // spider collision side effects (keep logic the same)
    public void collisionSpider() {
        System.out.println("Ant hit a spider.");

        // lighten red a bit (same effect)
        setColor(ColorUtil.rgb(ColorUtil.red(getColor()) - 5, 0, 0));

        // max speed drops; health drops
        setMaximumSpeed(getMaximumSpeed() - 2);
        setHealthLevel(getHealthLevel() - 1);

        // clamp actual speed to new max
        if (getSpeed() > getMaximumSpeed()) {
            setSpeed(getMaximumSpeed());
        }
    }

    // singleton factory
    public static Ant createAnt(Point location, GameWorld gw) {
        if (ant == null) {
            ant = new Ant(location, gw);
        }
        return ant;
    }

    // allow recreation
    public static void destroyAnt() {
        ant = null;
    }

    // tick: eat food, move, stay in bounds (only when food remains)
    public void modifyFoodLevel(int width, int height, int tick) {
        if (foodLevel > 0) {
            setFoodLevel(foodLevel - foodConsumptionRate);
            move(tick);
            checkBoundary(width, height, tick);
        }
    }

    // draw as a filled circle centered at current location
    @Override
    public void draw(Graphics g, Point pCmpRelPrnt) {
        int size = getSize();
        int x = (int) (getX() + pCmpRelPrnt.getX() - size / 2);
        int y = (int) (getY() + pCmpRelPrnt.getY() - size / 2);

        g.setColor(getColor());
        g.fillArc(x, y, size, size, 0, 360);
    }

    // getters/setters (unchanged signatures)
    public int getMaximumSpeed() { return maximumSpeed; }
    public void setMaximumSpeed(int maximumSpeed) { this.maximumSpeed = maximumSpeed; }

    public int getFoodLevel() { return foodLevel; }
    public void setFoodLevel(int foodLevel) { this.foodLevel = foodLevel; }

    public int getFoodConsumptionRate() { return foodConsumptionRate; }
    public void setFoodConsumptionRate(int foodConsumptionRate) { this.foodConsumptionRate = foodConsumptionRate; }

    public int getHealthLevel() { return healthLevel; }
    public void setHealthLevel(int healthLevel) { this.healthLevel = healthLevel; }

    public int getLastFlagReached() { return lastFlagReached; }
    public void setLastFlagReached(int lastFlagReached) { this.lastFlagReached = lastFlagReached; }

    // concise status line (same fields/same order)
    @Override
    public String toString() {
        return "Ant: loc=" + (Math.round(getX() * 10.0) / 10.0) + "," + (Math.round(getY() * 10.0) / 10.0) +
               " color=" + colorToString() +
               " heading=" + getHeading() +
               " speed=" + getSpeed() +
               " size=" + getSize() +
               " maxSpeed=" + getMaximumSpeed() +
               " foodConsumptionRate=" + getFoodConsumptionRate();
    }
}
