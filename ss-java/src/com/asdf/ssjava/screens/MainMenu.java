package com.asdf.ssjava.screens;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.badlogic.gdx.Gdx;
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
 * Main menu of the game.  
 * Allows the player to play the game, change the options, start the level creator, view the high scores, view the credits, and exit to the desktop. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class MainMenu implements Screen {
	
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
	 * The text labels
	 */
	Label titleLabel, creditsLabel;
	
	/**
	 * The buttons
	 */
	MenuButton playButton, optionsButton, highScoresButton, creditsButton, exitButton, creatorButton;
	
	/**
	 * The background image
	 */
	Image bgImage;
	
	/**
	 * Concrete reference to this menu
	 */
	Screen thisMainMenu = this;
	
	/**
	 * Creates a main menu with the specified parameters. 
	 * @param game The game instance of type SSJava
	 */
	public MainMenu(SSJava game) {
		this.game = game;
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
				
		// Play button
		playButton = new MenuButton("Play", 280, 65, game);		
		playButton.setX(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2);
		playButton.setY(Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2 + 140);
		playButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Play button up");
				game.setScreen(new LevelSelectMenu(game, thisMainMenu));
			}
		});
		
		// Options button
		optionsButton = new MenuButton("Options", 280, 65, game);
		optionsButton.setX(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2);
		optionsButton.setY(Gdx.graphics.getHeight() / 2 - optionsButton.getHeight() / 2 + 60);
		optionsButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Options button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Options button up");
				game.setScreen(new OptionsMenu(game, thisMainMenu));
			}
		});
		
		// High scores button
		highScoresButton = new MenuButton("High Scores", 280, 65, game);
		highScoresButton.setX(Gdx.graphics.getWidth() / 2 - highScoresButton.getWidth() / 2);
		highScoresButton.setY(Gdx.graphics.getHeight() / 2 - highScoresButton.getHeight() / 2 - 20);
		highScoresButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "High scores button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "High scores button up");
				game.setScreen(new HighScoresMenu(game, thisMainMenu));
			}
		});
		
		// Level creator button
		creatorButton = new MenuButton("Level creator", 280, 65, game);
		creatorButton.setX(Gdx.graphics.getWidth() / 2 - creatorButton.getWidth() / 2);
		creatorButton.setY(Gdx.graphics.getHeight() / 2 - creatorButton.getHeight() / 2 - 100);
		creatorButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Level creator button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Level creator button up");
				game.setScreen(new LevelCreatorScreen(game, null));
			}
		});
		
		// Credits button
		creditsButton = new MenuButton("Credits", 280, 65, game);
		creditsButton.setX(Gdx.graphics.getWidth() / 2 - creditsButton.getWidth() / 2);
		creditsButton.setY(Gdx.graphics.getHeight() / 2 - creditsButton.getHeight() / 2 - 180);
		creditsButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Credits button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Credits button up");
				game.setScreen(new CreditsMenu(game, thisMainMenu));
			}
		});
		
		// Exit button
		exitButton = new MenuButton("Exit", 280, 65, game);
		exitButton.setX(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2);
		exitButton.setY(Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - 260);
		exitButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Exit button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Exit button up");
				Gdx.app.exit();
			}
		});
		
		
		// Title text
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("SS-Java", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		// Bottom right credits text
		creditsLabel = new Label("(C) 2014 ASDF", ls);
		creditsLabel.setX(width - creditsLabel.getWidth() - 10);
		creditsLabel.setY(10);
		
		// Background image
		bgImage = new Image(SSJava.assetManager.get("data/textures/backgrounds/menu_bg.png", Texture.class));
		bgImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(bgImage);
		
		stage.addActor(titleLabel);	
		stage.addActor(creditsLabel);
		stage.addActor(playButton);
		stage.addActor(optionsButton);
		stage.addActor(highScoresButton);
		stage.addActor(creatorButton);
		stage.addActor(creditsButton);
		stage.addActor(exitButton);
	}
 
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show main menu");
		whiteFont = SSJava.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
		
		if (AudioPlayer.gameMusic.isPlaying()) {			
			AudioPlayer.stopGameMusic();
		}
		
		if (!AudioPlayer.menuMusic.isPlaying()) {
			AudioPlayer.playMenuMusic(true);
		}
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
