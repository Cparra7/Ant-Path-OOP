package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable {
	
	private Media m;
		
		
	public BGSound(String fileName) {
			
		if (Display.getInstance().getCurrent() == null) {
				
			System.out.println("Error: Create sound objects after calling show()!");			
			System.exit(0);
			
		}
		
		//Needs to do a loop while the media is still full
		//Sound I used cuts out and reloops after a bit
		while (m == null) {
			
			try {				
				InputStream in = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
				

				m = MediaManager.createMedia(in, "audio/wav", this);
				
			} 
			
			catch(Exception e) {				
				e.printStackTrace();
				
			}
		
		}
		
	}
	
	//public methods needed - Pause, Play, and Run
	
	public void pause() {		
		m.pause();		
	}
	
	public void play() {		
		m.play();
	}

	@Override
	public void run() {
		m.setTime(0);		
		m.play();
		
	}

}