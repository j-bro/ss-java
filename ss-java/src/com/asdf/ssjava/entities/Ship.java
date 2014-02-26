/**
 * 
 */
package com.asdf.ssjava.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * @author Simon Thompson
 *
 */

public class Ship extends MoveableEntity {
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param SPEED
	 * @param rotation
	 */
	public Ship(Vector2 position, float width, float height, float SPEED,
			float rotation) {
		super(position, width, height, SPEED, rotation);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void advance(float delta) {
		// TODO Auto-generated method stub`
		 
	}

	@Override
	public void update() { 
		super.update();
		position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime() * SPEED)); 
		rotation = velocity.angle() - 90;
	}
	
}
