package com.mycompany.a2;

import com.codename1.charts.models.Point;

public abstract class Movable extends GameObject {

    // 0..359 compass heading; 0=north, 90=east
    private int heading;
    // units per tick (>= 0)
    private int speed;

    public Movable(int color, int size, Point location, int heading, int speed) {
        super(color, size, location);
        setHeading(heading);
        setSpeed(speed);
    }

    // Advance by one tick based on current heading/speed.
     
    public void move() {
        if (speed <= 0) return;

        double rad = Math.toRadians(90 - heading);
        float dx = (float)(Math.cos(rad) * speed);
        float dy = (float)(Math.sin(rad) * speed);

        setLocation(getX() + dx, getY() + dy);
    }

    /**
     * Simple world-edge handling: if out of bounds, flip heading 180° and nudge back in.
     */
    public void checkBoundary(int worldW, int worldH) {
        boolean out = false;

        float x = getX();
        float y = getY();

        if (x < 0)      { x = 0; out = true; }
        else if (x > worldW) { x = worldW; out = true; }

        if (y < 0)      { y = 0; out = true; }
        else if (y > worldH) { y = worldH; out = true; }

        if (out) {
            setLocation(x, y);
            setHeading(heading + 180);   // bounce back
            move();                      // quick nudge inside
        }
    }

    // --- Accessors ---

    public int getHeading() { return heading; }

    public void setHeading(int heading) {
        int h = heading % 360;
        if (h < 0) h += 360;
        this.heading = h;
    }

    public int getSpeed() { return speed; }

    public void setSpeed(int speed) {
        this.speed = Math.max(0, speed);
    }
}
