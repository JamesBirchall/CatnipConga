package com.smeestudio.catnipconga;

import java.util.List;
import java.util.Random;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Pixmap;
import com.smeestudio.framework.Input.TouchEvent;
import com.smeestudio.framework.Screen;

public class MainMenuScreen extends Screen {

	CongaCat cat;
	Random random;	
	float catSizeX = 0;
	float catSizeY = 0;
	float catMove = 0.0f;
	float catChangeDirection = 0.0f;
	
	public MainMenuScreen(Game game) {
		super(game);
		random = new Random();
		//generate random cat at top left of screen with random colour and direction
		cat = new CongaCat(random.nextInt(9),random.nextInt(6),random.nextInt(5),random.nextInt(4));
		
		catSizeX = game.getGraphics().getWidth()/10;
		catSizeY = game.getGraphics().getHeight()/7;	
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		
		//Get list of touch events
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		//Get any key events
		game.getInput().getKeyEvents();
		
		//Process touch Events/key events
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			//if event is touch up process the location to see if buttons were pressed
			//this means nothing is accidently pressed!
			if(event.type == TouchEvent.TOUCH_UP){
				//check for volume location presses and set on/off accordingly
				if(inBounds(event, 5, (g.getHeight() - (Assets.soundOnButton.getHeight())), Assets.soundOnButton.getWidth(), Assets.soundOnButton.getHeight())){
					Settings.soundEnabled = !Settings.soundEnabled;
					if(Assets.bgMusic.isPlaying()){
						Assets.bgMusic.stop();
					} else {
						Assets.bgMusic.play();
					}
					
					if(Settings.soundEnabled){
						Assets.purr.play(1);
						return;
					}
				}
				
				//Check for Play button press
				if(inBounds(event,((g.getWidth()/2-(Assets.mainMenuPlay.getWidth()/2))), (g.getHeight()/10)+Assets.logo.getHeight(), Assets.mainMenuPlay.getWidth(), Assets.mainMenuPlay.getHeight())){
					game.setScreen(new GameScreen(game));
					if(Settings.soundEnabled){
						Assets.bgMusic.stop();
						Assets.purr.play(1);
						return;
					}	
				}
				
				
				//Check highscores press
				if(inBounds(event,(((g.getWidth()/2)-(Assets.mainMenuHighScores.getWidth()/2))), (g.getHeight()/10)+Assets.logo.getHeight()+Assets.mainMenuPlay.getHeight(), Assets.mainMenuHighScores.getWidth(), Assets.mainMenuHighScores.getHeight())){
					game.setScreen(new HighScoreScreen(game));
					if(Settings.soundEnabled)
						Assets.purr.play(1);
						return;
				}
				
				
				//Check help press
				if(inBounds(event,(((g.getWidth()/2)-(Assets.mainMenuHelp.getWidth()/2))), (g.getHeight()/10)+Assets.logo.getHeight()+Assets.mainMenuPlay.getHeight()+Assets.mainMenuHighScores.getHeight(), Assets.mainMenuHelp.getWidth(), Assets.mainMenuHelp.getHeight())){
					game.setScreen(new HelpScreen(game));
					if(Settings.soundEnabled)
						Assets.purr.play(1);
						return;
				}
				
				//Check credits press
				if(inBounds(event, (g.getWidth()-Assets.credits.getWidth()), ((g.getHeight()/10)*8), Assets.credits.getWidth(),Assets.credits.getHeight())){
					game.setScreen(new CreditsScreen(game));
					if(Settings.soundEnabled)
						Assets.purr.play(1);
						return;
				}
				
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
		
		//background
		g.drawPixmap(Assets.background, 0, 0);
		
		//draw cat by determining type
		int x = cat.x * (int) catSizeX;
		
		int y = cat.y * (int) catSizeY;
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
		g.drawPixmap(currentCatPixmap, x, y);	
		
		///Cat ends here!
		
		//logo
		g.drawPixmap(Assets.logo, ((g.getWidth()/2-(Assets.logo.getWidth()/2))), (g.getHeight()/10));
		//draw logo (smee sudios logo) - Redo & Replace locational
		g.drawPixmap(Assets.logo2, (g.getWidth()-Assets.logo2.getWidth()), ((g.getHeight()/10)*9));
		//Add Credits button here... 2 sizes for assets & location placement above logo2
		g.drawPixmap(Assets.credits, (g.getWidth()-Assets.credits.getWidth()), ((g.getHeight()/10)*8));
		
		//draw main menu items
		g.drawPixmap(Assets.mainMenuPlay, ((g.getWidth()/2-(Assets.mainMenuPlay.getWidth()/2))), (g.getHeight()/10)+Assets.logo.getHeight());
		g.drawPixmap(Assets.mainMenuHighScores, ((g.getWidth()/2-(Assets.mainMenuHighScores.getWidth()/2))), (g.getHeight()/10)+Assets.logo.getHeight()+Assets.mainMenuPlay.getHeight());
		g.drawPixmap(Assets.mainMenuHelp, ((g.getWidth()/2-(Assets.mainMenuHelp.getWidth()/2))), (g.getHeight()/10)+Assets.logo.getHeight()+Assets.mainMenuPlay.getHeight()+Assets.mainMenuHighScores.getHeight());
		
		//Test touch surface area:
		//g.drawRectNoFill(((g.getWidth()/2-(Assets.mainMenuPlay.getWidth()/2))), (g.getHeight()/10)+Assets.logo.getHeight(), Assets.mainMenuPlay.getWidth(), Assets.mainMenuPlay.getHeight(), Color.BLUE);
		//g.drawRectNoFill((((g.getWidth()/2)-(Assets.mainMenuHighScores.getWidth()/2))), (g.getHeight()/10)+Assets.logo.getHeight()+Assets.mainMenuPlay.getHeight(), Assets.mainMenuHighScores.getWidth(), Assets.mainMenuHighScores.getHeight(), Color.BLUE);
		//g.drawRectNoFill((((g.getWidth()/2)-(Assets.mainMenuHelp.getWidth()/2))), (g.getHeight()/10)+Assets.logo.getHeight()+Assets.mainMenuPlay.getHeight()+Assets.mainMenuHighScores.getHeight(), Assets.mainMenuHelp.getWidth(), Assets.mainMenuHelp.getHeight(), Color.BLUE);
		
		if(Settings.soundEnabled){
			g.drawPixmap(Assets.soundOnButton, 5, (g.getHeight() - (Assets.soundOnButton.getHeight())));
		}else{
			g.drawPixmap(Assets.soundOffButton, 5, (g.getHeight() - (Assets.soundOnButton.getHeight())));
		}
			
		
		
	}

	@Override
	public void pause() {
		Settings.save(game.getFileIO());	
	}

	@Override
	public void resume() {
		if(Settings.soundEnabled){
			Assets.bgMusic.play();
			Assets.bgMusic.setLooping(true);
			Assets.bgMusic.restart();
		}
	}

	@Override
	public void dispose() {
				
	}
	
	

}
