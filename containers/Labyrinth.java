/**
 * 
 */
package com.benjamindebotte.labyrinth.containers;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

import com.benjamindebotte.labyrinth.entities.Bonus;
import com.benjamindebotte.labyrinth.entities.LabyObject;
import com.benjamindebotte.labyrinth.entities.Monster;
import com.benjamindebotte.labyrinth.entities.Player;
import com.benjamindebotte.labyrinth.entities.Wall;


/**
 * @author benjamindebotte
 *
 */
public class Labyrinth implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7093066492876133264L;
	private Map map;
	private ArrayList<LabyObject> objects;
	private Player player;
	
	private final int BONUS_SCORE_MAX = 1000;
	private final int BONUS_SCORE_MIN = 100;
	
	
	public Labyrinth(int length, int width) throws Exception {
		map = new Map(length,width);
		objects = new ArrayList<LabyObject>();
		generateLabyrinth();
	}
	
	public Map getMap() {
		return map;
	}
	public ArrayList<LabyObject> getObjects() {
		return objects;
	}
	
	
	private void addPlayer() {
		this.player = new Player(null);
		this.assignObject(this.getMap().getCase(1, 0), this.player);
	}
	
	private void generateLabyrinth() throws Exception {
			generateLevel();
			generateMonsters();
			generateItems();
			addPlayer();
	}
	
	private void generateLevel() throws Exception {
		generateLowLevel();
		generateIslets();
		generateWays();
		generateWalls();
	}
	
	
	/**
	 * Génération de la structure "basse" du labyrinthe : construction de murs seulement.
	 */
	private void generateLowLevel() {
		Case[][] mapCases = map.getCases();
		for(int line = 0; line < map.getLength(); line ++ )
	        for(int col = 0; col < map.getWidth(); col ++){
	            mapCases[line][col] = new Case(line, col);
	        }

	}
	
	private void generateIslets() {
		int value = 1;
		for(int line = 1; line < map.getLength(); line +=2)
			for(int width = 1; width < map.getWidth(); width += 2) {
				LabyIslet islet = new LabyIslet(value++);
				map.getCase(line, width).setObj(islet);
				objects.add(islet);
			}
		
	}
	
	private void generateWays() throws Exception {
		
		int MAX_VALUE = 1;
		
		/* Re-calcul du maximum d'îlot.
		 * TODO : Calculer sans boucler ? */
		for(int line = 1; line < map.getLength(); line +=2)
			for(int width = 1; width < map.getWidth(); width += 2)
				MAX_VALUE++;
		
	
		
		
		/* Départ aléatoire */
		LabyIslet islet = null;
		int randomValue = (int)(Math.random() * (MAX_VALUE - 1) + 1);
		
		for(LabyObject obj : objects) {
			if(obj instanceof LabyIslet){
				islet = (LabyIslet)obj;
				if(islet.getValue() == randomValue)
					break;
				islet = null;
			}		
		}
		
		if(islet == null)
			throw new Exception("Impossible de trouver un islet de valeur " + randomValue);
		
		
		/* Visite du Labyrinthe */
		int totalCells = MAX_VALUE; /* On ne compte pas les bordures */
		int visitedCells = 1;
		ArrayDeque<Case> previousCases = new ArrayDeque<Case>();
		Case currentCell = map.getCase(islet.getX(), islet.getY());
		
		previousCases.add(currentCell); /* Initialisation avec la première case */
		
		while(visitedCells < totalCells - 1) {
			Case lookOutCell = null, intermediaryCase = null;
			
			
			Case N = map.getNorth(currentCell), S = map.getSouth(currentCell),
					E = map.getEast(currentCell), W = map.getWest(currentCell);
			
				// Une case est considérée comme visitée si nulle ou si son ID est égal à celui de la case actuelle.
				Case NN = map.getNorth(N), SS = map.getSouth(S),
						EE = map.getEast(E), WW = map.getWest(W);
				
				boolean visitedN = (NN == null) || ((LabyIslet)NN.getObj()).getValue() == islet.getValue();
				boolean visitedE = (EE == null) || ((LabyIslet)EE.getObj()).getValue() == islet.getValue();
				boolean visitedS = (SS == null) || ((LabyIslet)SS.getObj()).getValue() == islet.getValue();
				boolean visitedW = (WW == null) || ((LabyIslet)WW.getObj()).getValue() == islet.getValue();
				
				if(visitedE && visitedN && visitedS && visitedW) {
					/* Toutes les cases ont été visitées : on retourne en arrière. */
						
					currentCell = previousCases.poll();
					if(currentCell == null) 
						throw new Exception("Plus de cellule. :(");
					
					continue; /* On repart avec une nouvelle cellule ! */
				}
			
			
			
			
			/* Selection aléatoire d'une case adjacente */
			int random = (int)(Math.random()*4);
			
			switch(random) {
			case 0: //N
				if(N == null) //OOB
					continue;
				if(N.getObj() != null) //Déjà traité.
					continue;
				intermediaryCase = N;
				lookOutCell = map.getNorth(intermediaryCase); //N+2
				if(lookOutCell != null)
					break;
			case 1: //W
				if(W == null) //OOB
					continue;
				if(W.getObj() != null) //Déjà traité.
					continue;
				intermediaryCase = W;
				lookOutCell = map.getWest(intermediaryCase); //W+2
				if(lookOutCell != null)
					break;
			case 2: //S
				if(S == null) //OOB
					continue;
				if(S.getObj() != null) //Déjà traité.
					continue;
				intermediaryCase = S;
				lookOutCell = map.getSouth(intermediaryCase); //S+2
				if(lookOutCell != null)
					break;
			case 3: //E
			case 4:
				if(E == null) //OOB
					continue;
				if(E.getObj() != null) //Déjà traité. Seuls les îlots ont des LabyObject actuellement.
					continue;
				intermediaryCase = E;
				lookOutCell = map.getEast(intermediaryCase); //E+2
				if(lookOutCell != null)
					break;
			}
			
			if(lookOutCell == null) {
				continue;
			}
			
			/* -- À cette étape, une case valide a été trouvée -- */
	
			if(((LabyIslet)lookOutCell.getObj()).getValue() == ((LabyIslet)currentCell.getObj()).getValue()){
				continue; /* On n'attaque pas une cellule ayant la même valeur. */
			}
				

			
			intermediaryCase.setObj(currentCell.getObj()); /* On assigne le même ID */
			lookOutCell.setObj(currentCell.getObj()); /* On assigne le même ID */
			
			/* On ajoute la case actuelle comme case précédente */
			previousCases.add(currentCell);
			visitedCells++;
			
			/* On recommence à partir de la nouvelle case */
			currentCell = lookOutCell;
			
			
		}
		
		/* Le labyrinthe est généré */
		
		map.getCase(1,0).setObj(islet);
		map.getCase(map.getLength() - 2, map.getWidth() - 1).setObj(islet);

	}
	
	
	private void generateWalls() {
		Case cases[][] = this.map.getCases();
		for(int i = 0; i < map.getLength(); i++){
			for(int j = 0; j < map.getWidth(); j++){
				if(cases[i][j].getObj() == null){
					Wall w = new Wall();
					assignObject(cases[i][j], w);
				}
				else {
					objects.remove(cases[i][j].getObj()); //Suppression des Islets
					cases[i][j].setObj(null);
				}

			}
		}
	}
	
	private void generateMonsters() {
		for(int i = 1; i < map.getLength() - 1; i++)
			for(int j = 1; j < map.getWidth() - 1; j++) {
				Case c = map.getCase(i, j);
				
				
				if(c.getObj() != null)
					continue;
				
				int nbWalls = ((map.getNorth(c).getObj() instanceof Wall) ? 1 : 0) + 
						((map.getSouth(c).getObj() instanceof Wall)  ? 1 : 0) + 
						((map.getEast(c).getObj()  instanceof Wall)  ? 1 : 0) + 
						((map.getWest(c).getObj()  instanceof Wall)  ? 1 : 0) ;
				
				if(nbWalls == 3 && (int)(Math.random() * 4) == 1){
					Monster m = new Monster();
					assignObject(c, m);
				}
				
				
			}
	}
	
	private void generateItems() {
		int NB_ITEMS = map.getLength()/3 + map.getWidth()/3;
		
		while(NB_ITEMS > 0) {
			Case c = map.getCase((int)(Math.random() * map.getLength()), (int)(Math.random() * map.getWidth()));
			if(c == null)
				continue;
			if(c.getObj() != null)
				continue;
			
			
			Bonus b = new Bonus((int)(Math.random()*BONUS_SCORE_MAX) + BONUS_SCORE_MIN);
			assignObject(c, b);
			NB_ITEMS--;
		}
	}
	
	public void print() {
		for(Case[] cs : this.map.getCases()){
			for(Case c : cs){
				LabyObject obj = c.getObj();
				if(obj == null)
					System.out.print('0');
				else {
					if(obj instanceof Monster)
						System.out.print('M');
					else if(obj instanceof Wall)
						System.out.print('#');
					else if(obj instanceof Bonus)
						System.out.print('$');
				}

			}
			System.out.print('\n');
		}
		
		System.out.print('\n');
		System.out.print('\n');
		
	}
	
	
	private boolean assignObject(Case c, LabyObject obj) {
		if(c.getObj() != null)
			return false;
		c.setObj(obj);
		if(!objects.contains(obj))
			this.objects.add(obj);
		
		return true;
	}
	
	
	
	public Player getPlayer() {
		return player;
	}




	/* Classe privée utilisée lors de la génération d'île */
	private class LabyIslet extends LabyObject {
		private int value;
		

		public int getValue() {
			return value;
		}

		public LabyIslet(int value) {
			this.value = value;
		}
	}


}
