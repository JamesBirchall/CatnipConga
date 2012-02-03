package com.smeestudio.catnipconga;

import java.util.List;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Screen;
import com.smeestudio.framework.Input.TouchEvent;

public class HelpScreen5 extends Screen {
	
	public HelpScreen5(Game game) {
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
		//Set cat location
		
		g.drawPixmap(Assets.background, 0, 0);
		
			
		g.drawPixmap(Assets.help5text, ((g.getWidth()/2)-(Assets.help4text.getWidth()/2)), (g.getHeight()/7));
		
		//draw cat line
		//5 cats
		g.drawPixmap(Assets.catLeft, (g.getWidth()/2), ((g.getHeight()/2)+Assets.catLeft.getHeight()));
		//to the right
		g.drawPixmap(Assets.bwCatLeft, (g.getWidth()/2)+Assets.catLeft.getWidth(), ((g.getHeight()/2)+Assets.catLeft.getHeight()));
		//below
		g.drawPixmap(Assets.brownCatUp, (g.getWidth()/2)+Assets.catLeft.getWidth(), ((g.getHeight()/2)+Assets.catLeft.getHeight())+Assets.bwCatLeft.getHeight());
		//left
		g.drawPixmap(Assets.bwCatRight, (g.getWidth()/2), ((g.getHeight()/2)+Assets.catLeft.getHeight())+Assets.bwCatLeft.getHeight());
		//left
		g.drawPixmap(Assets.blueCatDown, (g.getWidth()/2)-Assets.catDown.getWidth(), ((g.getHeight()/2)+Assets.catLeft.getHeight())+Assets.bwCatLeft.getHeight());
		//up
		g.drawPixmap(Assets.greyCatDown, (g.getWidth()/2)-Assets.catDown.getWidth(), ((g.getHeight()/2)+Assets.catLeft.getHeight()));
		//up
		g.drawPixmap(Assets.catDown, (g.getWidth()/2)-Assets.catDown.getWidth(), ((g.getHeight()/2)));
		//up
		
		
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
