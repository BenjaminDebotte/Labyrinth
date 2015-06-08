/**
 * 
 */
package com.benjamindebotte.labyrinth.events;

/**
 * @author benjamindebotte
 *
 */
public class Event {
	boolean accepted;
	
	/**
	 * 
	 */
	public Event() {
		accepted = false;
	}
	
	
	public void accept() {
		accepted = true;
	}
	
	public void ignore() {
		accepted = false;
	}
	
	public boolean isAccepted() {
		return accepted;
	}

}
