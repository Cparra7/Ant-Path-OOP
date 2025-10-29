package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PositionCommand extends Command {	
	private GameWorld gw;

	public PositionCommand(GameWorld gw) {		
		super("Position");		
		this.gw = gw;
		
	}

	// When the position button is clicked
	@Override
	public void actionPerformed(ActionEvent e) {		
		if (gw.getPaused()) {			
			gw.switchPosition();
			
		}
		
	}
	
}