package com.mycompany.a1;

import com.codename1.charts.util.ColorUtil;

public class FoodStation extends Fixed {

    private int capacity;

    public FoodStation() {
        super(ColorUtil.GREEN, random.nextInt(41) + 10, randomLocation());
        capacity = getSize();
    }

    @Override
    public String toString() {
        return "FoodStation: loc=" + (Math.round(getX() * 10.0) / 10.0) + "," + (Math.round(getY() * 10.0) / 10.0) +
               " color=" + toStringColor() +
               " size=" + getSize() +
               " capacity=" + capacity;
    }

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }
}
