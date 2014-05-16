package com.asdf.ssjava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

/**
 * The high scores manager object. 
 * Is exported & imported with the game. 
 * @author Jeremy Brown
 */
public class HighScores {

	/**
	 * The internal scores Array
	 */
	private Array<Score> scores;
	
	/**
	 * Creates an empty object with an array of size 10
	 */
	public HighScores() {
		scores = new Array<Score>(10);
	}

	/**
	 * Adds a score to the list. 
	 * @return true if the score was added
	 */
	public boolean add(Score s) {
		if (isHighScore(s)) {
			scores.add(s);
			scores.sort();
			scores.reverse();
			scores.truncate(10);
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the score at the specified index. 
	 * @param index the index of the desired score
	 * @return the score at the designated index
	 */
	public Score get(int index) {
		return scores.get(index);
	}
	
	/**
	 * Gets the scores list. 
	 * @return the base score array
	 */
	public Array<Score> getScoreArray() {
		return scores;
	}
	
	/**
	 * Gets the size of the high scores list. 
	 * @return the size of the scores array
	 */
	public int size() {
		return scores.size;
	}
	
	/**
	 * Verifies that the given score makes the high scores list. 
	 * @param s the score to be checked
	 * @return true if the score will go into the high scores list; false otherwise. 
	 */
	public boolean isHighScore(Score s) {
		if (scores.size < 10) {
			return true;
		}
		else if (s.compareTo(scores.get(scores.size - 1)) > 0) {			
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a score from the list. 
	 */
	public void remove(Score s) {
		scores.removeValue(s, true);
		scores.sort();
	}
	
	/**
	 * Saves the high scores entries to disk. 
	 * Important: must be called every time a score is added. Scores are not saved to disk automatically. 
	 * @return true if the scores were saved
	 */
	public boolean exportScores() {
		Gdx.files.local(SSJava.highScoresPath).writeString(new Json().prettyPrint(this), false);
		return true;
	}
}

