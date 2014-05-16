package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public abstract class Powerup extends MoveableEntity {

	/**
	 * Creates a powerup with the specified parameters
	 * @param position the position of the powerup
	 * @param width the width of the powerup
	 * @param height the height of the powerup
	 * @param rotation the rotation of the powerup, in degrees
	 */
	public Powerup(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#die()
	 */
	@Override
	public void die() {
		gameWorld.getLevel().powerups.removeValue(this, true);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Abstract Powerup";
	}
	
}
