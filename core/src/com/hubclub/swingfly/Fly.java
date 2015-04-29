package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Fly {
	private Rectangle flyRectangle;
	private Texture flies;
	private TextureRegion[][] flyTextures;
	private float acc;
	private float speed,time,animTime;
	private boolean alive,tap;
	private Animation anim;
	private Texture tapTexture, rightbody, leftbody, bloodmark;
	private Sound flySound, flyDeath;
	
	public Fly () {
		animTime=0;
		flyRectangle= new Rectangle();
		tapTexture = new Texture ("tapTexture.png");
		rightbody = new Texture ("rightbodyfly.png");
		leftbody = new Texture ("leftbodyfly.png");
		bloodmark = new Texture ("bloodmark.png");
		flySound = Gdx.audio.newSound(Gdx.files.internal("sound/flysound.mp3"));
		flyDeath = Gdx.audio.newSound(Gdx.files.internal("sound/flydeath.mp3"));
		revive();
		reset();
		flies=new Texture("flytextures.png");
		flyTextures = TextureRegion.split(flies, flies.getWidth()/6,flies.getHeight() );
		anim=new Animation(0.0005f,flyTextures[0]);
		anim.setPlayMode(PlayMode.LOOP);
	}
	
	public void draw(SpriteBatch batch) {
		if(!tap){
			batch.draw(tapTexture,Gdx.graphics.getWidth()/2-Constants.FLY_WIDTH - Constants.COZMA/2, 200*Constants.HEIGHT_RATIO, 2*Constants.FLY_WIDTH, 2*Constants.FLY_HEIGHT );
			MyGame.game.getFont().draw(batch, "TAP TO FLY", Gdx.graphics.getWidth()/2 - Constants.FLY_WIDTH - Constants.COZMA , 380 * Constants.HEIGHT_RATIO);
			batch.draw(flyTextures[0][1],Constants.FLY_START.x,Constants.FLY_START.y,Constants.FLY_WIDTH,Constants.FLY_HEIGHT);
			
		}else{
			if(speed< -3 * Constants.HEIGHT_RATIO)
				batch.draw(flyTextures[0][1],flyRectangle.x,flyRectangle.y,Constants.FLY_WIDTH,Constants.FLY_HEIGHT);
			else
				batch.draw(anim.getKeyFrame(animTime),flyRectangle.x,flyRectangle.y,Constants.FLY_WIDTH,Constants.FLY_HEIGHT);
		}
	}
	
	public void update (float delta) {
		if(tap){
			time+=delta;
			speed -= time*acc;
			flyRectangle.y += speed;
			
		}
		
		animTime+=delta;
		
		if (Gdx.input.justTouched() ||  Gdx.input.isKeyPressed(Keys.SPACE)) {
			flySound.play();
			time=0;
			speed=Constants.FLY_SPEED;
			tap=true;
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
		
		//batch.draw(bloodmark, leftSpider.width , leftSpider.y - flyRectangle.height/2, rightSpider.x - leftSpider.width, flyRectangle.height);
		batch.draw(bloodmark, 240 * Constants.WIDTH_RATIO - flyRectangle.width/2, leftSpider.y - flyRectangle.height/2, flyRectangle.width, flyRectangle.height);
		batch.draw(leftbody, leftSpider.width - flyRectangle.width + 10 * Constants.WIDTH_RATIO, leftSpider.y - flyRectangle.height / 2, flyRectangle.width, flyRectangle.height);
		batch.draw(rightbody, rightSpider.x, leftSpider.y - flyRectangle.height / 2, flyRectangle.width, flyRectangle.height);
		
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
		tap=false;
	}
	
	public void die(){
		flyDeath.play();
		alive=false;
	}
	
	public void dispose () {
		tapTexture.dispose();
		flies.dispose();
		flySound.dispose();
		flyDeath.dispose();
	}
	
}
