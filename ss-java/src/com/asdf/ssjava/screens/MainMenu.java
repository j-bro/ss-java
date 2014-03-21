/**
 * 
 */
package com.asdf.ssjava.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
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
	
	
	/**
	 * 
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
				
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonnormal");
		style.down = skin.getDrawable("buttonpressed");
		style.font = blackFont;  
		
		playButton = new TextButton("Play", style);
		playButton.setWidth(280);
		playButton.setHeight(65);
		
		playButton.setX(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2);
		playButton.setY(Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2);
		
		playButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Play button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Play button up");
				game.gameScreen = new GameScreen(game);
				game.setScreen(game.gameScreen);
//				TODO dispose();
			}
		});
		
		LabelStyle ls = new LabelStyle(whiteFont, Color.WHITE);
		titleLabel = new Label("SS-Java", ls);
		titleLabel.setX(0);
		titleLabel.setY(Gdx.graphics.getHeight() / 2 + 150);
		titleLabel.setWidth(width);
		titleLabel.setAlignment(Align.center);
		
		stage.addActor(titleLabel);		
		stage.addActor(playButton);
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
		skin.dispose();
		atlas.dispose();
		whiteFont.dispose();
		blackFont.dispose();
	}

}
