package com.asdf.ssjava.screens;

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
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * Allows the user to save a level under a specified name.
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class LevelSaveMenu implements Screen {

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
	 * The Buttons
	 */
	MenuButton backButton, saveButton;
	
	/**
	 * The file name text field
	 */
	TextField fileNameField;
	
	/**
	 * The labels
	 */
	Label titleLabel, textLabel;
	
	/**
	 * Background images
	 */
	Image bgImage, opacityImage;
	
	BitmapFont whiteFont;
	
	/**
	 * Creates a level save menu with the specified parameters. 
	 * @param game the SSJava instance
	 * @param referrer the referring screen
	 */
	public LevelSaveMenu(SSJava game, Screen referrer) {
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
		

		// Back button
		backButton = new BackButton(280, 65, game, referrer);
		backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 150);
		
		// File name 
		SpriteDrawable cursorDrawable = new SpriteDrawable(new Sprite(SSJava.assetManager.get("data/textures/textfieldcursor.png", Texture.class)));
		fileNameField = new TextField("LevelName", new TextField.TextFieldStyle(whiteFont, Color.WHITE, cursorDrawable, null, null));
		fileNameField.setSelection(0, fileNameField.getText().length() - 1);
		fileNameField.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if ((Gdx.app.getType() == Application.ApplicationType.Desktop && key == 13) || (Gdx.app.getType() == Application.ApplicationType.Android && key == 10)) {
					saveLevel(fileNameField.getText());
					game.setScreen(referrer);
				}
			}
		});
		fileNameField.setWidth(200);
		fileNameField.setX(Gdx.graphics.getWidth() / 2 - fileNameField.getWidth() / 2);
		fileNameField.setY(Gdx.graphics.getHeight() / 2 + 20);
		
		// Save button
		saveButton = new MenuButton("Save", 280, 65, game);
		saveButton.setX(Gdx.graphics.getWidth() / 2 - saveButton.getWidth() / 2);
		saveButton.setY(Gdx.graphics.getHeight() / 2 - saveButton.getHeight() / 2 - 250);
		saveButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Save button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Save button up");
				saveLevel(fileNameField.getText());
				game.setScreen(referrer);
			}
		});
		
		// Title label
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("Save level", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		// Text label
		textLabel = new Label("Enter a name:", ls);
		textLabel.setX(0);
		textLabel.setY(Gdx.graphics.getHeight() / 2 + 60);
		textLabel.setWidth(width);
		textLabel.setAlignment(Align.center);
		
		// Background
		if (game.screenshot != null) {
			bgImage = new Image(game.screenshot);
			opacityImage = new Image(SSJava.assetManager.get("data/textures/backgrounds/black_50-opacity.png", Texture.class));
			opacityImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			stage.addActor(bgImage);
			stage.addActor(opacityImage);
		}
		
		stage.addActor(backButton);
		stage.addActor(fileNameField);
		stage.addActor(saveButton);
		stage.addActor(titleLabel);
		stage.addActor(textLabel);
	}

	/**
	 * Save the level with the specified name. 
	 * Appends the .json extension to the file if it is not already specified. 
	 */
	public void saveLevel(String name) {
		LevelCreatorScreen creator = ((LevelCreatorScreen) ((LevelCreatorOptionsMenu) referrer).getReferrer());
		if (name.length() <= 5) {
			name += ".json";
		}
		else if (!(name.substring(name.length() - 5).equalsIgnoreCase(".json"))) {
			name += ".json";
		}
		
		creator.gameWorld.exportLevel(Gdx.files.local("levels/" + name));
		creator.setLevelModified(false);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show level save menu");
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
