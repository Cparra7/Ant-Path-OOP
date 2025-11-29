package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class PauseCommand extends Command {	
	private Game game;
	
	public PauseCommand(Game game) {
		super("Pause");		
		this.game = game;
		
	}
	
	//When the pause button is clicked on screen
	@Override 
	public void actionPerformed(ActionEvent e) {		
		game.gamePausePlay();
		
	}
	
}