package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class PowerupHealthUp extends Powerup {
	
<<<<<<< HEAD
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	/**
	 * The health points given to the ship when it picks up this power up
	 */
	public static final int HEALTH_GIVEN = 2;
	
	/**
=======
	public static final float DEFAULT_WIDTH = 2;
	public static final float DEFAULT_HEIGHT = 2;
	public static final float DEFAULT_ROTATION = 0;
	public static final int DEFAULT_HEALTH = 1;
	
	/**
>>>>>>> level-loader
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public PowerupHealthUp(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(DEFAULT_HEALTH);
<<<<<<< HEAD
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Powerup#update()
	 */
	public void update() {
		super.update();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
=======
	}
	
	// constructor for serialization
	public PowerupHealthUp() {
		super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
		setHealth(DEFAULT_HEALTH);
	}
	
	/**
	 * 
	 */
	public void update() { // TODO
		super.update();
	}
	
	/**
	 * 
>>>>>>> level-loader
	 */
	public String toString() {
		return "Health Up Powerup";
	}

}
