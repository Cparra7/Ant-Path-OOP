package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class FoodStation extends Fixed {
    
    private int capacity;

    // builds a food station at a random location with random size
    public FoodStation(int width, int height, GameWorld gw) {
        super(ColorUtil.GREEN, random.nextInt(50 - 25) + 25, randomLocation(width, height), gw);
        capacity = getSize();
    }

    // draws the food station as a square (filled or outlined if selected)
    @Override
    public void draw(Graphics g, Point pCmpRelPrnt) {
        int size = getSize();
        int x = (int) (getX() + pCmpRelPrnt.getX() - size / 2);
        int y = (int) (getY() + pCmpRelPrnt.getY() - size / 2);

        g.setColor(getColor());

        // draw outline if selected, filled if not
        if (isSelected()) {
            g.drawRect(x, y, size, size);
        } else {
            g.fillRect(x, y, size, size);
        }

        // label the square with its capacity
        g.setColor(ColorUtil.BLACK);
        g.drawString(Integer.toString(capacity), x + 5, y + 5);
    }

    // getter/setter for capacity
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // info string for console/debugging
    @Override
    public String toString() {
        return "FoodStation: loc=" + Math.round(getX() * 10.0) / 10.0 + "," +
               Math.round(getY() * 10.0) / 10.0 +
               " color=" + colorToString() +
               " size=" + getSize() +
               " capacity=" + getCapacity();
    }
}
