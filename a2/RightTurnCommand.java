package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * RightTurnCommand - Rotates the Ant to the right when triggered.
 */
public class RightTurnCommand extends Command {

    // Reference to the current GameWorld
    private GameWorld gw;

    // Sets the command name and stores the GameWorld reference
    public RightTurnCommand(GameWorld gw) {
        super("Right");
        this.gw = gw;
    }

    // Runs when the player triggers the right turn command
    @Override
    public void actionPerformed(ActionEvent e) {
        gw.right(); // turn the ant right
    }
}
