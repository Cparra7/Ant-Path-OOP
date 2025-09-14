package com.mycompany.a1;

import com.codename1.charts.models.Point;


public abstract class Movable extends GameObject {

    // Compass heading in degrees: 0=north, 90=east, etc.
    private int heading;

    // Units per tick (non-negative).
    private int speed;

    
    public Movable(int color, int size, Point location, int heading, int speed) {
        super(color, size, location);
        setHeading(heading);
        setSpeed(speed);
    }

    
    public void move() {
        if (speed <= 0) return; // no movement if not moving

        double radians = Math.toRadians(90 - getHeading());
        float deltaX = (float)(Math.cos(radians) * getSpeed());
        float deltaY = (float)(Math.sin(radians) * getSpeed());

        setLocation(getX() + deltaX, getY() + deltaY);
        // World-boundary checks (bounce/clamp) are handled by concrete types if needed.
    }

    // --- Accessors/Mutators ---

    public int getHeading() {
        return heading;
    }

  
    public void setHeading(int heading) {
        int h = heading % 360;
        if (h < 0) h += 360;
        this.heading = h;
    }

    public int getSpeed() {
        return speed;
    }

    
    public void setSpeed(int speed) {
        this.speed = Math.max(0, speed);
    }
}
