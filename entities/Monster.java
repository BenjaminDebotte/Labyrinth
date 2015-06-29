/**
 *
 */
package com.benjamindebotte.labyrinth.entities;

import com.benjamindebotte.labyrinth.containers.Case;
import com.benjamindebotte.labyrinth.events.game.MonsterEncounterEvent;

/**
 * @author benjamindebotte
 * Représente un monstre déambulant dans le labyrinthe. Surcharge la méthode move pour lever un événement en cas de rencontre avec le joueur
 * et pour éviter de ramasser les items.
 */
public class Monster extends Entity {

	/**
	 *
	 */
	private static final long serialVersionUID = -4874459095828096635L;

	/**
	 *
	 */
	public Monster() {
		super();
	}

	/**
	 * @param X
	 * @param Y
	 */

	public Monster(Case c) {
		super(c);
		// TODO Auto-generated constructor stub

	}

	@Override
	public boolean move(Case newPosition) {
		LabyObject obj = newPosition.getObj();
		if (obj != null) {
			if (obj instanceof Player) {
				this.postEvent(new MonsterEncounterEvent(this, this));
			} else
				return false;
		}
		this.getCase().setObj(null);
		newPosition.setObj(this);
		return true;
	}

}
