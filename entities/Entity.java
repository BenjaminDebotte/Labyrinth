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

	
	
	public Entity(Case c) {
		super(c);

	}
	public Entity() {
		super();

	}
	
	
	public abstract boolean move(Case newPosition);

}
