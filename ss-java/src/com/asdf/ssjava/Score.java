package com.asdf.ssjava;

/**
 * Definition of a score. 
 * @author Jeremy Brown
 *
 */
public class Score implements Comparable<Score> {
	
	/**
	 * The player's name
	 */
	String name;
	
	/**
	 * The player's score
	 */
	int score;
	
	/**
	 * Creates a new score
	 * @param name the name of the player who obtained this score
	 * @param score the score obtained by the player
	 */
	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	/**
	 * Constructor for de-serialization.
	 * All fields are set to default values.
	 * Serialized values can then be loaded over them. 
	 */
	public Score() {
		this.name = "AAA";
		this.score = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Score s) {
		return getScore() - s.getScore();
	}

	/**
	 * Gets the name. 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name. 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the score. 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Sets the score.
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return name + ": " + score;
	}
}