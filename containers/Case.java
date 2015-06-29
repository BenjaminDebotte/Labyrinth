/**
 *
 */
package com.benjamindebotte.labyrinth.containers;

import java.io.Serializable;

import com.benjamindebotte.labyrinth.entities.LabyObject;

/**
 * @author benjamindebotte
 * La classe Case représente la base du labyrinthe dont elle joue le rôle de conteneur pour les éléments qui s'y trouvent.
 * Pour cela, elle contient une composition forte avec un LabyObject ainsi qu'un jeu de coordonnées X;Y pour se situer dans le plan.
 */

public class Case implements Serializable {
	private static final long serialVersionUID = 5620104008635818621L;
	private LabyObject obj;
	private int X, Y;

	
	/** Constructeur affectant des coordonnées à une case, sans lui associer d'élement LabyObject.
	 * @param X Coordonnée X
	 * @param Y Coordonnée Y
	 */
	public Case(int X, int Y) {
		this(X, Y, null);
	}

	/* Constructor */
	/** Constructeur affectant des coordonnées et un élément à une case.
	 * @param X Coordonnée X
	 * @param Y Coordonnée Y
	 * @param obj Élément LabyObject.
	 */
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

	/** Méthode associant un élément obj à une case. obj se verra lui aussi mettre à jour son attribut 'obj.case'.
	 * Cette méthode peut être utilisée pour affecter 'null' à la case. Des vérifications permettent d'éviter des appels récursifs infinis
	 * entre obj et case.
	 * @param obj l'Élément à assigner à la case.
	 */
	public void setObj(LabyObject obj) {

		/* Assignation des nouvelles coordonnées */

		if (obj != this.obj) {
			if(obj == null)
				if(this.obj != null)
					this.obj.setCase(null);
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
