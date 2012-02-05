package com.smeestudio.catnipconga;

import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Graphics.PixmapFormat;
import com.smeestudio.framework.Screen;

public class LoadingScreen extends Screen {
	
	float scaleX, scaleY = 0.0f;

	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		
		//capture scaling set in graphics class so loader can use
		scaleX = g.getScaleX();
		scaleY = g.getScaleY();	
		
		
		//determine screen size
		if(g.getDeviceX() >= 640){
			//load large assets for game - make all of them the correct size by setting them up correctly
			Assets.background = g.newPixmap("BackgroundGrass_800_480.png", PixmapFormat.RGB565);
			Assets.iBackground = g.newPixmap("IBackgroundGrass_800x480.png", PixmapFormat.RGB565);
			
			Assets.logo = g.newPixmap("CatnipCongaLogo_570x106.png", PixmapFormat.ARGB4444);
			Assets.logo2 = g.newPixmap("BySmeeStudiosLogo_265x43.png", PixmapFormat.ARGB4444);
			Assets.mainMenuPlay = g.newPixmap("PlayButton_173x57.png", PixmapFormat.ARGB4444);
			Assets.mainMenuHighScores = g.newPixmap("HighscoresButton_449x54.png", PixmapFormat.ARGB4444);
			Assets.mainMenuHelp = g.newPixmap("HelpButton_169x55.png", PixmapFormat.ARGB4444);
			Assets.credits = g.newPixmap("CreditsButton137x25.png", PixmapFormat.ARGB4444);
			Assets.controls = g.newPixmap("ControlsButton171x25.png", PixmapFormat.ARGB4444);
			
			Assets.controlCopy = g.newPixmap("ControlText400x86.png", PixmapFormat.ARGB4444);
			
			//Setup number map but don't allow scaling due to screen resolutions & grabbing numbers
			//Assets.numberMap = g.newPixmap("HastyPuddings_NumberMap_210x32.png", PixmapFormat.ARGB4444);
			Assets.numberMap = g.newPixmapNoScaling("HastyPuddings_NumberMap_420x64.png", PixmapFormat.ARGB4444);
			
			Assets.catDown = g.newPixmap("CatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.catUp = g.newPixmap("CatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.catLeft = g.newPixmap("CatSideLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.catRight = g.newPixmap("CatSideRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.bwCatDown = g.newPixmap("BWCatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.bwCatUp = g.newPixmap("BWCatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.bwCatLeft = g.newPixmap("BWCatLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.bwCatRight = g.newPixmap("BWCatRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.blueCatDown = g.newPixmap("BlueCatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.blueCatUp = g.newPixmap("BlueCatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.blueCatLeft = g.newPixmap("BlueCatLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.blueCatRight = g.newPixmap("BlueCatRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.brownCatDown = g.newPixmap("BrownCatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.brownCatUp = g.newPixmap("BrownCatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.brownCatLeft = g.newPixmap("BrownCatLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.brownCatRight = g.newPixmap("BrownCatRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.greyCatDown = g.newPixmap("GreyCatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.greyCatUp = g.newPixmap("GreyCatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.greyCatLeft = g.newPixmap("GreyCatLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.greyCatRight = g.newPixmap("GreyCatRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.icatDown = g.newPixmap("ICatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.icatUp = g.newPixmap("ICatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.icatLeft = g.newPixmap("ICatSideLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.icatRight = g.newPixmap("ICatSideRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.ibwCatDown = g.newPixmap("IBWCatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.ibwCatUp = g.newPixmap("IBWCatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.ibwCatLeft = g.newPixmap("IBWCatLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.ibwCatRight = g.newPixmap("IBWCatRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.iblueCatDown = g.newPixmap("IBlueCatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.iblueCatUp = g.newPixmap("IBlueCatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.iblueCatLeft = g.newPixmap("IBlueCatLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.iblueCatRight = g.newPixmap("IBlueCatRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.ibrownCatDown = g.newPixmap("IBrownCatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.ibrownCatUp = g.newPixmap("IBrownCatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.ibrownCatLeft = g.newPixmap("IBrownCatLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.ibrownCatRight = g.newPixmap("IBrownCatRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.igreyCatDown = g.newPixmap("IGreyCatFront80x68.png", PixmapFormat.ARGB4444);
			Assets.igreyCatUp = g.newPixmap("IGreyCatBack80x68.png", PixmapFormat.ARGB4444);
			Assets.igreyCatLeft = g.newPixmap("IGreyCatLeft80x68.png", PixmapFormat.ARGB4444);
			Assets.igreyCatRight = g.newPixmap("IGreyCatRight80x68.png", PixmapFormat.ARGB4444);
			
			Assets.plant1 = g.newPixmap("CatnipPlant1_80x65.png", PixmapFormat.ARGB4444);
			Assets.plant2 = g.newPixmap("CatnipPlant2_80x65.png", PixmapFormat.ARGB4444);
			Assets.plant3 = g.newPixmap("CatnipPlant3_80x65.png", PixmapFormat.ARGB4444);
			Assets.plant4 = g.newPixmap("CatnipPlant4_80x65.png", PixmapFormat.ARGB4444);
			Assets.plant5 = g.newPixmap("CatnipPlant5_80x65.png", PixmapFormat.ARGB4444);
			Assets.plant6 = g.newPixmap("CatnipPlant6_80x65.png", PixmapFormat.ARGB4444);
			Assets.iplant1 = g.newPixmap("InvertedCatnipplant1_80x65.png", PixmapFormat.ARGB4444);
			Assets.gameOver = g.newPixmap("GameOverButton_168x24.png", PixmapFormat.ARGB4444);
			Assets.pauseButton = g.newPixmap("PauseButton64x64.png", PixmapFormat.ARGB4444);
			Assets.playButton= g.newPixmap("PlayButton64x64.png", PixmapFormat.ARGB4444);
			Assets.soundOnButton = g.newPixmap("SoundOnButton64x64.png", PixmapFormat.ARGB4444);
			Assets.soundOffButton = g.newPixmap("SoundOffButton64_64.png", PixmapFormat.ARGB4444);
			Assets.resume = g.newPixmap("ResumeButton_208x42.png", PixmapFormat.ARGB4444);
			Assets.quit = g.newPixmap("QuitButton_140x41.png", PixmapFormat.ARGB4444);
			Assets.score = g.newPixmap("ScoreLogo136x31.png", PixmapFormat.ARGB4444);
			Assets.ready = g.newPixmap("TouchToStartButton_251x24.png", PixmapFormat.ARGB4444);
			
			Assets.help1text = g.newPixmap("HelpScreen1text_400x147.png", PixmapFormat.ARGB4444);
			Assets.help2text = g.newPixmap("HelpScreen2text_400x147.png", PixmapFormat.ARGB4444);
			Assets.help3text = g.newPixmap("HelpScreen3text_400x147.png", PixmapFormat.ARGB4444);
			Assets.help4text = g.newPixmap("HelpScreen4text_400x147.png", PixmapFormat.ARGB4444);
			Assets.help5text = g.newPixmap("HelpScreen5text_400x147.png", PixmapFormat.ARGB4444);
			
			Assets.gestureLeftTouch = g.newPixmap("Gesture_LeftHand160x188.png", PixmapFormat.ARGB4444);
			Assets.gestureRightTouch = g.newPixmap("Gesture_RightHand160x188.png", PixmapFormat.ARGB4444);
			
			
			Assets.creditsScreen = g.newPixmap("CreditsScreen800x480.png", PixmapFormat.ARGB4444);
			
			Assets.buttonDown = g.newPixmap("ButtonDown80x80.png",	PixmapFormat.ARGB4444);
			Assets.buttonUp = g.newPixmap("ButtonUp80x80.png",	PixmapFormat.ARGB4444);
			Assets.buttonLeft = g.newPixmap("ButtonLeft80x80.png",	PixmapFormat.ARGB4444);
			Assets.buttonRight = g.newPixmap("ButtonRight80x80.png",	PixmapFormat.ARGB4444);
			
		}else{
			//load default small assets for game	
			Assets.background = g.newPixmap("BackgroundGrass_480_320.png", PixmapFormat.RGB565);
			Assets.iBackground = g.newPixmap("IBackgroundGrass_480_320.png", PixmapFormat.RGB565);
			
			Assets.logo = g.newPixmap("CatnipCongaLogo_338x62.png", PixmapFormat.ARGB4444);
			Assets.logo2 = g.newPixmap("BySmeeStudiosLogo_188x31.png", PixmapFormat.ARGB4444);
			Assets.mainMenuPlay = g.newPixmap("PlayButton_101x33.png", PixmapFormat.ARGB4444);
			Assets.mainMenuHighScores = g.newPixmap("HighscoresButton_261x31.png", PixmapFormat.ARGB4444);
			Assets.mainMenuHelp = g.newPixmap("HelpButton_98x31.png", PixmapFormat.ARGB4444);
			Assets.credits = g.newPixmap("CreditsButton99x18.png", PixmapFormat.ARGB4444);
			Assets.controls = g.newPixmap("ControlsButton123x18.png", PixmapFormat.ARGB4444);
			Assets.controlCopy = g.newPixmap("ControlText302x65.png", PixmapFormat.ARGB4444);

			//Setup number map but don't allow scaling due to screen resolutions & grabbing numbers
			//Assets.numberMap = g.newPixmap("HastyPuddings_NumberMap_210x32.png", PixmapFormat.ARGB4444);
			Assets.numberMap = g.newPixmapNoScaling("HastyPuddings_NumberMap_210x32.png", PixmapFormat.ARGB4444);
		
			Assets.catDown = g.newPixmap("CatDown48x45.png", PixmapFormat.ARGB4444);
			Assets.catUp = g.newPixmap("CatUp48x45.png", PixmapFormat.ARGB4444);
			Assets.catLeft = g.newPixmap("CatSideLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.catRight = g.newPixmap("CatSideRight48x45.png", PixmapFormat.ARGB4444);
			
			Assets.bwCatDown = g.newPixmap("BWCatFront48x45.png", PixmapFormat.ARGB4444);
			Assets.bwCatUp = g.newPixmap("BWCatBack48x45.png", PixmapFormat.ARGB4444);
			Assets.bwCatLeft = g.newPixmap("BWCatLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.bwCatRight = g.newPixmap("BWCatRight48x45.png", PixmapFormat.ARGB4444);
			
			Assets.blueCatDown = g.newPixmap("BlueCatFront48x45.png", PixmapFormat.ARGB4444);
			Assets.blueCatUp = g.newPixmap("BlueCatBack48x45.png", PixmapFormat.ARGB4444);
			Assets.blueCatLeft = g.newPixmap("BlueCatLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.blueCatRight = g.newPixmap("BlueCatRight48x45.png", PixmapFormat.ARGB4444);
			
			Assets.brownCatDown = g.newPixmap("BrownCatFront48x45.png", PixmapFormat.ARGB4444);
			Assets.brownCatUp = g.newPixmap("BrownCatBack48x45.png", PixmapFormat.ARGB4444);
			Assets.brownCatLeft = g.newPixmap("BrownCatLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.brownCatRight = g.newPixmap("BrownCatRight48x45.png", PixmapFormat.ARGB4444);

			Assets.greyCatDown = g.newPixmap("GreyCatFront48x45.png", PixmapFormat.ARGB4444);
			Assets.greyCatUp = g.newPixmap("GreyCatBack48x45.png", PixmapFormat.ARGB4444);
			Assets.greyCatLeft = g.newPixmap("GreyCatLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.greyCatRight = g.newPixmap("GreyCatRight48x45.png", PixmapFormat.ARGB4444);

			Assets.icatDown = g.newPixmap("ICatDown48x45.png", PixmapFormat.ARGB4444);
			Assets.icatUp = g.newPixmap("ICatUp48x45.png", PixmapFormat.ARGB4444);
			Assets.icatLeft = g.newPixmap("ICatSideLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.icatRight = g.newPixmap("ICatSideRight48x45.png", PixmapFormat.ARGB4444);
			
			Assets.ibwCatDown = g.newPixmap("IBWCatFront48x45.png", PixmapFormat.ARGB4444);
			Assets.ibwCatUp = g.newPixmap("IBWCatBack48x45.png", PixmapFormat.ARGB4444);
			Assets.ibwCatLeft = g.newPixmap("IBWCatLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.ibwCatRight = g.newPixmap("IBWCatRight48x45.png", PixmapFormat.ARGB4444);
			
			Assets.iblueCatDown = g.newPixmap("IBlueCatFront48x45.png", PixmapFormat.ARGB4444);
			Assets.iblueCatUp = g.newPixmap("IBlueCatBack48x45.png", PixmapFormat.ARGB4444);
			Assets.iblueCatLeft = g.newPixmap("IBlueCatLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.iblueCatRight = g.newPixmap("IBlueCatRight48x45.png", PixmapFormat.ARGB4444);
			
			Assets.ibrownCatDown = g.newPixmap("BrownCatFront48x45.png", PixmapFormat.ARGB4444);
			Assets.ibrownCatUp = g.newPixmap("BrownCatBack48x45.png", PixmapFormat.ARGB4444);
			Assets.ibrownCatLeft = g.newPixmap("BrownCatLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.ibrownCatRight = g.newPixmap("BrownCatRight48x45.png", PixmapFormat.ARGB4444);
			
			Assets.igreyCatDown = g.newPixmap("IGreyCatFront48x45.png", PixmapFormat.ARGB4444);
			Assets.igreyCatUp = g.newPixmap("IGreyCatBack48x45.png", PixmapFormat.ARGB4444);
			Assets.igreyCatLeft = g.newPixmap("IGreyCatLeft48x45.png", PixmapFormat.ARGB4444);
			Assets.igreyCatRight = g.newPixmap("IGreyCatRight48x45.png", PixmapFormat.ARGB4444);
			
			Assets.plant1 = g.newPixmap("CatnipPlant1_48x45.png", PixmapFormat.ARGB4444);
			Assets.plant2 = g.newPixmap("CatnipPlant2_48x45.png", PixmapFormat.ARGB4444);
			Assets.plant3 = g.newPixmap("CatnipPlant3_48x45.png", PixmapFormat.ARGB4444);
			Assets.plant4 = g.newPixmap("CatnipPlant4_48x45.png", PixmapFormat.ARGB4444);
			Assets.plant5 = g.newPixmap("CatnipPlant5_48x45.png", PixmapFormat.ARGB4444);
			Assets.plant6 = g.newPixmap("CatnipPlant6_48x45.png", PixmapFormat.ARGB4444);
			Assets.iplant1 = g.newPixmap("InvertedCatnipplant1_48x45.png", PixmapFormat.ARGB4444);
			Assets.gameOver = g.newPixmap("GameOverButton_168x24.png", PixmapFormat.ARGB4444);
			Assets.pauseButton = g.newPixmap("PauseButton32x32.png", PixmapFormat.ARGB4444);
			Assets.playButton= g.newPixmap("PlayButton32x32.png", PixmapFormat.ARGB4444);
			Assets.soundOnButton = g.newPixmap("SoundOnButton32x32.png", PixmapFormat.ARGB4444);
			Assets.soundOffButton = g.newPixmap("SoundOffButton32x32.png", PixmapFormat.ARGB4444);
			Assets.resume = g.newPixmap("ResumeButton_104x21.png", PixmapFormat.ARGB4444);
			Assets.quit = g.newPixmap("QuitButton_69_20.png", PixmapFormat.ARGB4444);
			Assets.score = g.newPixmap("ScoreLogo_92x21.png", PixmapFormat.ARGB4444);
			Assets.ready = g.newPixmap("TouchToStartButton_251x24.png", PixmapFormat.ARGB4444);
			
			Assets.help1text = g.newPixmap("HelpScreen1text_300x110.png", PixmapFormat.ARGB4444);
			Assets.help2text = g.newPixmap("HelpScreen2text_300x110.png", PixmapFormat.ARGB4444);
			Assets.help3text = g.newPixmap("HelpScreen3text_300x110.png", PixmapFormat.ARGB4444);
			Assets.help4text = g.newPixmap("HelpScreen4text_300x110.png", PixmapFormat.ARGB4444);
			Assets.help5text = g.newPixmap("HelpScreen5text_300x110.png", PixmapFormat.ARGB4444);
			
			Assets.gestureLeftTouch = g.newPixmap("Gesture_LeftHand80x94.png", PixmapFormat.ARGB4444);
			Assets.gestureRightTouch = g.newPixmap("Gesture_RightHand80x94.png", PixmapFormat.ARGB4444);
			
			Assets.creditsScreen = g.newPixmap("CreditsScreen480x320.png", PixmapFormat.ARGB4444);

			Assets.buttonDown = g.newPixmap("ButtonDown50x50.png",	PixmapFormat.ARGB4444);
			Assets.buttonUp = g.newPixmap("ButtonUp50x50.png",	PixmapFormat.ARGB4444);
			Assets.buttonLeft = g.newPixmap("ButtonLeft50x50.png",	PixmapFormat.ARGB4444);
			Assets.buttonRight = g.newPixmap("ButtonRight50x50.png",	PixmapFormat.ARGB4444);
		}
		
		Assets.click = game.getAudio().newSound("Click.ogg");
		Assets.purr = game.getAudio().newSound("CatPurring.ogg");
		Assets.screech = game.getAudio().newSound("CatScreech.ogg");
		Assets.meow = game.getAudio().newSound("CatMeow.ogg");
		
		Assets.bgMusic = game.getAudio().newMusic("whistle1song.ogg");
		Assets.bgMusic2 = game.getAudio().newMusic("whistle2song.ogg");
		Assets.bgMusic3 = game.getAudio().newMusic("whistle3song.ogg");
		
		//setup scaling factor for images to fit the world grid
		float tempScale = g.getDeviceX()/10;
		tempScale = tempScale/Assets.catDown.getWidth();
		float tempScale2 = g.getDeviceX()/7;
		tempScale = tempScale/Assets.catDown.getWidth();
		g.setScaleX2(tempScale);
		g.setScaleY2(tempScale2);
		
		Settings.load(game.getFileIO());
		
		//add some time delay here or go to splash screen before directly showing main menu screen
		if(Settings.controls == 0){
			//show controls chooser
			game.setScreen(new ChooseControlsScreen(game));
		} else{
			game.setScreen(new MainMenuScreen(game));	
		}
		
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		
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
