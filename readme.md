# SS-Java

**A Java sidescroller game**

SS-Java is a simple 2D space shooter that mixes basic physics in space and a level-based game with obstacles and power-ups. It is easy to pick up and has a simple control scheme. It comes with 5 included levels, as well as an easy to use level creator and editor.

This project was born as our submission for an integrative project for our last semester in Computer Science & Math. In this framework, we attempted to integrate concepts we learned in our physics courses (kinematics, torque, intertia, equilibrium, Newton's Laws, etc.)

The game is in its final form for the assignment (v0.1.0). Only the desktop version currently runs.

## Requirements
- Java 6.0 or greater

## Setup

### Standalone
- The .jar files under releases are runnable and should work fine on any system.

### From source
- This project was built using libGDX 0.9 and hasn't been updated for the new Gradle system. Please install the source files from the site's [old releases section](http://libgdx.badlogicgames.com/releases/) or run the old libGDX [setup app](http://www.aurelienribon.com/blog/2012/09/libgdx-project-setup-v3-0-0/).
- Ensure the 'ss-java', 'ss-java-desktop' and 'ss-java-android' are imported
- Run using Main.java in the desktop package

** Refer to system_manual.md for more information

## Features
- [Gameplay](#gameplay)
  - Scoring
  - Health level
  - Obstacles
  - Powerups
  - Enemies
- [5 bundled levels](#levels)
- Saving level completion
- [Level Creator & Editor](#creator)
- Sound and music volume controls


## [Gameplay](id:gameplay)

### The gameplay screen
![Gameplay screnshot](https://dl.dropboxusercontent.com/u/1996708/gameplay_screenshot.png)

This screen is shown to the user during the play-through of any level. In the left centre part of the screen is the ship, which always stays there since the camera stays focused on it. As it progresses throughout the level, it comes across various entities that are present in the level. This screenshot depicts three enemy ships that are firing at the player’s ship. The other elements on the screen together compose the Heads-up display (HUD). At the top left corner is the score display. Simply enough, it displays the player’s current score in real-time. In the top right corner, there is the progress indicator. It displays the ship’s current completion of the level in percentage relative to the level’s start and end points. In the bottom left corner are the life points display and the ship heat display. The hearts represent the ship’s life, which can be lost and regained in increments of one full heart or one half heart. The heat indicator allows the player to be notified of the ship’s rising heat level and potential damage to its health when it approaches the Sun for extended periods of time.

The controls in the gameplay screen mainly control the ship itself. The W & S keys are used to move the ship vertically up and down. The spacebar is used to fire a bullet from the ship. The ship itself is limited at firing one bullet per second, so there is no use in trying to shoot faster than this speed. Most levels are preceded by a short introduction that helps the player understand what new elements are present in this level. At any time during this introduction, the I key can be pressed to skip directly to the start of the actual level. Also, at any point in any level, pressing the Escape key will pause the game.

## [Levels](id:levels)
The game comes with 5 bundled levels.

### Level 0 - Tutorial level
This level informs the player of the controls and basic mechanics of the game, including dodging obstacles, shooting, accumulating points and the health bar. This level will be simple and straightforward, with only a few space rocks and asteroids as obstacles. Gravity, speed of light, thermodynamics and magnetism will not be included here.

### Level 1 - First challenging level
This level will introduce gravity as a game mechanic with the addition of planets that attract the player’s ship. Enemy spaceships that shoot randomly and health up power-ups will be introduced as well. There will be an increase in the quantity of space rocks and asteroids.

### Level 2 - Speed of light
This level will introduce the speed of light power-up. This power-up will appear on every level at a set frequency (rare, no more than 3 on a single level). The quantity of enemy spaceships will be increased. The quantity of space rocks, asteroids and planets remain the same. 

### Level 3 - Thermodynamics
This level will introduce thermodynamics by adding the Sun, which will appear at various points throughout the level. The quantity of space rocks, asteroids and planets is increased, but the quantity of enemy spaceships does not increase.  

### Level 4 - Magnetism
This level introduces magnetism to the game by including magnetic objects (denoted in red). The quantity of space rocks, asteroids, planets and enemy spaceships as well as the frequency of the appearance of the Sun do not increase. 

### Level 5 - Final level
The final level in the game (excluding bonus levels). This level will be of the highest difficulty. The quantity of magnetic objects and the frequency of the appearance of the Sun are increased.


## [The level creator](id:creator)
![Level creator screenshot](https://dl.dropboxusercontent.com/u/1996708/level_creator.png)

The level creator offers a robust yet simple way for the player to create levels. It displays a level in a way similar to the gameplay screen, but none of the entities are active and the camera is controlled by the user instead of moving automatically with the ship. The text display in the top left corner shows the user the camera’s current coordinates in the level and other useful information about on how to use the creator and the save state of the level.

### Controls
- Move camera: A & D
- Cycle entity list: X & C
- Add/remove entities: V & Z
- Move entities: arrow keys & click-and-drag mouse
- Options menu: ESC
- Set level endpoint: E

## Menu hierarchy

- Main Menu
  - Play
    - Level Selection Menu
  - Options
    - Music control
    - Sound control
  - High scores
  - Level creator
    - Level creator options menu
      - Back
      - Test Level
      - Save Level
      - Load Level
      - Exit
    - Level save menu
  - Credits
  - Exit
- Gameplay
  - Pause Menu
    - Back
    - Options
    - Exit
  - Level retry menu
    - Replay
    - Select level
    - Exit
  - Level completed menu
    - Retry
    - Select Level
    - Exit


## Acknowledgements

Game art was all created graciously by @smallestbrown. Visit her on [Twitter](http://twitter.com/smallestbrown) and [Tumblr](http://smallestbrown.tumblr.com).

SS-Java was built using the awesome [libGDX](http://libgdx.badlogicgames.com) multi-platform OpenGL library.

The physics engine used is the Java port of [Box2D](https://code.google.com/p/box2d/), included in libGDX.

Aurélien Ribbon's [Universal Tween Engine](http://www.aurelienribon.com/blog/projects/universal-tween-engine/) was used for animations.

