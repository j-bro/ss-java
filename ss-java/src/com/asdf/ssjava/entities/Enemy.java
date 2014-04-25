/**
 * 
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Jeremy Brown
 *
 */

public abstract class Enemy extends MoveableEntity {
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Enemy(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
	}
	
	/**
	 * Fires a bullet from the enemy ship
	 * Shot interval specified by shotCooldown
	 */
	public abstract void fire();
	
	/**
	 * @return type the type of enemy
	 */
	public abstract int getType();
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#die()
	 */
	@Override
	public void die() {
		gameWorld.getLevel().enemies.removeValue(this, true);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Abstract Enemy";
	}
}
