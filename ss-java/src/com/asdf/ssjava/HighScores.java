/**
 * 
 */
package com.asdf.ssjava;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

/**
 * @author Jeremy Brown
 * 
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
	 * Adds a score to the list
	 * @return true if the score was added
	 */
	public boolean add(Score s) {
		if (isHighScore(s)) {
			scores.add(s);
			scores.sort();
			scores.truncate(10);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param index the index of the desired score
	 * @return the score at the designated index
	 */
	public Score get(int index) {
		return scores.get(index);
	}
	
	/**
	 * 
	 * @return the size of the scores array
	 */
	public int size() {
		return scores.size;
	}
	
	/**
	 * 
	 * @param s the score to be checked
	 * @return whether or not the score will go into the high scores list
	 */
	public boolean isHighScore(Score s) {
		if (scores.size == 0) {
			return true;
		}
		else if (s.compareTo(scores.get(scores.size - 1)) >= 0) {			
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a score from the list
	 */
	public void remove(Score s) {
		scores.removeValue(s, true);
		scores.sort();
	}
	
	public boolean saveHighScores(SSJava game) {
		Gdx.files.local("highScores.json").writeString(new Json().prettyPrint(game.highScores), false);
		return true;
	}
}
