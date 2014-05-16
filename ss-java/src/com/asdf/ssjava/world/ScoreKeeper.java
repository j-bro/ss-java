package com.asdf.ssjava.world;

/**
 * Manages the score for the current level. 
 * @author Jeremy Brown
 * @author Simon Thompson
 */
public class ScoreKeeper {

	/**
	 * The default score for the start of a level
	 */
	public static final int DEFAULT_SCORE = 0;
	
	/**
	 * The player's current score
	 */
	private int score;
	
	/**
	 * Gets the current score. 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Adds the specified score to the current score. 
	 * @param addedScore the points to add to the current score
	 */
	public synchronized void add(int addedScore) {
		score += addedScore;
	}	
}
