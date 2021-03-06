package com.asdf.ssjava.screens;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.BackButton;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Pause menu allowing the player to temporarily stop playing the current level. 
 * Allows to continue playing the level, go to the options menu, and exit to the main menu.  
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class PauseMenu implements Screen {

	/**
	 * The game instance
	 */
	SSJava game;
	
	/**
	 * The stage instance
	 */
	Stage stage;	
	
	/**
	 * The text font
	 */
	BitmapFont whiteFont;
	
	/**
	 * The buttons
	 */
	MenuButton backButton, optionsButton, exitButton;
	
	/**
	 * The title label
	 */
	Label titleLabel;
	
	/**
	 * Background images
	 */
	Image bgImage, opacityImage;
	
	/**
	 * A reference to this menu object to pass to the anonymous listener classes
	 */
	Screen thisMenu = this;

	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * Creates a pause menu with the specified parameters
	 * @param game the SSJava instance
	 * @param referrer the referring screen
	 */
	public PauseMenu(SSJava game, Screen referrer) {
		this.game = game;
		this.referrer = referrer;
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#render(float)
	 */
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		if (stage == null) {
			stage = new Stage(width, height, true) {
				/*
				 * (non-Javadoc)
				 * @see com.badlogic.gdx.scenes.scene2d.Stage#keyDown(int)
				 */
				@Override
		        public boolean keyDown(int keyCode) {
		            if (keyCode == Keys.ESCAPE) {
		                game.setScreen(referrer);
		            }
		            return super.keyDown(keyCode);
		        }
			};
		}

		stage.setViewport(width, height);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		// Return to game button
		backButton = new BackButton(280, 65, game, referrer);
		backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 + 50);
		
		// Options button
		optionsButton = new MenuButton("Options", 280, 65, game);
		optionsButton.setX(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2);
		optionsButton.setY(Gdx.graphics.getHeight() / 2 - optionsButton.getHeight() / 2 - 50);
		optionsButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Options button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				game.setScreen(new OptionsMenu(game, thisMenu));
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Options button up");
			}
		});
		
		// Exit to main menu button
		exitButton = new MenuButton("Exit", 280, 65, game);
		exitButton.setX(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2);
		exitButton.setY(Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - 150);
		exitButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Exit button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Exit button up");
				// TODO potential bug??
				AudioPlayer.stopGameMusic();
				AudioPlayer.stopGameSounds();
				if (((GameScreen) referrer).getGameWorld().getCreator() != null) {
					game.setScreen(game.gameScreen.gameWorld.getCreator());
				}
				else {					
					game.setScreen(new MainMenu(game));
				}
			}
		});
		
		// Title
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("Paused", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		// Background
		if (game.screenshot != null) {
			bgImage = new Image(game.screenshot);
			opacityImage = new Image(SSJava.assetManager.get("data/textures/backgrounds/black_50-opacity.png", Texture.class));
			opacityImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			stage.addActor(bgImage);
			stage.addActor(opacityImage);
		}
				
		stage.addActor(titleLabel);
		stage.addActor(backButton);
		stage.addActor(optionsButton);
		stage.addActor(exitButton);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show pause menu");
		whiteFont = SSJava.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);				
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override
	public void hide() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#pause()
	 */
	@Override
	public void pause() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#resume()
	 */
	@Override
	public void resume() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#dispose()
	 */
	@Override
	public void dispose() {
		
	}
}
