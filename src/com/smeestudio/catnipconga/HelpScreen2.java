package com.smeestudio.catnipconga;

import java.util.List;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Screen;
import com.smeestudio.framework.Input.TouchEvent;

public class HelpScreen2 extends Screen {

	CongaCat cat;
	float currentTime = 0;
	float switchTime = 0.2f;
	
	public HelpScreen2(Game game) {
		super(game);
		cat = new CongaCat(0,0,0,CongaCat.RIGHT);
		Graphics g = game.getGraphics();
		cat.y = (g.getHeight()/2)+(Assets.catRight.getHeight());
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
				game.setScreen(new HelpScreen3(game));
				if(Settings.soundEnabled)
					Assets.purr.play(1);
					return;	
			}

		}
		
		Graphics g = game.getGraphics();
		
		if((deltaTime+=currentTime) > 0.5f){
			//move cat left 1, if edge of screen set back to 0!
			if(cat.x >= g.getWidth()){
				cat.x = 0;
			} else{
				cat.x += Assets.catRight.getWidth();
			}						
			currentTime = 0;
		} else{
			currentTime = deltaTime;
		}
				
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.background, 0, 0);
		
		g.drawPixmap(Assets.help2text, ((g.getWidth()/2)-(Assets.help2text.getWidth()/2)), (g.getHeight()/7));
		
		//draw cat moving left and right across the screen in a loop
		g.drawPixmap(Assets.catRight, cat.x, cat.y);
		
		g.drawPixmap(Assets.buttonRight, g.getWidth()-Assets.buttonRight.getWidth()-10, 10);
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
