/**
 * 
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.AudioPlayer;
import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.TimeUtils;
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
		if (body1.getUserData() instanceof BulletType0 && !contact.getFixtureB().isSensor()) {			
			bulletImpact((Bullet) body1.getUserData(), (AbstractEntity) body2.getUserData());
		}
		else if (body2.getUserData() instanceof BulletType0 && !contact.getFixtureA().isSensor()) {
			bulletImpact((Bullet) body2.getUserData(), (AbstractEntity) body1.getUserData());
		}
		else if (body1.getUserData() instanceof BulletType1 && body2.getUserData() instanceof Ship) {
			Ship ship = (Ship) body2.getUserData();
			if (!ship.isSpeedOfLightEnabled()) {				
				bulletImpact((Bullet) body1.getUserData(), (AbstractEntity) body2.getUserData());
			}
		}
		else if (body2.getUserData() instanceof BulletType1 && body1.getUserData() instanceof Ship) {
			Ship ship = (Ship) body1.getUserData();
			if (!ship.isSpeedOfLightEnabled()) {				
				bulletImpact((Bullet) body2.getUserData(), (AbstractEntity) body1.getUserData());
			}
		}
		
		// Ship collision
		else if (body1.getUserData() instanceof Ship) {	
			// Powerup collection
			Ship ship = (Ship) body1.getUserData();
			boolean	notCollide = false;
			// Kill other entities in light speed
			if (!contact.getFixtureB().isSensor()) {
				AbstractEntity e = (AbstractEntity) body2.getUserData();
				if (ship.isSpeedOfLightEnabled()) {	
					if (!(e instanceof Sun || e instanceof Planet)){
						e.setHealth(0);
					}
				}
				else {
					long currentTime = TimeUtils.millis();
					if (currentTime - e.lastContactTime >= 500) {
						ship.healthChange(-1);						
						e.lastContactTime = currentTime;
						AudioPlayer.shipImpact();
					}
				}
			}
			if (body2.getUserData() instanceof PowerupHealthUp) {
				healthUpActivate((PowerupHealthUp) body2.getUserData(), ship);
				 AudioPlayer.healthUp();
			}
			else if (body2.getUserData() instanceof PowerupSpeedOfLight) {
				speedOfLightActivate((PowerupSpeedOfLight) body2.getUserData(), ship);
				 AudioPlayer.speedOfLightOn();
			}
			else if (body2.getUserData() instanceof Points) {
				pointsCollected((Points) body2.getUserData(), ship);	
			}
			
			// Game changers collection
			else if (body2.getUserData() instanceof Sun && !notCollide) {
				if (contact.getFixtureB().isSensor()) {
					sunActivate((Sun) body2.getUserData(), ship);
				}
				else {
					ship.healthChange(-2);
				}	
			}
			else if (body2.getUserData() instanceof Planet && !notCollide) {
				if (contact.getFixtureB().isSensor()) {
					gravityActivate((Planet) body2.getUserData(), ship);
				}
			}
			else if (body2.getUserData() instanceof MagneticObject && !notCollide) {
				if (contact.getFixtureB().isSensor()) {
					magnetActivate((MagneticObject) body2.getUserData(), ship);
				}
				else {
					ship.healthChange(-1);
				}	
			}
		}
		
		else if (body2.getUserData() instanceof Ship) {
			// Powerup collection
			Ship ship = (Ship) body2.getUserData();
			// Kill other entities in light speed
			if (!contact.getFixtureA().isSensor()) {
				AbstractEntity e = (AbstractEntity) body1.getUserData();
				if (ship.isSpeedOfLightEnabled()) {	
					if (!(e instanceof Sun || e instanceof Planet)){
						e.setHealth(0);
					}
				}
				else {
					long currentTime = TimeUtils.millis();
					if (currentTime - e.lastContactTime >= 500) {
						ship.healthChange(-1);						
						e.lastContactTime = currentTime;
						AudioPlayer.shipImpact();
					}
				}
			}
			else if (body1.getUserData() instanceof PowerupHealthUp) {
				healthUpActivate((PowerupHealthUp) body1.getUserData(), ship);
				 AudioPlayer.healthUp();
			}
			else if (body1.getUserData() instanceof PowerupSpeedOfLight) {
				speedOfLightActivate((PowerupSpeedOfLight) body1.getUserData(), ship);
				AudioPlayer.speedOfLightOn();
			}
			else if (body1.getUserData() instanceof Points) {
				pointsCollected((Points) body1.getUserData(), ship);	
			}
			// Game changers collection
			else if (body1.getUserData() instanceof Sun) {
				if (contact.getFixtureA().isSensor()) {
					sunActivate((Sun) body1.getUserData(), ship);
				}
				else {
					ship.healthChange(-2);
				}	
			}
			else if (body1.getUserData() instanceof Planet) {
				if (contact.getFixtureA().isSensor()) {
					gravityActivate((Planet) body1.getUserData(), ship);
				}	
			}
			else if (body1.getUserData() instanceof MagneticObject) {
				if (contact.getFixtureA().isSensor()) {
					magnetActivate((MagneticObject) body1.getUserData(), ship);
				}
				else {
					ship.healthChange(-1);
				}	
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
		if (!(e instanceof Powerup || e instanceof Bullet)) {
			if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Bullet " + Integer.toHexString(b.hashCode()) + " collided with " + e.toString() + " " + Integer.toHexString(e.hashCode()));
			e.healthChange((-1) * b.getDamage()); 
			b.setHealth(0);
			AudioPlayer.bulletImpact();
			
			// Hit score
			gameWorld.getScoreKeeper().add(e.getHitScore());
		}
	}
	
	/**
	 * Called when the "points" object is picked up
	 * Removes the object and adds score to the ScoreKeeper
	 * @param p the object picked up
	 * @param s the ship
	 */
	public void pointsCollected(Points p, Ship s) {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Points collected: " + Integer.toHexString(p.hashCode()) + " Amount: " + Points.SCORE + " points added");
		gameWorld.getScoreKeeper().add(Points.SCORE);
		AudioPlayer.pointsPickedUp();
		p.setHealth(0);
	}
	
	/**
	 * Called when the "health up" powerup is picked up
	 * Removes the powerup and gives health to the ship
	 * @param p the powerup picked up
	 * @param s the ship
	 */
	public void healthUpActivate(PowerupHealthUp p, Ship s) {
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Health up " + Integer.toHexString(p.hashCode()) + " activated: " + PowerupHealthUp.HEALTH_GIVEN + " health points restored");
		s.healthChange(PowerupHealthUp.HEALTH_GIVEN);
		AudioPlayer.healthUp();
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
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, "Speed of light " + Integer.toHexString(p.hashCode()) + " activated");
		s.enableSpeedOfLight();
		new Timer().scheduleTask(new Task() {
			@Override
			public void run() {
				s.disableSpeedOfLight();
			}
		}, PowerupSpeedOfLight.COOLDOWN_SECONDS);
		p.setHealth(0);
	}
	
	/**
	 * Called when the ship enters the sun's zone of heat
	 * Sets the sunActivated boolean variable in GameWorld to "true"
	 * Sends the instance of Sun over to GameWorld
	 * @param b
	 * @param e
	 */
	public void sunActivate(Sun b, Ship e) {
		gameWorld.setSun(b);
		gameWorld.sunActivated = true;
		if (!gameWorld.ship.isSpeedOfLightEnabled())
		gameWorld.renderer.shipHeatIndicatorLabel.setText("Heat: Rising!");
	}
	
	public void sunDeactivate() {
		gameWorld.sunActivated = false;
		gameWorld.shipHeatIndicator = 0;
		gameWorld.renderer.shipHeatIndicatorLabel.setText("Heat: Stable");
	}
	
	/**
	 * Called when the ship enters a planet's gravitational pull
	 * Sets the gravityActivated boolean variable in GameWorld to "true"
	 * Sends the instance of Planet over to GameWorld
	 * @param b
	 * @param e
	 */
	public void gravityActivate(Planet b, Ship e) {
		gameWorld.setPlanet(b);
		gameWorld.gravityActivated = true;
	}
	
	public void gravityDeactivate() {
		gameWorld.gravityActivated = false;
	}
	
	/**
	 * Called when the ship enters a magnetic object's magnetic field
	 * Sets the magnetActivated boolean variable in GameWorld to "true"
	 * Sends the instance of MagneticObject over to GameWorld
	 * @param b
	 * @param e
	 */
	public void magnetActivate(MagneticObject b, Ship e) {
		gameWorld.setMagneticObject(b);
		gameWorld.magnetActivated = true;
	}
	
	public void magnetDeactivate() {
		gameWorld.magnetActivated = false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.badlogic.gdx.physics.box2d.ContactListener#endContact(com.badlogic.gdx.physics.box2d.Contact)
	 */
	@Override
	public void endContact(Contact contact) {
		if (contact.getFixtureA() != null && contact.getFixtureB() != null) {
			// Get colliding bodies from contact points
			Body body1 = contact.getFixtureA().getBody(); 
			Body body2 = contact.getFixtureB().getBody();
			
			// Ship collision
			if (body1.getUserData() instanceof Ship) {	
				// Game Changers collection
				if (body2.getUserData() instanceof Sun) {
					if (contact.getFixtureB().isSensor()) {
						sunDeactivate();
					}
				}
				else if (body2.getUserData() instanceof Planet) {
					if (contact.getFixtureB().isSensor()) {
						gravityDeactivate();
					}	
				}
				else if (body2.getUserData() instanceof MagneticObject) {
					if (contact.getFixtureB().isSensor()) {
						magnetDeactivate();
					}	
				}
			}
			else if (body2.getUserData() instanceof Ship) {
				// Game Changers collection
				if (body1.getUserData() instanceof Sun) {
					if (contact.getFixtureA().isSensor()) {
						sunDeactivate();
					}
				}
				else if (body1.getUserData() instanceof Planet) {
					if (contact.getFixtureA().isSensor()) {
						gravityDeactivate();
					}	
				}
				else if (body1.getUserData() instanceof MagneticObject) {
					if (contact.getFixtureA().isSensor()) {
						magnetDeactivate();
					}
				}
			}
		}
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
