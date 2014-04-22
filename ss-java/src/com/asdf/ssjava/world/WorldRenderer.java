/**
 * Manages the rendering and drawing dependencies of all entities present in the World.
 * Draws the HUD comprising of the score, the player's life points and the level progress.
 */
package com.asdf.ssjava.world;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.AbstractEntity;
import com.asdf.ssjava.entities.Asteroid;
import com.asdf.ssjava.entities.Bullet;
import com.asdf.ssjava.entities.BulletType0;
import com.asdf.ssjava.entities.BulletType1;
import com.asdf.ssjava.entities.Enemy;
import com.asdf.ssjava.entities.EnemyType1;
import com.asdf.ssjava.entities.Obstacle;
import com.asdf.ssjava.entities.Planet;
import com.asdf.ssjava.entities.Powerup;
import com.asdf.ssjava.entities.PowerupHealthUp;
import com.asdf.ssjava.entities.PowerupSpeedOfLight;
import com.asdf.ssjava.entities.Ship;
import com.asdf.ssjava.entities.SpaceRock;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 * @author Jeremy Brown
 *
 */

public class WorldRenderer {

	/**
	 * The game's instance
	 */
	SSJava game;
	/**
	 * The World's instance
	 */
	GameWorld gameWorld;
	
	/**
	 * The sprite batches responsible for drawing all elements in the world
	 */
	SpriteBatch batch;
	
	/**
	 * The stage for drawing the score
	 */
	Stage stage;
	Label scoreLabel;
	
	/**
	 * The debug text elements
	 */
	Label debugLabel;
	
	/**
	 * The camera 
	 */
	OrthographicCamera cam;
	
	/**
	 * The ship's instance
	 */
	Ship ship;
	
	/**
	 * Textures for the various elements 
	 */
	Texture shipTexture;
	Texture spaceRockTexture, asteroidTexture;
	Texture enemyType1Texture, enemyType2Texture, enemyType3Texture;
	Texture bulletType0Texture, bulletType1Texture, bulletType2Texture, bulletType3Texture;
	Texture speedOfLightTexture, healthUpTexture;
	Texture powerupHealthUpTexture, powerupSpeedOfLightTexture;
	Texture planetTexture;
	
	Image fullHeartImage1, fullHeartImage2, fullHeartImage3, emptyHeartImage1, emptyHeartImage2, emptyHeartImage3, halfHeartImage;
	Texture fullHeartTexture, emptyHeartTexture, halfHeartTexture;
	
	Image asteroidImage, spaceRockImage, enemyType1Image, planetImage, powerupHealthUpImage, powerupSpeedOfLightImage;
	
	
	/**
	 * The entity type to add to the level
	 */
	private AbstractEntity entityToAdd;

	// TODO ...
	float width, height;
	
	/**
	 * TODO Shape renderer for debugging ONLY!
	 */
	Box2DDebugRenderer debugRenderer;
	
