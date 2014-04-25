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
	
	/**
	 * The x-coordinate of the level's ending point
	 */
	private float levelEnd;
	
	/**
	 * The path to the level's background
	 */
	private String backgroundPath;
	
	/**
	 * 
	 */
	public Level() {
		obstacles = new Array<Obstacle>();
		enemies = new Array<Enemy>();
		powerups = new Array<Powerup>();
		gameChangers = new Array<Obstacle>();
		
		levelEnd = 1500; // TODO
		backgroundPath = "data/textures/backgrounds/background_sparks.png"; // TODO
	}

	/**
	 * @return the levelEnd
	 */
	public float getLevelEnd() {
		return levelEnd;
	}

	/**
	 * @param levelEnd the levelEnd to set
	 */
	public void setLevelEnd(float levelEnd) {
		this.levelEnd = levelEnd;
	}

	/**
	 * @return the backgroundPath
	 */
	public String getBackgroundPath() {
		return backgroundPath;
	}

	/**
	 * @param backgroundPath the backgroundPath to set
	 */
	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}	
}
