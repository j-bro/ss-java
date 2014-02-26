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
	 * 
	 */
	public Ship(Vector2 position, float width, float height, float SPEED, float rotation) {
		super(position, width, height, SPEED, rotation);
		
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