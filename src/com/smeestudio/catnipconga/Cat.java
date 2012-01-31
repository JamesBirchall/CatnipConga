package com.smeestudio.catnipconga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Cat {
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	
	public List<CongaCat> herd = new ArrayList<CongaCat>();
	Random random = new Random();
	int rand;
	
	//cat constructor, starts off with no cats following!
	public Cat(){
		herd.add(new CongaCat(2,3,0, RIGHT));
		rand = random.nextInt(5);
		herd.add(new CongaCat(1,3,rand, RIGHT));
	}
	
	public void turnLeft(){
		CongaCat head = herd.get(0);
		head.direction +=1;
		if(head.direction > RIGHT)
			head.direction = UP;
	}
	
	public void turnRight(){
		CongaCat head = herd.get(0);
		head.direction -= 1;
		if(head.direction < UP)
			head.direction = RIGHT;
	}
	
	public void gnaw(){
		rand = random.nextInt(5);
		CongaCat end = herd.get(herd.size() -1);
		herd.add(new CongaCat(end.x, end.y,rand,end.direction));
	}
	
	public void advance(){
		//Move conga line along one and make sure every is updated directions and stuff!!
		
		//Get head cat
		CongaCat head = herd.get(0);
		int len = herd.size()-1; //because it starts at 0!
		
		//update conga line
		for(int i = len; i > 0; i--){
				CongaCat before = herd.get(i-1);
				CongaCat part = herd.get(i);
				part.x = before.x;
				part.y = before.y;
				part.direction = before.direction;  //sets direction properly so the cat can also have the correct image later when drawn
			}
	
		if(head.direction == UP){
			head.y -= 1;
		}
		if(head.direction == LEFT){
			head.x -= 1;
		}
		if(head.direction == DOWN){
			head.y += 1;
		}
		if(head.direction == RIGHT){
			head.x += 1;
		}
			
		
		//if wraps around world make sure to reset to other side!
		//world size is 10 on x and 7 on y!
		if(head.x < 0)
			head.x = 9;
		if(head.x > 9)
			head.x = 0;
		if(head.y < 0)
			head.y = 6;
		if(head.y > 6)
			head.y = 0;
	}
	
	//check we haven't eaten ourselves!
	public boolean checkBitten(){
		int len = herd.size();
		CongaCat head = herd.get(0);
		for(int i = 1; i < len; i++){
			CongaCat part = herd.get(i);
			if(part.x == head.x && part.y == head.y)
				return true;
		}		
		return false;
	}
}
