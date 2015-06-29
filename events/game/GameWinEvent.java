package com.benjamindebotte.labyrinth.events.game;

import com.benjamindebotte.labyrinth.entities.LabyObject;

/**
 * @author benjamindebotte
 * Évenement levé quand une condition de victoire s'est déclarée.
 */
public class GameWinEvent extends GameEvent {

	public GameWinEvent(LabyObject sender) {
		super(sender);
		// TODO Auto-generated constructor stub
	}

}
