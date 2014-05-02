package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.Score;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class LevelCompletedMenu implements Screen {

	/**
	 * The game instance
	 */
	SSJava game;
	
	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * The stage instance
	 */
	Stage stage;
	
	/**
	 * The gameWorld instance
	 */
	GameWorld gameWorld;
	
	/**
	 * Background images
	 */
	Image bgImage, opacityImage;
	
	/**
	 * Buttons
	 */
	MenuButton retryButton, selectLevelButton, exitButton;
	
	/**
	 * Display labels
	 */
	Label titleLabel;
	Label scoreLabel;
	Label nameLabel;
	
	/**
	 * The name entry field
	 */
	TextField nameField;
	
	/**
	 * Menu text font
	 */
	BitmapFont whiteFont;
	
	
	/**
	 * Whether or not the new score is a high score
	 */
	boolean newHighScore;
	
	/**
	 * The player's score
	 */
	Score playerScore;
	
	/**
	 * 
	 * @param game
	 * @param referrer
	 */
	public LevelCompletedMenu(SSJava game, Screen referrer) {
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
		
		// High score check
		playerScore = new Score("", gameWorld.getScoreKeeper().getScore());
		if (game.highScores.isHighScore(playerScore)) {
			newHighScore = true;
		}
			
		nameLabel = new Label("New high score!\nEnter your name: ", ls);
		nameLabel.setAlignment(Align.center);
		nameLabel.setX(width / 2 - nameLabel.getWidth() / 2);
		nameLabel.setY(height / 2 + 100);
		nameLabel.setVisible(newHighScore);
		
		// Name entry field
		SpriteDrawable cursorDrawable = new SpriteDrawable(new Sprite(SSJava.assetManager.get("data/textures/textfieldcursor.png", Texture.class)));
		TextFieldStyle fieldStyle = new TextField.TextFieldStyle(whiteFont, Color.WHITE, cursorDrawable, null, null);
		
		nameField = new TextField("AAA", fieldStyle);
		nameField.setMaxLength(3);
		nameField.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if ((Gdx.app.getType() == Application.ApplicationType.Desktop && key == 13) || (Gdx.app.getType() == Application.ApplicationType.Android && key == 10)) {
					nameField.setCursorPosition(nameField.getText().length());
					playerScore.setName(nameField.getText());
				}
			}
		});
		nameField.setWidth(100);
		nameField.setX(width / 2 - nameField.getWidth() / 2);
		nameField.setY(height / 2 + 60);
		nameField.setVisible(newHighScore);
		
		// Retry the level
		retryButton = new MenuButton("Retry", 280, 65, game);
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
				if (newHighScore) {					
					saveScore(playerScore);
				}
				FileHandle levelFile = ((GameScreen) referrer).gameWorld.getLevelFile(); // TODO retry referrer
				game.gameScreen = new GameScreen(game, levelFile);
				game.setScreen(game.gameScreen);
			}
		});
		
		// Go to level selection screen
		selectLevelButton = new MenuButton("Select Level", 280, 65, game);
		selectLevelButton.setX(width / 2 - selectLevelButton.getWidth() / 2);
		selectLevelButton.setY(height / 2 - selectLevelButton.getHeight() / 2 - 150);
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
				if (newHighScore) {					
					saveScore(playerScore);
				}
				game.setScreen(new LevelSelectMenu(game, new MainMenu(game)));
			}
		});
		
		// Exit to main menu button
		exitButton = new MenuButton("Exit", 280, 65, game);
		exitButton.setX(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2);
		exitButton.setY(Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - 250);
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
				if (newHighScore) {					
					saveScore(playerScore);
				}
				game.setScreen(new MainMenu(game));
			}
		});
		
		// Title label
		titleLabel = new Label("Level Completed", ls);
		titleLabel.setX(0);
		titleLabel.setY(height / 2 + 240);
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
		stage.addActor(nameLabel);
		stage.addActor(nameField);
		stage.addActor(retryButton);
		stage.addActor(selectLevelButton);
		stage.addActor(exitButton);
		stage.addActor(titleLabel);
	}

	/**
	 * Save the user's score
	 */
	public void saveScore(Score s) {
		s.setName(nameField.getText());
		game.highScores.add(s);
		game.highScores.exportScores();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show level completed menu");
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
