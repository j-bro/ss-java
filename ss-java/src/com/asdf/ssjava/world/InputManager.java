/**
 * Implements InputProcessor to 
 * Currently maps ship's up and down movements to the arrow keys, as well as W and S and mouse clicks/screen touches in the top and bottom of the left half of the screen.
 * Firing a bullet is done with the spacebar or clicking/touching anywhere in the right half of the screen. 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Ship;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * @author Jeremy Brown
 * 
 */
public class InputManager implements InputProcessor {

	/**
	 * The game's instance
	 */
	SSJava game;
	
	/**
	 * The world's instance
	 */
	World world;
	
	/**
	 * The ship's instance
	 */
	Ship ship;
	
	/**
	 * @param game
	 * @param world 
	 */
	public InputManager(SSJava game, World world) {
		this.world = world;
		ship = world.getShip();
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		// TODO add keyconfigs 
		switch (keycode) {
			case Keys.UP: 
				ship.getAcceleration().y = ship.DEFAULT_ACCELERATION.y;
				break;
			case Keys.DOWN: 
				ship.getAcceleration().y = (-1) *  ship.DEFAULT_ACCELERATION.y;
				break;
			case Keys.W:
				ship.getAcceleration().y = ship.DEFAULT_ACCELERATION.y;
				break;
			case Keys.S:
				ship.getAcceleration().y = (-1) * ship.DEFAULT_ACCELERATION.y;
				break;
			case Keys.SPACE: 
				ship.fire();
				break;
			default: break;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		// TODO fix this badly implemented
		switch (keycode) {
		case Keys.UP: 
			ship.getAcceleration().y = 0;
			break;
		case Keys.DOWN:
			ship.getAcceleration().y = 0;
			break;
		case Keys.W:
			ship.getAcceleration().y = 0;
			break;
		case Keys.S:
			ship.getAcceleration().y = 0;
			break;
		case Keys.SPACE: 
			
			break;
		default: break;
		}
	return true;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
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
					ship.getAcceleration().y = (-1) * ship.DEFAULT_ACCELERATION.y;
				}
				else {
					ship.getAcceleration().y = ship.DEFAULT_ACCELERATION.y;
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
			if (screenY > Gdx.graphics.getHeight() / 2) {
				ship.getAcceleration().y = 0;
			}
			else {
				ship.getAcceleration().y = 0;
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
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
