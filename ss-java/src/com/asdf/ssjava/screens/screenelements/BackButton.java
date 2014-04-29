package com.asdf.ssjava.screens.screenelements;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.screens.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BackButton extends MenuButton {

	SSJava game;
	
	Screen referrer;
	
	/**
<<<<<<< .merge_file_a07352
	 * 
	 * @param text
	 * @param width
	 * @param height
=======
	 * @param width
	 * @param height
	 * @param game
	 * @param referrer
>>>>>>> .merge_file_a07976
	 */
	public BackButton(float width, float height, SSJava game, Screen referrer) {
		super("Back", width, height, game);
		
		this.game = game;
		this.referrer = referrer;
		
		addBackButtonListener();
	}
	
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
	 * @return the referrer
	 */
	public Screen getReferrer() {
		return referrer;
	}

	/**
	 * @param referrer the referrer to set
	 */
	public void setReferrer(Screen referrer) {
		this.referrer = referrer;
	}
}
