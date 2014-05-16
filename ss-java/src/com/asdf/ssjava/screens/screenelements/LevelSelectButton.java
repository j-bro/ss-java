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
 * Level selection button base class.
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
	 * Creates a level select button with the specified parameters.  
	 * @param text the text for the button
	 * @param width the width of the button
	 * @param height the height of the button
	 * @param game the SSJava instance
	 * @param levelFile the FileHandle of the corresponding level
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
	 * Creates a level select button based on another button. 
	 * @param button the button which to copy
	 * @param game the SSJava instance
	 */
	public LevelSelectButton(LevelSelectButton button, final SSJava game) {
		super(button.text, new TextButtonStyle() {{
			font = SSJava.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class);
		}});
		
		this.game = game;
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
				if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, text + " button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				if (enabled) {					
					game.gameScreen = new GameScreen(game, levelFile);
					game.setScreen(game.gameScreen);
					if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, text + " button up");
				}
			}
		});
	}

	/**
	 * Sets the button's text according to the levels unlocked. 
	 * Button is white if enabled, gray if disabled. 
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (this.enabled) {
			setText(text);
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "level select button enabled");
		}
		else {			
			setText("Locked");
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "level select button disabled");
		}
	}
}
