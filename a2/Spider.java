package com.mycompany.a2;

import com.codename1.charts.util.ColorUtil;

public class Spider extends Movable {

    public Spider() {
        // black, random size 10–50, random location, random heading 0–359, random speed 5–10
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

    // small random turn, move, then bounce off world edges
    public void controlSpider(int worldW, int worldH) {
        setHeading(getHeading() + (random.nextInt(11) - 5)); // -5..+5
        move();
        checkBoundary(worldW, worldH);
    }

    @Override
    public void setColor(int color) {
        // spiders keep their color
    }
}
