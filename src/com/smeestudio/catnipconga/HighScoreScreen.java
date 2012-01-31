package com.smeestudio.catnipconga;

import java.util.List;
import java.util.Random;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Pixmap;
import com.smeestudio.framework.Input.TouchEvent;
import com.smeestudio.framework.Screen;

public class HighScoreScreen extends Screen {
	String lines[] = new String[10];
	CongaCat cat;
	Random random;	
	float catSizeX = 0;
	float catSizeY = 0;
	float catMove = 0.0f;
	float catChangeDirection = 0.0f;
		
	public HighScoreScreen(Game game) {
		super(game);
		
		//store highscores so far
		for(int i = 0; i < 10; i++){
			lines[i] = "" + (i+1) + ". "+Settings.highscores[i];
		}
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
				
		//Process touch Events/key events
		int len = touchEvents.size();
		
		//for now any touch event go back to main menu screen
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP){
				game.setScreen(new MainMenuScreen(game));
				if(Settings.soundEnabled)
					Assets.meow.play(1);
					return;
			}
				
		}
		//Update cat mechanic
		
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

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		//draw background
		g.drawPixmap(Assets.background, 0, 0);
		
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
		
		///Cat ends here!
		
		//draw title of window
		g.drawPixmap(Assets.mainMenuHighScores,  ((g.getWidth()/2-(Assets.mainMenuHighScores.getWidth()/2))), 20);
				
		//draw highscores, first row on left!
		if(Assets.numberMap.getWidth() <= 210){
			int y = ((g.getHeight()/2)/8*4);
	        for (int i = 0; i < 5; i++) {
	            drawText(g, lines[i], (g.getWidth()/10)*2, y);
	            y += ((g.getHeight()/2)/8*2);
	        }
	        y = ((g.getHeight()/2)/8*4);
	        for (int i = 5; i < 9; i++) {
	            drawText(g, lines[i], (g.getWidth()/2)+(g.getWidth()/10), y);
	            y += ((g.getHeight()/2)/8*2);
	        }
	        drawText(g, lines[9], (g.getWidth()/2)+(g.getWidth()/10)-20, y);
		} else{
			int y = ((g.getHeight()/2)/8*4);
	        for (int i = 0; i < 5; i++) {
	            drawText(g, lines[i], (g.getWidth()/10)*2, y);
	            y += ((g.getHeight()/2)/8*2);
	        }
	        y = ((g.getHeight()/2)/8*4);
	        for (int i = 5; i < 9; i++) {
	            drawText(g, lines[i], (g.getWidth()/2)+(g.getWidth()/10), y);
	            y += ((g.getHeight()/2)/8*2);
	        }
	        drawText(g, lines[9], (g.getWidth()/2)+(g.getWidth()/10)-40, y);			
		}

		
		//y = 95;
		//for(int i = 5; i < 9; i++){
		//	drawText(g, lines[i], 225, y);
		//	y += 60; //advance 50 px down to next string doesn't overlap
		//}
		
		//then draw 10 but move x back as it's slightly larger
		//for(int i = 9; i < 10; i++){
		//	drawText(g, lines[i], 300, y);
		//	y += 60; //advance 50 px down to next string doesn't overlap
		//}
	}

	//Method used to render numbers from numberMap from string passed through & location to draw
	private void drawText(Graphics g, String line, int x, int y) {
		int len = line.length();
		if(Assets.numberMap.getWidth() <= 210){
			for(int i = 0; i < len; i++){
				char character = line.charAt(i);
				
				if(character == ' '){
					x += 20;
					continue;
				}
				
				int srcX = 0;
				int srcWidth = 0;
				
				if(character == '.'){
					srcX = 200;
					srcWidth = 10;
				} else{
					srcX = (character - '0')*20;
					srcWidth = 20;
				}
				
				g.drawPixmap(Assets.numberMap, x, y, srcX, 0, srcWidth, 32);
				x += srcWidth;
			}		
		} else{
			for(int i = 0; i < len; i++){
				char character = line.charAt(i);
				
				if(character == ' '){
					x += 40;
					continue;
				}
				
				int srcX = 0;
				int srcWidth = 0;
				
				if(character == '.'){
					srcX = 400;
					srcWidth = 20;
				} else{
					srcX = (character - '0')*40;
					srcWidth = 40;
				}
				
				g.drawPixmap(Assets.numberMap, x, y, srcX, 0, srcWidth, 64);
				x += srcWidth;
			}
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
