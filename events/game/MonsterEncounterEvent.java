package com.benjamindebotte.labyrinth.events.game;

import com.benjamindebotte.labyrinth.entities.Monster;


public class MonsterEncounterEvent extends GameEvent {

	public MonsterEncounterEvent(Monster sender) {
		super(sender);
	}

}
