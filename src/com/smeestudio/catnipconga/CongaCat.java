package com.smeestudio.catnipconga;


//class for the cats behing the head cat!
public class CongaCat {
	public static final int TYPE_1 = 0;
	public static final int TYPE_2 = 1;
	public static final int TYPE_3 = 2;
	public static final int TYPE_4 = 3;
	public static final int TYPE_5 = 4;
	
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public int x, y;
	public int type;
	public int direction;
	
	public CongaCat(int x, int y, int type, int direction){
		this.x = x;
		this.y = y;
		this.type = type;
		this.direction = direction;
	}
}
