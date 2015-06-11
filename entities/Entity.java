/**
 *
 */
package com.benjamindebotte.labyrinth.entities;

import com.benjamindebotte.labyrinth.containers.Case;

/**
 * @author benjamindebotte
 *
 */
public abstract class Entity extends LabyObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -700210289990072629L;

	public Entity() {
		super();

	}

	public Entity(Case c) {
		super(c);

	}

	public abstract boolean move(Case newPosition);

}
