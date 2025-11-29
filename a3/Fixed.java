package com.mycompany.a3;

import com.codename1.charts.models.Point;
public abstract class Fixed extends GameObject implements ISelectable {
    
    // true if this object is currently selected (for repositioning)
    private boolean selected;

    // constructor passes setup values to GameObject
    public Fixed(int color, int size, Point location, GameWorld gw) {
        super(color, size, location, gw);
    }

    // checks if a tap/click happened within this object's square bounds
    @Override
    public boolean contains(float px, float py) {
        int size = getSize();

        // find this object’s top-left corner in relation to the MapView origin
        float x = getX() - (size / 2f);
        float y = getY() - (size / 2f);

        // return true if pointer coordinates are within the box
        return (px >= x && px <= x + size && py >= y && py <= y + size);
    }

    // whether or not this object is currently selected
    @Override
    public boolean isSelected() {
        return selected;
    }

    // update selection status
    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
