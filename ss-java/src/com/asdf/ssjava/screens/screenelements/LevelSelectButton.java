/**
 * Level selection button base class 
 */

package com.asdf.ssjava.screens.screenelements;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


/**
 * 
 * @author Jeremy Brown
 *
 */

public class LevelSelectButton extends TextButton {

	SSJava game;
	String text;
	String levelPath;
	
	/**
	 * @param text
	 * @param width
	 * @param height
	 */
	
	boolean enabled = true;
	
	/**
	 * TODO
	 * @param text
	 * @param width
	 * @param height
	 * @param game
	 * @param levelPath
	 */
	public LevelSelectButton(String text, float width, float height, final SSJava game, String levelPath) {
		super(text, new TextButtonStyle() {{
			font = game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
			font.setColor(Color.WHITE);
		}});
		
		this.game = game;
		this.text = text;
		this.levelPath = levelPath;
		
		setWidth(width);
		setHeight(height);
		
		addLevelButtonListener();
	}
	

	/**
	 * TODO
	 * @param button
	 * @param game
	 */

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

	


	/**
	 * TODO
	 */
//	public void addLevelButtonListener() {
//		addListener(new InputListener() {
//			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
//				if (enabled) {					
//					Gdx.app.log(SSJava.LOG, text + " button down");
//					return true;
//				}
//				return false;
//			}
//			
//			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
//				if (enabled) {					
//					game.gameScreen = new GameScreen(game, levelPath);
//					game.setScreen(game.gameScreen);
//					Gdx.app.log(SSJava.LOG, text + " button up");
//				}
//			}
//		});
//	}

	/**
	 * Button is white if enabled, gray if disabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (this.enabled) {
			getStyle().font.setColor(Color.WHITE);
			Gdx.app.log(SSJava.LOG, "enabled");
		}
		else {			
			getStyle().font.setColor(Color.GRAY);
			Gdx.app.log(SSJava.LOG, "disabled");
		}
	}
}
