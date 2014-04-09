/**
 * Implements InputProcessor to 
 * Currently maps ship's up and down movements to the arrow keys, as well as W and S and mouse clicks/screen touches in the top and bottom of the left half of the screen.
 * Firing a bullet is done with the spacebar or clicking/touching anywhere in the right half of the screen. 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.Ship;
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
	GameWorld world;
	
	/**
	 * The ship's instance
	 */
	Ship ship;
	
	/**
	 * @param game
	 * @param world 
	 */
	public GameInputManager(SSJava game, GameWorld world) {
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
				if (!ship.maxUpSpeedReached){					
					ship.getBody().applyForce(0, Ship.DEFAULT_ACCELERATION.y, ship.getBody().getPosition().x + ship.getWidth() / 2 + ship.getWidth() / 4, ship.getBody().getPosition().y + ship.getHeight() / 2, true);
				}
				break;
			case Keys.DOWN: 
				if (!ship.maxDownSpeedReached) {					
					ship.getBody().applyForce(0, (-1) * Ship.DEFAULT_ACCELERATION.y, ship.getBody().getPosition().x + ship.getWidth() / 2 + ship.getWidth() / 4, ship.getBody().getPosition().y + ship.getHeight() / 2, true);
				}
				break;
			case Keys.W:
				if (!ship.maxUpSpeedReached){					
					ship.getBody().applyForce(0, Ship.DEFAULT_ACCELERATION.y, ship.getBody().getPosition().x + ship.getWidth() / 2 + ship.getWidth() / 4, ship.getBody().getPosition().y + ship.getHeight() / 2, true);
				}
				break;
			case Keys.S:
				if (!ship.maxDownSpeedReached) {					
					ship.getBody().applyForce(0, (-1) * Ship.DEFAULT_ACCELERATION.y, ship.getBody().getPosition().x + ship.getWidth() / 2 + ship.getWidth() / 4, ship.getBody().getPosition().y + ship.getHeight() / 2, true);
				}
				break;
			case Keys.SPACE: 
				ship.fire();
				break;
			case Keys.ESCAPE:
				world.pauseGame(); 
				break;
			case Keys.ENTER: // for testing enemy firing
				if (SSJava.DEBUG) {
					for (Enemy e: world.getEnemies()) {
						e.fire();
					}
				}
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
		// TODO fix this badly implemented?
		switch (keycode) {
		case Keys.UP: 
			ship.getBody().applyForceToCenter(0, 0, false);
			break;
		case Keys.DOWN:
			ship.getBody().applyForceToCenter(0, 0, false);
			break;
		case Keys.W:
			ship.getBody().applyForceToCenter(0, 0, false);
			break;
		case Keys.S:
			ship.getBody().applyForceToCenter(0, 0, false);
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
				world.pauseGame();
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
