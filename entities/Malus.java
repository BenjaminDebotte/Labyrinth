package com.benjamindebotte.labyrinth.entities;

import com.benjamindebotte.labyrinth.containers.Case;

/**
 * @author benjamindebotte
 * Représente un Malus dans le labyrinthe. Destiné à recevoir un nombre de points négatifs. 
 */
public class Malus extends LabyObject {

	private static final long serialVersionUID = -2717684632262739793L;
	private final int nbPoints;

	/**
	 * @param X
	 * @param Y
	 */
	public Malus(Case c, int nbPoints) {
		super(c);
		this.nbPoints = nbPoints;
	}

	/**
	 *
	 */
	public Malus(int nbPoints) {
		this.nbPoints = nbPoints;
	}

	public int getNbPoints() {
		return this.nbPoints;
	}

}
