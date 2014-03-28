/**
 * 
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.BackButton;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Jeremy Brown
 * 
 */

public class CreditsMenu implements Screen {

	SSJava game;
	Stage stage;
	SpriteBatch batch;	
	BitmapFont whiteFont;
	
	Label titleLabel;
	
	MenuButton backButton;
	
	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * 
	 * @param game The game instance of type SSJava
	 */
	public CreditsMenu(SSJava game, Screen referrer) {
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
		
		// exit to main menu button
		backButton = new BackButton(280, 65, game, referrer);
		backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 250);
		
		// Title text
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("Credits", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		stage.addActor(titleLabel);
		stage.addActor(backButton);
	}

	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show credits");
		
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
