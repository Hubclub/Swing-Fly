package com.hubclub.swingfly;

import com.badlogic.gdx.Game;

public class MyGame extends Game {
	
	GameScreen game;
	@Override
	public void create () {
		game = new GameScreen();
		this.setScreen(game);
	}
	
	public void render(){
		super.render();
	}
}
