package com.hubclub.swingfly;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MyGame extends Game {
	public static GameScreen game;
	public static ActionResolver actionResolver;
	
	public MyGame(ActionResolver actionResolver){
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void create () {
		game = new GameScreen();
		this.setScreen(new LoadingScreen(this));
	}
	
	public void render(){
		super.render();
	}
	
}
