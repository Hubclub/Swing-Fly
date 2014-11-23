package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

	
	private ShapeRenderer shape;
	private Fly fly;
	private SpriteBatch batch;
	private Array<Spider> spiders;
	private int noOfSpiders, score;
	private Spider finalspider;
	private Menu menu;
	private Texture background;
	private BitmapFont font;
	private Texture spiderstexture;
	private TextureRegion[][] spiderStates;
	private boolean flydeath; // 1 for being eaten by spider , 0 for falling down the screen
	
	
	public GameScreen () {
		font = new BitmapFont(Gdx.files.internal("gotham.fnt"));
		font.setScale(Constants.HEIGHT_RATIO);
		font.setColor(Color.BLACK);
		
		shape = new ShapeRenderer();
		Constants.cam=new OrthographicCamera();
		spiders = new Array<Spider>();
		fly = new Fly();
		batch = new SpriteBatch();
		background = new Texture ("background.jpg");
		
		spiderstexture = new Texture (Gdx.files.internal("spiders.png"));
		spiderStates = TextureRegion.split(spiderstexture, spiderstexture.getWidth()/2, spiderstexture.getHeight()/2);
		
		menu = new Menu(batch,font);
		
		menu.initializeMainMenuButtons();
		menu.show();
	
		reset();
	}
	
	public void reset () {
		noOfSpiders=0;
		spiders.clear();
		
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
		batch.draw(background,Constants.cam.position.x - Constants.cam.viewportWidth/2, Constants.cam.position.y - Constants.cam.viewportHeight/2, 480 * Constants.WIDTH_RATIO, 800 * Constants.HEIGHT_RATIO);
		batch.end();
		
		//System.out.println(alive);
		
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
		
		batch.begin();
		
		if (fly.isAlive() ){
			if(!menu.isShown()){
				fly.draw(batch);
				fly.update(delta);
			}
		}
		else if (flydeath) fly.split(finalspider.getLeftSpiderRect(), finalspider.getRightSpiderRect(), batch);

		for(Spider sp : spiders){
			sp.draw(batch);
		}
		
		batch.end();
		
	
		menu.drawButtons();
		
		Constants.cam.update();
		
	}
	
	private void handleSpiders(float deltaTime){
		shape.setProjectionMatrix(Constants.cam.combined);
		shape.begin(ShapeType.Filled);
		
		shape.setColor(Color.BLACK);
		
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
		//System.out.println(score);
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
	
	public Fly getFly () {
		return fly;
	}
	
	public BitmapFont getFont () {
		return font;
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
	}
	

}
