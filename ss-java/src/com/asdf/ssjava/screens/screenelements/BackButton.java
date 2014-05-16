package com.asdf.ssjava.screens.screenelements;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Specification of the back button. 
 * @author Jeremy Brown
 */
public class BackButton extends MenuButton {

	/**
	 * The game instance
	 */
	SSJava game;
	
	/**
	 * The referring screen
	 */
	Screen referrer;
	
	/**
	 * Creates a back button with the specified parameters. 
	 * @param width the width of the button
	 * @param height the height of the button
	 * @param game the SSJava instance
	 * @param referrer the referring screen
	 */
	public BackButton(float width, float height, SSJava game, Screen referrer) {
		super("Back", width, height, game);
		
		this.game = game;
		this.referrer = referrer;
		
		addBackButtonListener();
	}
	
	/**
	 * Adds an action listener to this button.
	 * Listener has the default behaviour of setting the screen to the referring screen
	 */
	public void addBackButtonListener() {
		addListener(new InputListener() {
			public boolean touchDown(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Back button down");
				return true;
			}
			
			public void touchUp(InputEvent even, float x, float y, int pointer, int button) {
				Gdx.app.log(SSJava.LOG, "Back button up");
				game.setScreen(referrer);
				if (referrer instanceof GameScreen) {					
					AudioPlayer.gameMusic.setVolume(SSJava.prefs.getInteger("musicVolume", 100));
				}
			}
		});
	}

	/**
	 * Gets the referring screen. 
	 * @return the referrer
	 */
	public Screen getReferrer() {
		return referrer;
	}

	/**
	 * Sets the referring screen. 
	 * @param referrer the referrer to set
	 */
	public void setReferrer(Screen referrer) {
		this.referrer = referrer;
	}
}
