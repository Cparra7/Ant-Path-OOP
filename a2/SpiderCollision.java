package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * SpiderCollision - Triggers a collision event between the Ant and a Spider.
 */
public class SpiderCollision extends Command {

    private GameWorld gw;

    public SpiderCollision(GameWorld gw) {
        super("Collide with Spider");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Notify the GameWorld of a spider collision
        gw.collisionSpider();
    }
}
