/**
 * 
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Jeremy Brown
 *
 */

public class HighScoresMenu implements Screen {

	SSJava game;
	Stage stage;
	SpriteBatch batch;	
	BitmapFont whiteFont;
	
	Label titleLabel;
	
	MenuButton backButton;
	
	/**
	 * 
	 */
	public HighScoresMenu(SSJava game) {
		this.game = game;
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
			stage = new Stage(width, height, true);
		}
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		// exit to main menu button
				backButton = new MenuButton("Back", 280, 65);
				backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
				backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 250);
				
				backButton.addListener(new InputListener() {
					public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
						Gdx.app.log(SSJava.LOG, "Back button down");
						return true;
					}
					
					public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
						game.setScreen(new MainMenu(game));
						Gdx.app.log(SSJava.LOG, "Back button up");
					}
				});
		
		// Title text
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("High Scores", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		stage.addActor(titleLabel);
		stage.addActor(backButton);
	}

	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show High Scores menu");
		
		batch = new SpriteBatch();
		whiteFont = new BitmapFont(Gdx.files.internal("data/fonts/whitefont.fnt"), false);
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
