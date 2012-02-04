package com.smeestudio.catnipconga;

import java.util.List;
import java.util.Random;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Pixmap;
import com.smeestudio.framework.Screen;
import com.smeestudio.framework.Input.TouchEvent;

public class HelpScreen2 extends Screen {

	CongaCat cat;
	float currentTime = 0;
	float switchTime = 0.2f;
	Random random;	
	float catSizeX = 0;
	float catSizeY = 0;
	float catMove = 0.0f;
	float catChangeDirection = 0.0f;
	
	public HelpScreen2(Game game) {
		super(game);
		random = new Random();
		//generate random cat at top left of screen with random colour and direction
		cat = new CongaCat(random.nextInt(9),random.nextInt(6),random.nextInt(5),random.nextInt(4));
		
		catSizeX = game.getGraphics().getWidth()/10;
		catSizeY = game.getGraphics().getHeight()/7;
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
				if(inBounds(event, g.getWidth()-Assets.buttonRight.getWidth()-10, 10, Assets.buttonRight.getWidth(), Assets.buttonRight.getHeight())){
					game.setScreen(new HelpScreen3(game));
					if(Settings.soundEnabled)
						Assets.purr.play(1);
						return;			
				}

			}
		}
		

		//keep timer 
		if((catMove += deltaTime) >= 0.5){
			//Move cat once space! - by taking x var and 
			if(cat.direction == CongaCat.UP){
				cat.y -= 1;
			}
			if(cat.direction == CongaCat.LEFT){
				cat.x -= 1;
			}
			if(cat.direction == CongaCat.DOWN){
				cat.y += 1;
			}
			if(cat.direction == CongaCat.RIGHT){
				cat.x += 1;
			}			
			//if wraps around world make sure to reset to other side!
			//world size is 10 on x and 7 on y!
			if(cat.x < 0)
				cat.x = 9;
			if(cat.x > 9)
				cat.x = 0;
			if(cat.y < 0)
				cat.y = 6;
			if(cat.y > 6)
				cat.y = 0;
			
			catMove = 0.0f;
		}
		
		if((catChangeDirection += deltaTime) >= 5.0f){
			//update random direction
			cat.direction = random.nextInt(4);
			catChangeDirection = 0.0f;
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
		
		g.drawPixmap(Assets.background, 0, 0);
		
		g.drawPixmap(Assets.help2text, ((g.getWidth()/2)-(Assets.help2text.getWidth()/2)), (g.getHeight()/7));

		//draw cat stuff here
		//draw cat by determining type
		int xcat = cat.x * (int) catSizeX;
		
		int ycat = cat.y * (int) catSizeY;
		//depending on direction draw here!
		Pixmap currentCatPixmap = null;
		if(cat.direction == 0 ){
			//set correct image depending on type identifier
			if(cat.type == 0){
				currentCatPixmap = Assets.catUp;	
			}	
			if(cat.type == 1){
				currentCatPixmap = Assets.bwCatUp;	
			}	
			if(cat.type == 2){
				currentCatPixmap = Assets.greyCatUp;	
			}	
			if(cat.type == 3){
				currentCatPixmap = Assets.blueCatUp;	
			}	
			if(cat.type == 4){
				currentCatPixmap = Assets.brownCatUp;	
			}	
		}					
		if(cat.direction == 2){
			if(cat.type == 0){
				currentCatPixmap = Assets.catDown;	
			}						
			if(cat.type == 1){
				currentCatPixmap = Assets.bwCatDown;	
			}	
			if(cat.type == 2){
				currentCatPixmap = Assets.greyCatDown;	
			}	
			if(cat.type == 3){
				currentCatPixmap = Assets.blueCatDown;	
			}	
			if(cat.type == 4){
				currentCatPixmap = Assets.brownCatDown;	
			}	
		}
		if(cat.direction == 1){
			if(cat.type == 0){
				currentCatPixmap = Assets.catLeft;	
			}						
			if(cat.type == 1){
				currentCatPixmap = Assets.bwCatLeft;	
			}	
			if(cat.type == 2){
				currentCatPixmap = Assets.greyCatLeft;	
			}	
			if(cat.type == 3){
				currentCatPixmap = Assets.blueCatLeft;	
			}	
			if(cat.type == 4){
				currentCatPixmap = Assets.brownCatLeft;	
			}	
		}					
		if(cat.direction == 3){
			if(cat.type == 0){
				currentCatPixmap = Assets.catRight;
			}						
			if(cat.type == 1){
				currentCatPixmap = Assets.bwCatRight;	
			}	
			if(cat.type == 2){
				currentCatPixmap = Assets.greyCatRight;	
			}	
			if(cat.type == 3){
				currentCatPixmap = Assets.blueCatRight;	
			}	
			if(cat.type == 4){
				currentCatPixmap = Assets.brownCatRight;	
			}	
		}
		g.drawPixmap(currentCatPixmap, xcat, ycat);			
		
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
