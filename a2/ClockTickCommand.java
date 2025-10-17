package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

/**
 * ClockTickCommand - Which advances the game clock by one tick.
 */
public class ClockTickCommand extends Command {

    private GameWorld gw;

    public ClockTickCommand(GameWorld gw) {
        super("Tick");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update the game clock
        gw.gameClockTick();
    }
}
