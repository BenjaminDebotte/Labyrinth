/**
 * 
 */
package com.benjamindebotte.labyrinth.gameplay;

import com.benjamindebotte.labyrinth.containers.Labyrinth;
import com.benjamindebotte.labyrinth.entities.Bonus;
import com.benjamindebotte.labyrinth.entities.Monster;
import com.benjamindebotte.labyrinth.events.game.BonusRetrievedEvent;
import com.benjamindebotte.labyrinth.events.game.GameEvent;
import com.benjamindebotte.labyrinth.events.game.GameOverEvent;
import com.benjamindebotte.labyrinth.events.game.MonsterEncounterEvent;

/**
 * @author benjamindebotte
 *
 */
public class GameplayHandler {
	
	private int score;
	private Game currentGame;
	private int lives;
	
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
	public int getScore() {
		return score;
	}
		
	public GameplayHandler(Game g) {
		this.currentGame = g;
		this.score = 0;
		this.lives = 3;
	}
	
	public void processGameEvent(GameEvent e) {
		if(e instanceof MonsterEncounterEvent){
			if(--lives == 0){ /* On perd une vie */
				currentGame.addEvent(new GameOverEvent(e.getSender()));
				return;
			}
			/* On supprime le monstre. */
			currentGame.getLabyrinth().getObjects().remove(((MonsterEncounterEvent) e).getEncounteredMonster());
			
		} else if(e instanceof BonusRetrievedEvent) {
			processScoreEvent((BonusRetrievedEvent)e);
		}
	}
	
	private void processScoreEvent(BonusRetrievedEvent e) {
		score += ((Bonus)e.getSender()).getNbPoints();
	}

}
