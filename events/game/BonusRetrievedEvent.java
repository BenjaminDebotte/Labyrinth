package com.benjamindebotte.labyrinth.events.game;

import com.benjamindebotte.labyrinth.entities.Bonus;

/**
 * @author benjamindebotte
 * Événement produit lorsqu'un Bonus a été ramassé par une entité en capacité d'intéragir avec un Bonus.
 */
public class BonusRetrievedEvent extends GameEvent {

	public BonusRetrievedEvent(Bonus sender) {
		super(sender);
	}

}
