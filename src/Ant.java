package com.mycompany.a1;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;

public class Ant extends Movable implements IFoodie {

    private int maximumSpeed = 20;
    private int foodLevel = 50;
    private int foodConsumptionRate = 2;
    private int healthLevel = 10;
    private int lastFlagReached = 1;


    public Ant(Point location) {
        super(ColorUtil.rgb(255, 0, 0), 20, location, 0, 10); // red, size=20, heading=0, speed=10
    }

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

    public void accelerate() {
        int allowed = allowedSpeedByHealth();
        int next = getSpeed() + 1;
        if (next > allowed) next = allowed;
        if (next > getSpeed()) {
            System.out.println("Ant is accelerating.");
            setSpeed(next);
        } else {
            System.out.println("Ant cannot accelerate further.");
        }
    }

    public void brake() {
        if (getSpeed() > 0) {
            System.out.println("Ant is braking.");
            setSpeed(getSpeed() - 1);
        } else {
            System.out.println("Ant has reached min speed.");
        }
    }

    public void collisionSpider() {
        System.out.println("Ant has collided with a spider.");
        if (healthLevel > 0) healthLevel--;
        int r = Math.max(0, ColorUtil.red(getColor()) - 15);
        setColor(ColorUtil.rgb(r, 0, 0));
        int allowed = allowedSpeedByHealth();
        if (getSpeed() > allowed) setSpeed(allowed);
    }

    public void checkFlagCollision(int sequenceNum) {
        if (lastFlagReached + 1 == sequenceNum) {
            System.out.println("Ant has collided with flag " + sequenceNum + ".");
            lastFlagReached = sequenceNum;
        } else {
            System.out.println("Please reach the flags in sequential order.");
        }
    }

    public void modifyFoodLevel() {
        if (foodLevel > 0 && healthLevel > 0) {
            foodLevel = Math.max(0, foodLevel - foodConsumptionRate);
            int allowed = allowedSpeedByHealth();
            if (getSpeed() > allowed) setSpeed(allowed);
            if (foodLevel > 0 && healthLevel > 0) move();
        }
    }

    @Override
    public void setFoodConsumption() {
        int delta = 0;
        while (delta == 0) {
            delta = random.nextInt(5) - 2; // -2..+2 excluding 0
        }
        int newRate = foodConsumptionRate + delta;
        if (newRate <= 0) newRate = foodConsumptionRate + 1;
        foodConsumptionRate = newRate;
    }

    private int allowedSpeedByHealth() {
        return (maximumSpeed * Math.max(0, Math.min(10, healthLevel))) / 10;
    }

    public int getMaximumSpeed() { return maximumSpeed; }
    public void setMaximumSpeed(int maximumSpeed) { this.maximumSpeed = maximumSpeed; }

    public int getFoodLevel() { return foodLevel; }
    public void setFoodLevel(int foodLevel) { this.foodLevel = Math.max(0, foodLevel); }

    public int getFoodConsumptionRate() { return foodConsumptionRate; }
    public void setFoodConsumptionRate(int foodConsumptionRate) {
        this.foodConsumptionRate = Math.max(1, foodConsumptionRate);
    }

    public int getHealthLevel() { return healthLevel; }
    public void setHealthLevel(int healthLevel) {
        this.healthLevel = Math.max(0, Math.min(10, healthLevel));
        int allowed = allowedSpeedByHealth();
        if (getSpeed() > allowed) setSpeed(allowed);
    }

    public int getLastFlagReached() { return lastFlagReached; }
    public void setLastFlagReached(int lastFlagReached) { this.lastFlagReached = lastFlagReached; }
}
