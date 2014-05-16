package com.asdf.ssjava.screens;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.BackButton;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
 * The options menu for the level creator.
 * Gives the options of returnning to the creator, testing & saving the created level, and exiting to the main menu. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class LevelCreatorOptionsMenu implements Screen {

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
	 * Buttons
	 */
	MenuButton backButton, testButton, saveButton, loadButton, exitButton;
	
	/**
	 * Menu display title
	 */
	Label titleLabel;
	
	/**
	 * Background images
	 */
	Image bgImage, opacityImage;
	
	/**
	 * The title font
	 */
	BitmapFont whiteFont;
	
	/**
	 * A concrete reference to this screen instance
	 */
	Screen thisMenu = this;
	
	/**
	 * Creates a creator options menu with the specified parameters. 
	 * @param game the SSJava instance
	 * @param referrer the referring screen
	 */
	public LevelCreatorOptionsMenu(SSJava game, Screen referrer) {
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
		
		// Return to creator button
		backButton = new BackButton(280, 65, game, referrer);
		backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 + 150);
		
		// Test level button
		testButton = new MenuButton("Test level", 280, 65, game);
		testButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		testButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 + 50);
		testButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Test button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Test button up");
				AudioPlayer.stopCreatorMusic();
				FileHandle tempLevel = Gdx.files.local("levels/temp.json");
				((LevelCreatorScreen) referrer).getGameWorld().exportLevel(tempLevel);
				game.gameScreen = new GameScreen(game, tempLevel, (LevelCreatorScreen) referrer);
				game.setScreen(game.gameScreen);
			}
		});
		
		// Save level button
		saveButton = new MenuButton("Save level", 280, 65, game);
		saveButton.setX(Gdx.graphics.getWidth() / 2 - saveButton.getWidth() / 2);
		saveButton.setY(Gdx.graphics.getHeight() / 2 - saveButton.getHeight() / 2 - 50);
		saveButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Save button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Save button up");
				game.setScreen(new LevelSaveMenu(game, thisMenu));
				}
			});
		
		// Load level button
		loadButton = new MenuButton("Load level", 280, 65, game);
		loadButton.setX(Gdx.graphics.getWidth() / 2 - loadButton.getWidth() / 2);
		loadButton.setY(Gdx.graphics.getHeight() / 2 - loadButton.getHeight() / 2 - 150);
		loadButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Load button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Load button up");
				
				// File selection
				if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
					JFileChooser chooser = new JFileChooser("levels");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON level files", "json");
					chooser.setFileFilter(filter);
					int returnVal = chooser.showOpenDialog(game.fileChooserPanel);
					if( returnVal == JFileChooser.APPROVE_OPTION) {
						AudioPlayer.stopCreatorMusic();
						FileHandle levelFile = Gdx.files.local(chooser.getSelectedFile().getPath());
						game.setScreen(new LevelCreatorScreen(game, levelFile));
					}
				}
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
				AudioPlayer.stopCreatorMusic();
				game.setScreen(new MainMenu(game));
				}
			});
		
		// Title label
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("Creator options", ls);
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
		
		stage.addActor(backButton);
		stage.addActor(testButton);
		stage.addActor(saveButton);
		stage.addActor(loadButton);
		stage.addActor(exitButton);
		stage.addActor(titleLabel);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show creator options");
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
	
	/**
	 * Gets the referring screen. 
	 * @return the referring screen
	 */
	public Screen getReferrer() {
		return referrer;
	}

}
