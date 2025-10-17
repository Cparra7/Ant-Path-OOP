package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * FoodStationCollisionCommand - This triggers a collision event with a food station in the GameWorld.
 */
public class FoodStationCollisionCommand extends Command {

    private GameWorld gw;

    public FoodStationCollisionCommand(GameWorld gw) {
        super("Collide with Food Station");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gw.collisionFoodStation();
    }
}
