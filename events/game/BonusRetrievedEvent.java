package com.benjamindebotte.labyrinth.events.game;

import com.benjamindebotte.labyrinth.entities.Bonus;

public class BonusRetrievedEvent extends GameEvent {

	public BonusRetrievedEvent(Bonus sender) {
		super(sender);
	}

}
