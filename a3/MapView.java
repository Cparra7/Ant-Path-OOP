package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;

public class MapView extends Container implements Observer {

    private final GameWorld gw;

    // UI frame + link to world
    public MapView(GameWorld gw) {
        this.gw = gw;

        // match spec: red border, white background
        getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.rgb(255, 0, 0)));
        getAllStyles().setBgColor(ColorUtil.WHITE);
        getAllStyles().setBgTransparency(255);
    }

    // world changed -> redraw
    @Override
    public void update(Observable o, Object data) {
        repaint();
    }

    // draw all game objects relative to this container
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Point pCmpRelPrnt = new Point(getX(), getY());
        IIterator it = gw.getIterator();

        while (it.hasNext()) {
            GameObject obj = it.getNext();
            if (obj instanceof IDrawable) {
                ((IDrawable) obj).draw(g, pCmpRelPrnt);
            }
        }
    }

    // click-to-select and click-to-place (only while paused)
    @Override
    public void pointerPressed(int xPointer, int yPointer) {
        if (!gw.getPaused()) return;

        // convert screen coords -> coords relative to this container’s parent
        int relX = xPointer - getParent().getAbsoluteX();
        int relY = yPointer - getParent().getAbsoluteY();

        // convert to coordinates relative to this MapView
        float px = relX - getX();
        float py = relY - getY();

        IIterator it = gw.getIterator();
        while (it.hasNext()) {
            GameObject obj = it.getNext();
            if (!(obj instanceof Fixed)) continue;

            Fixed fx = (Fixed) obj;

            // first click: select if pointer is inside
            if (fx.contains(px, py)) {
                fx.setSelected(true);
                gw.setPosition(false);     // selecting ends move mode
            }
            // second click: if selected, move (when position mode is on), then clear selection
            else if (fx.isSelected()) {
                if (gw.getPosition()) {
                    fx.setLocation(px, py);
                }
                gw.setPosition(false);
                fx.setSelected(false);
            }
        }

        revalidate();
    }
}
