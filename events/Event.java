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
		this.accepted = false;
	}

	public void accept() {
		this.accepted = true;
	}

	public void ignore() {
		this.accepted = false;
	}

	public boolean isAccepted() {
		return this.accepted;
	}

}
