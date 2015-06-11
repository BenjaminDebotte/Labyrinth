/**
 *
 */
package com.benjamindebotte.labyrinth.containers;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author benjamindebotte
 *
 */
public class Map implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4217788303181463323L;

	private final Case[][] Cases;

	private final int length;
	private final int width;

	public Map(int length, int width) throws Exception {

		if (length <= 0 || width <= 0)
			throw new Exception();
		if (length % 2 == 0 || width % 2 == 0)
			throw new Exception();

		this.length = length;
		this.width = width;

		this.Cases = new Case[length][width];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				this.Cases[i][j] = new Case(i, j);
			}
		}
	}

	public Case getCase(Case position) {
		return this.getCase(position.getX(), position.getY());
	}

	public Case getCase(int X, int Y) {
		if (X < 0 || Y < 0)
			return null;
		else if (X >= this.Cases.length || Y >= this.Cases[0].length)
			return null;
		return this.Cases[X][Y];
	}

	public Case[][] getCases() {
		return this.Cases;
	}

	public Case getEast(Case position) {
		if (position == null)
			return null;
		return this.getCase(position.getX(), position.getY() + 1);
	}

	public Case[] getInterval(Case start, Case end) {
		ArrayList<Case> interval = new ArrayList<Case>();

		if (!this.isValid(start) || !this.isValid(end))
			return new Case[0];

		int diffX = end.getX() - start.getX();
		int diffY = end.getY() - start.getY();

		if (diffX == 0 && diffY == 0) // Même coordonnées
			return new Case[0];
		if (diffX != 0 && diffY != 0) // Pas sur les mêmes axes
			return new Case[0];

		/*
		 * Une seule des deux boucles sera fonctionnelle, selon start et end
		 * sont alignés sur X ou Y.
		 */
		if (diffX > 0) {
			for (int i = start.getX(); i < end.getX(); i++) {
				interval.add(this.Cases[i][start.getY()]);
			}
		} else if (diffX < 0) {
			for (int i = start.getX(); i > end.getX(); i--) {
				interval.add(this.Cases[i][start.getY()]);
			}
		} else if (diffY > 0) {
			for (int i = start.getY(); i < end.getY(); i++) {
				interval.add(this.Cases[start.getX()][i]);
			}
		} else {
			for (int i = start.getY(); i > end.getY(); i--) {
				interval.add(this.Cases[start.getX()][i]);
			}
		}

		Case arrayOfCases[] = new Case[interval.size()];
		interval.toArray(arrayOfCases);
		return arrayOfCases;
	}

	public int getLength() {
		return this.length;
	}

	public Case getNorth(Case position) {
		if (position == null)
			return null;
		return this.getCase(position.getX() - 1, position.getY());
	}

	public Case getSouth(Case position) {
		if (position == null)
			return null;
		return this.getCase(position.getX() + 1, position.getY());
	}

	public Case getWest(Case position) {
		if (position == null)
			return null;
		return this.getCase(position.getX(), position.getY() - 1);
	}

	public int getWidth() {
		return this.width;
	}

	public boolean isValid(Case c) {
		if (c == null)
			return false;
		return c.getX() < this.length && c.getX() >= 0 && c.getY() < this.width
				&& c.getY() >= 0;
	}

}
