package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class Constants {
	public static final float HEIGHT_RATIO = Gdx.graphics.getHeight()/800f;
	public static final float WIDTH_RATIO = Gdx.graphics.getWidth()/480f;
	public static final float FLY_HEIGHT = 50*HEIGHT_RATIO;
	public static final float FLY_WIDTH = 50*WIDTH_RATIO;
	public static final float DISTANCE_PLATFORM = 350 * HEIGHT_RATIO;
	public static final float DELAY_TIME = 1f;
	public static final float WEB_HEIGHT = 10 * HEIGHT_RATIO;
	public static final float FLY_SPEED = 13 * HEIGHT_RATIO;
	public static final float GRAVITY_ACCELERATION = 0.5f * HEIGHT_RATIO;
	public static final float MAX_SPEED = - 12.5f * HEIGHT_RATIO;
	public static final Texture BACKGROUND = new Texture ("background.jpg");
	public static OrthographicCamera cam;
	public static Texture stringTexture, spiderTexture;

}
