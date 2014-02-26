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
public class EnemyType1 extends Enemy {

	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param SPEED
	 * @param rotation
	 */
	public EnemyType1(Vector2 position, float width, float height, float SPEED, float rotation) {
		super(position, width, height, SPEED, rotation);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Enemy#update()
	 */
	@Override
	public void update() {
		super.update();
		
		// position.lerp(ship, delta) to follow ship
	}
	
	
}
