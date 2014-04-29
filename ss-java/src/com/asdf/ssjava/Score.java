/**
 * 
 */
package com.asdf.ssjava;

/**
 * Inner class defining a score
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
	 * Constructor for serialization
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
	
	// equals method?

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
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