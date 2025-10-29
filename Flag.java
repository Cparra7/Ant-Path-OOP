package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;

public class Flag extends Fixed {
    
    private int seqNum;

    // creates a flag with a sequence number and position
    public Flag(int sequenceNumber, Point location, GameWorld gw) {
        super(ColorUtil.BLUE, 75, location, gw);
        this.seqNum = sequenceNumber;
    }

    // draws the flag as a blue triangle with its number on it
    @Override
    public void draw(Graphics g, Point pCmpRelPrnt) {
        int size = getSize();
        int x = (int) (getX() + pCmpRelPrnt.getX() - size / 2);
        int y = (int) (getY() + pCmpRelPrnt.getY() - size / 2);

        g.setColor(getColor());

        // triangle points
        int[] xPoints = {x, x + size, x + size / 2};
        int[] yPoints = {y, y, y + size};

        // outline if selected, filled if not
        if (isSelected()) {
            g.drawPolygon(xPoints, yPoints, 3);
        } else {
            g.fillPolygon(xPoints, yPoints, 3);
        }

        // label the flag with its sequence number
        g.setColor(ColorUtil.BLACK);
        g.drawString(Integer.toString(seqNum), x + 25, y + 10);
    }

    // getters/setters
    public int getSequenceNumber() {
        return seqNum;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.seqNum = sequenceNumber;
    }

    // info for console/debug output
    @Override
    public String toString() {
        return "Flag: loc=" + Math.round(getX() * 10.0) / 10.0 + "," +
               Math.round(getY() * 10.0) / 10.0 +
               " color=" + colorToString() +
               " size=" + getSize() +
               " seqNum=" + getSequenceNumber();
    }
}
