/**
 * Implements InputProcessor to 
 * Currently maps ship's up and down movements to the arrow keys, as well as W and S and mouse clicks/screen touches in the top and bottom of the left half of the screen.
 * Firing a bullet is done with the spacebar or clicking/touching anywhere in the right half of the screen. 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Ship;
import com.asdf.ssjava.screens.screenelements.Toast;
import com.asdf.ssjava.screens.screenelements.ToastMessage;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

/**
 * @author Jeremy Brown
 * 
 */
public class GameInputManager implements InputProcessor {
	
	/**
	 * The world's instance
	 */
	GameWorld gameWorld;
	
	/**
	 * The ship's instance
	 */
	Ship ship;
	
	/**
	 * @param game
	 * @param gameWorld 
	 */
	public GameInputManager(SSJava game, GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		ship = gameWorld.getShip();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		/*
		 * Ship movement controls moved to com.asdf.ssjava.entities.ship#update()
		 * The change to polling was required for Box2D implementation of force application
		 */
		if (!gameWorld.isPlayEnded()) {
			switch (keycode) {
				case Keys.SPACE: 
					ship.fire();
					break;
				case Keys.ESCAPE:
					gameWorld.pauseGame();
					break;
					
				case Keys.L:
					if (SSJava.DEBUG) { // speed of light testing
						if (!ship.isSpeedOfLightEnabled()) {
							ship.enableSpeedOfLight();
						}
						else {
							ship.disableSpeedOfLight();	
						}
					}
					break;
					
					
				case Keys.T:
					if (SSJava.DEBUG) { // Toast test						
						Gdx.app.log(SSJava.LOG, "t pressed");
						gameWorld.renderer.getStage().addActor(Toast.create(new ToastMessage("test toast", 5, 75)));
					}
					break;
				default: break;
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			// Ship movement input
			if (screenX < Gdx.graphics.getWidth() / 2) {
				if (screenY > Gdx.graphics.getHeight() / 2) {
					ship.getBody().applyForceToCenter(0, (-1) * Ship.DEFAULT_ACCELERATION.y, true);
				}
				else {
					ship.getBody().applyForceToCenter(0, Ship.DEFAULT_ACCELERATION.y, true);
				}
			}
			// Ship firing input
			else {
				ship.fire();
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS) {			
			
			// FIX TOUCH SCREEN PAUSE
			if (screenX < Gdx.graphics.getWidth() / 10 && screenY > Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 5) {
				gameWorld.pauseGame();
			}
			
			else if (screenY > Gdx.graphics.getHeight() / 2) {
				ship.getBody().applyForceToCenter(0, 0, true);
			}
			else {
				ship.getBody().applyForceToCenter(0, 0, true);
			}
			return true;
			
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
