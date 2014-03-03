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
public class EnemyType1 extends Enemy {

	/**
	 * Default velocity for the Type 1 Enemy
	 */
	public final float DEFAULT_VELOCITY = 0;
	
	/**
	 * @param position
	 * @param width
	 * @param height
	 * @param rotation
	 */
	public EnemyType1(Vector2 position, float width, float height, float rotation) {
		super(position, width, height, rotation);
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Enemy#update()
	 */
	@Override
	public void update() {
		super.update();
		position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime())); 
		
	}
	
	public void advance(Ship ship) {
		super.update();
//		position.lerp(ship.getPosition(), Gdx.graphics.getDeltaTime());
	}
	
}
