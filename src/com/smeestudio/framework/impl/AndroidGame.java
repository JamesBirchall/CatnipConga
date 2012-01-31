package com.smeestudio.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.smeestudio.framework.Audio;
import com.smeestudio.framework.FileIO;
import com.smeestudio.framework.Game;
import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Input;
import com.smeestudio.framework.Screen;

public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    float scaleX, scaleY;
    int deviceX, deviceY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;
        
        //get screen width & height and set correct framebuffer size
        if(isLandscape){
        	//then X is width so test for size =< 640 and set framebuffer to take alrger assets!
        	//test width because it's widest point
        	if(getWindowManager().getDefaultDisplay().getWidth() >= 640){
              frameBufferWidth = isLandscape ? 800 : 480;
              frameBufferHeight = isLandscape ? 480 : 800;
        	}
        	} else {
        	//then X is width so test for size =< 640 and set framebuffer to take alrger assets!
        	//test height because its the longest part
        	if(getWindowManager().getDefaultDisplay().getHeight() >= 640){
                frameBufferWidth = isLandscape ? 800 : 480;
                frameBufferHeight = isLandscape ? 480 : 800;
        	}
        }
        
        deviceX = getWindowManager().getDefaultDisplay().getWidth();
        deviceY = getWindowManager().getDefaultDisplay().getHeight();
        
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        //values use for input scaling
        scaleX = (float) frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
        scaleY = (float) frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);
        
        //set values for scaling graphics
        scaleX = (float) getWindowManager().getDefaultDisplay().getWidth()/frameBufferWidth;
        scaleY = (float) getWindowManager().getDefaultDisplay().getHeight()/frameBufferHeight;
        
        //set graphics variables for scaling on devices on both axis
        graphics.setScaleX(scaleX);
        graphics.setScaleY(scaleY);
        graphics.setDeviceX(deviceX);
        graphics.setDeviceY(deviceY);
        
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
    }

    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    
    public Screen getCurrentScreen() {
        return screen;
    }
}
