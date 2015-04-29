package com.hubclub.swingfly;

import sun.rmi.runtime.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

	
	private ShapeRenderer shape;
	private Fly fly;
	private SpriteBatch batch;
	private Array<Spider> spiders;
	private int noOfSpiders, score, highscore;
	private Spider finalspider;
	private Menu menu;
	private BitmapFont font;
	private Texture spiderstexture, menuspiderstexture, background, topweb, leftweb1,leftweb2, rightweb1, rightweb2;
	private Rectangle leftwebrect1, leftwebrect2;
	private TextureRegion[][] spiderStates, menuSpiderStates;
	private boolean flydeath; // 1 for being eaten by spider , 0 for falling down the screen
	private boolean drawlefttexture1, drawlefttexture2, drawrighttexture1, drawrighttexture2;    // chooses which of the two web textures to draw on the screen for the right and left parts
	private FileHandle file;
	private MenuSpider ms;
	
	public GameScreen () {
		font = new BitmapFont(Gdx.files.internal("font1.fnt"));
		font.setScale(Constants.FONT_SIZE);
		font.setColor(Color.WHITE);
		
		shape = new ShapeRenderer();
		Constants.cam=new OrthographicCamera();
		spiders = new Array<Spider>();
		fly = new Fly();
		leftwebrect1 = new Rectangle(0, 0, 75 * Constants.WIDTH_RATIO, 800 * Constants.HEIGHT_RATIO);
		leftwebrect2 = new Rectangle(0, 800 * Constants.HEIGHT_RATIO, 75 * Constants.WIDTH_RATIO, 800 *Constants.HEIGHT_RATIO);
		
		batch = new SpriteBatch();
		background = new Texture ("background3.png");
		topweb = new Texture("top_web.png");
		leftweb1 = new Texture("left_web1.png");
		leftweb2 = new Texture("left_web2.png");
		rightweb1 = new Texture("right_web1.png");
		rightweb2 = new Texture("right_web2.png");
		spiderstexture = new Texture (Gdx.files.internal("spiders1.png"));
		spiderStates = TextureRegion.split(spiderstexture, spiderstexture.getWidth()/4, spiderstexture.getHeight()/2);
		menuspiderstexture = new Texture(Gdx.files.internal("menu_spiders1.png"));
		menuSpiderStates = TextureRegion.split(menuspiderstexture, menuspiderstexture.getWidth()/4, menuspiderstexture.getHeight()/2);
		menu = new Menu(batch,font);
		
		ms = new MenuSpider(menuSpiderStates);
		
		menu.initializeMainMenuButtons();
		menu.show();
		
		reset();
		resetScore();
		checkAchievement(highscore);
	}
	
	public void reset () {
		noOfSpiders=0;
		spiders.clear();
		leftwebrect1.set(0, 0, 75 * Constants.WIDTH_RATIO, 800 * Constants.HEIGHT_RATIO);
		leftwebrect2.set(0, 800 * Constants.HEIGHT_RATIO, 75 * Constants.WIDTH_RATIO, 800 *Constants.HEIGHT_RATIO);
		
		initArray();
		
		Constants.cam.viewportWidth=Gdx.graphics.getWidth();
		Constants.cam.viewportHeight=Gdx.graphics.getHeight();
		Constants.cam.position.set(Constants.cam.viewportWidth/2,Constants.cam.viewportHeight/2,0);
	}
	
	private void initArray () {
		Spider spid;
		
		for (int i=0; i<6; i++) {
			spid=new Spider(spiderStates);
			spid.set(0,noOfSpiders);
			noOfSpiders++;
			spiders.add(spid);
		}
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0,0,0,0);
		batch.setProjectionMatrix(Constants.cam.combined);
		
		batch.begin();
		batch.draw(background,
				leftwebrect1.x, 
				leftwebrect1.y, 
				480 * Constants.WIDTH_RATIO, 
				800 * Constants.HEIGHT_RATIO );
		
		batch.draw(background,
				leftwebrect2.x,
				leftwebrect2.y,
				480 * Constants.WIDTH_RATIO,
				800 * Constants.HEIGHT_RATIO);
		
		batch.end();
		
		if (!fly.isAlive()) {
			if (flydeath) {
			if (finalspider.getLeftSpiderRect().width < - fly.getFlyRectangle().width ) {
				if (!menu.getMenushwon()) {
					menu.show();
					menu.setMenushown(true);
				}
				reset();
			}
			else {
				handleSpiders(delta); 
			}
			} else {
				if (!menu.getMenushwon()) {
					menu.show();
					menu.setMenushown(true);
				}
				reset();
			}
		}
		else handleSpiders(delta);
		
		if (leftwebrect1.y + leftwebrect1.height < Constants.cam.position.y - Constants.cam.viewportHeight/2) {
			leftwebrect1.y = leftwebrect2.y + leftwebrect2.height;
			drawlefttexture1 = MathUtils.randomBoolean();
			drawrighttexture1 = MathUtils.randomBoolean();
		}
		if (leftwebrect2.y + leftwebrect2.height < Constants.cam.position.y - Constants.cam.viewportHeight/2) {
			leftwebrect2.y = leftwebrect1.y + leftwebrect1.height;
			drawlefttexture2 = MathUtils.randomBoolean();
			drawrighttexture2 = MathUtils.randomBoolean();
		}
		
		if(menu.isShown()) {
			shape.setProjectionMatrix(Constants.cam.combined);
			shape.begin(ShapeType.Filled);
			shape.rect(ms.getRightSpiderRect().x, ms.getRightSpiderRect().y, ms.getRightSpiderRect().width, ms.getRightSpiderRect().height);
			shape.end();
		}
		
		batch.begin();
		
		drawSideTextures();

		for(Spider sp : spiders){
			sp.draw(batch);
		}
		
		batch.draw(topweb, 
				Constants.cam.position.x - Constants.cam.viewportWidth/2, 
				Constants.cam.position.y + Constants.cam.viewportHeight/2 - 100 * Constants.HEIGHT_RATIO,
				480 * Constants.WIDTH_RATIO,
				100 * Constants.HEIGHT_RATIO);
		
		drawText(delta);
		
		if (fly.isAlive() ){
			if(!menu.isShown()){
				fly.draw(batch);
				fly.update(delta);
			}
		}
		else if (flydeath) fly.split(finalspider.getLeftSpiderRect(), finalspider.getRightSpiderRect(), batch);
		
		batch.end();
		
		menu.drawButtons();
		
		Constants.cam.update();
		
	}
	
	private void handleSpiders(float deltaTime){
		shape.setProjectionMatrix(Constants.cam.combined);
		shape.begin(ShapeType.Filled);
		
		shape.setColor(Color.WHITE);
		
		for (Spider sp : spiders){
			
			shape.rect(sp.getLeftSpiderRect().x,sp.getLeftSpiderRect().y,sp.getLeftSpiderRect().width - Constants.COZMA,sp.getLeftSpiderRect().height);
			shape.rect(sp.getRightSpiderRect().x + Constants.COZMA,sp.getRightSpiderRect().y,sp.getRightSpiderRect().width,sp.getRightSpiderRect().height);
			
			if (fly.isAlive()){
				if (sp.getDelay()==true){
					if (sp.getLeftSpiderRect().y < Constants.cam.position.y + 400 * Constants.HEIGHT_RATIO){
						sp.Delay(deltaTime);
					}
				
				}
				else sp.movePlatform(deltaTime);
				
			
				if (sp.getLeftSpiderRect().y < Constants.cam.position.y - 400* Constants.HEIGHT_RATIO) {
					spiders.get(spiders.indexOf(sp, true)).set(score,noOfSpiders++);
				
				}
			
				if(Constants.cam.position.y > sp.getLeftSpiderRect().y && !sp.isRegistered()){
					sp.register();
					score++;
				}
			
				collision(sp);
			}else
				sp.moveOut(deltaTime);
			
		}
		shape.end();
	}
	
	private void collision (Spider sp) {
		if (fly.getFlyRectangle().overlaps(sp.getLeftSpiderRect())) {
			flydeath = true;
			fly.die();
			menu.initializeRetryButtons();
			finalspider=sp;
		}
		else {
			if (fly.getFlyRectangle().y< Constants.cam.position.y - Gdx.graphics.getHeight()/2) {
				flydeath = false;
				fly.die();
				menu.initializeRetryButtons();
			}
		}
	}
	
	private void drawSideTextures () {
		
		if (drawlefttexture1) {
			batch.draw(leftweb1, 
					leftwebrect1.x,
					leftwebrect1.y,
					leftwebrect1.width,
					leftwebrect1.height);
		} 
		else {
			batch.draw(leftweb2, 
					leftwebrect1.x,
					leftwebrect1.y,
					leftwebrect1.width,
					leftwebrect1.height);
		}
		
		if (drawlefttexture2) {
			batch.draw(leftweb1,
					leftwebrect2.x,
					leftwebrect2.y,
					leftwebrect2.width,
					leftwebrect2.height);
		}
		else {
			batch.draw(leftweb2,
					leftwebrect2.x,
					leftwebrect2.y,
					leftwebrect2.width,
					leftwebrect2.height);
		}
		
		if(drawrighttexture1) {
			batch.draw(rightweb1,
					396 * Constants.WIDTH_RATIO,
					leftwebrect1.y,
					84 * Constants.WIDTH_RATIO,
					leftwebrect1.height);
		}
		else {
			batch.draw(rightweb2,
					396 * Constants.WIDTH_RATIO,
					leftwebrect1.y,
					84 * Constants.WIDTH_RATIO,
					leftwebrect1.height);
		}
		
		if (drawrighttexture2) {
			batch.draw(rightweb1,
					396 * Constants.WIDTH_RATIO,
					leftwebrect2.y,
					84 * Constants.WIDTH_RATIO,
					leftwebrect2.height);
		}
		else {
			batch.draw(rightweb2,
					396 * Constants.WIDTH_RATIO,
					leftwebrect2.y,
					84 * Constants.WIDTH_RATIO,
					leftwebrect2.height);
		}
	}
	
	private void drawText (float deltaTime) {
		
		ms.movePlatform(deltaTime);
		if (menu.isShown()) {
			ms.draw(batch);
			font.setScale(1.5f * Constants.FONT_SIZE);
			if (menu.isStart()) {
				font.draw(batch, "Fly Escape",125 * Constants.WIDTH_RATIO, 650 * Constants.WIDTH_RATIO);
				font.setScale(Constants.FONT_SIZE);
				}
			else {
				font.draw(batch, "Game over",125 * Constants.WIDTH_RATIO, 650 * Constants.WIDTH_RATIO);
				font.setScale(Constants.FONT_SIZE);
				if (score > highscore) {
					file.writeString(Integer.toString(score ),false);
					font.draw(batch, "New highscore: " + score, 125 * Constants.WIDTH_RATIO, 500 * Constants.HEIGHT_RATIO);
				}
				else {
					font.draw(batch, "Score: " + score, 150 * Constants.WIDTH_RATIO, 500 * Constants.HEIGHT_RATIO);
					font.draw(batch, "Highscore: " + highscore, 150 * Constants.WIDTH_RATIO, 450 * Constants.HEIGHT_RATIO);
				}
			}
		}
		else {
			font.setScale(1.5f * Constants.FONT_SIZE);
			font.draw(batch, ""+score, Constants.cam.position.x - 10 * Constants.WIDTH_RATIO , Constants.cam.position.y + 250 * Constants.HEIGHT_RATIO);
			font.setScale(Constants.FONT_SIZE);
		}
	}
	
	public Fly getFly () {
		return fly;
	}
	
	public BitmapFont getFont () {
		return font;
	}
	
	public int getScore () {
		return score;
	}
	
	public void checkAchievement (int score) {
		if (MyGame.actionResolver.getSignedInGPGS()) {
			
			MyGame.actionResolver.submitScoreGPGS(score);
			
			if (highscore == 0) MyGame.actionResolver.unlockAchievementGPGS("CgkIj9LRirIbEAIQAQ");
			if (score>=10) MyGame.actionResolver.unlockAchievementGPGS("CgkIj9LRirIbEAIQAg");
			if (score>=20) MyGame.actionResolver.unlockAchievementGPGS("CgkIj9LRirIbEAIQAw");
			if (score>=35) MyGame.actionResolver.unlockAchievementGPGS("CgkIj9LRirIbEAIQBA");
			if (score>=55) MyGame.actionResolver.unlockAchievementGPGS("CgkIj9LRirIbEAIQBQ");
		}
	}
	
	public void resetScore () {
		score = 0;
		
		file = Gdx.files.local("savefile/highscore.txt");
		if (file.exists()) {
			highscore = Integer.valueOf(file.readString());
			
			if (MyGame.actionResolver.getSignedInGPGS()) MyGame.actionResolver.submitScoreGPGS(highscore);
			
		}
		else {
			highscore = 0;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		background.dispose();
		fly.dispose();
		batch.dispose();
		menu.dispose();
		font.dispose();
		spiderstexture.dispose();
		menuspiderstexture.dispose();
		leftweb1.dispose();
		topweb.dispose();
	}
	

}
