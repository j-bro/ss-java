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
	TextField volumeField;
	
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
		
		// TODO Volume still no go
		LabelStyle volumeLabelStyle = new LabelStyle(whiteFont, Color.WHITE);
		volumeLabel = new Label("Volume: ", volumeLabelStyle);
		volumeLabel.setX(width / 2 - volumeLabel.getWidth() / 2 - width / 15);
		volumeLabel.setY(Gdx.graphics.getHeight() / 2);
//		volumeLabel.setWidth(width);
		volumeLabel.setAlignment(Align.center);
		
		volumeField = new TextField(new Integer(SSJava.prefs.getInteger("volume", 100)).toString(), new TextField.TextFieldStyle(whiteFont, Color.WHITE, null, null, null));
		volumeField.setMaxLength(3);
		volumeField.setTextFieldFilter(new TextFieldFilter.DigitsOnlyFilter());
		volumeField.setTextFieldListener(new TextField.TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char key) {
				if ((Gdx.app.getType() == Application.ApplicationType.Desktop && key == 13) || (Gdx.app.getType() == Application.ApplicationType.Android && key == 10)) {
					int newVolume = Integer.parseInt(volumeField.getText()); 
					if (newVolume > 100) {
						SSJava.prefs.putInteger("volume", 100);
					}
					else {
						SSJava.prefs.putInteger("volume", newVolume);
					}
					Gdx.app.log(SSJava.LOG, "Volume: " + SSJava.prefs.getInteger("volume"));
				}
			}
		});
		volumeField.setWidth(80);
		volumeField.setX(width / 2 - volumeField.getWidth() + width / 15);
		volumeField.setY(Gdx.graphics.getHeight() / 2);
		// TODO implement save when user hits enter
		
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
		stage.addActor(volumeField);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
