package com.asdf.ssjava.world;

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
	 * The current multiplier;
	 */
	private int multiplier;
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @param addedScore the points to add to the current score
	 */
	public synchronized void add(int addedScore) {
		score += addedScore;
	}

	/**
	 * @return the score multiplier
	 */
	public synchronized int getMultiplier() {
		return multiplier;
	}

	/**
	 * @param multiplier the score multiplier to set
	 */
	public synchronized void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}	
}
