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
	private int X,Y;
	private LabyObject obj;
	
	
	
	/* Constructor */
	public Case(int X, int Y, LabyObject obj) {
		setX(X); setY(Y); setObj(obj);
	}
	
	public Case(int X, int Y) {
		this(X,Y,null);
	}
	
	/* Méthodes  */
	public int getX() {
		return X;
	}
	
	public void setX(int x) {
		X = x;
	}
	
	public int getY() {
		return Y;
	}
	
	public void setY(int y) {
		Y = y;
	}
	
	public LabyObject getObj() {
		return obj;
	}
	
	public void setObj(LabyObject obj) {
		
		/* Assignation des nouvelles coordonnées */
		
		if(obj != this.obj) {
			this.obj = obj;
			if(obj != null)
				obj.setCase(this);
		}
		
		
	}
}
