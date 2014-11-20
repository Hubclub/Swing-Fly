package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Fly {
	private Rectangle flyRectangle;
	private Texture flyTexture;
	private float acc;
	private float speed,time;
	private boolean alive;
	
	public Fly () {
		flyRectangle= new Rectangle();
		alive=true;
		reset();
		flyTexture = new Texture("badlogic.jpg");
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(flyTexture, flyRectangle.x, flyRectangle.y, flyRectangle.width, flyRectangle.height);
	}
	
	public void update (float delta) {
		time+=delta;
		speed -= time*acc;
		flyRectangle.y += speed;
		
		if (Gdx.input.justTouched() ) {
			time=0;
			speed=Constants.FLY_SPEED;
		}
		
		if (speed<Constants.MAX_SPEED) speed = Constants.MAX_SPEED;
		
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
	
	public void reset(){
		speed = 0;
		time = 0;
		acc=Constants.GRAVITY_ACCELERATION;
		flyRectangle.set(Gdx.graphics.getWidth()/2-Constants.FLY_WIDTH/2, 50*Constants.HEIGHT_RATIO, Constants.FLY_WIDTH, Constants.FLY_HEIGHT);
		

	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void revive(){
		alive=true;
	}
	
	public void die(){
		alive=false;
	}
	
}
