/**
 *
 */
package com.benjamindebotte.labyrinth.containers;

import java.io.Serializable;

import com.benjamindebotte.labyrinth.entities.LabyObject;

/**
 * @author benjamindebotte
 *
 */
public class Case implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 5620104008635818621L;
	private LabyObject obj;
	private int X, Y;

	public Case(int X, int Y) {
		this(X, Y, null);
	}

	/* Constructor */
	public Case(int X, int Y, LabyObject obj) {
		this.setX(X);
		this.setY(Y);
		this.setObj(obj);
	}

	public LabyObject getObj() {
		return this.obj;
	}

	/* Méthodes */
	public int getX() {
		return this.X;
	}

	public int getY() {
		return this.Y;
	}

	public void setObj(LabyObject obj) {

		/* Assignation des nouvelles coordonnées */

		if (obj != this.obj) {
			this.obj = obj;
			if (obj != null) {
				obj.setCase(this);
			}
		}

	}

	public void setX(int x) {
		this.X = x;
	}

	public void setY(int y) {
		this.Y = y;
	}
}
