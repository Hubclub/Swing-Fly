package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class GameScreen implements Screen {

	
	private ShapeRenderer shape;
	private Fly fly;
	private SpriteBatch batch;
	private Array<Spider> spiders;
	private int noOfSpiders, score;
	private boolean alive;
	private Spider finalspider;
	
	
	public GameScreen () {
		shape=new ShapeRenderer();
		alive = true;
		noOfSpiders=0;
		
		Constants.cam=new OrthographicCamera();
		Constants.cam.viewportWidth=Gdx.graphics.getWidth();
		Constants.cam.viewportHeight=Gdx.graphics.getHeight();
		Constants.cam.position.set(Constants.cam.viewportWidth/2,Constants.cam.viewportHeight/2,0);
		
		initArray();
		fly = new Fly();
		batch = new SpriteBatch();
	}
	
	private void initArray () {
		Spider spid;
		
		spiders = new Array<Spider>();
		
		for (int i=0; i<6; i++) {
			spid=new Spider();
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
		
		handleSpiders();
		
		batch.begin();
		
		if (alive){
			fly.draw(batch);
			fly.update(delta);
			
		}
		else finishGame();
		
		batch.end();
		
		Constants.cam.update();
		
	}
	
	private void finishGame () {
		fly.split(finalspider.getLeftSpiderRect(), finalspider.getRightSpiderRect(), batch);
		
		
		
			
		
	}
	
	private void handleSpiders(){
		shape.setProjectionMatrix(Constants.cam.combined);
		shape.begin(ShapeType.Filled);
		shape.setColor(Color.RED);
		
		for (Spider sp : spiders){
			
			if(sp.getDelay())
				shape.setColor(Color.MAGENTA);
			else shape.setColor(Color.RED);
			
			shape.rect(sp.getLeftSpiderRect().x,sp.getLeftSpiderRect().y,sp.getLeftSpiderRect().width,sp.getLeftSpiderRect().height);
			shape.rect(sp.getRightSpiderRect().x,sp.getRightSpiderRect().y,sp.getRightSpiderRect().width,sp.getRightSpiderRect().height);
			
			if (alive){
				if (sp.getDelay()==true){
					if (sp.getLeftSpiderRect().y < Constants.cam.position.y + 400 * Constants.HEIGHT_RATIO){
						sp.Delay();
					}
				
				}
				else sp.movePlatform();
			
				if (sp.getLeftSpiderRect().y < Constants.cam.position.y - 400* Constants.HEIGHT_RATIO) {
					spiders.get(spiders.indexOf(sp, true)).set(score,noOfSpiders++);
				
				}
			
				if(Constants.cam.position.y > sp.getLeftSpiderRect().y && !sp.isRegistered()){
					sp.register();
					score++;
				}
			
				collision(sp);
			}else
				sp.moveOut();
			
		}
		
		
		
		System.out.println(score);
		
		shape.end();
	}
	
	private void collision (Spider sp) {
		if (fly.getFlyRectangle().overlaps(sp.getLeftSpiderRect())) {
			alive = false;
			finalspider=sp;
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
		
	}

}
