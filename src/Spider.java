package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;

public class Spider extends Movable {

    public Spider() {
        // black color, random size between 10–50, random location, random heading 0–359, random speed 5–10
        super(ColorUtil.BLACK,
              random.nextInt(41) + 10,
              GameObject.randomLocation(),
              random.nextInt(360),
              random.nextInt(6) + 5);
    }

    @Override
    public String toString() {
        return "Spider: loc=" + (Math.round(getX() * 10.0) / 10.0) + "," + (Math.round(getY() * 10.0) / 10.0) +
               " color=" + toStringColor() +
               " size=" + getSize() +
               " heading=" + getHeading() +
               " speed=" + getSpeed();
    }

    // check if spider goes out of bounds, flip its heading and move it back inside
    public void checkBoundary() {
        boolean out = (getX() < 0f || getX() > 1000f || getY() < 0f || getY() > 1000f);
        if (out) {
            setHeading(getHeading() + 180);
            move();
            // make sure spider is inside the 0..1000 world after correction
            float x = Math.max(0f, Math.min(1000f, getX()));
            float y = Math.max(0f, Math.min(1000f, getY()));
            setLocation(x, y);
        }
    }

    // spider changes heading a little, then moves, then we check boundary
    public void controlSpider() {
        setHeading(getHeading() + (random.nextInt(11) - 5)); // -5..+5 turn
        move();
        checkBoundary();
    }

    @Override
    public void setColor(int color) {
        // spider color can’t change
    }
}
