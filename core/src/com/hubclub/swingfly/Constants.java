package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class Constants {
	public static final float HEIGHT_RATIO = Gdx.graphics.getHeight()/800f;
	public static final float WIDTH_RATIO = Gdx.graphics.getWidth()/480f;
	public static final float FLY_HEIGHT = 75*HEIGHT_RATIO;
	public static final float FLY_WIDTH = 75*WIDTH_RATIO;
	public static final Vector2 FLY_START = new Vector2(Gdx.graphics.getWidth()/2 - FLY_WIDTH/2,125 * HEIGHT_RATIO);
	public static final float DISTANCE_PLATFORM = 350 * HEIGHT_RATIO;
	public static final float DELAY_TIME = 0.7f;
	public static final float WEB_HEIGHT = 2.5f * HEIGHT_RATIO;
	public static final float SPIDER_HEIGHT = 105 * WIDTH_RATIO;
	public static final float SPIDER_WIDTH = 105 * HEIGHT_RATIO;
	public static final float FLY_SPEED = 12 * HEIGHT_RATIO;
	public static final float GRAVITY_ACCELERATION = 1f * HEIGHT_RATIO;
	public static final float MAX_SPEED = - 12.5f * HEIGHT_RATIO;
	public static final float COZMA = 15 * WIDTH_RATIO;
	public static OrthographicCamera cam;


}
