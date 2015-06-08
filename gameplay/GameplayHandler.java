/**
 * 
 */
package com.benjamindebotte.labyrinth.gameplay;

import com.benjamindebotte.labyrinth.containers.Labyrinth;
import com.benjamindebotte.labyrinth.entities.Bonus;
import com.benjamindebotte.labyrinth.events.game.BonusRetrievedEvent;
import com.benjamindebotte.labyrinth.events.game.GameEvent;
import com.benjamindebotte.labyrinth.events.game.MonsterEncounterEvent;

/**
 * @author benjamindebotte
 *
 */
public class GameplayHandler {
	
	private int score;
	
	public int getScore() {
		return score;
	}
	
	private Labyrinth laby;
	/**
	 * 
	 */
	public GameplayHandler(Labyrinth laby) {
		this.laby = laby;
		this.score = 0;
	}
	
	public void processGameEvent(GameEvent e) {
		if(e instanceof MonsterEncounterEvent){
			System.out.println("MORT !");
		} else if(e instanceof BonusRetrievedEvent) {
			processScoreEvent((BonusRetrievedEvent)e);
		}
	}
	
	private void processScoreEvent(BonusRetrievedEvent e) {
		score += ((Bonus)e.getSender()).getNbPoints();
		System.out.println("New score : " + score);
	}

}
