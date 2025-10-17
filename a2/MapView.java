package com.mycompany.a2;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.plaf.Border;

/**
 * MapView - Console style view of the world state.
 */
public class MapView extends Container implements Observer {

    private final GameWorld gw;

    public MapView(GameWorld gw) {
        // simple red border to match the spec look
        getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.rgb(255, 0, 0)));
        this.gw = gw;
    }

    @Override
    public void update(Observable o, Object data) {
        // dump current map to console
        gw.displayCurrentMap();
        // refresh UI (even though this view is console-first)
        revalidate();
    }
}
