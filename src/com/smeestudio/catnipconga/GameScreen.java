package com.smeestudio.catnipconga;

import java.util.List;

import android.util.Log;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Input.TouchEvent;
import com.smeestudio.framework.Pixmap;
import com.smeestudio.framework.Screen;

public class GameScreen extends Screen {
	
	enum GameState{
		Ready,
		Running,
		Paused,
		GameOver
	}
	
	GameState state = GameState.Ready;
	World world;
	int oldScore = 0;
	String score = "0";
	boolean started = false;
	int width = 0;  //set on startup to determine touch left and right size
	boolean gameOverUp = false;
	
	//scaling stuff setup when loading so the game doesn't keep running requests
	//with constructor set these up beforehand using assets scale size, e.g take 1 asset width for both * multiply by scaling factor
	float plantSizeX = 0;
	float plantSizeY = 0;
	float catSizeX = 0;
	float catSizeY = 0;
	
	
	long startTime = System.nanoTime();
    int frames = 0;

	public GameScreen(Game game) {
		super(game);
		world = new World();
		width = (game.getGraphics().getWidth()/2);
		
		//calculate scaled movement sizes based on which axis are off 1.0f
		plantSizeX = game.getGraphics().getWidth()/10;
		catSizeX = game.getGraphics().getWidth()/10;
		plantSizeY = game.getGraphics().getHeight()/7;
		catSizeY = game.getGraphics().getHeight()/7;		
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		if(state == GameState.Ready)
			updateReady(touchEvents);
		if(state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if(state == GameState.Paused)
			updatePaused(touchEvents);
		if(state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		
		if(!gameOverUp){
			//Nothing has happened so check for a lift of the finger first
			int len = touchEvents.size();
			for(int i = 0; i < len; i++){
				TouchEvent event = touchEvents.get(i);
				if(event.type == TouchEvent.TOUCH_UP){
					gameOverUp = true;
					touchEvents.clear();
					return;
				}
			}		
		}
		
		
		//Click anywhere and go back to the main menu screen
		if(gameOverUp){
			int len = touchEvents.size();
			for(int i = 0; i < len; i++){
				TouchEvent event = touchEvents.get(i);
				if(event.type == TouchEvent.TOUCH_UP){
					if(Settings.soundEnabled){
						Assets.meow.play(1);						
						
					}
					gameOverUp = false;  //setup again for next game
					//reset speed
					World.tick = World.TICK_INITIAL;
					game.setScreen(new MainMenuScreen(game));
					
					return;
				}
			}			
		}

	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		Graphics g = game.getGraphics();
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP && started){
				if(event.y < ((g.getHeight()/2)+25)){
					if(Settings.soundEnabled){
						Assets.meow.play(1);
					}
					state = GameState.Running;
					return;
				}
				if(event.y > ((g.getHeight()/2)+25)){
					if(Settings.soundEnabled){
						Assets.meow.play(1);
						Assets.bgMusic2.stop();
					}
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		if(!started){
        	touchEvents.clear();
        	started = true;
        }
		
		Graphics g = game.getGraphics();
		
		//Get cat current direction
		CongaCat headCat= world.cat.herd.get(0);
		
		
		int len = touchEvents.size();
		for(int i = 0; i < len; i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP && started){
				if(event.y < 64){  //anywhere across the top of the screen so far!
					if(Settings.soundEnabled){
						Assets.meow.play(1);
					}
					state = GameState.Paused;
					touchEvents.clear();
					return;
				}
			}
			//if touch down move head left or right ;)
			//eventuall replace with accelerometer readings compared to last known time
			if(event.type == TouchEvent.TOUCH_DOWN && started){
				//if cat head direction lef/right then we can only press up/down
				if((headCat.direction == CongaCat.LEFT) || (headCat.direction == CongaCat.RIGHT)){
					//detect either press on up down buttons only
					//detect up button first, so great than button.x, less than button .x + width of button, and same for height
					if((inBounds(event, (g.getWidth())-((g.getWidth()/10)*2)-Assets.buttonDown.getWidth(), g.getHeight()-((g.getHeight()/7)+(2*Assets.buttonUp.getHeight()))-Assets.buttonDown.getHeight(), Assets.buttonUp.getWidth(),Assets.buttonUp.getHeight())) ||
							inBounds(event, ((g.getWidth()/10)*2), g.getHeight()-((g.getHeight()/7)+(2*Assets.buttonUp.getHeight()))-Assets.buttonUp.getHeight(), Assets.buttonUp.getWidth(),Assets.buttonUp.getHeight())){
						//detect direction again and set either right or left appropriatly
						if(headCat.direction == CongaCat.LEFT){
							world.cat.turnRight();	//turning the cat antiCLockwise so down!
						}
						if(headCat.direction == CongaCat.RIGHT){
							world.cat.turnLeft();	//turning the cat clockwise so down!
						}
					}
					if((inBounds(event, (g.getWidth())-((g.getWidth()/10)*2)-Assets.buttonDown.getWidth(), (g.getHeight()-(g.getHeight()/7))-Assets.buttonDown.getHeight(), Assets.buttonDown.getWidth(),Assets.buttonDown.getHeight())) ||
							inBounds(event,((g.getWidth()/10)*2), (g.getHeight()-(g.getHeight()/7))-Assets.buttonDown.getHeight(), Assets.buttonDown.getWidth(),Assets.buttonDown.getHeight())){
						//detect direction again and set either right or left appropriatly
						if(headCat.direction == CongaCat.LEFT){
							world.cat.turnLeft();
						}
						if(headCat.direction == CongaCat.RIGHT){
							world.cat.turnRight();	
						}
					}
					
				}
				
				//if cat head direction up/down then we can only press left/right
				if((headCat.direction == CongaCat.UP) || (headCat.direction == CongaCat.DOWN)){
					if((inBounds(event,(g.getWidth())-((g.getWidth()/10)*2)-(2*Assets.buttonLeft.getWidth()), g.getHeight()-((g.getHeight()/7)+Assets.buttonLeft.getHeight())-Assets.buttonLeft.getHeight() , Assets.buttonLeft.getWidth(),Assets.buttonLeft.getHeight())) ||
							inBounds(event,((g.getWidth()/10)*2)-Assets.buttonLeft.getWidth(), g.getHeight()-((g.getHeight()/7)+Assets.buttonLeft.getHeight())-Assets.buttonLeft.getHeight(), Assets.buttonLeft.getWidth(),Assets.buttonLeft.getHeight())){
						//detect direction again and set either up or down
						if(headCat.direction == CongaCat.UP){
							world.cat.turnLeft();	//turning the cat antiCLockwise so down!
						}
						if(headCat.direction == CongaCat.DOWN){
							world.cat.turnRight();	//turning the cat clockwise so down!
						}
					}
					if((inBounds(event,(g.getWidth())-((g.getWidth()/10)*2), g.getHeight()-((g.getHeight()/7)+Assets.buttonRight.getHeight())-Assets.buttonRight.getHeight(), Assets.buttonRight.getWidth(),Assets.buttonRight.getHeight())) ||
							inBounds(event,((g.getWidth()/10)*2)+Assets.buttonRight.getWidth(), g.getHeight()-((g.getHeight()/7)+Assets.buttonRight.getHeight())-Assets.buttonRight.getHeight(), Assets.buttonRight.getWidth(),Assets.buttonRight.getHeight())){
						//detect direction again and set either up or down
						if(headCat.direction == CongaCat.UP){
							world.cat.turnRight();
						}
						if(headCat.direction == CongaCat.DOWN){
							world.cat.turnLeft();	
						}
					}					
				}				
			}
		}
		
		world.update(deltaTime); //update game world
		if(world.gameOver){
			if(Settings.soundEnabled){
				Assets.screech.play(1);
			}		
			touchEvents.clear();
			state = GameState.GameOver;
			
		}
		if(oldScore != world.score){
			oldScore = world.score;
			score = "" + oldScore;
			if(Settings.soundEnabled){
				Assets.purr.play(1);
				return;
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
	
	private void updateReady(List<TouchEvent> touchEvents) {
		if(touchEvents.size() > 0){
			state = GameState.Running;
			touchEvents.clear();
			
			
			if(Settings.soundEnabled){
				Assets.bgMusic2.play();
				Assets.bgMusic2.setLooping(true);
				Assets.bgMusic2.restart();
			}
			
			
		}

	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		
		//background draw
		if(!world.catCrazed){
			g.drawPixmap(Assets.background, 0, 0);	
		} else {
			g.drawPixmap(Assets.iBackground, 0, 0);
		}
		
		
		//force screen to only render objects when not game over where we will get a better overlay!
		if(state != GameState.GameOver)
			drawWorld(world);
		
		if(state == GameState.Ready)
			drawReadyUI();
		if(state == GameState.Running)
			drawRunningUI();
		if(state == GameState.Paused)
			drawPausedUI();
		if(state == GameState.GameOver)
			drawGameOverUI();
		
		//draw score pixmap
		g.drawPixmap(Assets.score, 5, g.getHeight()-(Assets.score.getHeight()+10));
		//draw Texts for score - need to size depending on size of number map!
		drawText(g, score, Assets.score.getWidth()+5, g.getHeight()-Assets.numberMap.getHeight());
		logFrame();
	}

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

	private void drawWorld(World world) {
		Graphics g = game.getGraphics();
		Cat cat = world.cat;
		CongaCat head = cat.herd.get(0);
		Plant plant = world.plant;
		Plant specialPlant = world.specialPlant;
		
		Pixmap plantPixmap = null;
		//determine which type of plant were drawing at this moment ;)
		if(plant.type == Plant.TYPE_1)
			plantPixmap = Assets.plant1;
		if(plant.type == Plant.TYPE_2)
			plantPixmap = Assets.plant2;
		if(plant.type == Plant.TYPE_3)
			plantPixmap = Assets.plant3;
		if(plant.type == Plant.TYPE_4)
			plantPixmap = Assets.plant4;
		if(plant.type == Plant.TYPE_5)
			plantPixmap = Assets.plant5;
		if(plant.type == Plant.TYPE_6)
			plantPixmap = Assets.plant6;
		int x = plant.x * (int) plantSizeX;
		int y = plant.y * (int) plantSizeY;
		g.drawPixmap(plantPixmap, x, y);
		
		if(specialPlant != null){
			Pixmap specialPlantPixmap = Assets.iplant1;
			x = specialPlant.x * (int) plantSizeX;
			y = specialPlant.y * (int) plantSizeY;
			g.drawPixmap(specialPlantPixmap, x, y);
		}
	
		if(!world.catCrazed){
			//Determine which cat graph to show based on 
			int len = cat.herd.size();
			for(int i = 1; i < len; i++){
					CongaCat part = cat.herd.get(i);
					x = part.x * (int) catSizeX;
					y = part.y * (int) catSizeY;
					//depending on direction draw here!
					Pixmap currentCatPixmap = null;
					if(part.direction == 0 ){
						//set correct image depending on type identifier
						if(part.type == 0){
							currentCatPixmap = Assets.catUp;	
						}	
						if(part.type == 1){
							currentCatPixmap = Assets.bwCatUp;	
						}	
						if(part.type == 2){
							currentCatPixmap = Assets.greyCatUp;	
						}	
						if(part.type == 3){
							currentCatPixmap = Assets.blueCatUp;	
						}	
						if(part.type == 4){
							currentCatPixmap = Assets.brownCatUp;	
						}	
					}					
					if(part.direction == 2){
						if(part.type == 0){
							currentCatPixmap = Assets.catDown;	
						}						
						if(part.type == 1){
							currentCatPixmap = Assets.bwCatDown;	
						}	
						if(part.type == 2){
							currentCatPixmap = Assets.greyCatDown;	
						}	
						if(part.type == 3){
							currentCatPixmap = Assets.blueCatDown;	
						}	
						if(part.type == 4){
							currentCatPixmap = Assets.brownCatDown;	
						}	
					}
					if(part.direction == 1){
						if(part.type == 0){
							currentCatPixmap = Assets.catLeft;	
						}						
						if(part.type == 1){
							currentCatPixmap = Assets.bwCatLeft;	
						}	
						if(part.type == 2){
							currentCatPixmap = Assets.greyCatLeft;	
						}	
						if(part.type == 3){
							currentCatPixmap = Assets.blueCatLeft;	
						}	
						if(part.type == 4){
							currentCatPixmap = Assets.brownCatLeft;	
						}	
					}					
					if(part.direction == 3){
						if(part.type == 0){
							currentCatPixmap = Assets.catRight;
						}						
						if(part.type == 1){
							currentCatPixmap = Assets.bwCatRight;	
						}	
						if(part.type == 2){
							currentCatPixmap = Assets.greyCatRight;	
						}	
						if(part.type == 3){
							currentCatPixmap = Assets.blueCatRight;	
						}	
						if(part.type == 4){
							currentCatPixmap = Assets.brownCatRight;	
						}	
					}
					g.drawPixmap(currentCatPixmap, x, y);	

					
			}
			
			Pixmap headPixmap = null;
			if(head.direction == Cat.UP){
				if(head.type == 0){
					headPixmap = Assets.catUp;	
				}				
				if(head.type == 1){
					headPixmap = Assets.bwCatUp;	
				}	
				if(head.type == 2){
					headPixmap = Assets.greyCatUp;	
				}	
				if(head.type == 3){
					headPixmap = Assets.blueCatUp;	
				}	
				if(head.type == 4){
					headPixmap = Assets.brownCatUp;	
				}
			}
			if(head.direction == Cat.DOWN){
				if(head.type == 0){
					headPixmap = Assets.catDown;	
				}			
				if(head.type == 1){
					headPixmap = Assets.bwCatDown;	
				}	
				if(head.type == 2){
					headPixmap = Assets.greyCatDown;	
				}	
				if(head.type == 3){
					headPixmap = Assets.blueCatDown;	
				}	
				if(head.type == 4){
					headPixmap = Assets.brownCatDown;	
				}
			}			
			if(head.direction == Cat.LEFT){
				if(head.type == 0){
					headPixmap = Assets.catLeft;	
				}			
				if(head.type == 1){
					headPixmap = Assets.bwCatLeft;	
				}	
				if(head.type == 2){
					headPixmap = Assets.greyCatLeft;	
				}	
				if(head.type == 3){
					headPixmap = Assets.blueCatLeft;	
				}	
				if(head.type == 4){
					headPixmap = Assets.brownCatLeft;	
				}
			}			
			if(head.direction == Cat.RIGHT){
				if(head.type == 0){
					headPixmap = Assets.catRight;
				}			
				if(head.type == 1){
					headPixmap = Assets.bwCatRight;	
				}	
				if(head.type == 2){
					headPixmap = Assets.greyCatRight;	
				}	
				if(head.type == 3){
					headPixmap = Assets.blueCatRight;	
				}	
				if(head.type == 4){
					headPixmap = Assets.brownCatRight;	
				}
			}			
			x = head.x * (int) catSizeX;
			y = head.y * (int) catSizeY;
			g.drawPixmap(headPixmap, x, y);			
		} else{
			//draw inverted versions of all assets
			//Determine which cat graph to show based on 
			int len = cat.herd.size();
			for(int i = 1; i < len; i++){
					CongaCat part = cat.herd.get(i);
					x = part.x * (int) catSizeX;
					y = part.y * (int) catSizeY;
					//depending on direction draw here!
					Pixmap currentCatPixmap = null;
					if(part.direction == 0 ){
						//set correct image depending on type identifier
						if(part.type == 0){
							currentCatPixmap = Assets.icatUp;	
						}	
						if(part.type == 1){
							currentCatPixmap = Assets.ibwCatUp;	
						}	
						if(part.type == 2){
							currentCatPixmap = Assets.igreyCatUp;	
						}	
						if(part.type == 3){
							currentCatPixmap = Assets.iblueCatUp;	
						}	
						if(part.type == 4){
							currentCatPixmap = Assets.ibrownCatUp;	
						}	
					}					
					if(part.direction == 2){
						if(part.type == 0){
							currentCatPixmap = Assets.icatDown;	
						}						
						if(part.type == 1){
							currentCatPixmap = Assets.ibwCatDown;	
						}	
						if(part.type == 2){
							currentCatPixmap = Assets.igreyCatDown;	
						}	
						if(part.type == 3){
							currentCatPixmap = Assets.iblueCatDown;	
						}	
						if(part.type == 4){
							currentCatPixmap = Assets.ibrownCatDown;	
						}	
					}
					if(part.direction == 1){
						if(part.type == 0){
							currentCatPixmap = Assets.icatLeft;	
						}						
						if(part.type == 1){
							currentCatPixmap = Assets.ibwCatLeft;	
						}	
						if(part.type == 2){
							currentCatPixmap = Assets.igreyCatLeft;	
						}	
						if(part.type == 3){
							currentCatPixmap = Assets.iblueCatLeft;	
						}	
						if(part.type == 4){
							currentCatPixmap = Assets.ibrownCatLeft;	
						}	
					}					
					if(part.direction == 3){
						if(part.type == 0){
							currentCatPixmap = Assets.icatRight;
						}						
						if(part.type == 1){
							currentCatPixmap = Assets.ibwCatRight;	
						}	
						if(part.type == 2){
							currentCatPixmap = Assets.igreyCatRight;	
						}	
						if(part.type == 3){
							currentCatPixmap = Assets.iblueCatRight;	
						}	
						if(part.type == 4){
							currentCatPixmap = Assets.ibrownCatRight;	
						}	
					}
					g.drawPixmap(currentCatPixmap, x, y);	

					
			}
			
			Pixmap headPixmap = null;
			if(head.direction == Cat.UP){
				if(head.type == 0){
					headPixmap = Assets.icatUp;	
				}				
				if(head.type == 1){
					headPixmap = Assets.ibwCatUp;	
				}	
				if(head.type == 2){
					headPixmap = Assets.igreyCatUp;	
				}	
				if(head.type == 3){
					headPixmap = Assets.iblueCatUp;	
				}	
				if(head.type == 4){
					headPixmap = Assets.ibrownCatUp;	
				}
			}
			if(head.direction == Cat.DOWN){
				if(head.type == 0){
					headPixmap = Assets.icatDown;	
				}			
				if(head.type == 1){
					headPixmap = Assets.ibwCatDown;	
				}	
				if(head.type == 2){
					headPixmap = Assets.igreyCatDown;	
				}	
				if(head.type == 3){
					headPixmap = Assets.iblueCatDown;	
				}	
				if(head.type == 4){
					headPixmap = Assets.ibrownCatDown;	
				}
			}			
			if(head.direction == Cat.LEFT){
				if(head.type == 0){
					headPixmap = Assets.icatLeft;	
				}			
				if(head.type == 1){
					headPixmap = Assets.ibwCatLeft;	
				}	
				if(head.type == 2){
					headPixmap = Assets.igreyCatLeft;	
				}	
				if(head.type == 3){
					headPixmap = Assets.iblueCatLeft;	
				}	
				if(head.type == 4){
					headPixmap = Assets.ibrownCatLeft;	
				}
			}			
			if(head.direction == Cat.RIGHT){
				if(head.type == 0){
					headPixmap = Assets.icatRight;
				}			
				if(head.type == 1){
					headPixmap = Assets.ibwCatRight;	
				}	
				if(head.type == 2){
					headPixmap = Assets.igreyCatRight;	
				}	
				if(head.type == 3){
					headPixmap = Assets.iblueCatRight;	
				}	
				if(head.type == 4){
					headPixmap = Assets.ibrownCatRight;	
				}
			}			
			x = head.x * (int) catSizeX;
			y = head.y * (int) catSizeY;
			g.drawPixmap(headPixmap, x, y);
		}

	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.ready, (g.getWidth()/2-Assets.ready.getWidth()/2), (g.getHeight()/2-Assets.ready.getHeight()/2));
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();
		
		if(!world.catCrazed){
			g.drawPixmap(Assets.pauseButton, 10, 10);	
		}else{
			g.drawPixmap(Assets.pauseButton, 10, 10);	
		}		
		
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

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		
		if(!world.catCrazed){
			g.drawPixmap(Assets.resume, (g.getWidth()/2-Assets.resume.getWidth()/2), g.getHeight()/4);
			g.drawPixmap(Assets.quit, (g.getWidth()/2-Assets.quit.getWidth()/2), (g.getHeight()/2)+g.getHeight()/4);
		} else{
			g.drawPixmap(Assets.resume, (g.getWidth()/2-Assets.resume.getWidth()/2), g.getHeight()/4);
			g.drawPixmap(Assets.quit, (g.getWidth()/2-Assets.quit.getWidth()/2), (g.getHeight()/2)+g.getHeight()/4);	
		}
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.gameOver, (g.getWidth()/2-Assets.gameOver.getWidth()/2), (g.getHeight()/2-Assets.gameOver.getHeight()/2));
	}

	@Override
	public void pause() {
		if(state == GameState.Running)
			state = GameState.Paused;
		
		if(world.gameOver){
			Settings.addScore(world.score);
			Settings.save(game.getFileIO());
			Assets.bgMusic2.stop();
		}
	}
	
	public void logFrame() {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter ", "fps: " + frames);
            frames = 0;
            startTime = System.nanoTime();
        }
    }
	
	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		
	}

}
