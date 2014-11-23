package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Menu {
	private boolean isShown;
//	private static GameScreen gameScreen;
	
	private Stage stage; //** stage holds the Button **//
	private TextureAtlas buttonsAtlas; //** image of buttons **//
	private boolean menushown;

	private TextButton button;
	protected static Skin buttonSkin; //** images are used as skins of the button **//
	private BitmapFont font;
	
	public Menu(SpriteBatch batch, BitmapFont font){
		this.font = font;
		stage = new Stage(new ScreenViewport(), batch);
		
		// load empty buttons
		buttonsAtlas = new TextureAtlas("button/Buttons.pack");
		buttonSkin = new Skin();
	    buttonSkin.addRegions(buttonsAtlas);
		
	 //	font = new BitmapFont(Gdx.files.internal("font/LCD_Solid.fnt"));
		
		isShown = false;
		menushown = false;
		
		//Gdx.input.setInputProcessor(stage);
	}
	
	public void initializeRetryButtons () {
		stage.clear();
		
		addButton("RETRY", "64X32", 40f, 15f, 30f, 10f , new InputListener(){
			 public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            System.out.println( "button pressed" );
	            return true;
	         }
	         public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	         	System.out.println( "button released" );
	         	hide();
	        	//TO DO: set the inputhandler back...
	        	MyGame.game.getFly().reset();
	        	MyGame.game.getFly().revive();
	        	resetactions();
	         }
		});
		
		addButton ("Main Menu", "64X32", 40f, 65f, 30f, 10f, new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button pressed" );
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	         	System.out.println( "button released" );
	         	hide();
	         	initializeMainMenuButtons();
	        	//TO DO: set the inputhandler back...
	        	resetactions();
	         }
		});
	}
	
	public void initializeMainMenuButtons () {
		stage.clear();
		
		addButton("START", "64X32", 40f, 15f, 30f, 10f, new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button pressed");
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button released");
				hide();
	        	MyGame.game.getFly().reset();
	        	MyGame.game.getFly().revive();
				resetactions();
			}
		});
	}
	
	public void drawButtons (){
		//System.out.println(isShown);
		if (isShown){
			stage.act();
			stage.draw();
		}
	}
	
	public void addButton (String name,String path, float x, float y, float width, float height, InputListener inpl ) {
		TextButtonStyle style = new TextButtonStyle(); //** Button properties **//
		style.up = buttonSkin.getDrawable(path);
		style.down = buttonSkin.getDrawable(path + "pressed");
		style.font = font;
		style.pressedOffsetX = 1;
		style.pressedOffsetY = -1;
		style.fontColor = Color.BLACK;
		
		button = new TextButton(name, style);
		button.setPosition(x/100 * Gdx.graphics.getWidth() , y/100 * Gdx.graphics.getHeight() ); //** Button location **//
		button.setWidth(width/100 * Gdx.graphics.getWidth());
		button.setHeight(height/100 * Gdx.graphics.getHeight());
		button.addListener(inpl);
		button.addAction(Actions.sequence(Actions.fadeOut(0.0001f), Actions.fadeIn(.5f)));
		//stage.addAction(Actions.sequence(Actions.fadeOut(0.0001f), Actions.fadeIn(2f)));
		
		stage.addActor(button);
	}
	
	public void resetactions () {
    	for (int i=0; i<stage.getActors().size; i++) {
    		stage.getActors().get(i).removeAction(Actions.sequence(Actions.fadeOut(0.0001f), Actions.fadeIn(.5f)));
    		stage.getActors().get(i).addAction(Actions.sequence(Actions.fadeOut(0.0001f), Actions.fadeIn(.5f)));
    	}
	}
	
	public void hide (){
		menushown = false;
		isShown = false;
		Gdx.input.setInputProcessor(null);
	}
	public void show(){
		isShown = true;
		
		// each time we show it we change the input processor
		Timer.schedule(new Task () {
			public void run () {
				Gdx.input.setInputProcessor(stage);
			}
		}, .5f); 
	}
	
	public boolean isShown(){
		return isShown;
	}
	
	public boolean getMenushwon() {
		return menushown;
	}
	
	public void setMenushown (boolean menushown) {
		this.menushown = menushown;
	}
	
	public void dispose(){
		stage.dispose();
		buttonsAtlas.dispose();
		buttonSkin.dispose();
	}
	
}
