package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Spider {
	
	private Rectangle leftSpider, rightSpider;
	private float moveSpeed;
	private int score, level, isExpanding;
	private boolean hasDelay,registered;
	
	public Spider () {
		leftSpider = new Rectangle();
		rightSpider = new Rectangle();
	}
	
	public void set(int score,int i){
		this.score = score;
		isExpanding = 1;
		registered=false;
		hasDelay=false;
		
		if (score<=3) level = 1;
		else if (score >3 && score <=7) level = 2;
		else if (score>7 && score <=10) level = 3;
		
		switch (level) {
		case 1 : level1(); break;
		case 2 : level2(); break;
		case 3 : level3(); break;
		case 4 : level4(); break;
		}

		float x = MathUtils.random(100 * Constants.WIDTH_RATIO, 240 * Constants.WIDTH_RATIO);
		leftSpider.set(0,0,x,10);
		rightSpider.set(Gdx.graphics.getWidth() - x ,0,x,10);
		
		leftSpider.y = 800*Constants.HEIGHT_RATIO + i * Constants.DISTANCE_PLATFORM;
		rightSpider.y = 800*Constants.HEIGHT_RATIO + i * Constants.DISTANCE_PLATFORM;
		
		if (MathUtils.random(1, 5) == 1 ) {
			hasDelay = true;
			
			leftSpider.width=240*Constants.WIDTH_RATIO;
			rightSpider.width=rightSpider.x=240*Constants.WIDTH_RATIO;
			
		}else  hasDelay=false;
		
		if (hasDelay) {
			leftSpider.width=240*Constants.WIDTH_RATIO;
			rightSpider.width=rightSpider.x=240*Constants.WIDTH_RATIO;
		}
		
		
	}
	
	public void movePlatform () {
		leftSpider.width += isExpanding*moveSpeed;
		rightSpider.width +=isExpanding*moveSpeed;
		rightSpider.x+= (-1)*isExpanding*moveSpeed;
		
		if (leftSpider.width > rightSpider.x) isExpanding = -isExpanding;
		
		if (leftSpider.width< 100 * Constants.WIDTH_RATIO) isExpanding = - isExpanding;
		
		
	}
	
	public Rectangle getLeftSpiderRect () {
		return leftSpider;
	}
	
	public Rectangle getRightSpiderRect () {
		return rightSpider;
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
		moveSpeed = 2f;
	}
	
	private void level2 () {
		int d=MathUtils.random(1,3);
		moveSpeed = 4f;
		if (d==1) {
			moveSpeed = 2f;
		} 
		
	}
	
	private void level3 () {
		int d = MathUtils.random(1,6);
		moveSpeed = 6f;
		if(d==1){
			moveSpeed=2f;
		}else if(d<4) moveSpeed = 4f;
	}
	
	private void level4 () {
		
	}
	
	
	public void Delay() {
		Timer.schedule(new Task () {
			public void run() {
				movePlatform();
			}
		}, Constants.DELAY_TIME);
		
	}
	
	public boolean isExpanding(){
		return isExpanding == -1;
	}
	
	public boolean moveOut () {
		leftSpider.width += -moveSpeed;
		rightSpider.width += -moveSpeed;
		rightSpider.x+= moveSpeed;
		
		return leftSpider.width == 0;
	}
	
	
}
