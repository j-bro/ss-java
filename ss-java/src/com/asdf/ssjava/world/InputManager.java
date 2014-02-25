/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Ship;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 * 
 */
public class InputManager implements InputProcessor {

	/**
	 * 
	 */
	SSJava game;
	
	/**
	 * 
	 */
	World world;
	
	/**
	 * 
	 */
	Ship ship;
	
	/**
	 * 
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
				ship.getVelocity().y = 1;
				break;
			case Keys.DOWN: 
				ship.getVelocity().y = -1;
				break;
			case Keys.SPACE: 
				
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
			ship.getVelocity().y = 0;
			break;
		case Keys.DOWN: 
			ship.getVelocity().y = 0;
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
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
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
