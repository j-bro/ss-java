/**
 * 
 */
package com.asdf.ssjava.entities;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.world.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author Jeremy Brown
 * 
 */
public class EnemyType1 extends Enemy {
	
	/**
	 * The World's instance
	 */
	private World world;
	
	/**
	 * Default velocity for the Type 1 Enemy
	 */
	public final Vector2 DEFAULT_VELOCITY = new Vector2(0, 0);
	
	/**
	 * The enemy's type
	 */
	public final int type = 1;
	
	/**
	 * The type of bullets the ship will fire
	 */
	public final int bulletType = 1;
	
	/**
	 * The cooldown, in milliseconds, of the enemy's fire
	 */
	private int shotCooldown = 300;
	
	/**
	 * The time since the last shot was taken
	 */
	private long lastShotTime = 0;
	
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
		if (TimeUtils.millis() - lastShotTime >= shotCooldown){
		Bullet b = new BulletType1(new Vector2(position.x, position.y), 3, 2, 0);
		b.getPosition().x = position.x - b.width;
		b.getPosition().y = position.y + height / 2 - b.height / 2;
		b.getVelocity().x =(-1) * b.getDEFAULT_VELOCITY().x;
		b.getVelocity().y = b.getDEFAULT_VELOCITY().y;
		world.getBullets().add(b);
		
		lastShotTime = TimeUtils.millis();
		Gdx.app.log(SSJava.LOG, "Enemy fired a bullet!");	
		}
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
	/**
	 * @return the shot cooldown time for the enemy, in milliseconds
	 */
	public int getShotCooldown() {
		return shotCooldown;
	}

	/**
	 * @param shotCooldown the shot cooldown time to set, in milliseconds
	 */
	public void setShotCooldown(int shotCooldown) {
		this.shotCooldown = shotCooldown;
	}
}
