package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.GameWorld;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Entity model for an obstacle. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public abstract class Obstacle extends MoveableEntity {

	/**
	 * Creates an obstacle with the specified parameters
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 * @param gameWorld
	 * @param box2DWorld
	 */
	public Obstacle(Vector2 position, float width, float height, float rotation, GameWorld gameWorld, World box2DWorld) {
		super(position, width, height, rotation, gameWorld, box2DWorld);
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.AbstractEntity#die()
	 */
	@Override
	public void die() {
		if (this instanceof Planet || this instanceof MagneticObject || this instanceof Sun) {		
			if (this instanceof MagneticObject){
				gameWorld.magnet = null;
				gameWorld.magnetActivated = false;
			}
			gameWorld.getLevel().gameChangers.removeValue(this, true);
		}
		else {
			gameWorld.getLevel().obstacles.removeValue(this, true);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Abstract Obstacle";
	}	
}
