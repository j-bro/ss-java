/**
* Extends AbstactEntity to model a moveable entity.
* Adds fields for velocity and acceleration vectors, as well as default acceleration and rotation.
* Adds methods for ... TODO
* Implements update method for the entity's hitbox.
*/
package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Jeremy Brown
 *
 */

public abstract class MoveableEntity extends AbstractEntity {
	
	/**
	 * The entity's default velocity vector, as values for x and y
	 */
	public static Vector2 DEFAULT_VELOCITY;
	
	/**
	 * Creates an entity with a position, dimensions, [SPEED]
	 * @param position the entity's position, as values for x and y
	 * @param width the entity's width in pixels
	 * @param height the entity's height in pixels
	 */
	protected MoveableEntity(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World world) {
		super(position, width, height, rotation, gameWorld, world);
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
