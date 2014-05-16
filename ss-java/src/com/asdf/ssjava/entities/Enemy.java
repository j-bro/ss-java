package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Entity model for an enemy. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public abstract class Enemy extends MoveableEntity {
	
	/**
	 * Creates an enemy with the specified parameters
	 * @param position the position of the enemy
	 * @param width the width of the enemy
	 * @param height the height of the enemy
	 * @param rotation the rotation of the enemy
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance
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
	 * Get the type of the enemy
	 * @return type the type of the enemy
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
