 package com.asdf.ssjava.screens;

import com.asdf.ssjava.SSJava;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenu implements Screen {
	
	SSJava game;
	Stage stage;
	BitmapFont font;
	TextureAtlas atlas;
	Skin skin; 
	SpriteBatch batch;
	BitmapFont black;
	BitmapFont white;
	TextButton button; 
	
	
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
			white.draw(batch, "ss-java", Gdx.graphics.getWidth() / 2 - 15, Gdx.graphics.getHeight() / 2 + 100); 
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
		style.font = black;  
		
		button = new TextButton("Play", style);
		button.setWidth(400);
		button.setHeight(100);
		
		button.setX(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2);
		button.setY(Gdx.graphics.getHeight() / 2 - button.getHeight() / 2);
		
		button.addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "up");
				game.setScreen(new Game(game));
			}
		});
		
		stage.addActor(button);
		
	}
 
	@Override
	public void show() {
		Gdx.app.log(SSJava.LOG, "Show main menu");
		
		batch = new SpriteBatch();
		atlas = new TextureAtlas("data/button.pack");    
		skin = new Skin();
		skin.addRegions(atlas);
		white = new BitmapFont(Gdx.files.internal("data/whitefont.fnt"), false);
		black = new BitmapFont(Gdx.files.internal("data/font.fnt"), false);
		
		
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
		white.dispose();
		black.dispose();
	}

}
