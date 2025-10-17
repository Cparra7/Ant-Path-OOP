package com.mycompany.a2;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;

/**
 * FlagCollisionCommand - This prompts the user for a flag number and notifies GameWorld of the collision.
 */
public class FlagCollisionCommand extends Command {

    private GameWorld gw;

    public FlagCollisionCommand(GameWorld gw) {
        super("Collide with Flag");
        this.gw = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            TextField input = new TextField();
            Dialog.show("Enter flag number:", input, new Command("OK"));

            int flagNum = Integer.parseInt(input.getText().trim());

            if (flagNum >= 1 && flagNum <= 4) {
                gw.collisionFlag(flagNum);
            } else {
                Dialog.show("Invalid Input", "Please enter a flag number between 1 and 4.", "OK", null);
            }
            //Needed Catch Method or else
        } catch (NumberFormatException ex) {
            Dialog.show("Invalid Input", "Please enter a numeric flag number between 1 and 4.", "OK", null);
        }
    }
}
