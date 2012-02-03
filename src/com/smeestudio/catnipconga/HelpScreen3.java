package com.smeestudio.catnipconga;

import java.util.List;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Screen;
import com.smeestudio.framework.Input.TouchEvent;

public class HelpScreen3 extends Screen {

	CongaCat cat;
	float currentTime = 0;
	float switchTime = 0.2f;
	boolean rotationSwitch;
	
	public HelpScreen3(Game game) {
		super(game);
		cat = new CongaCat(0,0,1,CongaCat.DOWN);
		rotationSwitch = false;
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
				game.setScreen(new HelpScreen4(game));
				if(Settings.soundEnabled)
					Assets.purr.play(1);
					return;		
			}

		}
		
		//Every half second rotate cat, after set amount of time change direction
				
		if((deltaTime+=currentTime) > 0.5f){
			// take action here!
			//Turn cat left
			cat.direction += 1;
			if(cat.direction > CongaCat.RIGHT){
				cat.direction = CongaCat.UP;
			}				
			
			currentTime = 0;
		} else{
			currentTime = deltaTime;
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		//Set cat location
		cat.x = (g.getWidth()/2) - (Assets.catDown.getWidth()/2);
		cat.y = g.getHeight()-(Assets.catDown.getHeight()*2);
		
		g.drawPixmap(Assets.background, 0, 0);
		
			
		g.drawPixmap(Assets.help3text, ((g.getWidth()/2)-(Assets.help3text.getWidth()/2)), (g.getHeight()/7));
		
		//draw gestures
		g.drawPixmapWithFade(Assets.gestureLeftTouch, 10, g.getHeight()-10-Assets.gestureLeftTouch.getHeight(), 0);
		g.drawPixmapWithFade(Assets.gestureRightTouch,  g.getWidth()-10-Assets.gestureRightTouch.getWidth(),  g.getHeight()-10-Assets.gestureRightTouch.getHeight(), 0);
		
				
		//Draw cats depending on cat direction
		if(cat.direction == CongaCat.DOWN){
			g.drawPixmap(Assets.catDown, cat.x, cat.y);			
		}
		if(cat.direction == CongaCat.UP){
			g.drawPixmap(Assets.catUp, cat.x, cat.y);			
		}
		if(cat.direction == CongaCat.LEFT){
			g.drawPixmap(Assets.catLeft, cat.x, cat.y);			
		}
		if(cat.direction == CongaCat.RIGHT){
			g.drawPixmap(Assets.catRight, cat.x, cat.y);			
		}
		
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
