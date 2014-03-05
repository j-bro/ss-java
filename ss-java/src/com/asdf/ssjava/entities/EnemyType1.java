/**
 * 
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Jeremy Brown
 * 
 */
public class EnemyType1 extends Enemy {
	
	/**
	 * The World's instance
	 */
	World world;
	
	/**
	 * Default velocity for the Type 1 Enemy
	 */
	public final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The enemy's type
	 */
	final int type = 1;
	
	/**
	 * The type of bullets the ship will fire
	 */
	int bulletType = 1;
	
	/**
	 * @param position the position of the enemy
	 * @param width the width of the enemy
	 * @param height the height of the enemy
	 * @param rotation the rotation of the enemy in degrees
	 * @param world the world instance
	 */
	public EnemyType1(Vector2 position, float width, float height, float rotation, World world) {
		super(position, width, height, rotation);
		this.world = world;
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Enemy#update()
	 */
	@Override
	public void update() {
		super.update();
		position.add(velocity.cpy().scl(Gdx.graphics.getDeltaTime())); 
		
	}
	
	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Enemy#fire()
	 */
	@Override
	public void fire() { // TODO not tested!
		Bullet b = new BulletType1(new Vector2(position.x, position.y), 3, 2, 0);
		b.getPosition().x = position.x - b.width;
		b.getPosition().y = position.y + height / 2 - b.height / 2;
		b.getVelocity().x =(-1) * b.DEFAULT_VELOCITY.x;
		b.getVelocity().y = b.DEFAULT_VELOCITY.y;
		world.getBullets().add(b);
	}

	public void advance(Ship ship) {
		super.update();
//		position.lerp(ship.getPosition(), Gdx.graphics.getDeltaTime());
	}

	/* (non-Javadoc)
	 * @see com.asdf.ssjava.entities.Enemy#getType()
	 */
	@Override
	public int getType() {
		return type;
	}
}
