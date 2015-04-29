package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class MenuSpider extends Spider {
	
	private float height;
	
	public MenuSpider(TextureRegion[][] spiderStates) {
		super(spiderStates);
		height = MathUtils.random((float) 1/3 * Gdx.graphics.getHeight(), (float) 2/3 * Gdx.graphics.getHeight());
		super.getRightSpiderRect().set (MathUtils.random(100 * Constants.WIDTH_RATIO, 380 * Constants.WIDTH_RATIO),800 * Constants.HEIGHT_RATIO, Constants.WEB_HEIGHT, 0);
		super.getRightSpider().set(super.getRightSpiderRect().x - Constants.SPIDER_WIDTH/2 , super.getRightSpiderRect().y - Constants.SPIDER_HEIGHT, Constants.SPIDER_WIDTH, Constants.SPIDER_HEIGHT);
		
	}
	
	public void movePlatform (float deltaTime) {
		
		if (!isExpanding()) {
		super.getRightSpiderRect().height += 2f * Constants.WIDTH_RATIO;
		super.getRightSpiderRect().y -= 2f * Constants.WIDTH_RATIO;
		}
		else {
			super.getRightSpiderRect().height -= 2f * Constants.WIDTH_RATIO;
			super.getRightSpiderRect().y += 2f * Constants.WIDTH_RATIO;
		}
		super.getRightSpider().y = super.getRightSpiderRect().y - Constants.COZMA;
		
		if (getRightSpiderRect().y < height) {
			super.setisExpanding(-1);
		}
		
		if (getRightSpiderRect().height <= 0) {
			height = MathUtils.random((float) 1/3 * Gdx.graphics.getHeight(), (float) 2/3 * Gdx.graphics.getHeight());
			super.getRightSpiderRect().x = MathUtils.random(100 * Constants.WIDTH_RATIO, 380 * Constants.WIDTH_RATIO);
			super.getRightSpider().x = super.getRightSpiderRect().x - Constants.SPIDER_WIDTH/2;
			super.setisExpanding(1);
		}
		
		setanimTime(getanimTime() + deltaTime);
		
	}
	
	/* public void draw (SpriteBatch batch) {
		batch.draw(menuspider, getRightSpider().x, getRightSpider().y, getRightSpider().width, getRightSpider().height);
	} 
	*/


}
