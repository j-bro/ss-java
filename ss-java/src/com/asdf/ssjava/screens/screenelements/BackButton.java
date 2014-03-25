package com.asdf.ssjava.screens.screenelements;

import com.asdf.ssjava.SSJava;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BackButton extends MenuButton {

	SSJava game;
	
	Screen referrer;
	
	/**
	 * 
	 * @param text
	 * @param width
	 * @param height
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
			}
		});
	}
}
