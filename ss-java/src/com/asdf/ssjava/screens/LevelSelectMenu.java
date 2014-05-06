/**
 * 
 */
package com.asdf.ssjava.screens;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.BackButton;
import com.asdf.ssjava.screens.screenelements.LevelSelectButton;
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

/**
 * @author Jeremy Brown
 * 
 */

public class LevelSelectMenu implements Screen {

	/**
	 * The game instance
	 */
	SSJava game;
	
	/**
	 * The stage instance
	 */
	Stage stage;
	
	/**
	 * The title font
	 */
	BitmapFont whiteFont;
	
	/**
	 * The title label
	 */
	Label titleLabel;
	
	/**
	 * The buttons
	 */
	MenuButton loadButton, backButton;
	LevelSelectButton tutorialButton, level1Button, level2Button, level3Button, level4Button, level5Button;
	
	/**
	 * The background image
	 */
	Image bgImage;
	
	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * @param game the game instance of type SSJava
	 * @param referrer the referring screen
	 */
	public LevelSelectMenu(SSJava game, Screen referrer) {
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
		
		// Level buttons
		tutorialButton = new LevelSelectButton("Tutorial", 280, 65, game, Gdx.files.internal("data/levels/level0.json"));
		tutorialButton.setX(Gdx.graphics.getWidth() / 2 - tutorialButton.getWidth() / 2);
		tutorialButton.setY(Gdx.graphics.getHeight() / 2 - tutorialButton.getHeight() / 2 + 200);
		
		// Level buttons
		level1Button = new LevelSelectButton("Level 1", 280, 65, game, Gdx.files.internal("data/levels/level1-intro.json"));
		level1Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level1Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 150);
		
		if (!SSJava.checkLevelCompletion(1 - 1)) {
			level1Button.setEnabled(false);
		}
		else {
			level1Button.setEnabled(true);
		}
		
		level2Button = new LevelSelectButton("Level 2", 280, 65, game, Gdx.files.internal("data/levels/level2-intro.json"));
		level2Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level2Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 100);
		if (!SSJava.checkLevelCompletion(2 - 1)) {
			level2Button.setEnabled(false);
		}
		else {
			level2Button.setEnabled(true);
		}
		
		level3Button = new LevelSelectButton("Level 3", 280, 65, game, null);
		level3Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level3Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 50);
		if (!SSJava.checkLevelCompletion(3 - 1)) {
			level3Button.setEnabled(false);
		}
		else {
			level3Button.setEnabled(true);
		}
		
		level4Button = new LevelSelectButton("Level 4", 280, 65, game, null);
		level4Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level4Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2);
		if (!SSJava.checkLevelCompletion(4 - 1)) {
			level4Button.setEnabled(false);
		}
		else {
			level4Button.setEnabled(true);
		}
		
		level5Button = new LevelSelectButton("Level 5", 280, 65, game, Gdx.files.internal("data/levels/level5.json"));
		level5Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level5Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 - 50);
		if (!SSJava.checkLevelCompletion(5 - 1)) {
			level5Button.setEnabled(false);
		}
		else {
			level5Button.setEnabled(true);
		}
		
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
					int returnVal = chooser.showOpenDialog(new JPanel());
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						String levelPath = chooser.getSelectedFile().getPath();
						game.gameScreen = new GameScreen(game, Gdx.files.local(levelPath));
						game.setScreen(game.gameScreen);
					}
				}
			}
		});
		
		// Exit to main menu button
		backButton = new BackButton(280, 65, game, referrer);
		backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 250);
		
		// Title text
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("Select Level", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		// Background image
		bgImage = new Image(SSJava.assetManager.get("data/textures/backgrounds/menu_bg.png", Texture.class));
		bgImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.addActor(bgImage);
		
		stage.addActor(titleLabel);
		stage.addActor(backButton);
		stage.addActor(tutorialButton);
		stage.addActor(level1Button);
		stage.addActor(level2Button);
		stage.addActor(level3Button);
		stage.addActor(level4Button);
		stage.addActor(level5Button);
		stage.addActor(loadButton);
		stage.addActor(backButton);
		stage.addActor(titleLabel);
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show level selection");
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
