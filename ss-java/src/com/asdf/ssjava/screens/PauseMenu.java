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
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Jeremy Brown
 *
 */

public class PauseMenu implements Screen {

	SSJava game;
	Stage stage;
	SpriteBatch batch;
	BitmapFont whiteFont;
	BitmapFont blackFont;
	
	MenuButton optionsButton;
	MenuButton returnButton;
	MenuButton exitButton;
	
	Label titleLabel;
	
	public PauseMenu(SSJava game) {
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
			stage = new Stage(width, height, true) {
				@Override
		        public boolean keyDown(int keyCode) {
		            if (keyCode == Keys.ESCAPE) {
		                game.setScreen(game.gameScreen);
		            }
		            return super.keyDown(keyCode);
		        }
			};
		}
		stage.clear();
		 
		Gdx.input.setInputProcessor(stage);
		
		// return to game button
		returnButton = new MenuButton("Back to game", 280, 65);
		returnButton.setX(Gdx.graphics.getWidth() / 2 - returnButton.getWidth() / 2);
		returnButton.setY(Gdx.graphics.getHeight() / 2 - returnButton.getHeight() / 2 + 50);
		
		returnButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Return button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Return button up");
				game.setScreen(game.gameScreen);
			}
		});
		
		// options button
		optionsButton = new MenuButton("Options", 280, 65);
		optionsButton.setX(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2);
		optionsButton.setY(Gdx.graphics.getHeight() / 2 - optionsButton.getHeight() / 2 - 50);
		
		optionsButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Options button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Options button up");
			}
		});
		
		// exit to main menu button
		exitButton = new MenuButton("Exit", 280, 65);
		exitButton.setX(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2);
		exitButton.setY(Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - 150);
		
		exitButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Options button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				AudioPlayer.stopGameMusic();
				game.setScreen(new MainMenu(game));
				Gdx.app.log(SSJava.LOG, "Options button up");
			}
		});
		
		
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("Paused", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 200);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		stage.addActor(titleLabel);
		stage.addActor(optionsButton);
		stage.addActor(returnButton);
		stage.addActor(exitButton);
		
		// TODO escape key listener
		stage.addListener(new EventListener() {

			@Override
			public boolean handle(Event event) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
	}

	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show pause menu");
		
		batch = new SpriteBatch();
		whiteFont = new BitmapFont(Gdx.files.internal("data/fonts/whitefont.fnt"), false);
		blackFont = new BitmapFont(Gdx.files.internal("data/fonts/font.fnt"), false);
		
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
		
	}

}
