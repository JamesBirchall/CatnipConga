package com.smeestudio.catnipconga;

import java.util.List;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Screen;
import com.smeestudio.framework.Input.TouchEvent;

public class ChooseControlsScreen extends Screen {
	
	public ChooseControlsScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		//Get list of touch events
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		//Get any key events
		game.getInput().getKeyEvents();
				
		Graphics g = game.getGraphics();
		//Process touch Events/key events
		int len = touchEvents.size();
		
		//for now any touch event go back to main menu screen
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				if(event.x > (g.getWidth()/2)){
					//set to use right hand controls
					Settings.controls = 2;
					game.setScreen(new MainMenuScreen(game));
				} else {
					Settings.controls = 1;
					game.setScreen(new MainMenuScreen(game));
				}
			}

		}
		
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		//Set cat location
		
		g.drawPixmap(Assets.background, 0, 0);
		
		
		//Draw buttons depending on which side has been saved in settings
		//Once setting sin place put and if else here depending on the state of settings, simples
		//Right hand side set of buttons
		g.drawPixmap(Assets.buttonDown, (g.getWidth())-((g.getWidth()/10)*2)-Assets.buttonDown.getWidth(), (g.getHeight()-(g.getHeight()/7))-Assets.buttonDown.getHeight());
		g.drawPixmap(Assets.buttonRight, (g.getWidth())-((g.getWidth()/10)*2), g.getHeight()-((g.getHeight()/7)+Assets.buttonRight.getHeight())-Assets.buttonRight.getHeight());
		g.drawPixmap(Assets.buttonLeft, (g.getWidth())-((g.getWidth()/10)*2)-(2*Assets.buttonLeft.getWidth()), g.getHeight()-((g.getHeight()/7)+Assets.buttonLeft.getHeight())-Assets.buttonLeft.getHeight());
		g.drawPixmap(Assets.buttonUp, (g.getWidth())-((g.getWidth()/10)*2)-Assets.buttonDown.getWidth(), g.getHeight()-((g.getHeight()/7)+(2*Assets.buttonUp.getHeight()))-Assets.buttonDown.getHeight());
		
		//Left hand side buttons
		g.drawPixmap(Assets.buttonDown, ((g.getWidth()/10)*2), (g.getHeight()-(g.getHeight()/7))-Assets.buttonDown.getHeight());
		g.drawPixmap(Assets.buttonRight, ((g.getWidth()/10)*2)+Assets.buttonRight.getWidth(), g.getHeight()-((g.getHeight()/7)+Assets.buttonRight.getHeight())-Assets.buttonRight.getHeight());
		g.drawPixmap(Assets.buttonLeft,((g.getWidth()/10)*2)-Assets.buttonLeft.getWidth(), g.getHeight()-((g.getHeight()/7)+Assets.buttonLeft.getHeight())-Assets.buttonLeft.getHeight());
		g.drawPixmap(Assets.buttonUp, ((g.getWidth()/10)*2), g.getHeight()-((g.getHeight()/7)+(2*Assets.buttonUp.getHeight()))-Assets.buttonUp.getHeight());
		
		g.drawPixmap(Assets.controlCopy, ((g.getWidth()/2)-(Assets.controlCopy.getWidth()/2)), (g.getHeight()/7));
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
