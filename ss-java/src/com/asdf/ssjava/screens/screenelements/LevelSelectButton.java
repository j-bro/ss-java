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
	String levelPath;
	
	/**
	 * @param text
	 * @param width
	 * @param height
	 */
	public LevelSelectButton(String text, float width, float height, final SSJava game, String levelPath) {
		super(text, new TextButtonStyle() {{
			font = game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
		}});
		
		this.game = game;
		this.text = text;
		this.levelPath = levelPath;
		
		setWidth(width);
		setHeight(height);
		
		addLevelButtonListener();
	}
	
	public LevelSelectButton(LevelSelectButton button, final SSJava game) {
		super(button.text, new TextButtonStyle() {{
			font = game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
		}});
		
		this.game = button.game;
		this.text = button.text;
		
		setWidth(button.getWidth());
		setHeight(button.getHeight());
		
		addLevelButtonListener();
	}
	
	public void addLevelButtonListener() {
		addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, text + " button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				game.gameScreen = new GameScreen(game, levelPath);
				game.setScreen(game.gameScreen);
				Gdx.app.log(SSJava.LOG, text + " button up");
			}
		});
	}

	

}
