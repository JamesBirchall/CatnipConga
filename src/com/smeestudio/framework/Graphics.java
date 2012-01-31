package com.smeestudio.framework;

public interface Graphics {
	
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format);
    public Pixmap newPixmapNoScaling(String fileName, PixmapFormat format);

    public void clear(int color);

    public void drawPixel(int x, int y, int color);

    public void drawLine(int x, int y, int x2, int y2, int color);

    public void drawRect(int x, int y, int width, int height, int color);
    
    public void drawRectNoFill(int x, int y, int width, int height, int color);

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
            int srcWidth, int srcHeight);

    public void drawPixmap(Pixmap pixmap, int x, int y);
    
    public void drawPixmapWithFade(Pixmap pixmap, int x, int y, int fade);

    public int getWidth();

    public int getHeight();
    
    public void setScaleX(float x);
    public void setScaleY(float y);
    public float getScaleX();
    public float getScaleY();

    public void setScaleX2(float x);
    public void setScaleY2(float y);
    public float getScaleX2();
    public float getScaleY2();
    
    public void setDeviceX(int x);
    public void setDeviceY(int y);
    public int getDeviceX();
    public int getDeviceY();
    
}
