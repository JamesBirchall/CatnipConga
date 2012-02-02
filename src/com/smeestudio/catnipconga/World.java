package com.smeestudio.catnipconga;

import java.util.Random;


public class World {
	static final int WORLD_WIDTH = 10;
	static final int WORLD_HEIGHT = 7;
	static final int SCORE_INCREMENT = 10;
	static final float TICK_INITIAL = 0.5f;
	static final float TICK_DECREMENT = 0.05f;
	
	public Cat cat;
	public Plant plant;
	public Plant specialPlant;
	public boolean gameOver  = false;
	public int score = 0;
	int plantOrCatDefault = 0;
	
	boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
	Random random = new Random();
	float tickTime = 0;
	static float tick = TICK_INITIAL;
	
	//attributes for flower power
	boolean generateFlowerTimeActive = true;  //Set to false once in other states and reset once ready
	float generateFlowerTime = 0;	//once this equals 10 generate a new special flower in the game
	boolean specialFlowerShowTimeActive = false;  //set once generated and count, once finished reset and raise generateFlower again
	float specialFlowerShowTime = 0;  //counts to 3 seconds and if not taken makes the flower dissapear
	float catCrazedTime = 0;  //Counts up to 4 seconds before the next flower countdown can be started
	boolean catCrazed = false;  //If set to true cat in crazed mode, use inverted graphics and slow down tick time temporarily (save tick time prior)
	float savedTickTime = 0;  //For saving current tick time before reducing
	
	public World(){
		cat = new Cat();
		placePlant();
	}

	private void placePlant() {
		
		//Setup a random plant from assets list
		plantOrCatDefault = random.nextInt(5);
		
		//clear fields array
		for(int x=0; x < WORLD_WIDTH; x++){
			for(int y = 0; y < WORLD_HEIGHT; y++){
				fields[x][y] = false;
			}
		}
		
		//set cells occupied by cats
		int len = cat.herd.size();
		for(int i = 0; i < len; i++){
			CongaCat part = cat.herd.get(i);
			fields[part.x][part.y] = true;
		}
		
		//set cells occupied by specialPlant - because it could be there
		//Have to remember to set special plant to null in cooldown period
		if(specialPlant != null){
			fields[specialPlant.x][specialPlant.y] = true;
		}
		
		//create random position and find a cell for the plant
		int plantX = random.nextInt(WORLD_WIDTH);
		int plantY = random.nextInt(WORLD_HEIGHT);
		while(true){
			if(fields[plantX][plantY] == false)
				break;
			plantX += 1;
			if(plantX >= WORLD_WIDTH){
				plantX = 0;
				plantY += 1;
				if(plantY >= WORLD_HEIGHT){
					plantY = 0;
				}
			}
		}
		plant = new Plant(plantX, plantY, plantOrCatDefault); 
	}
	
	public void placeSpecialFlower() {
		//Similar to place plant, look through all locations for cats/current catnip plant and find a random location to draw it
		//Use the same plant class but fix to type (inverted for drawing!)
		plantOrCatDefault = 6;  //Type for inverted plant type
		
		//clear fields array
		for(int x=0; x < WORLD_WIDTH; x++){
			for(int y = 0; y < WORLD_HEIGHT; y++){
				fields[x][y] = false;
			}
		}
				
		//set cells occupied by cats
		int len = cat.herd.size();
		for(int i = 0; i < len; i++){
			CongaCat part = cat.herd.get(i);
			fields[part.x][part.y] = true;
		}		
		
		//set cells occupied by the current plant - only ever one of these active
		fields[plant.x][plant.y] = true;
		
		//create random position and find a cell for the plant
		int plantX = random.nextInt(WORLD_WIDTH);
		int plantY = random.nextInt(WORLD_HEIGHT);
		while(true){
			if(fields[plantX][plantY] == false)
				break;
			plantX += 1;
			if(plantX >= WORLD_WIDTH){
				plantX = 0;
				plantY += 1;
				if(plantY >= WORLD_HEIGHT){
					plantY = 0;
				}
			}
		}
		specialPlant = new Plant(plantX, plantY, plantOrCatDefault);
	}
	
	public void update(float deltaTime){
		if(gameOver)
			return;
		
		tickTime += deltaTime;
		
		//if in generate wait stage count for 10 seconds and activate new flower stage
		if(generateFlowerTimeActive){
			generateFlowerTime += deltaTime;
			if(generateFlowerTime >= 10.0f){
				//10 seconds has pass so generate new Power Flower and fire up Flower show timer
				generateFlowerTime = 0.0f;
				generateFlowerTimeActive = false;
				specialFlowerShowTimeActive = true;

				//call generate flower
				placeSpecialFlower();
			}			
		}
		
		if(specialFlowerShowTimeActive){
			//count for 3 seconds and then null the special plant
			//This will also all be reset if check bitten special occurs
			specialFlowerShowTime += deltaTime;
			if(specialFlowerShowTime >= 5.0f){
				//reset to original state and nullify flower
				specialFlowerShowTime = 0.0f;
				specialFlowerShowTimeActive = false;
				specialPlant = null;
				generateFlowerTimeActive = true;
			}
		}
		
		if(catCrazed){
			catCrazedTime += deltaTime;
			if(catCrazedTime >=8.0f){
				//reset states and set tick back to normal
				catCrazed = false;
				catCrazedTime = 0;
				generateFlowerTimeActive = true;
				tick = savedTickTime;
			}
		}

		while(tickTime > tick){
			tickTime -= tick;
			cat.advance();
			if(cat.checkBitten()){
				gameOver = true;
				return;
			}
			
			CongaCat head = cat.herd.get(0);
			
			//check haven't eaten special flower first - no reason
			if(specialPlant != null){
				if(head.x == specialPlant.x && head.y == specialPlant.y){
					//we have eatent he special plant so change the state of the game to use all inverted assets, record time and set slow cycle for a set numebr of seconds
					specialPlant = null;
					specialFlowerShowTimeActive = false;
					specialFlowerShowTime = 0;
					catCrazed = true;
					//record tick time and set new one temporarily here
					savedTickTime = tick;
					tick = 0.5f; //slow time!
					if(Settings.soundEnabled)
						Assets.meow.play(1);
				}
			}
			
			if(head.x == plant.x && head.y == plant.y){
				score += SCORE_INCREMENT;
				cat.gnaw();
				if(cat.herd.size() == WORLD_WIDTH*WORLD_HEIGHT){
					gameOver = true;
					return;
				} else{
					placePlant();
				}
				
				//increment speed every 30 points
				if(score % 50 == 0 && tick - TICK_DECREMENT > 0){
					tick -= TICK_DECREMENT;
				}
			}
		}
	}
}
