/**
 * 
 */
package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.screenelements.MenuButton;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
	TextureAtlas atlas;
	Skin skin; 
	BitmapFont blackFont;
	BitmapFont whiteFont;
	
	Label titleLabel;
	
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
				
		// play button
		playButton = new MenuButton("Play", 280, 65);		
		playButton.setX(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2);
		playButton.setY(Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2 + 140);
		
		playButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Play button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Play button up");
				game.setScreen(new LevelSelectMenu(game, thisMainMenu));
//				TODO dispose();
			}
		});
		
		// options button
		optionsButton = new MenuButton("Options", 280, 65);
		optionsButton.setX(Gdx.graphics.getWidth() / 2 - optionsButton.getWidth() / 2);
		optionsButton.setY(Gdx.graphics.getHeight() / 2 - optionsButton.getHeight() / 2 + 60);
		
		optionsButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Return button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Return button up");
				game.setScreen(new OptionsMenu(game, thisMainMenu));
			}
		});
		
		// high scores button
		highScoresButton = new MenuButton("High Scores", 280, 65);
		highScoresButton.setX(Gdx.graphics.getWidth() / 2 - highScoresButton.getWidth() / 2);
		highScoresButton.setY(Gdx.graphics.getHeight() / 2 - highScoresButton.getHeight() / 2 - 20);
		
		highScoresButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Return button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Return button up");
				game.setScreen(new HighScoresMenu(game, thisMainMenu));
			}
		});
		
		// credits button
		creditsButton = new MenuButton("Credits", 280, 65);
		creditsButton.setX(Gdx.graphics.getWidth() / 2 - creditsButton.getWidth() / 2);
		creditsButton.setY(Gdx.graphics.getHeight() / 2 - creditsButton.getHeight() / 2 - 100);
		
		creditsButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Credits button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Credits button up");
				game.setScreen(new CreditsMenu(game, thisMainMenu));
			}
		});
		
		// exit button
		exitButton = new MenuButton("Exit", 280, 65);
		exitButton.setX(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2);
		exitButton.setY(Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - 180);
		
		exitButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Return button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Return button up");
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
		
		stage.addActor(titleLabel);		
		stage.addActor(playButton);
		stage.addActor(optionsButton);
		stage.addActor(highScoresButton);
		stage.addActor(creditsButton);
		stage.addActor(exitButton);
	}
 
	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show main menu");
		
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/menu/button.pack");    
		skin = new Skin();
		skin.addRegions(atlas);
		whiteFont = new BitmapFont(Gdx.files.internal("data/fonts/whitefont.fnt"), false);
		blackFont = new BitmapFont(Gdx.files.internal("data/fonts/font.fnt"), false);
		
		
	}

	@Override 
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		whiteFont.dispose();
		blackFont.dispose();
	}

}
