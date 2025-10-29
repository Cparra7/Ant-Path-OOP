package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Spider extends Movable {

    // create a black spider with random size, location, heading, and speed
    public Spider(int width, int height, GameWorld gw) {
    	super(ColorUtil.BLACK, random.nextInt(30) + 25, randomLocation(width, height),
    		      random.nextInt(360), random.nextInt(51) + 100, gw);

    }

    // small random turn each frame (-5..+5), then move and bounce at edges
    public void moveSpider(int worldW, int worldH, int tick) {
        setHeading(getHeading() + (random.nextInt(11) - 5));
        move(tick);
        checkBoundary(worldW, worldH, tick);
    }

    // spiders stay black – ignore color changes
    @Override
    public void setColor(int color) {
        // do nothing
    }

    // draw as a simple triangle outline
    @Override
    public void draw(Graphics g, Point pCmpRelPrnt) {
        int size = getSize();
        int x = (int) (getX() + pCmpRelPrnt.getX() - size / 2);
        int y = (int) (getY() + pCmpRelPrnt.getY() - size / 2);

        int[] xPoints = {x, x + size, x + size / 2};
        int[] yPoints = {y, y, y + size};

        g.setColor(getColor());
        g.drawPolygon(xPoints, yPoints, 3);
    }

    // info line for debugging
    @Override
    public String toString() {
        return "Spider: loc=" + (Math.round(getX() * 10.0) / 10.0) + "," +
               (Math.round(getY() * 10.0) / 10.0) +
               " color=" + colorToString() +
               " size=" + getSize() +
               " heading=" + getHeading() +
               " speed=" + getSpeed();
    }
}
