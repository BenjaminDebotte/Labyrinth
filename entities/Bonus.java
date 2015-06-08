/**
 * 
 */
package com.benjamindebotte.labyrinth.entities;

import com.benjamindebotte.labyrinth.containers.Case;

/**
 * @author benjamindebotte
 *
 */
public class Bonus extends LabyObject {
	private int nbPoints;
	
	public int getNbPoints() {
		return nbPoints;
	}

	/**
	 * @param X
	 * @param Y
	 */
	public Bonus(Case c, int nbPoints) {
		super(c);
		this.nbPoints = nbPoints;
	}

	/**
	 * 
	 */
	public Bonus(int nbPoints) {
		this.nbPoints = nbPoints;
	}

}
