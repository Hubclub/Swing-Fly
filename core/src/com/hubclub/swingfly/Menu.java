package com.hubclub.swingfly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
	private final Sound menuSound;
	private boolean isShown, start;
//	private static GameScreen gameScreen;
	
	private Stage stage; //** stage holds the Button **//
	private TextureAtlas buttonsAtlas; //** image of buttons **//
	private boolean menushown;

	private TextButton button;
	protected static Skin buttonSkin; //** images are used as skins of the button **//
	private BitmapFont font;
	
	public Menu(SpriteBatch batch, BitmapFont font){
		this.font = font;
		menuSound = Gdx.audio.newSound(Gdx.files.internal("sound/menutap.mp3"));
		
		stage = new Stage(new ScreenViewport(), batch);
		
		// load empty buttons
		buttonsAtlas = new TextureAtlas("button/button.pack");
		buttonSkin = new Skin();
	    buttonSkin.addRegions(buttonsAtlas);
		
	 //	font = new BitmapFont(Gdx.files.internal("font/LCD_Solid.fnt"));
		
		isShown = false;
		menushown = false;
		
		//Gdx.input.setInputProcessor(stage);
	}
	
	public void initializeRetryButtons () {
		stage.clear();
		MyGame.actionResolver.showAds(true);
		MyGame.game.checkAchievement(MyGame.game.getScore());
		
		addButton("Retry", "button", 25f, 30f, 45f, 10f , new InputListener(){
			 public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            System.out.println( "button pressed" );
	            menuSound.play();
	            return true;
	         }
	         public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	         	System.out.println( "button released" );
	         	hide();
	        	//TO DO: set the inputhandler back...
	        	MyGame.game.getFly().reset();
	        	MyGame.game.getFly().revive();
	        	MyGame.game.resetScore();
	        	resetactions();
	        	MyGame.actionResolver.showAds(false);
	         }
		});
		
		addButton ("Main Menu", "button", 25f, 15f, 45f, 10f, new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println( "button pressed" );
				menuSound.play();
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	         	System.out.println( "button released" );
	         	hide();
	         	MyGame.game.resetScore();
	         	initializeMainMenuButtons();
	        	//TO DO: set the inputhandler back...
	        	resetactions();
	         }
		});
		
		start = false;
		
		
	}
	
	public void initializeMainMenuButtons () {
		stage.clear();
		
		addButton("Start", "button", 20f, 50f, 55f, 10f, new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button pressed");
				menuSound.play();
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button released");
				hide();
	        	MyGame.game.getFly().reset();
	        	MyGame.game.getFly().revive();
				resetactions();
				MyGame.actionResolver.showAds(false);
			}
		});

		
		addButton("Ranks", "button", 15f, 20f, 30f, 7f, new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button pressed");
				menuSound.play();
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button released");
				
				if (MyGame.actionResolver.getSignedInGPGS()) {
					MyGame.actionResolver.getLeaderboardGPGS();
				} else MyGame.actionResolver.loginGPGS();
			}
		});
		
		addButton("Succes", "button", 55f, 20f, 30f, 7f, new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button pressed");
				menuSound.play();
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button released");
				
				if (MyGame.actionResolver.getSignedInGPGS()) {
					MyGame.actionResolver.getAchievementsGPGS();
				} else MyGame.actionResolver.loginGPGS();
			}
		});
		
		addButton("Rate", "button", 30f, 35f, 40f, 7f, new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button pressed");
				menuSound.play();
				return true;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("button released");
				
				MyGame.actionResolver.openAppGPS();
			}
		});
		
		start = true;
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
		style.fontColor = Color.WHITE;
		
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
		menuSound.dispose();
	}
	
	public boolean isStart(){
		return start;
	}
	
}
