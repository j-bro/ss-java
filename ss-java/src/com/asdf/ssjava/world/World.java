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
		
		ship = new Ship(new Vector2(5, Gdx.graphics.getHeight() / 40), 1, 1, 270);
		ship.setVelocity(new Vector2(5, 0)); // default horizontal ship speed
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 7; j++) {
				enemies.add(new EnemyType1(new Vector2(50 * (i + 1), 5 * (j + 1)), 1, 1, 0));
//				enemies.get(i).setVelocity(new Vector2(1, 0));
			}
			
		}
		
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
		
		for (Enemy e: enemies) {
			e.update();
		}
		
		/*
		for (Obstacle o: obstacles) {
			o.update();
		}
		
		for (Powerup p: powerups) {
			p.update();
		}
		*/
		
		// Collision detection
		for (Enemy e: enemies) {
			if (ship.getHitbox().overlaps(e.getHitbox())) {
				Gdx.app.log("Collision", "Ship hit");
			}
		}		
	}
	
	/**
	 *
	 */
	public void dispose() {
		
	}
}
