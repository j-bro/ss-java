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
import com.asdf.ssjava.screens.LevelCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;

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
	
	Image fullHeartImage1, fullHeartImage2, fullHeartImage3, halfHeartImage;
	Texture fullHeartTexture, halfHeartTexture;
	
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
		
		asteroidTexture = game.assetManager.get("data/textures/brick.png", Texture.class);
		asteroidTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		enemyType1Texture = game.assetManager.get("data/textures/enemy1.png", Texture.class);
		enemyType1Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bulletType0Texture = game.assetManager.get("data/textures/bullet_strip.png", Texture.class);
		bulletType0Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		powerupHealthUpTexture = game.assetManager.get("data/textures/healthUp.png", Texture.class);
		powerupHealthUpTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		powerupSpeedOfLightTexture = game.assetManager.get("data/textures/waterball.png", Texture.class);
		powerupHealthUpTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		planetTexture = game.assetManager.get("data/textures/planet.png", Texture.class);
		planetTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		// TODO extra textures
		bulletType1Texture = bulletType0Texture;
		bulletType2Texture = bulletType0Texture;
		
		fullHeartTexture = game.assetManager.get("data/textures/heart_full.png", Texture.class);
		halfHeartTexture = game.assetManager.get("data/textures/heart_half.png", Texture.class);
		
		ship = gameWorld.getShip();
		
		// HUD stage
		
		if (stage == null) {
			stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		}
		stage.clear();
		
		if (gameWorld.getWorldType() == 0) { // game HUD
			LabelStyle ls = new LabelStyle(game.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class), Color.WHITE);
			scoreLabel = new Label("Score: " + gameWorld.scoreKeeper.getScore(), ls);
			scoreLabel.setX(10);
			scoreLabel.setY(Gdx.graphics.getHeight() - 10 - scoreLabel.getHeight());
			
			stage.addActor(scoreLabel);
			
			fullHeartImage1 = new Image(fullHeartTexture);
			fullHeartImage2 = new Image(fullHeartTexture);
			fullHeartImage3 = new Image(fullHeartTexture);
			halfHeartImage = new Image(halfHeartTexture);
			
			int heartScale = 10;
			
			fullHeartImage1.setBounds(10, 10, heartScale, heartScale);
			fullHeartImage2.setBounds(10, 10, heartScale, heartScale);
			fullHeartImage3.setBounds(10, 10, heartScale, heartScale);
			fullHeartImage2.setPosition(10 + fullHeartImage1.getWidth(), 10);
			fullHeartImage3.setPosition(10 + fullHeartImage1.getWidth() + fullHeartImage2.getWidth(), 10);
			halfHeartImage.setY(10);
			
			stage.addActor(fullHeartImage1);
			stage.addActor(fullHeartImage2);
			stage.addActor(fullHeartImage3);
			stage.addActor(halfHeartImage);
		}
		else { // TODO creator HUD
						
		}
		
		
		// shape renderer
		debugRenderer = new Box2DDebugRenderer();
	}
	
	
	/**
	 * Render loop
	 */
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);  
		
		// camera follows ship if in game
		if (gameWorld.getWorldType() == 0) {			
			cam.position.set(ship.getPosition().x + 20, cam.position.y, 0);
		}
		else {
			
		}

		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		
			for (Obstacle o: gameWorld.level.obstacles) {
				Texture obstacleTexture = getTexture(o);
				batch.draw(obstacleTexture, o.getPosition().x, o.getPosition().y, o.getWidth(), o.getHeight());
			}
			
			for (Enemy e: gameWorld.level.enemies) {
				Texture enemyTexture = getTexture(e);
				batch.draw(enemyTexture, e.getPosition().x, e.getPosition().y, e.getWidth() / 2, e.getHeight() / 2 , e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, enemyTexture.getWidth(), enemyTexture.getHeight(), false, false);
			}
			
			// bullet rendering
			for (Bullet b: gameWorld.bullets) {
				Texture bulletTexture = getTexture(b);
				batch.draw(bulletTexture, b.getPosition().x, b.getPosition().y, b.getWidth() / 2, b.getHeight() / 2 , b.getWidth(), b.getHeight(), 1, 1, b.getRotation(), 0, 0, bulletTexture.getWidth(), bulletTexture.getHeight(), false, false);
			}
			
			for (Powerup p: gameWorld.level.powerups) {
				Texture powerupTexture = getTexture(p);
				batch.draw(powerupTexture, p.getPosition().x, p.getPosition().y, p.getWidth(), p.getHeight());
			}
			
			// game changer rendering
			for (Obstacle g: gameWorld.level.gameChangers) {
				Texture planetTexture = getTexture(g);
				batch.draw(planetTexture, g.getPosition().x, g.getPosition().y, g.getWidth(), g.getHeight());
			}
			
			// ship rendering
			batch.draw(shipTexture, ship.getPosition().x, ship.getPosition().y, ship.getWidth() / 2, ship.getHeight() / 2, ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 8, 4, 48, 24, false, false);
			
		batch.end();
		
		// game HUD
		if (gameWorld.getWorldType() == 0) {
			// score
			scoreLabel.setText("Score: " + new Integer(gameWorld.scoreKeeper.getScore()).toString());
			
			// health (hearts) display
			switch(ship.getHealth()) {
			case 0:
				halfHeartImage.setVisible(false);
				fullHeartImage1.setVisible(false);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				break;
			case 1: 
				halfHeartImage.setX(10);
				halfHeartImage.setVisible(true);
				fullHeartImage1.setVisible(false);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				break;
			case 2: 
				halfHeartImage.setVisible(false);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				break;
			case 3: 
				halfHeartImage.setX(10 + fullHeartImage1.getWidth());
				halfHeartImage.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				break;
			case 4: 
				halfHeartImage.setVisible(false);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(false);
				break;
			case 5: 
				halfHeartImage.setX(10 + fullHeartImage1.getWidth() + fullHeartImage2.getWidth());
				halfHeartImage.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(false);
				break;
			case 6: 
				halfHeartImage.setVisible(false);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(true);
				break;
			}
			// TODO hearts problem
			
		}
		// creator HUD
		else {
			
		}
		
		stage.act();
		stage.draw();
		
		
		if (SSJava.DEBUG) { 			
			debugRenderer.render(gameWorld.box2DWorld, cam.combined);
		}
	}

	/**
	 * Dispose method
	 */
	public void dispose() {
		batch.dispose();
		
		shipTexture.dispose();
		
		enemyType1Texture.dispose();
		enemyType2Texture.dispose();
		enemyType3Texture.dispose();
		
		bulletType1Texture.dispose();
		bulletType2Texture.dispose();
		bulletType3Texture.dispose();
		
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
}
