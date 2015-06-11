/**
 *
 */
package com.benjamindebotte.labyrinth.containers;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

import com.benjamindebotte.labyrinth.entities.Bonus;
import com.benjamindebotte.labyrinth.entities.FinishLine;
import com.benjamindebotte.labyrinth.entities.LabyObject;
import com.benjamindebotte.labyrinth.entities.Monster;
import com.benjamindebotte.labyrinth.entities.Player;
import com.benjamindebotte.labyrinth.entities.Wall;

/**
 * @author benjamindebotte
 *
 */
public class Labyrinth implements Serializable {
	/* Classe privée utilisée lors de la génération d'île */
	private class LabyIslet extends LabyObject {
		/**
		 *
		 */
		private static final long serialVersionUID = 5067646912346942784L;
		private final int value;

		public LabyIslet(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 7093066492876133264L;
	private final int BONUS_SCORE_MAX = 1000;
	private final int BONUS_SCORE_MIN = 100;

	private final Map map;
	private final ArrayList<LabyObject> objects;

	private Player player;

	public Labyrinth(int length, int width) throws Exception {
		this.map = new Map(length, width);
		this.objects = new ArrayList<LabyObject>();
		this.generateLabyrinth();
	}

	private void addFinishLine() {
		this.assignObject(
				this.getMap().getCase(this.getMap().getLength() - 2,
						this.getMap().getWidth() - 1), new FinishLine());
	}

	private void addPlayer() {
		this.player = new Player(null);
		this.assignObject(this.getMap().getCase(1, 0), this.player);
	}

	private boolean assignObject(Case c, LabyObject obj) {
		if (c.getObj() != null)
			return false;
		c.setObj(obj);
		if (!this.objects.contains(obj)) {
			this.objects.add(obj);
		}

		return true;
	}

	private void generateIslets() {
		int value = 1;
		for (int line = 1; line < this.map.getLength(); line += 2) {
			for (int width = 1; width < this.map.getWidth(); width += 2) {
				LabyIslet islet = new LabyIslet(value++);
				this.map.getCase(line, width).setObj(islet);
				this.objects.add(islet);
			}
		}

	}

	private void generateItems() {
		int NB_ITEMS = this.map.getLength() / 3 + this.map.getWidth() / 3;

		while (NB_ITEMS > 0) {
			Case c = this.map.getCase(
					(int) (Math.random() * this.map.getLength()),
					(int) (Math.random() * this.map.getWidth()));
			if (c == null) {
				continue;
			}
			if (c.getObj() != null) {
				continue;
			}

			Bonus b = new Bonus((int) (Math.random() * this.BONUS_SCORE_MAX)
					+ this.BONUS_SCORE_MIN);
			this.assignObject(c, b);
			NB_ITEMS--;
		}
	}

	private void generateLabyrinth() throws Exception {
		this.generateLevel();
		this.generateMonsters();
		this.generateItems();
		this.addPlayer();
		this.addFinishLine();
	}

	private void generateLevel() throws Exception {
		this.generateLowLevel();
		this.generateIslets();
		this.generateWays();
		this.generateWalls();
	}

	/**
	 * Génération de la structure "basse" du labyrinthe : construction de murs
	 * seulement.
	 */
	private void generateLowLevel() {
		Case[][] mapCases = this.map.getCases();
		for (int line = 0; line < this.map.getLength(); line++) {
			for (int col = 0; col < this.map.getWidth(); col++) {
				mapCases[line][col] = new Case(line, col);
			}
		}

	}

	private void generateMonsters() {
		for (int i = 1; i < this.map.getLength() - 1; i++) {
			for (int j = 1; j < this.map.getWidth() - 1; j++) {
				Case c = this.map.getCase(i, j);

				if (c.getObj() != null) {
					continue;
				}

				int nbWalls = ((this.map.getNorth(c).getObj() instanceof Wall) ? 1
						: 0)
						+ ((this.map.getSouth(c).getObj() instanceof Wall) ? 1
								: 0)
						+ ((this.map.getEast(c).getObj() instanceof Wall) ? 1
								: 0)
						+ ((this.map.getWest(c).getObj() instanceof Wall) ? 1
								: 0);

				if (nbWalls == 3 && (int) (Math.random() * 4) == 1) {
					Monster m = new Monster();
					this.assignObject(c, m);
				}

			}
		}
	}

	private void generateWalls() {
		Case cases[][] = this.map.getCases();
		for (int i = 0; i < this.map.getLength(); i++) {
			for (int j = 0; j < this.map.getWidth(); j++) {
				if (cases[i][j].getObj() == null) {
					Wall w = new Wall();
					this.assignObject(cases[i][j], w);
				} else {
					this.objects.remove(cases[i][j].getObj()); // Suppression
					// des Islets
					cases[i][j].setObj(null);
				}

			}
		}
	}

	private void generateWays() throws Exception {

		int MAX_VALUE = 1;

		/*
		 * Re-calcul du maximum d'îlot. TODO : Calculer sans boucler ?
		 */
		for (int line = 1; line < this.map.getLength(); line += 2) {
			for (int width = 1; width < this.map.getWidth(); width += 2) {
				MAX_VALUE++;
			}
		}

		/* Départ aléatoire */
		LabyIslet islet = null;
		int randomValue = (int) (Math.random() * (MAX_VALUE - 1) + 1);

		for (LabyObject obj : this.objects) {
			if (obj instanceof LabyIslet) {
				islet = (LabyIslet) obj;
				if (islet.getValue() == randomValue) {
					break;
				}
				islet = null;
			}
		}

		if (islet == null)
			throw new Exception("Impossible de trouver un islet de valeur "
					+ randomValue);

		/* Visite du Labyrinthe */
		int totalCells = MAX_VALUE; /* On ne compte pas les bordures */
		int visitedCells = 1;
		ArrayDeque<Case> previousCases = new ArrayDeque<Case>();
		Case currentCell = this.map.getCase(islet.getX(), islet.getY());

		previousCases.add(currentCell); /* Initialisation avec la première case */

		while (visitedCells < totalCells - 1) {
			Case lookOutCell = null, intermediaryCase = null;

			Case N = this.map.getNorth(currentCell), S = this.map
					.getSouth(currentCell), E = this.map.getEast(currentCell), W = this.map
					.getWest(currentCell);

			// Une case est considérée comme visitée si nulle ou si son ID est
			// égal à celui de la case actuelle.
			Case NN = this.map.getNorth(N), SS = this.map.getSouth(S), EE = this.map
					.getEast(E), WW = this.map.getWest(W);

			boolean visitedN = (NN == null)
					|| ((LabyIslet) NN.getObj()).getValue() == islet.getValue();
			boolean visitedE = (EE == null)
					|| ((LabyIslet) EE.getObj()).getValue() == islet.getValue();
			boolean visitedS = (SS == null)
					|| ((LabyIslet) SS.getObj()).getValue() == islet.getValue();
			boolean visitedW = (WW == null)
					|| ((LabyIslet) WW.getObj()).getValue() == islet.getValue();

			if (visitedE && visitedN && visitedS && visitedW) {
				/* Toutes les cases ont été visitées : on retourne en arrière. */

				currentCell = previousCases.poll();
				if (currentCell == null)
					throw new Exception("Plus de cellule. :(");

				continue; /* On repart avec une nouvelle cellule ! */
			}

			/* Selection aléatoire d'une case adjacente */
			int random = (int) (Math.random() * 4);

			switch (random) {
			case 0: // N
				if (N == null) {
					continue;
				}
				if (N.getObj() != null) {
					continue;
				}
				intermediaryCase = N;
				lookOutCell = this.map.getNorth(intermediaryCase); // N+2
				if (lookOutCell != null) {
					break;
				}
			case 1: // W
				if (W == null) {
					continue;
				}
				if (W.getObj() != null) {
					continue;
				}
				intermediaryCase = W;
				lookOutCell = this.map.getWest(intermediaryCase); // W+2
				if (lookOutCell != null) {
					break;
				}
			case 2: // S
				if (S == null) {
					continue;
				}
				if (S.getObj() != null) {
					continue;
				}
				intermediaryCase = S;
				lookOutCell = this.map.getSouth(intermediaryCase); // S+2
				if (lookOutCell != null) {
					break;
				}
			case 3: // E
			case 4:
				if (E == null) {
					continue;
				}
				if (E.getObj() != null) {
					// LabyObject actuellement.
					continue;
				}
				intermediaryCase = E;
				lookOutCell = this.map.getEast(intermediaryCase); // E+2
				if (lookOutCell != null) {
					break;
				}
			}

			if (lookOutCell == null) {
				continue;
			}

			/* -- À cette étape, une case valide a été trouvée -- */

			if (((LabyIslet) lookOutCell.getObj()).getValue() == ((LabyIslet) currentCell
					.getObj()).getValue()) {
				continue; /* On n'attaque pas une cellule ayant la même valeur. */
			}

			intermediaryCase.setObj(currentCell.getObj()); /*
															 * On assigne le
															 * même ID
															 */
			lookOutCell.setObj(currentCell.getObj()); /* On assigne le même ID */

			/* On ajoute la case actuelle comme case précédente */
			previousCases.add(currentCell);
			visitedCells++;

			/* On recommence à partir de la nouvelle case */
			currentCell = lookOutCell;

		}

		/* Le labyrinthe est généré */

		this.map.getCase(1, 0).setObj(islet);
		this.map.getCase(this.map.getLength() - 2, this.map.getWidth() - 1)
				.setObj(islet);

	}

	public Map getMap() {
		return this.map;
	}

	public ArrayList<LabyObject> getObjects() {
		return this.objects;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void print() {
		for (Case[] cs : this.map.getCases()) {
			for (Case c : cs) {
				LabyObject obj = c.getObj();
				if (obj == null) {
					System.out.print('0');
				} else {
					if (obj instanceof Monster) {
						System.out.print('M');
					} else if (obj instanceof Wall) {
						System.out.print('#');
					} else if (obj instanceof Bonus) {
						System.out.print('$');
					}
				}

			}
			System.out.print('\n');
		}

		System.out.print('\n');
		System.out.print('\n');

	}

}
