/**
 * 
 */
package com.benjamindebotte.labyrinth.entities;


import com.benjamindebotte.labyrinth.containers.Case;
import com.benjamindebotte.labyrinth.events.game.MonsterEncounterEvent;

/**
 * @author benjamindebotte
 *
 */
public class Monster extends Entity {

	
	/**
	 * @param X
	 * @param Y
	 */
	
	

	
	public Monster(Case c) {
		super(c);
		// TODO Auto-generated constructor stub

	}

	/**
	 * 
	 */
	public Monster() {
		super();
	}

	@Override
	public boolean move(Case newPosition) {
		LabyObject obj = newPosition.getObj();
		if(obj != null){
			if(obj instanceof Player) {
				postEvent(new MonsterEncounterEvent(this));
			} else {
				return false;
			}
		}
		this.getCase().setObj(null);
		newPosition.setObj(this);
		return true;
	}

}
