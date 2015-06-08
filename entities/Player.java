/**
 * 
 */
package com.benjamindebotte.labyrinth.entities;

import com.benjamindebotte.labyrinth.containers.Case;
import com.benjamindebotte.labyrinth.events.game.BonusRetrievedEvent;

/**
 * @author benjamindebotte
 *
 */
public class Player extends Entity {

	/**
	 * 
	 */
	public Player(Case c) {
		super(c);
	}

	@Override
	public boolean move(Case newPosition) {
		if(newPosition == null)
			return false;
		if(newPosition.getObj() != null){
			if(newPosition.getObj() instanceof Bonus)
				postEvent(new BonusRetrievedEvent( (Bonus)( newPosition.getObj() )) );
			else
				return false;
		}
				
		this.getCase().setObj(null);
		newPosition.setObj(this);
		return true;
	}

}
