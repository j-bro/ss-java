package com.asdf.ssjava.world;

import com.asdf.ssjava.SSJava;
import com.asdf.ssjava.entities.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Manages the rendering and drawing dependencies of all entities present in the associated GameWorld instance.
 * Draws the HUD comprising of the score, the player's life points, the ship heat indicator, and the level progress.
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class WorldRenderer {

	/**
	 * The SSJava instance
	 */
	SSJava game;
	/**
	 * The GameWorld instance
	 */
	GameWorld gameWorld;
	
	/**
	 * The sprite batches responsible for drawing all elements in the world
	 */
	SpriteBatch batch;
	
	/**
	 * The stage for drawing the score, life hearts and level progress
	 */
	Stage stage;
	
	/**
	 * Labels for the game HUD
	 */
	Label scoreLabel, progressLabel;
	
	/**
	 * Shape renderer for debugging
	 */
	Box2DDebugRenderer debugRenderer;
	
	/**
	 * The debug text elements
	 */
	Label debugLabel;
	
	/**
	 * The ship's heat indicator
	 */
	Label shipHeatIndicatorLabel;
	
	/**
	 * The camera 
	 */
	OrthographicCamera cam;
	
	/**
	 * The ship's instance
	 */
	Ship ship;
	
	/**
	 * Textures for all game elements 
	 */
	Texture shipTexture, spaceRockTexture, asteroidTexture, enemyType1Texture, enemyType2Texture, enemyType3Texture, bulletType0Texture, bulletType1Texture, bulletType2Texture, bulletType3Texture, speedOfLightTexture, healthUpTexture, powerupHealthUpTexture, powerupSpeedOfLightTexture, pointsTexture, planetTexture, sunTexture, magneticObjectTexture;
	
	/**
	 * Background texture
	 */
	Texture bgTexture;
	
	/**
	 * Background sprite
	 */
	Sprite bgSprite;
	
	/**
	 * Images for the game HUD stage
	 */
	Image fullHeartImage1, fullHeartImage2, fullHeartImage3, fullHeartImage4, emptyHeartImage1, emptyHeartImage2, emptyHeartImage3, emptyHeartImage4, halfHeartImage;
	
	/**
	 * Texture for the game HUD stage
	 */
	Texture fullHeartTexture, emptyHeartTexture, halfHeartTexture;
	
	/**
	 * Images for the level creator HUD stage
	 */
	Image asteroidImage, spaceRockImage, enemyType1Image, planetImage, sunImage, magneticObjectImage, powerupHealthUpImage, powerupSpeedOfLightImage, pointsImage;
	
	/**
	 * The entity type to add to the level
	 */
	private AbstractEntity entityToAdd;
	
	/**
	 * The currently selected entity
	 */
	private AbstractEntity selectedEntity;
	
	/**
	 * Shape renderer for selected entity debugging
	 */
	ShapeRenderer sr;

	/**
	 * The display width & height
	 */
	float width, height;
	
	/**
	 * Creates a renderer for the specified GameWorld instance. 
	 * @param gameWorld the GameWorle instance to be renderered
	 */
	public WorldRenderer(final GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		game = gameWorld.game;
		
		width = 64;
		height = 36;
		if (SSJava.DEBUG) Gdx.app.log(SSJava.LOG, width + " " + height);
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		cam.update();
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(cam.combined);
		
		shipTexture = SSJava.assetManager.get("data/textures/shipA.png", Texture.class);
		shipTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		spaceRockTexture = SSJava.assetManager.get("data/textures/space_rock.png", Texture.class);
		spaceRockTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		asteroidTexture = SSJava.assetManager.get("data/textures/asteroid.png", Texture.class);
		asteroidTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		enemyType1Texture = SSJava.assetManager.get("data/textures/enemy_1.png", Texture.class);
		enemyType1Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bulletType0Texture = SSJava.assetManager.get("data/textures/good_bullet.png", Texture.class);
		bulletType0Texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		powerupHealthUpTexture = SSJava.assetManager.get("data/textures/health_up.png", Texture.class);
		powerupHealthUpTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		powerupSpeedOfLightTexture = SSJava.assetManager.get("data/textures/speed_of_light.png", Texture.class);
		powerupHealthUpTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		pointsTexture = SSJava.assetManager.get("data/textures/coin.png", Texture.class);
		pointsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		planetTexture = SSJava.assetManager.get("data/textures/planet.png", Texture.class);
		planetTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		sunTexture = SSJava.assetManager.get("data/textures/sun.png", Texture.class);
		sunTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		magneticObjectTexture = SSJava.assetManager.get("data/textures/space_junk.png", Texture.class);
		magneticObjectTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		bulletType1Texture = bulletType0Texture;
		
		bgTexture = SSJava.assetManager.get("data/textures/backgrounds/background_sparks.png", Texture.class);
		bgTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear); 
		bgSprite = new Sprite(bgTexture);
		
		fullHeartTexture = SSJava.assetManager.get("data/textures/heart_full.png", Texture.class);
		fullHeartTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		emptyHeartTexture = SSJava.assetManager.get("data/textures/heart_empty.png", Texture.class);
		emptyHeartTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		halfHeartTexture = SSJava.assetManager.get("data/textures/heart_half.png", Texture.class);
		halfHeartTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		ship = gameWorld.getShip();
		
		createHUD(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			
		// Shape (hitbox) renderer
		debugRenderer = new Box2DDebugRenderer();
	}
	
	
	/**
	 * Render loop. 
	 * 
	 */
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10. GL_COLOR_BUFFER_BIT);  
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Camera follows ship if in GAME_TYPE mode
		if (gameWorld.getWorldType() == GameWorld.GAME_TYPE) {			
			cam.position.set(ship.getPosition().x + 20, cam.position.y, 0);
		}
		else if (gameWorld.getWorldType() == GameWorld.CREATOR_TYPE) {
			
		}
		cam.update();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		
		// Draw background
		if (gameWorld.getLevel().getBackgroundPath() != null) {
			bgSprite.setBounds(cam.position.x - cam.viewportWidth / 2, cam.position.y - cam.viewportHeight / 2, cam.viewportWidth, cam.viewportHeight);
			bgSprite.draw(batch);
		}
		
		// Obstacles rendering
		for (Obstacle o: gameWorld.getLevel().obstacles) {
			if (o.isVisible()) {
				Texture obstacleTexture = getTexture(o);
				batch.draw(obstacleTexture, o.getPosition().x - o.getWidth() / 2, o.getPosition().y - o.getHeight() / 2, o.getWidth() / 2, o.getHeight() / 2 , o.getWidth(), o.getHeight(), 1, 1, o.getRotation(), 0, 0, obstacleTexture.getWidth(), obstacleTexture.getHeight(), false, false);					
			}
		}
		// Enemies rendering
		for (Enemy e: gameWorld.getLevel().enemies) {
			if (e.isVisible()) {					
				Texture enemyTexture = getTexture(e);
				batch.draw(enemyTexture, e.getPosition().x - e.getWidth() / 2, e.getPosition().y - e.getHeight() / 2, e.getWidth() / 2, e.getHeight() / 2 , e.getWidth(), e.getHeight(), 1, 1, e.getRotation(), 0, 0, enemyTexture.getWidth(), enemyTexture.getHeight(), false, false);
			}
		}
		// Bullet rendering
		for (Bullet b: gameWorld.bullets) {
			if (b.isVisible()) {					
				Texture bulletTexture = getTexture(b);
				batch.draw(bulletTexture, b.getPosition().x - b.getWidth() / 2, b.getPosition().y - b.getHeight() / 2, b.getWidth() / 2, b.getHeight() / 2 , b.getWidth(), b.getHeight(), 1, 1, b.getRotation(), 0, 0, bulletTexture.getWidth(), bulletTexture.getHeight(), false, false);
			}
		}
		// Game changer rendering
		for (Obstacle g: gameWorld.getLevel().gameChangers) {
			if (g.isVisible()) {
				Texture gameChangerTexture = getTexture(g);
				batch.draw(gameChangerTexture, g.getPosition().x - g.getWidth() / 2, g.getPosition().y - g.getHeight() / 2, g.getWidth() / 2, g.getHeight() / 2 , g.getWidth(), g.getHeight(), 1, 1, g.getRotation(), 0, 0, gameChangerTexture.getWidth(), gameChangerTexture.getHeight(), false, false);
			}
		}
		for (Powerup p: gameWorld.getLevel().powerups) {
			if (p.isVisible()) {					
				Texture powerupTexture = getTexture(p);
				batch.draw(powerupTexture, p.getPosition().x - p.getWidth() / 2, p.getPosition().y - p.getHeight() / 2, p.getWidth() / 2, p.getHeight() / 2 , p.getWidth(), p.getHeight(), 1, 1, p.getRotation(), 0, 0, powerupTexture.getWidth(), powerupTexture.getHeight(), false, false);
			}
		}
		// Ship rendering
		if (ship.isVisible()) {
			batch.draw(shipTexture, ship.getPosition().x - ship.getWidth() / 2, ship.getPosition().y - ship.getHeight() / 2, ship.getWidth() / 2, ship.getHeight() / 2, ship.getWidth(), ship.getHeight(), 1, 1, ship.getRotation(), 8, 4, 48, 24, false, false);
		}
		batch.end();
		
		// Game HUD
		updateHUD();
	}
	
	/**
	 * Creates the HUD display. 
	 * @param width the width of the screen
	 * @param height the height of the screen
	 */
	public void createHUD(int width, int height) {
		// HUD stage
		if (stage == null) {
			stage = new Stage(width, height, true);
		}
		stage.clear();
		stage.setViewport(width, height);
		
		// Game HUD
		if (gameWorld.getWorldType() == GameWorld.GAME_TYPE) {
			LabelStyle ls = new LabelStyle(SSJava.assetManager.get("data/fonts/whitefont.fnt", BitmapFont.class), Color.WHITE);
			scoreLabel = new Label("Score: " + gameWorld.scoreKeeper.getScore(), ls);
			scoreLabel.setX(10);
			scoreLabel.setY(height - 10 - scoreLabel.getHeight());
			stage.addActor(scoreLabel);
			
			progressLabel = new Label(gameWorld.getProgress() + "%", ls);
			progressLabel.setAlignment(Align.right);
			progressLabel.setX(width - 10 - progressLabel.getWidth());
			progressLabel.setY(height - 10 - progressLabel.getHeight());
			stage.addActor(progressLabel);
			
			shipHeatIndicatorLabel = new Label("Heat: Stable", ls);
			shipHeatIndicatorLabel.setAlignment(Align.right);
			shipHeatIndicatorLabel.setX(15);
			shipHeatIndicatorLabel.setY(15 + 50 + 10);
			stage.addActor(shipHeatIndicatorLabel);
			
			fullHeartImage1 = new Image(fullHeartTexture);
			fullHeartImage2 = new Image(fullHeartTexture);
			fullHeartImage3 = new Image(fullHeartTexture);
			fullHeartImage4 = new Image(fullHeartTexture);
			emptyHeartImage1 = new Image(emptyHeartTexture);
			emptyHeartImage2 = new Image(emptyHeartTexture);
			emptyHeartImage3 = new Image(emptyHeartTexture);
			emptyHeartImage4 = new Image(emptyHeartTexture);
			halfHeartImage = new Image(halfHeartTexture);
			
			int heartScale = 50;
			
			fullHeartImage1.setBounds(15, 15, heartScale, heartScale);
			fullHeartImage2.setBounds(15, 15, heartScale, heartScale);
			fullHeartImage3.setBounds(15, 15, heartScale, heartScale);
			fullHeartImage4.setBounds(15, 15, heartScale, heartScale);
			emptyHeartImage1.setBounds(15, 15, heartScale, heartScale);
			emptyHeartImage2.setBounds(15, 15, heartScale, heartScale);
			emptyHeartImage3.setBounds(15, 15, heartScale, heartScale);
			emptyHeartImage4.setBounds(15, 15, heartScale, heartScale);
			halfHeartImage.setBounds(15, 15, heartScale, heartScale);
			
			fullHeartImage2.setX(15 + fullHeartImage1.getWidth());
			fullHeartImage3.setX(15 + fullHeartImage1.getWidth() + fullHeartImage2.getWidth());
			fullHeartImage4.setX(15 + fullHeartImage1.getWidth() + fullHeartImage2.getWidth() + fullHeartImage3.getWidth());
			
			emptyHeartImage2.setX(15 + emptyHeartImage1.getWidth());
			emptyHeartImage3.setX(15 + emptyHeartImage1.getWidth() + emptyHeartImage2.getWidth());
			emptyHeartImage4.setX(15 + emptyHeartImage1.getWidth() + emptyHeartImage2.getWidth() + emptyHeartImage3.getWidth());
			
			stage.addActor(fullHeartImage1);
			stage.addActor(fullHeartImage2);
			stage.addActor(fullHeartImage3);
			stage.addActor(fullHeartImage4);
			stage.addActor(emptyHeartImage1);
			stage.addActor(emptyHeartImage2);
			stage.addActor(emptyHeartImage3);
			stage.addActor(emptyHeartImage4);
			stage.addActor(halfHeartImage);
			
			// Debug display
			if (SSJava.DEBUG) {
				// Debug text
				LabelStyle dbls = new LabelStyle(SSJava.assetManager.get("data/fonts/debugFont-14.fnt", BitmapFont.class), Color.WHITE);
				debugLabel = new Label("DEBUG TEXT HOLDER"
						+ "\nDEBUG TEXT HOLDER"
						+ "\nDEBUG TEXT HOLDER"
						+ "\nDEBUG TEXT HOLDER"
						+ "\nDEBUG TEXT HOLDER"
						+ "\nDEBUG TEXT HOLDER"
						+ "\nDEBUG TEXT HOLDER"
						+ "\nDEBUG TEXT HOLDER", dbls);
				debugLabel.setX(10);
				debugLabel.setY(height - 40 - debugLabel.getHeight());
				stage.addActor(debugLabel);
			}
		}
		// Creator HUD
		else if (gameWorld.getWorldType() == GameWorld.CREATOR_TYPE) {
			asteroidImage = new Image(asteroidTexture);
			spaceRockImage = new Image(spaceRockTexture);
			planetImage = new Image(planetTexture);
			sunImage = new Image(sunTexture);
			magneticObjectImage = new Image(magneticObjectTexture);
			enemyType1Image = new Image(enemyType1Texture);
			powerupHealthUpImage = new Image(powerupHealthUpTexture);
			powerupSpeedOfLightImage = new Image(powerupSpeedOfLightTexture);
			pointsImage = new Image(pointsTexture);
			
			int imageScale = 70;
			
			asteroidImage.setBounds(10, 10, imageScale * 2, imageScale);
			spaceRockImage.setBounds(10, 10, imageScale, imageScale);
			planetImage.setBounds(10, 10, imageScale, imageScale);
			sunImage.setBounds(10, 10, imageScale, imageScale);
			magneticObjectImage.setBounds(10, 10, imageScale, imageScale);
			enemyType1Image.setBounds(10, 10, imageScale * 2, imageScale);
			enemyType1Image.setOrigin(enemyType1Image.getWidth() / 2, enemyType1Image.getHeight() / 2);
			enemyType1Image.setRotation(180);
			powerupHealthUpImage.setBounds(10, 10, imageScale, imageScale);
			powerupSpeedOfLightImage.setBounds(10, 10, imageScale * 2, imageScale);
			pointsImage.setBounds(10, 10, imageScale, imageScale);
			
			stage.addActor(asteroidImage);
			stage.addActor(spaceRockImage);
			stage.addActor(planetImage);
			stage.addActor(sunImage);
			stage.addActor(magneticObjectImage);
			stage.addActor(enemyType1Image);
			stage.addActor(powerupHealthUpImage);
			stage.addActor(powerupSpeedOfLightImage);
			stage.addActor(pointsImage);
			
			// Debug text
			LabelStyle dbls = new LabelStyle(SSJava.assetManager.get("data/fonts/debugFont-14.fnt", BitmapFont.class), Color.WHITE);
			debugLabel = new Label("DEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER"
					+ "\nDEBUG TEXT HOLDER", dbls);
			debugLabel.setX(10);
			debugLabel.setY(height - 40 - debugLabel.getHeight());
			stage.addActor(debugLabel);
			
			sr = new ShapeRenderer();
			sr.setProjectionMatrix(cam.combined);
		}
	}
	
	/**
	 * Updates the HUD display. 
	 * Score, life and progress for the gameplay mode. 
	 * Entity list, position, and selected entity box for level creator mode.  
	 */
	public void updateHUD() {
		if (gameWorld.getWorldType() == GameWorld.GAME_TYPE) {
			// Score display
			scoreLabel.setText("Score: " + new Integer(gameWorld.scoreKeeper.getScore()).toString());
			
			// Progress display
			progressLabel.setText(gameWorld.getProgress() + "%");
			
			// Health (hearts) display
			switch(ship.getHealth()) {
			case 0:
				emptyHeartImage1.setVisible(true);
				emptyHeartImage2.setVisible(true);
				emptyHeartImage3.setVisible(true);
				emptyHeartImage4.setVisible(true);
				fullHeartImage1.setVisible(false);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				fullHeartImage4.setVisible(false);
				halfHeartImage.setVisible(false);
				break;
			case 1: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(true);
				emptyHeartImage3.setVisible(true);
				emptyHeartImage4.setVisible(true);
				fullHeartImage1.setVisible(false);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				fullHeartImage4.setVisible(false);
				halfHeartImage.setX(15);
				halfHeartImage.setVisible(true);
				break;
			case 2: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(true);
				emptyHeartImage3.setVisible(true);
				emptyHeartImage4.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				fullHeartImage4.setVisible(false);
				halfHeartImage.setVisible(false);
				break;
			case 3: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(true);
				emptyHeartImage4.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(false);
				fullHeartImage3.setVisible(false);
				fullHeartImage4.setVisible(false);
				halfHeartImage.setX(15 + fullHeartImage1.getWidth());
				halfHeartImage.setVisible(true);
				break;
			case 4: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(true);
				emptyHeartImage4.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(false);
				fullHeartImage4.setVisible(false);
				halfHeartImage.setVisible(false);
				break;
			case 5: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(false);
				emptyHeartImage4.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(false);
				fullHeartImage4.setVisible(false);
				halfHeartImage.setX(15 + fullHeartImage1.getWidth() + fullHeartImage2.getWidth());
				halfHeartImage.setVisible(true);
				break;
			case 6: 
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(false);
				emptyHeartImage4.setVisible(true);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(true);
				fullHeartImage4.setVisible(false);
				halfHeartImage.setVisible(false);
				break;
			case 7:
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(false);
				emptyHeartImage4.setVisible(false);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(true);
				fullHeartImage4.setVisible(false);
				halfHeartImage.setX(15 + fullHeartImage1.getWidth() + fullHeartImage2.getWidth() + fullHeartImage3.getWidth());
				halfHeartImage.setVisible(true);
				break;
			case 8:
				emptyHeartImage1.setVisible(false);
				emptyHeartImage2.setVisible(false);
				emptyHeartImage3.setVisible(false);
				emptyHeartImage4.setVisible(false);
				fullHeartImage1.setVisible(true);
				fullHeartImage2.setVisible(true);
				fullHeartImage3.setVisible(true);
				fullHeartImage4.setVisible(true);
				halfHeartImage.setVisible(false);
				break;
			default: break;
			}
			
			// Debug text
			if (SSJava.DEBUG) { 
				debugRenderer.render(gameWorld.box2DWorld, cam.combined);

				float shipAngle = ship.getBody().getAngle();
				float mod = (float) (2 * Math.PI);
				float angleMod = (shipAngle < 0) ? (mod - (Math.abs(shipAngle) % mod) ) % mod : (shipAngle % mod);
				
				// Debug info
				debugLabel.setText("Position: " + (float) Math.round(ship.getBody().getPosition().x * 100) / 100 + " , " + (float) Math.round(ship.getBody().getPosition().y * 100) / 100
						+ "\nAngle (rad): " + (float) Math.round(angleMod * 10000) / 10000
						+ "\nVelocity: " + (float) Math.round(ship.getBody().getLinearVelocity().x * 100) / 100 + " , " + (float) Math.round(ship.getBody().getLinearVelocity().y * 100) / 100 
						+ "\nHealth: " + ship.getHealth() + " half hearts"
						+ "\nLight speed enabled: " + ship.isSpeedOfLightEnabled() 
						+ "\nLevel progress: " + gameWorld.getProgress() + "%"
						+ "\nDEBUG TEXT HOLDER"	
						+ "\nDEBUG TEXT HOLDER");	
			}
		}
		
		// Creator HUD
		else if (gameWorld.getWorldType() == GameWorld.CREATOR_TYPE) {
			if (entityToAdd instanceof Asteroid) {
				asteroidImage.setVisible(true);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				sunImage.setVisible(false);
				magneticObjectImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
				pointsImage.setVisible(false);
			}
			else if (entityToAdd instanceof SpaceRock) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(true);
				planetImage.setVisible(false);
				sunImage.setVisible(false);
				magneticObjectImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
				pointsImage.setVisible(false);
			}
			else if (entityToAdd instanceof Planet) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(true);
				sunImage.setVisible(false);
				magneticObjectImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
				pointsImage.setVisible(false);
			}
			else if (entityToAdd instanceof Sun) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				sunImage.setVisible(true);
				magneticObjectImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
				pointsImage.setVisible(false);
			}
			else if (entityToAdd instanceof MagneticObject) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				sunImage.setVisible(false);
				magneticObjectImage.setVisible(true);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
				pointsImage.setVisible(false);
			}
			else if (entityToAdd instanceof EnemyType1) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				sunImage.setVisible(false);
				magneticObjectImage.setVisible(false);
				enemyType1Image.setVisible(true);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
				pointsImage.setVisible(false);
			}
			else if (entityToAdd instanceof PowerupHealthUp) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				sunImage.setVisible(false);
				magneticObjectImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(true);
				powerupSpeedOfLightImage.setVisible(false);
				pointsImage.setVisible(false);
			}
			else if (entityToAdd instanceof PowerupSpeedOfLight) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				sunImage.setVisible(false);
				magneticObjectImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(true);
				pointsImage.setVisible(false);
			}
			else if (entityToAdd instanceof Points) {
				asteroidImage.setVisible(false);
				spaceRockImage.setVisible(false);
				planetImage.setVisible(false);
				sunImage.setVisible(false);
				magneticObjectImage.setVisible(false);
				enemyType1Image.setVisible(false);
				powerupHealthUpImage.setVisible(false);
				powerupSpeedOfLightImage.setVisible(false);
				pointsImage.setVisible(true);
			}
			
			sr.setProjectionMatrix(cam.combined);
			sr.begin(ShapeType.Line);
			// Draw box around selected entity
			if (selectedEntity != null) { // TODO issue...
				sr.rect(selectedEntity.getPosition().x - selectedEntity.getWidth() / 2, selectedEntity.getPosition().y - selectedEntity.getHeight() / 2, selectedEntity.getWidth(), selectedEntity.getHeight());
			}
			// Draw line at level end point
			sr.line(gameWorld.getLevel().getLevelEnd(), cam.position.y - cam.viewportHeight / 2, gameWorld.getLevel().getLevelEnd(), cam.position.y + cam.viewportHeight / 2);
			sr.end();
			
			// Debug info
			debugLabel.setText("Camera position: " + (float) Math.round(cam.position.x * 100) / 100 + " , " + (float) Math.round(cam.position.y * 100) / 100 
					+ "\nMove camera: A & D "
					+ "\nCycle entities: X & C" 
					+ "\nAdd/remove entities: V & Z" 
					+ "\nMove entities: arrows & mouse"
					+ "\nOptions menu: ESC"
					+ "\nSet Level end: E (" + gameWorld.getLevel().getLevelEnd() + ")"
					+ "\nLevel modified since last save: " + gameWorld.getCreator().isLevelModified());
		}
		
		stage.act();
		stage.draw();
	}

	/**
	 * Disposes the sprite batch and the debugging renderers. 
	 */
	public void dispose() {
		batch.dispose();
		if (sr != null)
			sr.dispose();
		if (debugRenderer != null)
			debugRenderer.dispose();
	}
	
	/**
	 * Gets the texture for the specified entity. 
	 * @param e the entity for which the texture is required
	 * @return the corresponding texture
	 */
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
		else if (e instanceof Points) {
			return pointsTexture;
		}
		else if (e instanceof Planet) {
			return planetTexture;
		}
		else if (e instanceof Sun) {
			return sunTexture;
		}
		else if (e instanceof MagneticObject) {
			return magneticObjectTexture;
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
	 * Gets the renderer's main stage. 
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}
	
	/**
	 * Gets the renderer's camera. 
	 * @return the renderer's camera
	 */
	public Camera getCamera() {
		return cam;
	}
	
	/**
	 * Gets the entity to add in level creator mode. 
	 * @return the entity to add
	 */
	public AbstractEntity getEntityToAdd() {
		return entityToAdd;
	}
	/**
	 * Sets the entity to add in level creator mode. 
	 * @param entityToAdd the new entity to add
	 */
	public void setEntityToAdd(AbstractEntity entityToAdd) {
		this.entityToAdd = entityToAdd;
	}
	/**
	 * Sets the selected entity in level creator mode. 
	 * @param selectedEntity the new selected entity
	 */
	public void setSelectedEntity(AbstractEntity selectedEntity) {
		this.selectedEntity = selectedEntity;
	}
}
