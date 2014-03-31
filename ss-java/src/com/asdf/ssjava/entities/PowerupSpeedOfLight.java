package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

public class PowerupSpeedOfLight extends Powerup {

	public static final float COOLDOWN_SECONDS = 5;
	
	/**
	 * The entity's starting health
	 */
	public static final int DEFAULT_HEALTH = 1;
	
	public PowerupSpeedOfLight(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		setHealth(DEFAULT_HEALTH);
	}

	public void update() { // TODO
		super.update();
	}
	
	public String toString() {
		return "Speed of Light Powerup";
	}
}
