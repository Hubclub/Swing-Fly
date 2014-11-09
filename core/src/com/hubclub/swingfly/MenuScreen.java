package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MenuScreen {
	
	private TextButton button;
	private Stage stage;
	private Skin skin;
	
	public MenuScreen(){
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();
		
		button = new TextButton("click me",skin,"default");
		
		button.setWidth(110);
		button.setHeight(20);
		button.setPosition(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/2);
	}

}
