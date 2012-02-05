package com.smeestudio.catnipconga;

import java.util.List;
import java.util.Random;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Pixmap;
import com.smeestudio.framework.Screen;
import com.smeestudio.framework.Input.TouchEvent;

public class HelpScreen3 extends Screen {

	CongaCat cat;
	float currentTime = 0;
	float switchTime = 0.2f;
	Random random;	
	float catSizeX = 0;
	float catSizeY = 0;
	float catMove = 0.0f;
	float catChangeDirection = 0.0f;
	
	public HelpScreen3(Game game) {
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
					game.setScreen(new HelpScreen4(game));
					if(Settings.soundEnabled)
						Assets.purr.play(1);
						return;			
				}

			}
			//if touch down move head left or right ;)
			//eventuall replace with accelerometer readings compared to last known time
			if(event.type == TouchEvent.TOUCH_DOWN){
				//if cat head direction lef/right then we can only press up/down
				if((cat.direction == CongaCat.LEFT) || (cat.direction == CongaCat.RIGHT)){
					//detect either press on up down buttons only
					//detect up button first, so great than button.x, less than button .x + width of button, and same for height
					if((inBounds(event, (g.getWidth())-((g.getWidth()/10)*2)-Assets.buttonDown.getWidth(), g.getHeight()-((g.getHeight()/7)+(2*Assets.buttonUp.getHeight()))-Assets.buttonDown.getHeight(), Assets.buttonUp.getWidth(),Assets.buttonUp.getHeight())) ||
							inBounds(event, ((g.getWidth()/10)*2), g.getHeight()-((g.getHeight()/7)+(2*Assets.buttonUp.getHeight()))-Assets.buttonUp.getHeight(), Assets.buttonUp.getWidth(),Assets.buttonUp.getHeight())){
						//detect direction again and set either right or left appropriatly
						if(cat.direction == CongaCat.LEFT){
							cat.turnRight();	//turning the cat antiCLockwise so down!
						}
						if(cat.direction == CongaCat.RIGHT){
							cat.turnLeft();	//turning the cat clockwise so down!
						}
					}
					if((inBounds(event, (g.getWidth())-((g.getWidth()/10)*2)-Assets.buttonDown.getWidth(), (g.getHeight()-(g.getHeight()/7))-Assets.buttonDown.getHeight(), Assets.buttonDown.getWidth(),Assets.buttonDown.getHeight())) ||
							inBounds(event,((g.getWidth()/10)*2), (g.getHeight()-(g.getHeight()/7))-Assets.buttonDown.getHeight(), Assets.buttonDown.getWidth(),Assets.buttonDown.getHeight())){
						//detect direction again and set either right or left appropriatly
						if(cat.direction == CongaCat.LEFT){
							cat.turnLeft();
						}
						if(cat.direction == CongaCat.RIGHT){
							cat.turnRight();	
						}
					}
					
				}
				
				//if cat head direction up/down then we can only press left/right
				if((cat.direction == CongaCat.UP) || (cat.direction == CongaCat.DOWN)){
					if((inBounds(event,(g.getWidth())-((g.getWidth()/10)*2)-(2*Assets.buttonLeft.getWidth()), g.getHeight()-((g.getHeight()/7)+Assets.buttonLeft.getHeight())-Assets.buttonLeft.getHeight() , Assets.buttonLeft.getWidth(),Assets.buttonLeft.getHeight())) ||
							inBounds(event,((g.getWidth()/10)*2)-Assets.buttonLeft.getWidth(), g.getHeight()-((g.getHeight()/7)+Assets.buttonLeft.getHeight())-Assets.buttonLeft.getHeight(), Assets.buttonLeft.getWidth(),Assets.buttonLeft.getHeight())){
						//detect direction again and set either up or down
						if(cat.direction == CongaCat.UP){
							cat.turnLeft();	//turning the cat antiCLockwise so down!
						}
						if(cat.direction == CongaCat.DOWN){
							cat.turnRight();	//turning the cat clockwise so down!
						}
					}
					if((inBounds(event,(g.getWidth())-((g.getWidth()/10)*2), g.getHeight()-((g.getHeight()/7)+Assets.buttonRight.getHeight())-Assets.buttonRight.getHeight(), Assets.buttonRight.getWidth(),Assets.buttonRight.getHeight())) ||
							inBounds(event,((g.getWidth()/10)*2)+Assets.buttonRight.getWidth(), g.getHeight()-((g.getHeight()/7)+Assets.buttonRight.getHeight())-Assets.buttonRight.getHeight(), Assets.buttonRight.getWidth(),Assets.buttonRight.getHeight())){
						//detect direction again and set either up or down
						if(cat.direction == CongaCat.UP){
							cat.turnRight();
						}
						if(cat.direction == CongaCat.DOWN){
							cat.turnLeft();	
						}
					}					
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
		
		//Instead of below take event touch and change that way
		/**if((catChangeDirection += deltaTime) >= 5.0f){
			//update random direction
			cat.direction = random.nextInt(4);
			catChangeDirection = 0.0f;
		}**/	

				
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
		
		
		g.drawPixmap(Assets.help3text, ((g.getWidth()/2)-(Assets.help3text.getWidth()/2)), (g.getHeight()/14));
		
		//Draw buttons depending on which side has been saved in settings
		if(Settings.controls == 2){
			g.drawPixmap(Assets.buttonDown, (g.getWidth())-((g.getWidth()/10)*2)-Assets.buttonDown.getWidth(), (g.getHeight()-(g.getHeight()/7))-Assets.buttonDown.getHeight());
			g.drawPixmap(Assets.buttonRight, (g.getWidth())-((g.getWidth()/10)*2), g.getHeight()-((g.getHeight()/7)+Assets.buttonRight.getHeight())-Assets.buttonRight.getHeight());
			g.drawPixmap(Assets.buttonLeft, (g.getWidth())-((g.getWidth()/10)*2)-(2*Assets.buttonLeft.getWidth()), g.getHeight()-((g.getHeight()/7)+Assets.buttonLeft.getHeight())-Assets.buttonLeft.getHeight());
			g.drawPixmap(Assets.buttonUp, (g.getWidth())-((g.getWidth()/10)*2)-Assets.buttonDown.getWidth(), g.getHeight()-((g.getHeight()/7)+(2*Assets.buttonUp.getHeight()))-Assets.buttonDown.getHeight());
			
		} else {
			//Left hand side buttons
			g.drawPixmap(Assets.buttonDown, ((g.getWidth()/10)*2), (g.getHeight()-(g.getHeight()/7))-Assets.buttonDown.getHeight());
			g.drawPixmap(Assets.buttonRight, ((g.getWidth()/10)*2)+Assets.buttonRight.getWidth(), g.getHeight()-((g.getHeight()/7)+Assets.buttonRight.getHeight())-Assets.buttonRight.getHeight());
			g.drawPixmap(Assets.buttonLeft,((g.getWidth()/10)*2)-Assets.buttonLeft.getWidth(), g.getHeight()-((g.getHeight()/7)+Assets.buttonLeft.getHeight())-Assets.buttonLeft.getHeight());
			g.drawPixmap(Assets.buttonUp, ((g.getWidth()/10)*2), g.getHeight()-((g.getHeight()/7)+(2*Assets.buttonUp.getHeight()))-Assets.buttonUp.getHeight());	
		}
		
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
