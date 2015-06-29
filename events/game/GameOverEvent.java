package com.benjamindebotte.labyrinth.events.game;

import com.benjamindebotte.labyrinth.entities.LabyObject;

/**
 * @author benjamindebotte
 * Evenement levé quand une condition de défaite s'est produite.
 */
public class GameOverEvent extends GameEvent {

	public GameOverEvent(LabyObject sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}

}
