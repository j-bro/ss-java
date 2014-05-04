/**
 * 
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
 * @author Jeremy Brown
 * 
 */
public class LevelRetryMenu implements Screen {

	/**
	 * The game instance
	 */
	SSJava game;
	
	/**
	 * The stage instance
	 */
	Stage stage;	
	
	/**
	 * 
	 */
	BitmapFont whiteFont;
	
	/**
	 * 
	 */
	MenuButton exitButton, retryButton, selectLevelButton;
	
	/**
	 * Display labels
	 */
	Label titleLabel, scoreLabel;
	
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
	 * The game world instance
	 */
	GameWorld gameWorld;
	
	/**
	 * 
	 */
	public LevelRetryMenu(SSJava game, Screen referrer) {
		this.game = game;
		this.referrer = referrer;
		gameWorld = (GameWorld) ((GameScreen) referrer).getGameWorld();
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
			stage = new Stage(width, height, true);
		}
		stage.setViewport(width, height);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		// Score display
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		scoreLabel = new Label("Score: " + gameWorld.getScoreKeeper().getScore(), ls);
		scoreLabel.setX(width / 2 - scoreLabel.getWidth() / 2);
		scoreLabel.setY(height / 2 + 180);
				
		// Replay the level
		retryButton = new MenuButton("Replay", 280, 65, game);
		retryButton.setX(Gdx.graphics.getWidth() / 2 - retryButton.getWidth() / 2);
		retryButton.setY(Gdx.graphics.getHeight() / 2 - retryButton.getHeight() / 2 - 50);
		retryButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Replay button down");
				return true;
			}
		
			/*
			 * (non-Javadoc)
 			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Replay button up");
				FileHandle levelFile = ((GameScreen) referrer).gameWorld.getLevelFile();
				game.gameScreen = new GameScreen(game, levelFile);
				game.setScreen(game.gameScreen);
			}
		});
		
		// Go to level selection screen
		selectLevelButton = new MenuButton("Select Level", 280, 65, game);
		selectLevelButton.setX(Gdx.graphics.getWidth() / 2 - selectLevelButton.getWidth() / 2);
		selectLevelButton.setY(Gdx.graphics.getHeight() / 2 - selectLevelButton.getHeight() / 2 - 150);
		selectLevelButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Select level button down");
				return true;
			}
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Select level button up");
				game.setScreen(new LevelSelectMenu(game, new MainMenu(game)));
			}
		});
		
		// exit to main menu button
		exitButton = new MenuButton("Exit", 280, 65, game);
		exitButton.setX(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2);
		exitButton.setY(Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - 250);
		exitButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Exit button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Exit button up");
				// TODO potential bug??
				if (((GameScreen) referrer).getGameWorld().getCreator() != null) {
					game.setScreen(game.gameScreen.gameWorld.getCreator());
				}
				else {					
					game.setScreen(new MainMenu(game));
				}
			}
		});
		
		titleLabel = new Label("You died!", ls);
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
		
		stage.addActor(scoreLabel);
		stage.addActor(retryButton);
		stage.addActor(selectLevelButton);
		stage.addActor(exitButton);
		stage.addActor(titleLabel);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show level retry menu");
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
