package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Fly {
	private Rectangle flyRectangle;
	private Texture flyTexture;
	private float ACCELERAtION=.5f;
	private float speed,time;
	
	public Fly () {
		flyRectangle = new Rectangle(Gdx.graphics.getWidth()/2-Constants.FLY_WIDTH/2, 50*Constants.HEIGHT_RATIO, Constants.FLY_WIDTH, Constants.FLY_HEIGHT);
		flyTexture = new Texture("badlogic.jpg");
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(flyTexture, flyRectangle.x, flyRectangle.y, flyRectangle.width, flyRectangle.height);
	}
	
	public void update (float delta) {
		time+=delta;
		speed -= time*ACCELERAtION;
		flyRectangle.y += speed;
		
		if (Gdx.input.justTouched()) {
			time=0;
			speed=10;
		}
		
		if (speed<-9.5f) speed = -9.5f;
		
		moveCam();
		
	}
	
	public void moveCam () {
		if (flyRectangle.y>Constants.cam.position.y) {
			Constants.cam.translate(0,flyRectangle.y - Constants.cam.position.y);
		}
	}
	
	public Rectangle getFlyRectangle () {
		return flyRectangle;
	}
	
	public void split (Rectangle leftSpider, Rectangle rightSpider, SpriteBatch batch) {
		flyRectangle.x = leftSpider.width;
		batch.draw(flyTexture, leftSpider.width, leftSpider.y - flyRectangle.height / 2, flyRectangle.width, flyRectangle.height);
		batch.draw(flyTexture, rightSpider.x - flyRectangle.width, leftSpider.y - flyRectangle.height / 2, flyRectangle.width, flyRectangle.height);
	}
	
	
	

}
