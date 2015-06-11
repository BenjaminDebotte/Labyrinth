/**
 *
 */
package com.benjamindebotte.labyrinth.entities;

import com.benjamindebotte.labyrinth.containers.Case;

/**
 * @author benjamindebotte
 *
 */
public class Wall extends LabyObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -8745339197197627296L;

	public Wall() {
		super();
	}

	/**
	 * @param X
	 * @param Y
	 */
	public Wall(Case c) {
		super(c);
	}

}
