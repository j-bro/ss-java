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

public class Enemy extends MoveableEntity {

	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public Enemy(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.MoveableEntity#update()
	 */
	@Override
	public void update() {
		super.update();
	}
}
