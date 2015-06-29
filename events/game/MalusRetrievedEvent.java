package com.benjamindebotte.labyrinth.events.game;

import com.benjamindebotte.labyrinth.entities.LabyObject;

/**
 * @author benjamindebotte
 * 	Événement levé quand un Malus est ramassé par une entité capable.
 */
public class MalusRetrievedEvent extends GameEvent {

	public MalusRetrievedEvent(LabyObject sender) {
		super(sender);
	}

}
