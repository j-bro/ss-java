/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author Simon Thompson
 *
 */
public class Planet extends Obstacle {
	
	/**
	 * The planet's default velocity (static)
	 */
	public static final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 4;
	
	/**
	 * The score given for hitting this entity
	 */
	public static final int HIT_SCORE = 100;
	
	/**
	 * The score given for killing this entity
	 */
	public static final int KILL_SCORE = 1000; 
	
	public static final float DEFAULT_WIDTH = 4;
	public static final float DEFAULT_HEIGHT = 4;
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * The gravitationnal constant
	 */
	public static final double GRAVITATIONNAL_CONSTANT = 6.67;

	/**
	 * The planet's weight modifier
	 */
	public static final long PLANET_WEIGHT_MOD = 59720000000000L;
	
	/**
	 * The planet's radius
	 */
	public static final long PLANET_RADIUS = 6378;
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Planet(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(DEFAULT_HEALTH);
	}
	
	// constructor for serialization
		public Planet() {
			super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
			setHealth(DEFAULT_HEALTH);
		}

	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Obstacle#getDEFAULT_VELOCITY()
	 */
	@Override
	public Vector2 getDEFAULT_VELOCITY() {
		return DEFAULT_VELOCITY;
	}
	
	public double gravitationnalPull(long mass, long distance){
		double force;
		force = GRAVITATIONNAL_CONSTANT * PLANET_WEIGHT_MOD * mass / Math.pow(distance * 5000 + PLANET_RADIUS, 2);
		return force;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Planet";
	}

}
