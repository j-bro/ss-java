/**
 * 
 */
package com.asdf.ssjava.world;

import java.util.ArrayList;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
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
	 * @param game
	 */
	public World(SSJava game) {
		this.game = game;
		
		enemies = new ArrayList<Enemy>();
		
		ship = new Ship(new Vector2(5, 5), 1, 1, 1, 270);
		ship.setVelocity(new Vector2(5, 0)); // default horizontal ship speed
		
		enemies.add(new EnemyType1(new Vector2(25, 5), 1, 1, (float)0.1, 0));
		enemies.get(0).setVelocity(new Vector2(1, 0));
		
		// Set game input processor
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
		// Entity position update
		ship.update();
		
		((EnemyType1) enemies.get(0)).update();
		//((EnemyType1) enemies.get(0)).advance(ship);
		/*
		for (all entities:e) {
			e.update();
		}
		*/ 
		
		// Collision detection
		// if (ship.getHitbox().overlaps(r))
	}
	
	/**
	 *
	 */
	public void dispose() {
		
	}
}
