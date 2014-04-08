package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class PowerupSpeedOfLight extends Powerup {

	public static final float COOLDOWN_SECONDS = 5;
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 1;
	

	public static final float DEFAULT_WIDTH = 2;
	public static final float DEFAULT_HEIGHT = 2;
	public static final float DEFAULT_ROTATION = 0;
	
	/**
	 * 
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public PowerupSpeedOfLight(Vector2 position, float width, float height, float rotation, World world) {
		super(position, width, height, rotation, world);
		setHealth(DEFAULT_HEALTH);
	}

	// TODO constructor for serialization
	/*
	public PowerupSpeedOfLight() {
		super(new Vector2(0, 0), DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_ROTATION);
		setHealth(DEFAULT_HEALTH);
	}
	*/
	
	/**
	 * 
	 */
	public void update() { // TODO
		super.update();
	}
	
	/**
	 * 
	 */
	public String toString() {
		return "Speed of Light Powerup";
	}
}
