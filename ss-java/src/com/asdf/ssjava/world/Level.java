package com.asdf.ssjava.world;

import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.screens.screenelements.ToastMessage;
import com.badlogic.gdx.utils.Array;

/**
 * The level object containing all data for a specific level.  
 * @author Jeremy Brown
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
	 * The level code for distinguishing different game levels
	 */
	int levelCode;
	
	/**
	 * The list of messages for this level
	 */
	public Array<ToastMessage> messages;
	
	/**
	 * The path of the level, if this level is an "intro" level
	 */
	public String nextLevelPath;
	
	/**
	 * Default Constructor. 
	 * Initializes all arrays of entities and messages. 
	 * Sets default values for the level end point, the background and the level code. 
	 */
	public Level() {
		obstacles = new Array<Obstacle>();
		enemies = new Array<Enemy>();
		powerups = new Array<Powerup>();
		gameChangers = new Array<Obstacle>();
		
		levelEnd = 100;
		backgroundPath = "data/textures/backgrounds/background_sparks.png"; // Default level background
		levelCode = -1; // level code for all non-in-game levels
		messages = new Array<ToastMessage>();
		nextLevelPath = null;
	}

	/**
	 * Gets the level end point. 
	 * @return the level end point, in world coordinates
	 */
	public float getLevelEnd() {
		return levelEnd;
	}

	/**
	 * Sets the level end point. 
	 * @param levelEnd the levelEnd to set, in world coordinates
	 */
	public void setLevelEnd(float levelEnd) {
		this.levelEnd = levelEnd;
	}

	/**
	 * Gets the background path. 
	 * @return the backgroundPath
	 */
	public String getBackgroundPath() {
		return backgroundPath;
	}

	/**
	 * Sets the background path. 
	 * @param backgroundPath the backgroundPath to set
	 */
	public void setBackgroundPath(String backgroundPath) {
		this.backgroundPath = backgroundPath;
	}

	/**
	 * Gets the level code. 
	 * @return the levelCode
	 */
	public int getLevelCode() {
		return levelCode;
	}

	/**
	 * Sets the level code. 
	 * @param levelCode the levelCode to set
	 */
	public void setLevelCode(int levelCode) {
		this.levelCode = levelCode;
	}
}
