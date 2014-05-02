/**
 * Level selection button base class 
 */

package com.asdf.ssjava.screens.screenelements;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

	/**
	 * The game instance
	 */
	SSJava game;
	
	/**
	 * The display text of this button
	 */
	String text;
	
	/**
	 * The path of the level to be loaded when this button is clicked 
	 */
	FileHandle levelFile;
	
	/**
	 * Whether or not the button is enabled
	 */
	boolean enabled = true;
	
	/**
	 * 
	 * @param text
	 * @param width
	 * @param height
	 * @param game
	 * @param levelPath
	 */
	public LevelSelectButton(String text, float width, float height, final SSJava game, FileHandle levelFile) {
		super(text, new TextButtonStyle() {{
			font = SSJava.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
			font.setColor(Color.WHITE);
		}});
		
		this.game = game;
		this.text = text;
		this.levelFile = levelFile;
		
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
			font = SSJava.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
		}});
		
		this.game = button.game;
		this.text = button.text;
		
		setWidth(button.getWidth());
		setHeight(button.getHeight());
		
		addLevelButtonListener();
	}

	/**
	 * Adds an action listener to this button.
	 * Listener has the default behaviour of setting the screen to a game screen that loads the specified level
	 */
	public void addLevelButtonListener() {
		addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				if (enabled) {					
					Gdx.app.log(SSJava.LOG, text + " button down");
					return true;
				}
				return false;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (enabled) {					
					game.gameScreen = new GameScreen(game, levelFile);
					game.setScreen(game.gameScreen);
					Gdx.app.log(SSJava.LOG, text + " button up");
				}
			}
		});
	}

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
