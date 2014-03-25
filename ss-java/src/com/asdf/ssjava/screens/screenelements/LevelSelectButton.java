package com.asdf.ssjava.screens.screenelements;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class LevelSelectButton extends TextButton {

	SSJava game;
	String text;
	
	/**
	 * @param text
	 * @param width
	 * @param height
	 */
	public LevelSelectButton(String text, float width, float height, SSJava game) {
		super(text, new TextButtonStyle() {{
			font = new BitmapFont(Gdx.files.internal("data/fonts/whitefont.fnt"), false);
		}});
		
		this.game = game;
		this.text = text;
		
		setWidth(width);
		setHeight(height);
		
		addLevelButtonListener();
	}
	
	public void addLevelButtonListener() {
		addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, text + " button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				game.gameScreen = new GameScreen(game);
				game.setScreen(game.gameScreen);
				Gdx.app.log(SSJava.LOG, text + " button up");
			}
		});
	}

	

}
