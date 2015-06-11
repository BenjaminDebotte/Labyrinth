/**
 *
 */
package com.benjamindebotte.labyrinth.entities;

import com.benjamindebotte.labyrinth.containers.Case;
import com.benjamindebotte.labyrinth.events.game.BonusRetrievedEvent;
import com.benjamindebotte.labyrinth.events.game.GameWinEvent;
import com.benjamindebotte.labyrinth.events.game.MonsterEncounterEvent;

/**
 * @author benjamindebotte
 *
 */
public class Player extends Entity {

	/**
	 *
	 */
	private static final long serialVersionUID = -2186119012928904195L;

	/**
	 *
	 */
	public Player(Case c) {
		super(c);
	}

	@Override
	public boolean move(Case newPosition) {
		if (newPosition == null)
			return false;
		if (newPosition.getObj() != null) {
			if (newPosition.getObj() instanceof Bonus) {
				this.postEvent(new BonusRetrievedEvent((Bonus) (newPosition
						.getObj())));
			} else if (newPosition.getObj() instanceof FinishLine) {
				this.postEvent(new GameWinEvent(this));
			} else if (newPosition.getObj() instanceof Monster) {
				this.postEvent(new MonsterEncounterEvent(this,
						(Monster) newPosition.getObj()));
			} else
				return false;
		}

		this.getCase().setObj(null);
		newPosition.setObj(this);
		return true;
	}

}
