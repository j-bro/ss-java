/**
 * 
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.MenuButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Jeremy Brown
 *
 */
public class MainMenu implements Screen {
	
	SSJava game;
	Stage stage;
	SpriteBatch batch;
	BitmapFont whiteFont;
	
	Label titleLabel;
	Label creditsLabel;
	
	TextButton playButton;
	MenuButton optionsButton;
	MenuButton highScoresButton;
	MenuButton creditsButton;
	MenuButton exitButton;
	
	Screen thisMainMenu = this;
	
	/**
	 * 
	 * @param game The game instance of type SSJava
	 */
	public MainMenu(SSJava game) {
		this.game = game;
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
		
		batch.begin();
			stage.draw(); 
		batch.end();
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
		            if (keyCode == Keys.C) {
		                game.setScreen(new LevelCreator(game));
		            }
		            return super.keyDown(keyCode);
		        }
			};
		}
		stage.clear();
		 
		Gdx.input.setInputProcessor(stage);
				
		// play button
		playButton = new MenuButton("Play", 280, 65, game);		
		playButton.setX(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2);
		playButton.setY(Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2 + 140);
		
		playButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Play button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Play button up");
				game.setScreen(new LevelSelectMenu(game, thisMainMenu));
//				TODO dispose();
			}
		});
		
		// options button
		optionsButton = new MenuButton("Options", 280, 65, game);
		optionsButton.setX(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2);
		optionsButton.setY(Gdx.graphics.getHeight() / 2 - optionsButton.getHeight() / 2 + 60);
		
		optionsButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Options button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Options button up");
				game.setScreen(new OptionsMenu(game, thisMainMenu));
			}
		});
		
		// high scores button
		highScoresButton = new MenuButton("High Scores", 280, 65, game);
		highScoresButton.setX(Gdx.graphics.getWidth() / 2 - highScoresButton.getWidth() / 2);
		highScoresButton.setY(Gdx.graphics.getHeight() / 2 - highScoresButton.getHeight() / 2 - 20);
		
		highScoresButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "High scores button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "High scores button up");
				game.setScreen(new HighScoresMenu(game, thisMainMenu));
			}
		});
		
		// credits button
		creditsButton = new MenuButton("Credits", 280, 65, game);
		creditsButton.setX(Gdx.graphics.getWidth() / 2 - creditsButton.getWidth() / 2);
		creditsButton.setY(Gdx.graphics.getHeight() / 2 - creditsButton.getHeight() / 2 - 100);
		
		creditsButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Credits button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Credits button up");
				game.setScreen(new CreditsMenu(game, thisMainMenu));
			}
		});
		
		// exit button
		exitButton = new MenuButton("Exit", 280, 65, game);
		exitButton.setX(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2);
		exitButton.setY(Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - 180);
		
		exitButton.addListener(new InputListener() {
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchDown(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Exit button down");
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.InputListener#touchUp(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float, int, int)
			 */
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Exit button up");
				Gdx.app.exit();
			}
		});
		
		
		// Title text
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("SS-Java", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 240);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		// bottom right credits text
		creditsLabel = new Label("(C) 2014 ASDF", ls);
		creditsLabel.setX(width - creditsLabel.getWidth() - 10);
		creditsLabel.setY(10);
		
		stage.addActor(titleLabel);	
		stage.addActor(creditsLabel);
		stage.addActor(playButton);
		stage.addActor(optionsButton);
		stage.addActor(highScoresButton);
		stage.addActor(creditsButton);
		stage.addActor(exitButton);
	}
 
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#show()
	 */
	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show main menu");
		
		batch = new SpriteBatch();
		whiteFont = game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.Screen#hide()
	 */
	@Override 
	public void hide() {
		dispose();
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
		batch.dispose();
	}

}