	/**
	 * Creates the world instance
	 * @param world
	 */
	public WorldRenderer(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		game = gameWorld.game;
		
		// TODO potentially fix this square/rectangle
		width = Gdx.graphics.getWidth() / 20;
		height = Gdx.graphics.getHeight() / 20;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		shipTexture = game.assetManager.get("data/textures/shipA.png", Texture.class);
		shipTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
//		shipTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		spaceRockTexture = game.assetManager.get("data/textures/space_rock.png", Texture.class);
		spaceRockTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		asteroidTexture = game.assetManager.get("data/textures/asteroid.png", Texture.class);
		asteroidTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		enemyType1Texture = game.assetManager.get("data/textures/enemy_1.png", Texture.class);
		enemyType1Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bulletType0Texture = game.assetManager.get("data/textures/bullet_strip.png", Texture.class);
		bulletType0Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		powerupHealthUpTexture = game.assetManager.get("data/textures/health_up.png", Texture.class);
		powerupHealthUpTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		powerupSpeedOfLightTexture = game.assetManager.get("data/textures/speed_of_light.png", Texture.class);
		powerupHealthUpTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		planetTexture = game.assetManager.get("data/textures/planet.png", Texture.class);
		planetTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		// TODO extra textures
		bulletType1Texture = bulletType0Texture;
		bulletType2Texture = bulletType0Texture;
		
		fullHeartTexture = game.assetManager.get("data/textures/heart_full.png", Texture.class);
		fullHeartTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		emptyHeartTexture = game.assetManager.get("data/textures/heart_empty.png", Texture.class);
		emptyHeartTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		halfHeartTexture = game.assetManager.get("data/textures/heart_half.png", Texture.class);
		halfHeartTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		ship = gameWorld.getShip();
		
		// HUD stage
		if (stage == null) {
			stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		}
		stage.clear();
		
		// Game HUD
		if (gameWorld.getWorldType() == 0) {
			LabelStyle ls = new LabelStyle(game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class), Color.WHITE);
			scoreLabel = new Label("Score: " + gameWorld.scoreKeeper.getScore(), ls);
			scoreLabel.setX(10);
			scoreLabel.setY(Gdx.graphics.getHeight() - 10 - scoreLabel.getHeight());
			
			stage.addActor(scoreLabel);
			
			fullHeartImage1 = new Image(fullHeartTexture);
			fullHeartImage2 = new Image(fullHeartTexture);
			fullHeartImage3 = new Image(fullHeartTexture);
			emptyHeartImage1 = new Image(emptyHeartTexture);
			emptyHeartImage2 = new Image(emptyHeartTexture);
			emptyHeartImage3 = new Image(emptyHeartTexture);
			halfHeartImage = new Image(halfHeartTexture);
			
			int heartScale = 50;
			
			fullHeartImage1.setBounds(10, 10, heartScale, heartScale);
			fullHeartImage2.setBounds(10, 10, heartScale, heartScale);
			fullHeartImage3.setBounds(10, 10, heartScale, heartScale);
			emptyHeartImage1.setBounds(10, 10, heartScale, heartScale);
			emptyHeartImage2.setBounds(10, 10, heartScale, heartScale);
			emptyHeartImage3.setBounds(10, 10, heartScale, heartScale);
			halfHeartImage.setBounds(10, 10, heartScale, heartScale);
			
			fullHeartImage2.setX(10 + fullHeartImage1.getWidth());
			fullHeartImage3.setX(10 + fullHeartImage1.getWidth() + fullHeartImage2.getWidth());
			
			stage.addActor(fullHeartImage1);
			stage.addActor(fullHeartImage2);
			stage.addActor(fullHeartImage3);
			stage.addActor(halfHeartImage);
		}
		// Creator HUD
		else {
			asteroidImage = new Image(asteroidTexture);
			spaceRockImage = new Image(spaceRockTexture);
			planetImage = new Image(planetTexture);
			enemyType1Image = new Image(enemyType1Texture);
			powerupHealthUpImage = new Image(powerupHealthUpTexture);
			powerupSpeedOfLightImage = new Image(powerupSpeedOfLightTexture);
			
			int imageScale = 50;
			
			asteroidImage.setBounds(10, 10, imageScale * 2, imageScale);
			spaceRockImage.setBounds(10, 10, imageScale, imageScale);
			planetImage.setBounds(10, 10, imageScale, imageScale);
			enemyType1Image.setBounds(10, 10, imageScale * 2, imageScale);
			powerupHealthUpImage.setBounds(10, 10, imageScale, imageScale);
			powerupSpeedOfLightImage.setBounds(10, 10, imageScale * 2, imageScale);
			
			stage.addActor(asteroidImage);
			stage.addActor(spaceRockImage);
			stage.addActor(planetImage);
			stage.addActor(enemyType1Image);
			stage.addActor(powerupHealthUpImage);
			stage.addActor(powerupSpeedOfLightImage);
		}
		
