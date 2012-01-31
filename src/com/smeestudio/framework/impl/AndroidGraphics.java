package com.smeestudio.framework.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;

import com.smeestudio.framework.Graphics;
import com.smeestudio.framework.Pixmap;

public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();
    float scaleX, scaleY, scaleX2, scaleY2;
    int deviceX, deviceY;

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Config config = null;
        if (format == PixmapFormat.RGB565)
            config = Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;
        
        
        //Scaling images down to be used!
        if(((scaleX > 1.0f) || (scaleY > 1.0f))){
            Bitmap tempBitmap = null;
            int tempX, tempY = 0;
            tempX = (int) (scaleX*bitmap.getWidth());
            Log.d("tempX", " "+tempX);
            tempY = (int) (scaleY*bitmap.getHeight());
            Log.d("tempY", " "+tempY);
            tempBitmap = Bitmap.createScaledBitmap(bitmap, tempX, tempY, false);
                        
            return new AndroidPixmap(tempBitmap, format);
        }
        return new AndroidPixmap(bitmap, format);
    }
    
    //Non scaling to assets, for things that rely on being fixed width (e.g number maps)
    @Override
	public Pixmap newPixmapNoScaling(String fileName, PixmapFormat format) {
        Config config = null;
        if (format == PixmapFormat.RGB565)
            config = Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;
        
        return new AndroidPixmap(bitmap, format);		
	}   

    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    @Override
    public void drawPixel(int x, int y, int color) {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color) {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }
    
    @Override
	public void drawRectNoFill(int x, int y, int width, int height, int color) {
    	paint.setColor(color);
        paint.setStyle(Style.STROKE);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);		
	}

    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        canvas.drawBitmap(((AndroidPixmap) pixmap).bitmap, srcRect, dstRect,
                null);
    }
    
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);

    }
    
	@Override
	public void drawPixmapWithFade(Pixmap pixmap, int x, int y, int fade) {
		 paint.setAlpha(fade);
		 canvas.drawBitmap(((AndroidPixmap)pixmap).bitmap, x, y, null);
		 paint.setAlpha(0);
	}
    
    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }

	@Override
	public void setScaleX(float x) {
		this.scaleX = x;
	}

	@Override
	public void setScaleY(float y) {
		this.scaleY = y;
	}

	@Override
	public float getScaleX() {
		return this.scaleX;
	}

	@Override
	public float getScaleY() {
		return this.scaleY;
	}

	@Override
	public void setDeviceX(int x) {
		this.deviceX = x;
	}

	@Override
	public void setDeviceY(int y) {
		this.deviceY = y;
	}

	@Override
	public int getDeviceX() {
		return this.deviceX;
	}

	@Override
	public int getDeviceY() {
		return this.deviceY;
	}

	@Override
	public void setScaleX2(float x) {
		this.scaleX2 = x;
	}

	@Override
	public void setScaleY2(float y) {
		this.scaleY2 = y;
	}

	@Override
	public float getScaleX2() {
		return scaleX2;
	}

	@Override
	public float getScaleY2() {
		return scaleY2;
	}



	
}
