package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class SpaceRock extends Obstacle {

	
	/**
	 * The space rock's default velocity (yes, it is static)
	 */
<<<<<<< HEAD
	public final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 5;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 10; 
=======
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	public static final float DEFAULT_WIDTH = 2;
	public static final float DEFAULT_HEIGHT = 2;
	public static final float DEFAULT_ROTATION = 0;
	public static final int DEFAULT_HEALTH = 1;
>>>>>>> level-loader
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public SpaceRock(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(DEFAULT_HEALTH);
<<<<<<< HEAD
=======
	}
	
	// constructor for serialization
	public SpaceRock() {
		super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
		setHealth(DEFAULT_HEALTH);
>>>>>>> level-loader
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#getDEFAULT_VELOCITY()
	 */
	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}

	public String toString() {
		return "Space Rock";
	}
}
