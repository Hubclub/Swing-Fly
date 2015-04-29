package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Spider {
	
	private Rectangle leftWeb, rightWeb, leftSpider, rightSpider;
	private float moveSpeed;
	private int score, level, isExpanding;
	private boolean hasDelay,registered;
	private TextureRegion[][] spiderStates;
	private Animation animLeft,animRight;
	private float animTime;
	private float delay;
	
	public Spider (TextureRegion[][] spiderStates) {
		leftWeb = new Rectangle();
		rightWeb = new Rectangle();
		leftSpider = new Rectangle();
		rightSpider = new Rectangle();
		this.spiderStates=spiderStates;
		animLeft = new Animation(0.1f , spiderStates[1]);
		animLeft.setPlayMode(PlayMode.LOOP);
		animRight = new Animation(0.1f , spiderStates[0]);
		animRight.setPlayMode(PlayMode.LOOP);
	}
	
	
	public void set(int score,int i){
		this.score = score;
		isExpanding = 1;
		registered=false;
		hasDelay=false;
		
		if (score<=3) level = 1;
		else if (score >3 && score <=7) level = 2;
		else if (score>7 && score <=10) level = 3;
		else level = 4;
		
		switch (level) {
		case 1 : level1(); break;
		case 2 : level2(); break;
		case 3 : level3(); break;
		case 4 : level4(); break;
		}
	
		float x = MathUtils.random(100 * Constants.WIDTH_RATIO, 240 * Constants.WIDTH_RATIO);
		
		leftWeb.set(0,0,x,Constants.WEB_HEIGHT);
		rightWeb.set(Gdx.graphics.getWidth() - x ,0,x,Constants.WEB_HEIGHT);
		
		leftWeb.y = 800*Constants.HEIGHT_RATIO + Constants.SPIDER_HEIGHT/2 + i * Constants.DISTANCE_PLATFORM;
		rightWeb.y = 800*Constants.HEIGHT_RATIO + Constants.SPIDER_HEIGHT/2 + i * Constants.DISTANCE_PLATFORM;
		
		leftSpider.set(x - Constants.SPIDER_WIDTH,leftWeb.y - Constants.SPIDER_HEIGHT/2, Constants.SPIDER_WIDTH, Constants.SPIDER_HEIGHT);
		rightSpider.set(x ,leftWeb.y - Constants.SPIDER_HEIGHT/2, Constants.SPIDER_WIDTH, Constants.SPIDER_HEIGHT);
		
		hasDelay = false;
		
		switch (level) {
		case 1 : if (MathUtils.random(1, 6) == 1) {
			setDelay();
		}break;
		case 2 : if (MathUtils.random(1, 5) == 1) {
			setDelay();
		}break;
		case 3 : if (MathUtils.random(1, 4) == 1) {
			setDelay();
		}break;
		case 4 : if (MathUtils.random(1, 3) == 1) {
			setDelay();
		}break;
		}
		
	}
	
	private void setDelay () {
		hasDelay = true;
		
		leftWeb.width=240*Constants.WIDTH_RATIO;
		rightWeb.width=rightWeb.x=240*Constants.WIDTH_RATIO;
		
		leftSpider.x = leftWeb.width - leftSpider.width;
		rightSpider.x= rightWeb.x;
	}
	
	public void movePlatform (float deltaTime) {
		leftWeb.width += isExpanding*moveSpeed*deltaTime * 60;
		rightWeb.width +=isExpanding*moveSpeed*deltaTime * 60;
		rightWeb.x+= (-1)*isExpanding*moveSpeed*deltaTime * 60;
		
		if (leftWeb.overlaps(rightWeb)) isExpanding = - 1;
		
		if (leftWeb.width< 100 * Constants.WIDTH_RATIO) isExpanding = 1;
		
		leftSpider.x = leftWeb.width - leftSpider.width;
		rightSpider.x = rightWeb.x;
		
		animTime+=deltaTime;
	
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(animLeft.getKeyFrame(animTime),leftSpider.x , leftSpider.y , leftSpider.width , leftSpider.height);
		batch.draw(animRight.getKeyFrame(animTime), rightSpider.x, rightSpider.y , rightSpider.width, rightSpider.height);
	}
	
	public Rectangle getLeftSpiderRect () {
		return leftWeb;
	}
	
	public Rectangle getRightSpiderRect () {
		return rightWeb;
	}
	
	public float getanimTime () {
		return animTime;
	}
	
	public void setanimTime (float animTime) {
		this.animTime = animTime;
	}
	
	public boolean getDelay () {
		return hasDelay;
	}
	
	public boolean isRegistered(){
		return registered;
	}
	
	public void register(){
		registered=true;
	}
	
	private void level1 () {
		moveSpeed = 2f * Constants.WIDTH_RATIO;
	}
	
	private void level2 () {
		int d=MathUtils.random(1,3);
		moveSpeed = 4f * Constants.WIDTH_RATIO;
		if (d==1) {
			moveSpeed = 2f * Constants.WIDTH_RATIO;
		} 
		
	}
	
	private void level3 () {
		int d = MathUtils.random(1,6);
		moveSpeed = 6f * Constants.WIDTH_RATIO;
		if(d==1){
			moveSpeed=2f * Constants.WIDTH_RATIO;
		}else if(d<4) moveSpeed = 4f * Constants.WIDTH_RATIO;
	}
	
	private void level4 () {
		int d = MathUtils.random(1, 4);
		
		moveSpeed = 6 * Constants.WIDTH_RATIO;
		
		if(d == 1){
			moveSpeed = 2f * Constants.WIDTH_RATIO;
		}else if(d == 2){
			moveSpeed = 4f * Constants.WIDTH_RATIO;
		}
	}
	
	
	public void Delay(float deltaTime) {
		delay += deltaTime;
		
		if(delay>Constants.DELAY_TIME){
			movePlatform(deltaTime);
		
		}
		
		
	}
	
	public boolean isExpanding(){
		return isExpanding == -1;
	}
	
	public boolean moveOut (float deltaTime) {
		leftWeb.width += - 4 * Constants.WIDTH_RATIO * deltaTime * 60;
		rightWeb.width += - 4 * Constants.WIDTH_RATIO * deltaTime * 60;
		rightWeb.x+= 4 * Constants.WIDTH_RATIO * deltaTime * 60;
		

		leftSpider.x = leftWeb.width - leftSpider.width;
		rightSpider.x= rightWeb.x;
		
		return leftWeb.width == 0;
	}
	
	public Rectangle getRightSpider(){
		return rightSpider;
	}
	
	public void setisExpanding(int i) {
		this.isExpanding = i;
	}
	
}
