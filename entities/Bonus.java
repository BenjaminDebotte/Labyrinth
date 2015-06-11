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
	/**
	 *
	 */
	private static final long serialVersionUID = -2717684632262739793L;
	private final int nbPoints;

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

	public int getNbPoints() {
		return this.nbPoints;
	}

}
