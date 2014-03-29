/**
 * 
 */
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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Jeremy Brown
 *
 */

public class OptionsMenu implements Screen {

	SSJava game;
	Stage stage;
	SpriteBatch batch;	
	BitmapFont whiteFont;
	
	Label titleLabel;
	
	Label volumeLabel;
	Label musicLabel;
	Label soundLabel;
	TextField musicField;
	TextField soundField;
	
	MenuButton backButton;
	
	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * 
	 * @param game The game instance of type SSJava
	 * @param referer The screen object that called this screen
	 */
	public OptionsMenu(SSJava game, Screen referrer) {
		this.game = game;
		this.referrer = referrer;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		batch.begin();
			stage.draw(); 
		batch.end();
	}

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
		musicLabel.setX(width / 2 - musicLabel.getWidth() / 2 - width / 15);
		musicLabel.setY(Gdx.graphics.getHeight() / 2 + 20);
		musicLabel.setAlignment(Align.center);
		
		musicField = new TextField(new Integer(SSJava.prefs.getInteger("musicVolume", 100)).toString(), new TextField.TextFieldStyle(whiteFont, Color.WHITE, null, null, null));
		musicField.setMaxLength(3);
		musicField.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
		musicField.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if ((Gdx.app.getType() == Application.ApplicationType.Desktop && key == 13) || (Gdx.app.getType() == Application.ApplicationType.Android && key == 10)) {
					int newVolume = Integer.parseInt(musicField.getText()); 
					if (newVolume > 100) {
						SSJava.prefs.putInteger("musicVolume", 100);
						SSJava.prefs.flush();
					}
					else {
						SSJava.prefs.putInteger("musicVolume", newVolume);
						SSJava.prefs.flush();
					}
				}
			}
		});
		musicField.setWidth(80);
		musicField.setX(width / 2 - musicField.getWidth() + width / 15);
		musicField.setY(Gdx.graphics.getHeight() / 2 + 20);
		
		soundLabel = new Label("Sound: ", volumeLabelStyle);
		soundLabel.setX(width / 2 - soundLabel.getWidth() / 2 - width / 15);
		soundLabel.setY(Gdx.graphics.getHeight() / 2 - 30);
		soundLabel.setAlignment(Align.center);
		
		soundField = new TextField(new Integer(SSJava.prefs.getInteger("soundVolume", 100)).toString(), new TextField.TextFieldStyle(whiteFont, Color.WHITE, null, null, null));
		soundField.setMaxLength(3);
		soundField.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
		soundField.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if ((Gdx.app.getType() == Application.ApplicationType.Desktop && key == 13) || (Gdx.app.getType() == Application.ApplicationType.Android && key == 10)) {
					int newVolume = Integer.parseInt(soundField.getText()); 
					if (newVolume > 100) {
						SSJava.prefs.putInteger("soundVolume", 100);
						SSJava.prefs.flush();
					}
					else {
						SSJava.prefs.putInteger("soundVolume", newVolume);
						SSJava.prefs.flush();
					}
				}
			}
		});
		soundField.setWidth(80);
		soundField.setX(width / 2 - soundField.getWidth() + width / 15);
		soundField.setY(Gdx.graphics.getHeight() / 2 - 30);
		
		
		// exit to main menu button
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
		
		stage.addActor(titleLabel);
		stage.addActor(volumeLabel);
		stage.addActor(musicLabel);
		stage.addActor(soundLabel);
		stage.addActor(musicField);
		stage.addActor(soundField);
		stage.addActor(backButton);
	}

	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show Options menu");
		
		batch = new SpriteBatch();
		whiteFont = game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);		
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
