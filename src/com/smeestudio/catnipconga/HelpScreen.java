package com.smeestudio.catnipconga;

import java.util.List;
import java.util.Random;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Input.TouchEvent;
import com.smeestudio.framework.Screen;

public class HelpScreen extends Screen {
	
	boolean switchPlants;
	float currentTime = 0;
	float switchTime = 0.2f;
	int[] x = new int[10];
	int[] y = new int[10];

	public HelpScreen(Game game) {
		super(game);
		switchPlants = false;
		Random random = new Random();
		Graphics g = game.getGraphics();
		//generate random locations for plants on the screen :) - going to have 10 plants so 10 variables x and y, with limit being scrwidth & height
		for(int i = 0; i < 10; i++){
			x[i] = random.nextInt(g.getWidth()-50);
			y[i] = (random.nextInt((g.getHeight()/2)-50))+(g.getHeight()/2);
		}
	}

	@Override
	public void update(float deltaTime) {
		//Get list of touch events
				List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
				//Get any key events
				game.getInput().getKeyEvents();
						
				//Process touch Events/key events
				int len = touchEvents.size();
				
				Graphics g = game.getGraphics();
				
				//for now any touch event go back to main menu screen
				for(int i = 0; i < len; i++){
					TouchEvent event = touchEvents.get(i);
					if(event.type == TouchEvent.TOUCH_UP){
						if(inBounds(event, g.getWidth()-Assets.buttonRight.getWidth()-10, 10, Assets.buttonRight.getWidth(), Assets.buttonRight.getHeight())){
							game.setScreen(new HelpScreen2(game));
							if(Settings.soundEnabled)
								Assets.purr.play(1);
								return;			
						}
					}

				}
	}

	//method for checking whether object with starting x, y and width/height has been touched!
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if(event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		//draw background
		g.drawPixmap(Assets.background, 0, 0);
		
		if((deltaTime+=currentTime) > 0.5f){
			switchPlants = !switchPlants;
			currentTime = 0;
		} else{
			currentTime = deltaTime;
		}
		
		if(switchPlants){
			//draw 1 set
			g.drawPixmap(Assets.plant1, x[0], y[0]);
			g.drawPixmap(Assets.plant1, x[1], y[1]);
			g.drawPixmap(Assets.plant4, x[2], y[2]);
			g.drawPixmap(Assets.plant1, x[3], y[3]);
			g.drawPixmap(Assets.plant3, x[4], y[4]);
			g.drawPixmap(Assets.plant3, x[5], y[5]);
			g.drawPixmap(Assets.plant4, x[6], y[6]);
			g.drawPixmap(Assets.plant4, x[7], y[7]);
			g.drawPixmap(Assets.plant1, x[8], y[8]);
			g.drawPixmap(Assets.plant3, x[9], y[9]);
			
		} else{
			//draw opposite set
			g.drawPixmap(Assets.plant2, x[0], y[0]);
			g.drawPixmap(Assets.plant2, x[1], y[1]);
			g.drawPixmap(Assets.plant5, x[2], y[2]);
			g.drawPixmap(Assets.plant2, x[3], y[3]);
			g.drawPixmap(Assets.plant6, x[4], y[4]);
			g.drawPixmap(Assets.plant6, x[5], y[5]);
			g.drawPixmap(Assets.plant5, x[6], y[6]);
			g.drawPixmap(Assets.plant5, x[7], y[7]);
			g.drawPixmap(Assets.plant2, x[8], y[8]);
			g.drawPixmap(Assets.plant6, x[9], y[9]);
		}
		
		g.drawPixmap(Assets.help1text, ((g.getWidth()/2)-(Assets.help1text.getWidth()/2)), (g.getHeight()/14));
		
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
