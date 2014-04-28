/**
 * 
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.BackButton;
import com.asdf.ssjava.screens.screenelements.LevelSelectButton;
import com.asdf.ssjava.screens.screenelements.MenuButton;
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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Jeremy Brown
 * 
 */

public class LevelSelectMenu implements Screen {

	SSJava game;
	Stage stage;
	SpriteBatch batch;	
	BitmapFont whiteFont;
	
	Label titleLabel;
	
	MenuButton backButton;
	
	LevelSelectButton level1Button;
	LevelSelectButton level2Button;
	LevelSelectButton level3Button;
	LevelSelectButton level4Button;
	LevelSelectButton level5Button;
	
	/**
	 * The screen which to switch to when the back button is clicked
	 */
	Screen referrer;
	
	/**
	 * 
	 * @param game The game instance of type SSJava
	 */
	public LevelSelectMenu(SSJava game, Screen referrer) {
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
		stage.setViewport(width, height);
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		
		// level buttons
		level1Button = new LevelSelectButton("Level 1", 280, 65, game, "levels/level1.json");
		level1Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level1Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 150);
		if (!SSJava.checkLevelCompletion(1 - 1)) {
			level1Button.setEnabled(false);
		}
		else {
			level1Button.setEnabled(true);
		}
		
		level2Button = new LevelSelectButton("Level 2", 280, 65, game, "");
		level2Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level2Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 100);
		if (!SSJava.checkLevelCompletion(2 - 1)) {
			level2Button.setEnabled(false);
		}
		else {
			level2Button.setEnabled(true);
		}
		
		level3Button = new LevelSelectButton("Level 3", 280, 65, game, "");
		level3Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level3Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 + 50);
		if (!SSJava.checkLevelCompletion(3 - 1)) {
			level3Button.setEnabled(false);
		}
		else {
			level3Button.setEnabled(true);
		}
		
		level4Button = new LevelSelectButton("Level 4", 280, 65, game, "");
		level4Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level4Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2);
		if (!SSJava.checkLevelCompletion(4 - 1)) {
			level4Button.setEnabled(false);
		}
		else {
			level4Button.setEnabled(true);
		}
		
		level5Button = new LevelSelectButton("Level 5", 280, 65, game, "");
		level5Button.setX(Gdx.graphics.getWidth() / 2 - level1Button.getWidth() / 2);
		level5Button.setY(Gdx.graphics.getHeight() / 2 - level1Button.getHeight() / 2 - 50);
		if (!SSJava.checkLevelCompletion(5 - 1)) {
			level5Button.setEnabled(false);
		}
		else {
			level5Button.setEnabled(true);
		}
		
		// exit to main menu button
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
		
		stage.addActor(level1Button);
		stage.addActor(level2Button);
		stage.addActor(level3Button);
		stage.addActor(level4Button);
		stage.addActor(level5Button);
		stage.addActor(titleLabel);
		stage.addActor(backButton);
		
	}

	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show level selection");
		
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
