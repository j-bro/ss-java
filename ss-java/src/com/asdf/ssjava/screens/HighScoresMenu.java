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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Jeremy Brown
 *
 */

public class HighScoresMenu implements Screen {

	/**
	 * The game instance
	 */
	SSJava game;
	
	/**
	 * The stage instance
	 */
	Stage stage;
	
	/**
	 * The font
	 */
	BitmapFont whiteFont;
	
	/**
	 * The title label
	 */
	Label titleLabel;
	
	/**
	 * Buttons
	 */
	MenuButton backButton;
	
	/**
	 * The score labels 
	 */
	Label positionsLabel, namesLabel, scoresLabel;
	
	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * @param game the game instance of type SSJava
	 * @param referrer the referring screen
	 */
	public HighScoresMenu(SSJava game, Screen referrer) {
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
		
		// exit to main menu button
		backButton = new BackButton(280, 65, game, referrer);
		backButton.setX(Gdx.graphics.getWidth() / 2 - backButton.getWidth() / 2);
		backButton.setY(Gdx.graphics.getHeight() / 2 - backButton.getHeight() / 2 - 250);
		stage.addActor(backButton);
		
		// Title text
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("High Scores", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		stage.addActor(titleLabel);
		
		namesLabel = new Label("Name"
				+ "\n"
				+ "\nn01"
				+ "\nn02"
				+ "\nn03"
				+ "\nn04"
				+ "\nn05"
				+ "\nn06"
				+ "\nn07"
				+ "\nn08"
				+ "\nn09"
				+ "\nn10", ls);
		namesLabel.setX(width / 2 - namesLabel.getWidth() / 2);
		namesLabel.setY(height / 2 - namesLabel.getHeight() / 2);
		namesLabel.setAlignment(Align.center);
		stage.addActor(namesLabel);
		
		
		positionsLabel = new Label("Pos"
				+ "\n"
				+ "\n1."
				+ "\n2."
				+ "\n3."
				+ "\n4."
				+ "\n5."
				+ "\n6."
				+ "\n7."
				+ "\n8."
				+ "\n9."
				+ "\n10.", ls);
		positionsLabel.setX(width / 2 - namesLabel.getWidth() / 2 - width / 10);
		positionsLabel.setY(height / 2 - positionsLabel.getHeight() / 2);
		positionsLabel.setAlignment(Align.right);
		stage.addActor(positionsLabel);
		
		scoresLabel = new Label("Score"
				+ "\n"
				+ "\nscore1"
				+ "\nscore2"
				+ "\nscore3"
				+ "\nscore4"
				+ "\nscore5"
				+ "\nscore6"
				+ "\nscore7"
				+ "\nscore8"
				+ "\nscore9"
				+ "\nscore10", ls);
		scoresLabel.setX(width / 2 + namesLabel.getWidth() / 2 + width / 20);
		scoresLabel.setY(height / 2 - scoresLabel.getHeight() / 2);
		scoresLabel.setAlignment(Align.left);
		stage.addActor(scoresLabel);
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show High Scores menu");
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
}
