/**
 * 
 */
package com.benjamindebotte.labyrinth.events.game;

import com.benjamindebotte.labyrinth.entities.LabyObject;
import com.benjamindebotte.labyrinth.events.Event;

/**
 * @author benjamindebotte
 *
 */
public class GameEvent extends Event {

	private LabyObject sender;

	/**
	 * 
	 */
	public GameEvent(LabyObject sender) {
		this.sender = sender;
	}
	
	public LabyObject getSender() {
		return sender;
	}
	public void setSender(LabyObject sender) {
		this.sender = sender;
	}

}
