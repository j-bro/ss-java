package com.asdf.ssjava.screens;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.BackButton;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.badlogic.gdx.Application;
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
	
	BitmapFont whiteFont;
	
	Screen thisLevelCreatorOptions = this;
	
	/**
	 * 
	 * @param game
	 * @param referrer
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
				Gdx.app.log(SSJava.LOG, "Test button up");
				((LevelCreatorScreen) referrer).getGameWorld().exportLevel("levels/temp.json");
				game.gameScreen = new GameScreen(game, "levels/temp.json", (LevelCreatorScreen) referrer);
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
				game.setScreen(new LevelSaveMenu(game, thisLevelCreatorOptions));
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
				Gdx.app.log(SSJava.LOG, "Load button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Load button up");
				
				// File selection
				if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
					JFileChooser chooser = new JFileChooser("levels");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON level files", "json");
					chooser.setFileFilter(filter);
					int returnVal = chooser.showOpenDialog(new JPanel());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						String levelPath = chooser.getSelectedFile().getPath();
						game.setScreen(new LevelCreatorScreen(game, levelPath));
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
				Gdx.app.log(SSJava.LOG, "Exit button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Exit button up");
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
			opacityImage = new Image(game.assetManager.get("data/textures/backgrounds/black_50-opacity.png", Texture.class));
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
		whiteFont = game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
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
	 * 
	 * @return the referring screen
	 */
	public Screen getReferrer() {
		return referrer;
	}

}