		if (SSJava.DEBUG) {
			// Debug text
			LabelStyle dbls = new LabelStyle(game.assetManager.get("data/fonts/debugFont-14.fnt", BitmapFont.class), Color.WHITE);
			debugLabel = new Label("DEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER", dbls);
			debugLabel.setX(10);
			debugLabel.setY(cam.position.y + cam.viewportHeight / 2 - 40 - debugLabel.getHeight());
			
			stage.addActor(debugLabel);
			stage.addActor(new Label("", dbls));
			
			// shape renderer
			debugRenderer = new Box2DDebugRenderer();
		}
	}
	
	
	/**
	 * Render loop
	 */
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);  
		
		// camera follows ship if in game
		if (gameWorld.getWorldType() == GameWorld.GAME_TYPE) {			
			cam.position.set(ship.getPosition().x + 20, cam.position.y, 0);
		}
		else if (gameWorld.getWorldType() == GameWorld.CREATOR_TYPE) {
			
		}

		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
		/* TODO simpler rendering
		Array<Body> bodiesArray = new Array<Body>();
		gameWorld.box2DWorld.getBodies(bodiesArray);
		Iterator<Body> bi = bodiesArray.iterator(); 
		while (bi.hasNext()) {
			Body b = bi.next();

		    // Get the entity corresponding to the body
		    AbstractEntity e = (AbstractEntity) b.getUserData();

		    if (e != null) {
		    	if (e.isVisible()) {
		    		Texture texture = getTexture(e);
		    		batch.draw(texture, e.getPosition().x - e.getWidth() / 2, e.getPosition().y - e.getHeight() / 2, e.getWidth() / 2, e.getHeight() / 2 , e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
		    	}
		    }
		}
		*/
			// obstacles rendering
			for (Obstacle o: gameWorld.level.obstacles) {
				if (o.isVisible()) {
					Texture obstacleTexture = getTexture(o);
					batch.draw(obstacleTexture, o.getPosition().x - o.getWidth() / 2, o.getPosition().y - o.getHeight() / 2, o.getWidth() / 2, o.getHeight() / 2 , o.getWidth(), o.getHeight(), 1, 1, o.getRotation(), 0, 0, obstacleTexture.getWidth(), obstacleTexture.getHeight(), false, false);					
				}
			}
			// enemies rendering
			for (Enemy e: gameWorld.level.enemies) {
				if (e.isVisible()) {					
					Texture enemyTexture = getTexture(e);
					batch.draw(enemyTexture, e.getPosition().x - e.getWidth() / 2, e.getPosition().y - e.getHeight() / 2, e.getWidth() / 2, e.getHeight() / 2 , e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, enemyTexture.getWidth(), enemyTexture.getHeight(), false, false);
				}
			}
			// bullet rendering
			for (Bullet b: gameWorld.bullets) {
				if (b.isVisible()) {					
					Texture bulletTexture = getTexture(b);
					batch.draw(bulletTexture, b.getPosition().x - b.getWidth() / 2, b.getPosition().y - b.getHeight() / 2, b.getWidth() / 2, b.getHeight() / 2 , b.getWidth(), b.getHeight(), 1, 1, b.getRotation(), 0, 0, bulletTexture.getWidth(), bulletTexture.getHeight(), false, false);
				}
			}
			// game changer rendering
			for (Obstacle g: gameWorld.level.gameChangers) {
				if (g.isVisible()) {
					Texture gameChangerTexture = getTexture(g);
					batch.draw(gameChangerTexture, g.getPosition().x - g.getWidth() / 2, g.getPosition().y - g.getHeight() / 2, g.getWidth() / 2, g.getHeight() / 2 , g.getWidth(), g.getHeight(), 1, 1, g.getRotation(), 0, 0, gameChangerTexture.getWidth(), gameChangerTexture.getHeight(), false, false);
				}
			}
			for (Powerup p: gameWorld.level.powerups) {
				if (p.isVisible()) {					
					Texture powerupTexture = getTexture(p);
					batch.draw(powerupTexture, p.getPosition().x - p.getWidth() / 2, p.getPosition().y - p.getHeight() / 2, p.getWidth() / 2, p.getHeight() / 2 , p.getWidth(), p.getHeight(), 1, 1, p.getRotation(), 0, 0, powerupTexture.getWidth(), powerupTexture.getHeight(), false, false);
				}
			}
			// ship rendering
			if (ship.isVisible()) {
				batch.draw(shipTexture, ship.getPosition().x - ship.getWidth() / 2, ship.getPosition().y - ship.getHeight() / 2, ship.getWidth() / 2, ship.getHeight() / 2, ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 8, 4, 48, 24, false, false);
			}
			
		batch.end();
		
		
		// game HUD
		if (gameWorld.getWorldType() == GameWorld.GAME_TYPE) {
			// score
			scoreLabel.setText("Score: " + new Integer(gameWorld.scoreKeeper.getScore()).toString());
			
			// health (hearts) display
			switch(ship.getHealth()) {
			case 0:
				emptyHeartImage1.setVisible(true);
				emptyHeartImage2.setVisible(true);
				emptyHeartImage3.setVisible(true);
				fullHeartImage1.setVisible(false);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				halfHeartImage.setVisible(false);
				break;
			case 1: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(true);
				emptyHeartImage3.setVisible(true);
				fullHeartImage1.setVisible(false);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				halfHeartImage.setX(10);
				halfHeartImage.setVisible(true);
				break;
			case 2: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(true);
				emptyHeartImage3.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				halfHeartImage.setVisible(false);
				break;
			case 3: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				halfHeartImage.setX(10 + fullHeartImage1.getWidth());
				halfHeartImage.setVisible(true);
				break;
			case 4: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(false);
				halfHeartImage.setVisible(false);
				break;
			case 5: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(false);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(false);
				halfHeartImage.setX(10 + fullHeartImage1.getWidth() + fullHeartImage2.getWidth());
				halfHeartImage.setVisible(true);
				break;
			case 6: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(false);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(true);
				halfHeartImage.setVisible(false);
				break;
			}
			
		}
		// creator HUD
		else if (gameWorld.getWorldType() == GameWorld.CREATOR_TYPE) {
			if (entityToAdd instanceof Asteroid) {
				asteroidImage.setVisible(true);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
			}
			else if (entityToAdd instanceof SpaceRock) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(true);
				planetImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
			}
			else if (entityToAdd instanceof Planet) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(true);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
			}
			else if (entityToAdd instanceof EnemyType1) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				enemyType1Image.setVisible(true);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
			}
			else if (entityToAdd instanceof PowerupHealthUp) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(true);
				powerupSpeedOfLightImage.setVisible(false);
			}
			else if (entityToAdd instanceof PowerupSpeedOfLight) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(true);
			}		
		}
		
		// Debug renderer
		if (SSJava.DEBUG) { 
			if (gameWorld.getWorldType() == GameWorld.GAME_TYPE) {
				debugRenderer.render(gameWorld.box2DWorld, cam.combined);

				float shipAngle = ship.getBody().getAngle();
				float mod = (float) (2 * Math.PI);
				float angleMod = (shipAngle < 0) ? (mod - (Math.abs(shipAngle) % mod) ) % mod : (shipAngle % mod);
				
				// Debug info
				debugLabel.setText("Position: " + (float) Math.round(ship.getBody().getPosition().x * 100) / 100 + " , " + (float) Math.round(ship.getBody().getPosition().y * 100) / 100 + 
						"\nAngle (rad): " + (float) Math.round(angleMod * 10000) / 10000 +
						"\nVelocity: " + (float) Math.round(ship.getBody().getLinearVelocity().x * 100) / 100 + " , " + (float) Math.round(ship.getBody().getLinearVelocity().y * 100) / 100 + 
						"\nHealth: " + ship.getHealth() + " half hearts" + 
						"\nLight speed enabled: " + ship.isLightSpeedEnabled());	
			}
			
			else if (gameWorld.getWorldType() == GameWorld.CREATOR_TYPE) {
				// Debug info
				debugLabel.setText("Position: " + (float) Math.round(cam.position.x * 100) / 100 + " , " + (float) Math.round(cam.position.y * 100) / 100 + 
						"\n" + 
						"\n" +  
						"\n" + 
						"\n");
			}
		}
		
		stage.act();
		stage.draw();
	}

	/**
	 * Dispose method
	 */
	public void dispose() {
		batch.dispose();		
		debugRenderer.dispose();
	}
	
	public Texture getTexture(AbstractEntity e) {
		if (e instanceof SpaceRock) {
			return spaceRockTexture;
		}
		else if (e instanceof Asteroid) {
			return asteroidTexture;
		}
		else if (e instanceof EnemyType1) {
			return enemyType1Texture;
		}
		else if (e instanceof PowerupHealthUp) {
			return powerupHealthUpTexture;
		}
		else if (e instanceof PowerupSpeedOfLight) {
			return powerupSpeedOfLightTexture;
		}
		else if (e instanceof Planet) {
			return planetTexture;
		}
		else if (e instanceof BulletType0) {
			return bulletType0Texture;
		}
		else if (e instanceof BulletType1) {
			return bulletType1Texture;
		}
		else {
			return null;
		}
	}
	
	/**
	 * 
	 * @return the renderer's camera
	 */
	public Camera getCamera() {
		return cam;
	}
	
	/**
	 * @return the entityToAdd
	 */
	public AbstractEntity getEntityToAdd() {
		return entityToAdd;
	}
	/**
	 * @param entityToAdd the entityToAdd to set
	 */
	public void setEntityToAdd(AbstractEntity entityToAdd) {
		this.entityToAdd = entityToAdd;
	}
}
