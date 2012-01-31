package com.smeestudio.catnipconga;

import com.smeestudio.framework.Screen;
import com.smeestudio.framework.impl.AndroidGame;

public class CatnipCongaGame extends AndroidGame{

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
	
	@Override
	public void onPause(){
		super.onPause();
			if(Settings.soundEnabled){
				if(Assets.bgMusic.isPlaying()){
					Assets.bgMusic.dispose();	
				}
				if(Assets.bgMusic2.isPlaying()){
					Assets.bgMusic2.dispose();	
				}
			}
	}

}
