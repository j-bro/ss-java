/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * 
 */
public class Asteroid extends Obstacle {

	/**
	 * The asteroid's default velocity
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(2, 0);
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 5;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 20; 
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Asteroid(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(2);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#getDEFAULT_VELOCITY()
	 */
	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}
	
	/**
	 * @return the object's description string
	 */
	public String toString() {
		return "Asteroid";
	}
}
