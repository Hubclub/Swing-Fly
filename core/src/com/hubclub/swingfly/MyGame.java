package com.hubclub.swingfly;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MyGame extends Game {
	
	public static GameScreen game;
	@Override
	public void create () {
		game = new GameScreen();
		this.setScreen(game);
	}
	
	public void render(){
		super.render();
	}
}
