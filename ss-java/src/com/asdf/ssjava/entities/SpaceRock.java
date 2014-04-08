package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class SpaceRock extends Obstacle {

	
	/**
	 * The space rock's default velocity (yes, it is static)
	 */

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
	
	public static final float DEFAULT_WIDTH = 2;
	public static final float DEFAULT_HEIGHT = 2;
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public SpaceRock(Vector2 position, float width, float height, float rotation, World world) {
		super(position, width, height, rotation, world);
		setHealth(DEFAULT_HEALTH);
	}
	
	// TODO constructor for serialization
	/*
	public SpaceRock() {
		super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
		setHealth(DEFAULT_HEALTH);
	}
	*/

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
