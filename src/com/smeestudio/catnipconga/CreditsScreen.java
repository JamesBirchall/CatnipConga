package com.smeestudio.catnipconga;

import java.util.List;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Screen;
import com.smeestudio.framework.Input.TouchEvent;

public class CreditsScreen extends Screen {

	public CreditsScreen(Game game) {
		super(game);
		
	}

	@Override
	public void update(float deltaTime) {
		//Get list of touch events
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		//Get any key events
		game.getInput().getKeyEvents();
				
		//Process touch Events/key events
		int len = touchEvents.size();
		
		//for now any touch event go back to main menu screen
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				game.setScreen(new MainMenuScreen(game));
				if(Settings.soundEnabled)
					Assets.purr.play(1);
					return;
			}
				
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		//draw background
		g.drawPixmap(Assets.creditsScreen, 0, 0);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
