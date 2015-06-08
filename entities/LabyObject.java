/**
 * 
 */
package com.benjamindebotte.labyrinth.entities;

import java.io.Serializable;
import java.util.Observable;

import com.benjamindebotte.labyrinth.containers.Case;
import com.benjamindebotte.labyrinth.events.Event;

/**
 * @author benjamindebotte
 *
 */

public class LabyObject extends Observable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4863281990231657549L;
	private Case c;
	
	/* Methods */
	
	public LabyObject(Case c) {
		this.c = c;
		if(c != null)
			c.setObj(this);
	}
	
	/**
	 *  Constructeur déléguant les coordonnées à une Case.
	 *  Attention à s'assurer qu'une Case va bien assigner de nouvelles coordonnées !
	 *  */
	public LabyObject() {
		this.c = null;
	}
	
	
	public int getX() {
		return c.getX();
	}


	public int getY() {
		return c.getY();
	}
	
	public Case getCase() {
		return this.c;
	}
	
	public void setCase(Case c) {
		if(this.c != c){
			this.c = c;
			if(c != null)
				c.setObj(this);
		}
	}
	
	
	protected void postEvent(Event e) {
		setChanged();
		notifyObservers(e);
	}
	

}
