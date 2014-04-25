/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.AbstractEntity;
import com.asdf.ssjava.entities.Bullet;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.PowerupHealthUp;
import com.asdf.ssjava.entities.PowerupSpeedOfLight;
import com.asdf.ssjava.entities.Ship;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * 
 * @author Jeremy Brown
 *
 */
public class GameCollisionListener implements ContactListener {

	/**
	 * The world instance
	 */
	GameWorld gameWorld;
	
	/**
	 * 
	 * @param gameWorld
	 */
	public GameCollisionListener(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#beginContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void beginContact(Contact contact) {
		// Get colliding bodies from contact points
		Body body1 = contact.getFixtureA().getBody();
		Body body2 = contact.getFixtureB().getBody();
		
		// Bullet collision
		if (body1.getUserData() instanceof Bullet) {			
			bulletImpact((Bullet) body1.getUserData(), (AbstractEntity) body2.getUserData());
		}
		else if (body2.getUserData() instanceof Bullet) {
			bulletImpact((Bullet) body2.getUserData(), (AbstractEntity) body1.getUserData());
		}
		
		// Ship collision
		else if (body1.getUserData() instanceof Ship) {	
			// Powerup collection
			Ship ship = (Ship) body1.getUserData();
			if (body2.getUserData() instanceof PowerupHealthUp) {
				healthUpActivate((PowerupHealthUp) body2.getUserData(), ship);
				// TODO AudioPlayer.healthUp();
			}
			else if (body2.getUserData() instanceof PowerupSpeedOfLight) {
				speedOfLightActivate((PowerupSpeedOfLight) body2.getUserData(), ship);
				// TODO AudioPlayer.speedOfLight();
			}
			else {
				// Kill other entities in light speed
				if (ship.isLightSpeedEnabled()) {
					((AbstractEntity) body2.getUserData()).setHealth(0);
				}
				AudioPlayer.shipImpact();
			}
		}
		else if (body2.getUserData() instanceof Ship) {
			// Powerup collection
			Ship ship = (Ship) body2.getUserData();
			if (body1.getUserData() instanceof PowerupHealthUp) {
				healthUpActivate((PowerupHealthUp) body2.getUserData(), ship);
				// TODO AudioPlayer.healthUp();
			}
			else if (body1.getUserData() instanceof PowerupSpeedOfLight) {
				speedOfLightActivate((PowerupSpeedOfLight) body2.getUserData(), ship);
				// TODO AudioPlayer.speedOfLight();
			}
			else {
				// Kill other entities in light speed
				if (ship.isLightSpeedEnabled()) {
					((AbstractEntity) body1.getUserData()).setHealth(0);
				}
				AudioPlayer.shipImpact();
			}
		}			
		}	
	
	/**
	 * Called when a bullet collides with an entity
	 * Removes the bullet and deals damage to the entity
	 * @param b the bullet that collided
	 * @param e the entity that collided
	 */
	public void bulletImpact(Bullet b, AbstractEntity e) {
		if (!(e instanceof Powerup)) {
			Gdx.app.log(SSJava.LOG, "Bullet " + Integer.toHexString(b.hashCode()) + " collided with " + e.toString() + " " + Integer.toHexString(e.hashCode()));
			e.healthChange((-1) * b.getDamage()); 
			b.setHealth(0);
			AudioPlayer.bulletImpact();
			
			// Hit score
			gameWorld.getScoreKeeper().add(e.getHitScore());
		}
	}
	
	/**
	 * Called when the "health up" powerup is picked up
	 * Removes the powerup and gives health to the ship
	 * @param p the powerup picked up
	 * @param s the ship
	 */
	public void healthUpActivate(PowerupHealthUp p, Ship s) {
		Gdx.app.log(SSJava.LOG, "Health up " + Integer.toHexString(p.hashCode()) + " activated: " + PowerupHealthUp.HEALTH_GIVEN + " health points restored");
		s.healthChange(PowerupHealthUp.HEALTH_GIVEN);
		p.setHealth(0);
	}
	
	/**
	 * Called when the "speed of light" powerup is picked up
	 * Removes the powerup and sends the ship into light speed mode
	 * Sets timer to disable speed of light mode after a given interval
	 * @param p the powerup picked up
	 * @param s the ship
	 */
	public void speedOfLightActivate(PowerupSpeedOfLight p, final Ship s) {
		Gdx.app.log(SSJava.LOG, "Speed of light " + Integer.toHexString(p.hashCode()) + " activated");
		s.setLightSpeedEnabled(true);
		s.getBody().getFixtureList().get(0).setSensor(true);
		s.getBody().setLinearVelocity(Ship.SPEED_OF_LIGHT_VELOCITY);
		AudioPlayer.speedOfLightOn();
		new Timer().scheduleTask(new Task() {
			@Override
			public void run() {
				s.getBody().setLinearVelocity(Ship.DEFAULT_VELOCITY.x, 0);
				Fixture f = s.getBody().getFixtureList().get(0);
				if (f != null) { 					
					s.getBody().getFixtureList().get(0).setSensor(false);
				}
				s.setLightSpeedEnabled(false);
				AudioPlayer.speedOfLightOff();
			}
		}, PowerupSpeedOfLight.COOLDOWN_SECONDS);
		p.setHealth(0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#endContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#preSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.Manifold)
	 */
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#postSolve(com.badlogic.gdx.physics.box2d.Contact, com.badlogic.gdx.physics.box2d.ContactImpulse)
	 */
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
