/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author jeremybrown
 *
 */

public class World {

	SSJava game;
	Ship ship;
	
	/**
	 * 
	 */
	public World(SSJava game) {
		this.game = game;
		ship = new Ship(new Vector2(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 2), 1, 1, 5, 90);
	}

	/**
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}
	
	/**
	 *
	 */
	public void update() {
		ship.update();
	}
	
	/**
	 *
	 */
	public void dispose() {
		
	}
}
