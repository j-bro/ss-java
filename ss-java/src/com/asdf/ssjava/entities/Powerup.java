/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 * 
 */
public class Powerup extends MoveableEntity {

	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param SPEED
	 * @param rotation
	 */
	public Powerup(Vector2 position, float width, float height, float SPEED, float rotation) {
		super(position, width, height, SPEED, rotation);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#advance(float)
	 */
	@Override
	public void advance(float delta) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
}
