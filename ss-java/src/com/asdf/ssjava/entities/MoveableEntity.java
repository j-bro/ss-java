package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Model of a moveable entity. 
 * Adds fields for velocity and acceleration vectors, as well as default acceleration and rotation. 
 * Implements update method for the entity's hitbox.
 * @author Jeremy Brown
 * @author Simon Thompson
 */

public abstract class MoveableEntity extends AbstractEntity {
	
	/**
	 * Creates an entity with the specified parameters
	 * @param position the entity's position
	 * @param width the entity's width
	 * @param height the entity's height
	 * @param rotation the entity's rotation in degrees
	 * @param gameWorld the GameWorld instance
	 * @param box2DWorld the World instance (Box2D for collisions)
	 */
	protected MoveableEntity(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#update()
	 */
	@Override
	public void update() {
		super.update();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Abstract Moveable Entity";
	}
}
