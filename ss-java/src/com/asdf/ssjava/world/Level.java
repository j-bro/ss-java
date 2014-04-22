/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Powerup;
import com.badlogic.gdx.utils.Array;

/**
 * @author Jeremy Brown
 *
 */

public class Level {

	/**
	 * Array containing all the obstacles in the current level
	 */
	public Array<Obstacle> obstacles;
	
	/**
	 * ArrayList containing all the enemies in the current level
	 */
	public Array<Enemy> enemies;
	
	/**
	 * ArrayList containing all the powerups in the current level
	 */
	public Array<Powerup> powerups;
	
	/**
	 * ArrayList containing all the powerups in the current level
	 */
	public Array<Obstacle> gameChangers;
	
	public Level() {
		obstacles = new Array<Obstacle>();
		enemies = new Array<Enemy>();
		powerups = new Array<Powerup>();
		gameChangers = new Array<Obstacle>();
	}

}
