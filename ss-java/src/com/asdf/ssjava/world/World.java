/**
 * 
 */
package com.asdf.ssjava.world;

import java.util.ArrayList;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 *
 */

public class World {

	/**
	 * 
	 */
	SSJava game;
	
	/**
	 * 
	 */
	Ship ship;
	
	/**
	 * 
	 */
	ArrayList<Obstacle> obstacles;
	
	/**
	 * 
	 */
	ArrayList<Enemy> enemies;
	
	/**
	 * 
	 */
	ArrayList<Powerup> powerups;
	
	/**
	 * 
	 */
	public World(SSJava game) {
		this.game = game;
		ship = new Ship(new Vector2(5, 5), 1, 1, 5, 270);
		ship.setVelocity(new Vector2(1, 0)); // default horizontal ship speed
		Gdx.input.setInputProcessor(new InputManager(game, this));
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
