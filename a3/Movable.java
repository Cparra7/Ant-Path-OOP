package com.mycompany.a3;

import com.codename1.charts.models.Point;

public abstract class Movable extends GameObject {

    // 0..359 compass heading; 0 = north, 90 = east
    private int heading;
    // units per tick (>= 0)
    private int speed;

    public Movable(int color, int size, Point location, int heading, int speed, GameWorld gw) {
        super(color, size, location, gw);   // <-- pass gw to GameObject
        setHeading(heading);
        setSpeed(speed);
    }

    // advance based on heading/speed and tick interval
    // advance one tick: 0° = up, 90° = right, positive = clockwise
    public void move(int tick) {
        if (speed <= 0) return;

        double rad = Math.toRadians(getHeading());
        float dx = (float)(Math.sin(rad) * (speed / 10f));   // x to the right
        float dy = (float)(-Math.cos(rad) * (speed / 10f));  // y downward, so minus

        setLocation(getX() + dx, getY() + dy);
    }

    // Keep inside bounds and reflect heading, which fixes the inversion
    public void checkBoundary(int worldW, int worldH, int tick) {
        boolean hitV = false, hitH = false;

        float x = getX();
        float y = getY();

        if (x < 0)          { x = 0;        hitV = true; }
        else if (x > worldW){ x = worldW;   hitV = true; }

        if (y < 0)          { y = 0;        hitH = true; }
        else if (y > worldH){ y = worldH;   hitH = true; }

        if (hitV || hitH) {
            setLocation(x, y);
            if (hitV) setHeading((360 - getHeading()) % 360);   // mirror on vertical wall
            if (hitH) setHeading((540 - getHeading()) % 360);   // mirror on horizontal wall (180 - h)
            move(tick);  // small nudge back in using current heading
        }
    }


    // --- accessors ---

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
