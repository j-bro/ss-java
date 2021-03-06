package com.asdf.ssjava.screens;

import com.asdf.ssjava.AudioPlayer;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

/**
 * An options menu allowing the player to modify the sound and music volumes. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class OptionsMenu implements Screen {

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
	 * The title label
	 */
	Label titleLabel;
	
	/**
	 * The text labels
	 */
	Label volumeLabel, musicLabel, soundLabel;
	
	/**
	 * The text fields
	 */
	TextField musicField, soundField;
	
	/**
	 * The back button
	 */
	MenuButton backButton;
	
	/**
	 * Background images
	 */
	Image bgImage, opacityImage;
	
	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * Creates an options menu with the specified parameters. 
	 * @param game the SSJava instance
	 * @param referrer the referring screen
	 */
	public OptionsMenu(SSJava game, Screen referrer) {
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
		
		// Volume controls
		LabelStyle volumeLabelStyle = new LabelStyle(whiteFont, Color.WHITE);
		volumeLabel = new Label("Volume", volumeLabelStyle);
		volumeLabel.setX(0);
		volumeLabel.setY(height / 2 + 70);
		volumeLabel.setWidth(width);
		volumeLabel.setAlignment(Align.center);
		
		musicLabel = new Label("Music: ", volumeLabelStyle);
		musicLabel.setX(width / 2 - musicLabel.getWidth() / 2 - width / 25);
		musicLabel.setY(Gdx.graphics.getHeight() / 2 + 20);
		musicLabel.setAlignment(Align.center);
		
		SpriteDrawable cursorDrawable = new SpriteDrawable(new Sprite(SSJava.assetManager.get("data/textures/textfieldcursor.png", Texture.class)));
		TextFieldStyle fieldStyle = new TextField.TextFieldStyle(whiteFont, Color.WHITE, cursorDrawable, null, null);
		
		musicField = new TextField(new Integer(SSJava.prefs.getInteger("musicVolume", 100)).toString(), fieldStyle);
		musicField.setMaxLength(3);
		musicField.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
		musicField.setTextFieldListener(new TextField.TextFieldListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener#keyTyped(com.badlogic.gdx.scenes.scene2d.ui.TextField, char)
			 */
			@Override
			public void keyTyped(TextField textField, char key) {
				if ((Gdx.app.getType() == Application.ApplicationType.Desktop && key == 13) || (Gdx.app.getType() == Application.ApplicationType.Android && key == 10)) {
					int newVolume = Integer.parseInt(musicField.getText());
					if (saveVolume(newVolume, "musicVolume") == 100) {						
						musicField.setText(new Integer(100).toString());
						musicField.setCursorPosition(musicField.getText().length());
					}
					
					AudioPlayer.menuMusic.setVolume(SSJava.prefs.getInteger("musicVolume") / 100f);
					AudioPlayer.gameMusic.setVolume(SSJava.prefs.getInteger("musicVolume") / 100f);
					AudioPlayer.creatorMusic.setVolume(SSJava.prefs.getInteger("musicVolume") / 100f);
				}
			}
		});
		musicField.setWidth(80);
		musicField.setX(musicLabel.getX() + musicLabel.getWidth() + width / 50);
		musicField.setY(Gdx.graphics.getHeight() / 2 + 20);
		
		soundLabel = new Label("Sound: ", volumeLabelStyle);
		soundLabel.setX(width / 2 - soundLabel.getWidth() / 2 - width / 25);
		soundLabel.setY(Gdx.graphics.getHeight() / 2 - 30);
		soundLabel.setAlignment(Align.center);
		
		soundField = new TextField(new Integer(SSJava.prefs.getInteger("soundVolume", 100)).toString(), fieldStyle);
		soundField.setMaxLength(3);
		soundField.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
		soundField.setTextFieldListener(new TextField.TextFieldListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener#keyTyped(com.badlogic.gdx.scenes.scene2d.ui.TextField, char)
			 */
			@Override
			public void keyTyped(TextField textField, char key) {
				if ((Gdx.app.getType() == Application.ApplicationType.Desktop && key == 13) || (Gdx.app.getType() == Application.ApplicationType.Android && key == 10)) {
					int newVolume = Integer.parseInt(soundField.getText()); 
					if (saveVolume(newVolume, "soundVolume") == 100) {						
						soundField.setText(new Integer(100).toString());
						soundField.setCursorPosition(soundField.getText().length());
					}
					AudioPlayer.pointsPickedUp();
				}
			}
		});
		soundField.setWidth(80);
		soundField.setX(soundLabel.getX() + soundLabel.getWidth() + width / 50);
		soundField.setY(Gdx.graphics.getHeight() / 2 - 30);
		
		
		// Exit to main menu button
		backButton = new BackButton(280, 65, game, referrer);
		backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 250);
		
		// Title text
		LabelStyle titleLabelStyle = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("Options", titleLabelStyle);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		// Background
		if (game.screenshot != null && referrer instanceof PauseMenu) {
			bgImage = new Image(game.screenshot);
			opacityImage = new Image(SSJava.assetManager.get("data/textures/backgrounds/black_50-opacity.png", Texture.class));
			opacityImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			stage.addActor(bgImage);
			stage.addActor(opacityImage);
		}
		else {
			bgImage = new Image(SSJava.assetManager.get("data/textures/backgrounds/menu_bg.png", Texture.class));
			bgImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			stage.addActor(bgImage);
		}
		
		stage.addActor(titleLabel);
		stage.addActor(volumeLabel);
		stage.addActor(musicLabel);
		stage.addActor(soundLabel);
		stage.addActor(musicField);
		stage.addActor(soundField);
		stage.addActor(backButton);
	}

	/**
	 * Saves the new volume to the preferences & activates it immediately.
	 * @param newVolume the new volume to be saved
	 * @param key the key under which to save the new volume
	 * @return the new volume
	 */
	public int saveVolume(int newVolume, String key) {
		if (newVolume > 100) {
			SSJava.prefs.putInteger(key, 100);
			SSJava.prefs.flush();
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, key + " saved");
		}
		else {
			SSJava.prefs.putInteger(key, newVolume);
			SSJava.prefs.flush();
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, key + " saved");
		}
		return SSJava.prefs.getInteger(key);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Show Options menu");
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
