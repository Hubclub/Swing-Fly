package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoadingScreen implements Screen {
	private Stage stage;
	private Texture loadpic;
	private Image img;
	private MyGame myGame;
	
	public LoadingScreen (MyGame myGame) {
		
		this.myGame=myGame;
		stage = new Stage(new ScreenViewport());
		loadpic = new Texture ("Loadingpic.png");
		img = new Image(loadpic);
		img.setSize(480 * Constants.WIDTH_RATIO, 800 * Constants.HEIGHT_RATIO);
		
		stage.addActor(img);
		stage.addAction(Actions.fadeOut(1.8f));
		
		if (!MyGame.actionResolver.getSignedInGPGS()) {
			MyGame.actionResolver.loginGPGS();
		}
		set();
		
	}
	
	private void set(){
		Timer.schedule(new Task () {
			public void run () {
				myGame.setScreen(MyGame.game);
				dispose();
				MyGame.actionResolver.showAds(true);
			}
		}, 2);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0,0,0,0);
		
		stage.act();
		stage.draw();
		
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
		loadpic.dispose();
		stage.dispose();
	}

}
