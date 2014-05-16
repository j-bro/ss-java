package com.asdf.ssjava.screens.screenelements;

/**
 * Simple data structure for a toast message. 
 * Used in level intros to guide the player.
 * More potential uses, simple to implement.  
 * @author Jeremy Brown
 */
public class ToastMessage {
	
	/**
	 * The level progress at which to show the message
	 */
	public float progress;
	
	/**
	 * The message to be shown
	 */
	public String message;
	
	/**
	 * The duration of the message, in seconds
	 */
	public float duration;
	
	/**
	 * Creates a toast message with the specified parameters. 
	 * @param message the message string
	 * @param progress the point in the level at which to display the message
	 * @param duration the time for which the message should stay on screen
	 */
	public ToastMessage(String message, float duration, float progress) {
		this.message = message;
		this.duration = duration;
		this.progress = progress;
	}
	
	/**
	 * Constructor for de-serialization. 
	 * All is null but gets assigned at de-serialization. 
	 */
	public ToastMessage() {
		this.message = null;
		this.duration = 0;
		this.progress = 0;
	}
}