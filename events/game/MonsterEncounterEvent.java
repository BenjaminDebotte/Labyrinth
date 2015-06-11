package com.benjamindebotte.labyrinth.events.game;

import com.benjamindebotte.labyrinth.entities.LabyObject;
import com.benjamindebotte.labyrinth.entities.Monster;

public class MonsterEncounterEvent extends GameEvent {

	private final Monster encounteredMonster;

	public MonsterEncounterEvent(LabyObject sender, Monster encounteredMonster) {
		super(sender);
		this.encounteredMonster = encounteredMonster;
	}

	public Monster getEncounteredMonster() {
		return this.encounteredMonster;
	}

}
