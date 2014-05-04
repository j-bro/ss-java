/**
 * 
 */
package com.asdf.ssjava.screens.screenelements;

/**
 * 
 * @author Jeremy Brown
 *
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
	 * 
	 * @param message
	 * @param progress
	 * @param duration
	 */
	public ToastMessage(String message, float duration, float progress) {
		this.message = message;
		this.duration = duration;
		this.progress = progress;
	}
	
	/**
	 * Serialization
	 */
	public ToastMessage() {
		this.message = null;
		this.duration = 0;
		this.progress = 0;
	}
}